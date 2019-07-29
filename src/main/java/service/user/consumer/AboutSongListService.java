package service.user.consumer;

import controller.user.consumer.AboutMusic;
import entity.*;
import mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import service.user.IdExistence;
import service.user.SpecialFunctions;
import service.user.ValidationInformation;
import util.FileUpload;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 创建编辑歌单，创建编辑专辑，收藏音乐，收藏MV，收藏歌单，收藏专辑，查看编辑历史播放记录，评论，点赞
 *
 * @author 5月15日 张易兴创建
 */
@Service(value = "AboutSongListService")
public class AboutSongListService {
    private static final Logger logger = LoggerFactory.getLogger(AboutSongListService.class);
    @Resource(name = "SongListCollectMapper")
    SongListCollectMapper songListCollectMapper;
    @Resource(name = "SpecialFunctions")
    SpecialFunctions specialFunctions;
    @Resource(name = "Existence")
    ExistenceService existenceService;
    @Resource(name = "SongListMapper")
    SongListMapper songListMapper;
    @Resource(name = "ValidationInformation")
    ValidationInformation validationInformation;
    @Resource(name = "IdExistence")
    IdExistence idExistence;
    @Resource(name = "FileUpload")
    FileUpload fileUpload;
    @Resource(name = "MusicSongListMapper")
    MusicSongListMapper musicSongListMapper;
    @Resource(name = "TransactionService")
    TransactionService transactionService;
    @Resource(name = "MusicMapper")
    MusicMapper musicMapper;
    @Resource(name = "UserMapper")
    UserMapper userMapper;
    @Resource(name = "PlayMapper")
    PlayMapper playMapper;
    @Resource(name = "AboutMusicService")
    AboutMusicService aboutMusicService;

    /**
     * 显示用户创建的所有歌单或专辑
     *
     * @param type 1是歌单2是专辑
     */
    public String showUserSongList(Integer type, HttpSession session, Model model) {
        User user = specialFunctions.getUser(session);
        SongList songList = new SongList();
        songList.setType(type);
        songList.setUserId(user.getId());
        if (type == 1) {
            model.addAttribute("page", "songList");
        } else {
            model.addAttribute("page", "album");
        }
        List<SongList> songLists = songListMapper.selectListSongList(songList);
        // 得到指定歌单或专辑包含的音乐数量
        for (SongList s : songLists) {
            s.setType(songListMusicList(s.getType(), s.getId()).size());
            s.setActivity(songListCount(1, s.getType(), s.getId()));
            s.setClassificationId(songListCount(2, s.getType(), s.getId()));
        }
        // 得到用户的关注粉丝量及用户信息
        specialFunctions.getUserInformation(user, model);
        // 查找到用户创建的歌单或专辑
        model.addAttribute("songList", songLists);
        return "userPage/userPage";
    }

    /**
     * 显示用户收藏的所有歌单或专辑
     *
     * @param type 1是歌单2是专辑
     */
    public String showUserCollectionSongList(Integer type, HttpSession session, Model model) {
        User user = specialFunctions.getUser(session);
        SongListCollect songListCollect = new SongListCollect();
        songListCollect.setType(type);
        songListCollect.setUserId(user.getId());
        // 得到用户的关注粉丝量及用户信息
        specialFunctions.getUserInformation(user, model);
        // 查找到指定用户收藏的所有歌单或专辑
        model.addAttribute("page", "likePage");
        if (type == 1) {
            model.addAttribute("pages", "likeSongList");
        } else {
            model.addAttribute("pages", "likeAlbum");
        }
        List<SongListCollect> listCollects = songListCollectMapper.selectListSongListCollect(songListCollect);
        System.out.println(listCollects);
        System.out.println("==============================");
        List<Integer> songListId = new ArrayList<>();
        for (SongListCollect s : listCollects) {
            songListId.add(s.getMusicId());
        }
        List<SongList> songLists = songListMapper.listIdSelectListSongList(songListId);
        for (SongList s : songLists) {
            s.setType(songListMusicList(s.getType(), s.getId()).size());
            s.setActivity(songListCount(1, s.getType(), s.getId()));
            s.setClassificationId(songListCount(2, s.getType(), s.getId()));
        }
        model.addAttribute("songList", songLists);
        return "userPage/userPage";
    }


