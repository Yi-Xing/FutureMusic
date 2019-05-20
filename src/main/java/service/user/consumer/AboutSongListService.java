package service.user.consumer;

import entity.*;
import mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.SpecialFunctions;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

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
    @Resource(name = "MusicCollectMapper")
    MusicCollectMapper musicCollectMapper;
    @Resource(name = "CommentMapper")
    CommentMapper commentMapper;
    @Resource(name = "PlayMapper")
    PlayMapper playMapper;
    @Resource(name = "UserMapper")
    UserMapper userMapper;
    @Resource(name = "SpecialFunctions")
    SpecialFunctions specialFunctions;
    @Resource(name = "Existence")
    Existence existence;
    @Resource(name = "SongListMapper")
    SongListMapper songListMapper;

    /**
     * 删除歌单或专辑，ajax
     */
    public State deleteMusicSongList(Integer id) throws DataBaseException {
        if (songListMapper.deleteSongList(id) < 1) {
            // 如果失败是数据库错误
            logger.error("删除歌单或专辑时，数据库出错");
            throw new DataBaseException("删除歌单或专辑时，数据库出错");
        }
        State state = new State();
        state.setState(1);
        return state;
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
        State state = new State();
        state.setState(1);
        return state;
    }
}
