package service.user.consumer;

import entity.*;
import mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import service.user.IdExistence;
import service.user.SpecialFunctions;
import service.user.ValidationInformation;
import util.FileUpload;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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

    /**
     * 显示用户创建的所有歌单或专辑
     *
     * @param type 1是歌单2是专辑
     */
    public List<SongList> showUserSongList(Integer type, HttpSession session) {
        User user = specialFunctions.getUser(session);
        SongList songList = new SongList();
        songList.setType(type);
        songList.setUserId(user.getId());
        // 查找到用户创建的歌单或专辑
        return songListMapper.selectListSongList(songList);
    }

    /**
     * 显示用户收藏的所有歌单或专辑
     *
     * @param type 1是歌单2是专辑
     */
    public List<SongListCollect> showUserCollectionSongList(Integer type, HttpSession session) {
        User user = specialFunctions.getUser(session);
        SongListCollect songListCollect = new SongListCollect();
        songListCollect.setType(type);
        songListCollect.setUserId(user.getId());
        // 查找到指定用户收藏的所有歌单或专辑
        return songListCollectMapper.selectListSongListCollect(songListCollect);
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