    /**
     * 得到id 查找指定的专辑或歌单
     * 获得作者名字，歌单/专辑的收藏播放量   音乐名
     */
    public String showMusicList(String id, Model model) {
        System.out.println("开始执行" + id);
        // 判断id是否合法
        if (!validationInformation.isInt(id)) {
            return "index";
        }
        int songListId = Integer.valueOf(id);
        // 找到指定歌单或专辑
        SongList songList = idExistence.isSongListId(songListId);
        logger.debug("查找到的指定歌单/专辑信息" + songList);
        if (songList == null) {
            return "index";
        }
        // 表示是歌单/专辑
        if (songList.getType() == 1 || songList.getType() == 2) {
            // 找到该歌单/专辑的创建者
            User userInformation = idExistence.isUserId(songList.getUserId());
            //得到所有的音乐
            List<Music> musicList = songListMusicList(songList.getType(), songList.getId());
            logger.debug("查找到的指定歌单/专辑的所有音乐" + musicList);
            // 查找每个音乐属于的专辑和用户名
            logger.debug("4");
            User temporaryUser;
            SongList temporarySongList;
            for (Music music : musicList) {
                // 得到每个音乐的用户信息
                logger.debug("music" + music);
                temporaryUser = idExistence.isUserId(music.getSingerId());
                // 得到每个音乐的专辑信息
                temporarySongList = idExistence.isSongListId(music.getAlbumId());
                logger.debug("temporaryUser" + temporaryUser);
                // 音乐ID 音乐名字 歌手Id 歌手名字（音乐路径） 专辑Id 专辑名字(单词路径)
                music.setPath(temporaryUser.getName());
                logger.debug("temporarySongList" + temporarySongList);
                music.setLyricPath(temporarySongList.getName());
            }
            // 上传歌单或专辑信息
            model.addAttribute("songList", songList);
            // 上传歌单或专辑创建者的信息
            model.addAttribute("user", userInformation);
            // 上传播放量
            model.addAttribute("plays", songListCount(2, songList.getType(), songList.getId()));
            // 上传收藏量
            model.addAttribute("collects", songListCount(1, songList.getType(), songList.getId()));
            // 上传所有音乐
            model.addAttribute("musicList", musicList);
            logger.debug("歌单" + songList + "所有音乐" + musicList);
        } else {
            return "index";
        }
        return "userMusic/musicList";
    }

    /**
     * 查找指定歌单或专辑的收藏量或播放量
     *
     * @param type         1表示收藏2表示播放
     * @param songListType 1表示歌单 2表示专辑
     * @param songListId   歌单或专辑的ID
     */
    public int songListCount(int type, int songListType, int songListId) {
        if (type == 1) {
            // 查找收藏量
            SongListCollect songListCollect = new SongListCollect();
            songListCollect.setType(songListType);
            songListCollect.setMusicId(songListId);
            List<SongListCollect> songListCollectList = songListCollectMapper.selectListSongListCollect(songListCollect);
            return songListCollectList.size();
        } else if (type == 2) {
            Play play = new Play();
            play.setAlbumId(songListId);
            return playMapper.selectPlays(play);
        }
        return 0;
    }


    /**
     * 查找指定专辑或歌单中的所有的音乐
     */
    public List<Music> songListMusicList(int songListType, int songListId) {
        // 查找包含的所有音乐
        MusicSongList musicSongList = new MusicSongList();
        musicSongList.setBelongId(songListId);
        musicSongList.setType(songListType);
        List<MusicSongList> musics = musicSongListMapper.selectListMusicSongList(musicSongList);
        logger.debug("该歌单/专辑中包含的音乐" + musics);
        List<Integer> musicId = new ArrayList<>();
        // 得到该歌单或专辑包含的所有音乐
        logger.debug("3");
        for (MusicSongList m : musics) {
            musicId.add(m.getMusicId());
        }
        logger.debug("该歌单/专辑中包含的音乐Id" + musicId);
        return musicMapper.listIdSelectListMusic(musicId);
    }

    /**
     * 显示指定歌单或专辑的音乐播放页面
     *
     * @param songListId 歌单或专辑的iD
     */
    public String playMusicSongList(String songListId, String musicId, Model model, HttpSession session) {
        // 先判断音乐id和专辑id是否合法
        if (validationInformation.isInt(songListId)) {
            // 查找指定专辑或歌单
            SongList songList = idExistence.isSongListId(Integer.valueOf(songListId));
            // 音乐和专辑是否存在
            if (songList != null) {
                // 查找指定专辑中的所有音乐
                MusicSongList musicSongList = new MusicSongList();
                musicSongList.setBelongId(Integer.valueOf(songListId));
                // 查找到专辑中的所有信息
                List<MusicSongList> musicSongLists = musicSongListMapper.selectListMusicSongList(musicSongList);
                logger.debug("歌单/专辑中的所有音乐信息"+musicSongLists);
                // 用于存储专辑中所有音乐的id
                List<Integer> musicIdList = new ArrayList<>();
                for (MusicSongList m : musicSongLists) {
                    musicIdList.add(m.getMusicId());
                }
                // 查找到所有的音乐
                System.out.println("音乐ID" + musicIdList);
                List<Music> musicList = musicMapper.listIdSelectListMusic(musicIdList);
                // 传给前端
                // 音乐列表数据
                model.addAttribute("musicList", musicList);
                // 当前歌单数据
                model.addAttribute("songList", songList);
                // 即将播放的音乐 musicId
                model.addAttribute("musicId", musicId);
                logger.debug("歌单" + songList + "即将播放的音乐" + musicId + "所有音乐" + musicList);
            } else {
                return "index";
            }
        } else {
            return "index";
        }
        return "userMusic/musicPlayer";
    }

