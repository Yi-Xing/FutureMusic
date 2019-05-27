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
    Existence existence;
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

    /**
     * 显示用户创建的所有歌单或专辑
     * @param  type 1是歌单2是专辑
     */
    public String showUserSongList(Integer type,HttpSession session){
        User user = specialFunctions.getUser(session);
        SongList songList=new SongList();
        songList.setType(type);
        songList.setUserId(user.getId());
        // 查找到用户创建的歌单或专辑
        List<SongList> list=songListMapper.selectListSongList(songList);
        return null;
    }
    /**
     * 显示用户收藏的所有歌单或专辑
     * @param  type 1是歌单2是专辑
     */
    public String showUserCollectionSongList(Integer type,HttpSession session){
        User user = specialFunctions.getUser(session);
        SongListCollect songListCollect=new SongListCollect();
        songListCollect.setType(type);
        songListCollect.setUserId(user.getId());
        // 查找到指定用户收藏的所有歌单或专辑
        List<SongListCollect> list=songListCollectMapper.selectListSongListCollect(songListCollect);
        return null;
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
    public State createMusicSongList(@RequestBody SongList songList, HttpServletRequest request, HttpSession session) throws IOException, DataBaseException {
        User user = specialFunctions.getUser(session);
        State state = new State();
        //歌单或专辑的标题是否符合要求
        if (validationInformation.isName(songList.getName())) {
            //歌单或专辑的介绍是否符合要求
            if (0 < songList.getIntroduction().length() && songList.getIntroduction().length() >= 300) {
                // 判断分类是否存在
                if (idExistence.isClassificationId(songList.getClassificationId()) != null) {
                    // 将上传的图片存入硬盘上去
                    String path = fileUpload.songList(request);
                    // 存储图片路径
                    songList.setPicture(path);
                    // 存储创建者的id
                    songList.setUserId(user.getId());
                    // 设置何时创建
                    songList.setDate(new Date());
                    // 存入数据库
                    if (songListMapper.insertSongList(songList) < 1) {
                        // 如果失败是数据库错误
                        logger.error("歌单或专辑：" + songList + "添加歌单或专辑，数据库出错");
                        throw new DataBaseException("歌单或专辑：" + songList + "添加歌单或专辑，数据库出错");
                    }
                    logger.info("歌单或专辑：" + songList + "创建成功");
                    state.setState(1);
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
    public State editMusicSongList(SongList songList) throws DataBaseException {
        State state = new State();
        //歌单或专辑的标题是否符合要求
        if (validationInformation.isName(songList.getName())) {
            //歌单或专辑的介绍是否符合要求
            if (0 < songList.getIntroduction().length() && songList.getIntroduction().length() >= 300) {
                // 判断分类是否存在
                if (idExistence.isClassificationId(songList.getClassificationId()) != null) {
                    // 全部合法则更新数据库中的信息
                    modifySongListInformation(songList);
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
        return new State();
    }

    /**
     * 编辑歌单或专辑的封面图片
     *
     * @param id 需要更改的歌单或专辑的id
     */
    public State editMusicSongListPicture(Integer id, HttpServletRequest request) throws IOException, DataBaseException {
        // 得到图片的原路径
        String originalPath = idExistence.isSongListId(id).getPicture();
        // 用来存储最新的图片路径
        String path;
        SongList songList = new SongList();
        songList.setId(id);
        // 将上传的图片存入硬盘上去，并得到路径
        path = fileUpload.songList(request);
        // 存储图片路径
        songList.setPicture(path);
        // 更新数据库中的数据
        try {
            // 如果更新数据库中的数据出现了异常，则需要删除刚刚存储在硬盘上的文件
            modifySongListInformation(songList);
            // 先捕获异常，进行文件删除，然后再抛异常
        } catch (DataBaseException e) {
            fileUpload.deleteFile(path);
            throw new DataBaseException("歌单或专辑：" + songList + "修改歌单或专辑信息时，数据库出错");
        }
        // 更新成功则删除原来的图片
        fileUpload.deleteFile(originalPath);
        return new State(1);
    }

    /**
     * 删除歌单或专辑，ajax
     */
    public State deleteMusicSongList(Integer id) throws DataBaseException {
        String originalPath = idExistence.isSongListId(id).getPicture();
        if (songListMapper.deleteSongList(id) < 1) {
            // 如果失败是数据库错误
            logger.error("删除歌单或专辑时，数据库出错");
            throw new DataBaseException("删除歌单或专辑时，数据库出错");
        }
        // 数据库中删除成功，删除硬盘上的数据
        fileUpload.deleteFile(originalPath);
        return new State(1);
    }

    /**
     * 收藏或取消收藏歌单或专辑
     *
     * @param id               获取收藏歌单或专辑的id
     * @param type             获取类型1是歌单2是专辑
     * @param classificationId 分类的id
     * @param userCollectId    歌单创建者的用户id
     * @param session          获取当前会话
     */
    public State collectionSongList(Integer id, Integer type, Integer classificationId, Integer userCollectId, HttpSession session) throws DataBaseException {
        //得到会话上的用户
        User user = specialFunctions.getUser(session);
        SongListCollect songListCollect = existence.isUserCollectionSongList(user.getId(), id, type);
        if (songListCollect != null) {
            // 如果不为null表示已经收藏则需要用取消收藏
            if (songListCollectMapper.deleteSongListCollect(songListCollect.getId()) < 1) {
                // 如果失败是数据库错误
                logger.error("邮箱：" + user.getMailbox() + "删除收藏的歌单或专辑时，数据库出错");
                throw new DataBaseException("邮箱：" + user.getMailbox() + "删除收藏的歌单或专辑时，数据库出错");
            }
        } else {
            // 为null表示没有收藏，需要添加收藏
            songListCollect = new SongListCollect();
            songListCollect.setMusicId(id);
            songListCollect.setUserId(user.getId());
            songListCollect.setClassificationId(classificationId);
            songListCollect.setUserCollectId(userCollectId);
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
            throw new DataBaseException("歌单或专辑：" + songList + "修改歌单或专辑信息时，数据库出错");
        }
    }

    /**
     * 得到用户创建的所有歌单或专辑，没有返回null
     * @param userId 用户的id
     * @param  type 1是歌单2是专辑
     */
    public List<SongList> userSongList(int userId,int type){
        SongList songList=new SongList();
        songList.setUserId(userId);
        songList.setType(type);
        List<SongList> list=songListMapper.selectListSongList(songList);
        if(list.size()==0){
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
     *                        type 1是歌单2是专辑
     *                      musicId 音乐的id
     */
    public State addMusicSongList(MusicSongList musicSongList) throws DataBaseException {
        // 判断用户有没有购买
        // 查找音乐的歌手的id
        // 查找音乐的分类的id
        if (musicSongListMapper.insertMusicSongList(musicSongList) < 1) {
            // 如果失败是数据库错误
            logger.error("歌单或专辑：" + musicSongList + "添加歌单或专辑信息时，数据库出错");
            throw new DataBaseException("歌单或专辑：" + musicSongList + "添加歌单或专辑信息时，数据库出错");
        }
        return null;
    }
}