    /**
     * 创建歌单或专辑
     *
     * @param songList 获取传来的歌单信息
     *                 name           获取歌单或专辑的标题
     *                 picture        获取歌单或专辑的封面的图片路径
     *                 introduction   获取歌单或专辑的介绍
     *                 classification 获取分类
     *                 type           获取类型1是歌单2是专辑
     * @param session  获取当前会话
     */
    public State createMusicSongList(@RequestBody SongList songList, String languages, String region, String gender, String type, HttpServletRequest request, HttpSession session) throws DataBaseException {
        User user = specialFunctions.getUser(session);
        State state = isSongList(songList, languages, region, gender, type, request);
        if (state.getState() == 1) {
            // 存储创建者的id
            songList.setUserId(user.getId());
            // 设置何时创建
            songList.setDate(new Date());
            // 存入数据库
            logger.debug("歌单/专辑" + songList + "存入数据库");
            if (songListMapper.insertSongList(songList) < 1) {
                // 如果失败是数据库错误
                logger.error("歌单或专辑：" + songList + "添加歌单或专辑，数据库出错");
                // 添加失败删除图片
                fileUpload.deleteFile(songList.getPicture());
                throw new DataBaseException("歌单或专辑：" + songList + "添加歌单或专辑，数据库出错");
            }
            logger.info("歌单或专辑：" + songList + "创建成功");
        }
        return state;
    }

    /**
     * 编辑歌单或专辑
     *
     * @param songList 获取传来的歌单信息
     *                 name           获取歌单或专辑的标题
     *                 picture        获取歌单或专辑的封面的图片路径
     *                 introduction   获取歌单或专辑的介绍
     *                 classification 获取分类
     *                 type           获取类型1是歌单2是专辑
     */
    public State editMusicSongList(SongList songList, String languages, String region, String gender, String type, HttpServletRequest request) throws DataBaseException {
        State state = isSongList(songList, languages, region, gender, type, request);
        if (state.getState() == 1) {
            // 查找原歌单或专辑的信息
            SongList originalSongList = idExistence.isSongListId(songList.getId());
            if (originalSongList != null) {
                // 全部合法则更新数据库中的信息
                modifySongListInformation(songList);
                // 修改成功删除原来的照片
                fileUpload.deleteFile(originalSongList.getPicture());
            } else {
                state.setState(0);
                state.setInformation("指定专辑/歌单不存在");
            }
        }
        logger.info("歌单或专辑：" + songList + "创建成功");
        return state;
    }


    /**
     * 删除歌单或专辑，ajax
     */
    public State deleteMusicSongList(Integer id) throws DataBaseException {
        State state = new State();
        SongList songList = idExistence.isSongListId(id);
        if (songList != null) {
            String originalPath = songList.getPicture();
            if (songListMapper.deleteSongList(id) < 1) {
                // 如果失败是数据库错误
                logger.error("删除歌单或专辑时，数据库出错");
                throw new DataBaseException("删除歌单或专辑时，数据库出错");
            }
            state.setState(1);
            // 数据库中删除成功，删除硬盘上的数据
            fileUpload.deleteFile(originalPath);
        } else {
            state.setInformation("歌单/专辑ID不存在");
        }
        return state;
    }

    /**
     * 收藏或取消收藏歌单或专辑
     *
     * @param id      获取收藏歌单或专辑的id
     * @param type    获取类型1是歌单2是专辑
     * @param session 获取当前会话
     */
    public State collectionSongList(Integer id, Integer type, HttpSession session) throws DataBaseException {
        //得到会话上的用户
        User user = specialFunctions.getUser(session);
        SongListCollect songListCollect = existenceService.isUserCollectionSongList(user.getId(), id, type);
        if (songListCollect != null) {
            // 如果不为null表示已经收藏则需要用取消收藏
            if (songListCollectMapper.deleteSongListCollect(songListCollect.getId()) < 1) {
                // 如果失败是数据库错误
                logger.error("邮箱：" + user.getMailbox() + "删除收藏的歌单或专辑时，数据库出错");
                throw new DataBaseException("邮箱：" + user.getMailbox() + "删除收藏的歌单或专辑时，数据库出错");
            }
        } else {
            SongList songList = idExistence.isSongListId(id);
            // 为null表示没有收藏，需要添加收藏
            songListCollect = new SongListCollect();
            songListCollect.setMusicId(id);
            songListCollect.setUserId(user.getId());
            songListCollect.setClassificationId(songList.getClassificationId());
            songListCollect.setUserCollectId(songList.getUserId());
            songListCollect.setType(type);
            songListCollect.setDate(new Date());
            if (songListCollectMapper.insertSongListCollect(songListCollect) < 1) {
                // 如果失败是数据库错误
                logger.error("邮箱：" + user.getMailbox() + "收藏歌单或专辑时，数据库出错");
                throw new DataBaseException("邮箱：" + user.getMailbox() + "收藏歌单或专辑时，数据库出错");
            }
        }
        return new State(1);
    }

    /**
     * 修改数据库中的歌单或专辑信息，失败抛异常
     */
    private void modifySongListInformation(SongList songList) throws DataBaseException {
        if (songListMapper.updateSongList(songList) < 1) {
            // 如果失败是数据库错误
            logger.error("歌单或专辑：" + songList + "修改歌单或专辑信息时，数据库出错");
            // 修改失败删除刚上传的图片
            fileUpload.deleteFile(songList.getPicture());
            throw new DataBaseException("歌单或专辑：" + songList + "修改歌单或专辑信息时，数据库出错");
        }
    }

    /**
     * 得到用户创建的所有歌单或专辑，没有返回null
     *
     * @param userId 用户的id
     * @param type   1是歌单2是专辑
     */
    public List<SongList> userSongList(int userId, int type) {
        SongList songList = new SongList();
        songList.setUserId(userId);
        songList.setType(type);
        List<SongList> list = songListMapper.selectListSongList(songList);
        if (list.size() == 0) {
            return null;
        }
        return list;
    }

    /**
     * 将指定音乐添加到专辑或歌单中
     *
     * @param musicSongList 获取需要添加到指定专辑或歌单中的音乐
     *                      所需参数：
     *                      belongId 专辑或歌单的id
     *                      type 1是歌单2是专辑
     *                      musicId 音乐的id
     */
    public State addMusicSongList(MusicSongList musicSongList, HttpSession session) throws DataBaseException {
        // 得到音乐的id
        int musicId = musicSongList.getMusicId();
        // 得到音乐信息
        Music music = idExistence.isMusicId(musicId);
        musicSongList.setHave(1);
        // 为歌单时候，再判断
        if (musicSongList.getType() == 1) {
            // 判断用户有没有购买
            if (transactionService.isPurchaseMusic(musicId, 1, specialFunctions.getUser(session)) == null) {
                musicSongList.setHave(0);
            }
        }
        // 音乐的歌手的id
        musicSongList.setSingerId(music.getSingerId());
        // 音乐的分类的id
        musicSongList.setClassificationId(music.getClassificationId());
        if (musicSongListMapper.insertMusicSongList(musicSongList) < 1) {
            // 如果失败是数据库错误
            logger.error("歌单或专辑：" + musicSongList + "添加歌单或专辑信息时，数据库出错");
            throw new DataBaseException("歌单或专辑：" + musicSongList + "添加歌单或专辑信息时，数据库出错");
        }
        return new State(1);
    }


    /**
     * 判断歌单或专辑的各个信息是否合法
     */
    private State isSongList(SongList songList, String languages, String region, String gender, String type, HttpServletRequest request) {
        State state = new State();
        //歌单或专辑的标题是否符合要求
        if (validationInformation.isName(songList.getName())) {
            //歌单或专辑的介绍是否符合要求
            if (0 < songList.getIntroduction().length() && songList.getIntroduction().length() <= 300) {
                // 判断分类是否存在
                List<Integer> list = idExistence.getClassificationId(languages, region, gender, type);
                if (list != null && list.size() == 1) {
                    // 将上传的图片存入硬盘上去
                    String path = fileUpload.songList(fileUpload.getMultipartFile(request, ""));
                    if (path != null) {
                        // 存储分类
                        songList.setClassificationId(list.get(0));
                        // 存储图片路径
                        songList.setPicture(path);
                        state.setState(1);
                    } else {
                        logger.debug("歌单或专辑：" + songList + "歌单或专辑的图片不合法");
                        state.setInformation("歌单或专辑的图片不合法");
                    }
                } else {
                    logger.debug("歌单或专辑：" + songList + "歌单或专辑的分类不存在");
                    state.setInformation("歌单或专辑的分类不存在");
                }
            } else {
                logger.debug("歌单或专辑：" + songList + "歌单或专辑的标题格式有误");
                state.setInformation("标题格式有误");
            }
        } else {
            logger.debug("歌单或专辑：" + songList + "歌单或专辑的标题格式有误");
            state.setInformation("标题格式有误");
        }
        return state;
    }
}
