package service.user.consumer;

import entity.*;
import service.user.SpecialFunctions;
import util.exception.DataBaseException;
import mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * 创建编辑歌单，创建编辑专辑，收藏音乐，收藏MV，收藏歌单，收藏专辑，查看编辑历史播放记录，评论，点赞
 *
 * @author 5月15日 张易兴创建
 */
@Service(value = "AboutMusicService")
public class AboutMusicService {
    private static final Logger logger = LoggerFactory.getLogger(AboutMusicService.class);
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
    /**
     * 收藏歌单或专辑
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
        SongListCollect songListCollect = new SongListCollect();
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
        State state = new State();
        state.setState(1);
        return state;
    }

    /**
     * 收藏音乐或MV
     *
     * @param id               获取收藏音乐或MV的id
     * @param type             获取类型1是音乐2是MV
     * @param have             表示是音乐或MV是否已经购买，1表示购买，2表示没购买
     * @param singerId         表示是歌手的id
     * @param albumId          表示是专辑的id
     * @param classificationId 表示是音乐或MV的分类的id
     * @param session          获取当前会话
     */
    public State collectionMusic(Integer id, Integer type, Integer have, Integer singerId, Integer albumId, Integer classificationId, HttpSession session) throws DataBaseException {
        //得到会话上的用户
        User user = specialFunctions.getUser(session);
        MusicCollect musicCollect = new MusicCollect();
        musicCollect.setUserId(user.getId());
        musicCollect.setMusicId(id);
        musicCollect.setHave(have);
        musicCollect.setType(type);
        musicCollect.setSingerId(singerId);
        musicCollect.setAlbumId(albumId);
        musicCollect.setClassificationId(classificationId);
        musicCollect.setDate(new Date());
        if (musicCollectMapper.insertMusicCollect(musicCollect) < 1) {
            // 如果失败是数据库错误
            logger.error("邮箱：" + user.getMailbox() + "收藏音乐或MV时，数据库出错");
            throw new DataBaseException("邮箱：" + user.getMailbox() + "收藏音乐或MV时，数据库出错");
        }
        State state = new State();
        state.setState(1);
        return state;
    }

    /**
     * 音乐或MV的历史播放记录
     *
     * @param id               获取收藏音乐或MV的id
     * @param type             获取类型1是音乐2是MV
     * @param singerId         表示是歌手的id
     * @param albumId          表示是专辑的id
     * @param classificationId 表示是音乐或MV的分类的id
     * @param session          获取当前会话
     */
    public State musicPlay(Integer id, Integer type, Integer singerId, Integer albumId, Integer classificationId, HttpSession session) throws DataBaseException {
        //得到会话上的用户
        User user = specialFunctions.getUser(session);
        Play play = new Play();
        play.setUserId(user.getId());
        play.setMusicId(id);
        play.setType(type);
        play.setSingerId(singerId);
        play.setAlbumId(albumId);
        play.setClassificationId(classificationId);
        play.setDate(new Date());
        // 添加该音乐或MV的播放次数
        // 先判断该用户是否听过该音乐或MV，如果听过只需要更新时间
        if (playMapper.insertPlay(play) < 1) {
            // 如果失败是数据库错误
            logger.debug("邮箱：" + user.getMailbox() + "收藏音乐或MV时，数据库出错");
            throw new DataBaseException("邮箱：" + user.getMailbox() + "收藏音乐或MV时，数据库出错");
        }
        State state = new State();
        state.setState(1);
        return state;
    }


    /**
     * 音乐或MV或专辑的评论
     *
     * @param id      获取音乐或MV或专辑的id
     * @param type    获取类型1是音乐，2是MV，3是专辑
     * @param content 获取评论的内容
     * @param reply   获取回复哪个评论的id, 0为独立评论
     * @param session 获取当前会话
     */
    public State comment(Integer id, Integer type, String content, Integer reply, HttpSession session) throws DataBaseException {
        State state = new State();
        if (content.length() != 0) {
            if (content.length() <= 200) {
                //得到会话上的用户
                User user = specialFunctions.getUser(session);
                Comment comment = new Comment();
                comment.setMusicId(id);
                comment.setType(type);
                comment.setUserId(user.getId());
                comment.setContent(content);
                comment.setDate(new Date());
                comment.setReply(reply);
                if (commentMapper.insertComment(comment) < 1) {
                    // 如果失败是数据库错误
                    logger.error("邮箱：" + user.getMailbox() + "评论时，数据库出错");
                    throw new DataBaseException("邮箱：" + user.getMailbox() + "评论时，数据库出错");
                }
                state.setState(1);
            } else {
                state.setInformation("内容过长");
            }
        } else {
            state.setInformation("内容不能为空");
        }
        return state;
    }

    /**
     * 评论点赞或取消点赞（更改用户的点赞记录，更改评论被点赞的次数），事务处理
     *
     * @param id      评论的id
     * @param session 获取当前会话
     */
    public State commentFabulous(String id, HttpSession session) throws DataBaseException {
        //得到会话上的用户
        User user = specialFunctions.getUser(session);
        // 得到用户给哪些评论点过赞
        StringBuilder fabulousString = new StringBuilder(user.getFabulous());
        String[] fabulous = fabulousString.toString().split("#");
        boolean isFabulous = true;
        // 判断用户是否给该评论点过赞，如果点过则取消
        for (String s : fabulous) {
            if (s.equals(id)) {
                // 该评论点过赞，更改用户的点赞记录，并减少该评论点赞次数
                // 初始化
                fabulousString = new StringBuilder();
                // 先添加第一个,因为先添加第一个，所以变量从第二个开始循环
                int index = 1;
                if (!fabulous[0].equals(id)) {
                    fabulousString.append(fabulous[0]);
                } else {
                    //第一个是删除的id，第二个成为第一个（开头不需要加#）
                    fabulousString.append(fabulous[1]);
                    // 所以变量从第三个开始循环
                    index = 2;
                }
                for (; index < fabulous.length; index++) {
                    if (!fabulous[index].equals(id)) {
                        // 进行字符串拼接
                        fabulousString.append("#").append(fabulous[index]);
                    }
                }
                user.setFabulous(fabulousString.toString());
                isFabulous = false;
                break;
            }
        }
        Comment comment = new Comment();
        comment.setId(Integer.parseInt(id));
        List<Comment> commentList = commentMapper.selectListComment(comment);
        // 得带查找到的评论
        comment = commentList.get(0);
        if (isFabulous) {
            // 表示没有给该评论点过赞, 则将该评论的id加到后面
            user.setFabulous(user.getFabulous() + "#" + id);
            // 增加评论的点赞次数
            comment.setFabulous(comment.getFabulous() + 1);
        } else {
            // 减少评论的点赞次数
            comment.setFabulous(comment.getFabulous() - 1);
        }
        //事务处理
        // 更新评论的点赞次数
        if (commentMapper.updateComment(comment) < 1) {
            // 如果失败是数据库错误
            logger.error("邮箱：" + user.getMailbox() + "修改用户点赞记录时，数据库出错");
            throw new DataBaseException("邮箱：" + user.getMailbox() + "修改用户点赞记录时，数据库出错");
        }
        // 更新用户的数据库
        if (userMapper.updateUser(user) < 1) {
            // 如果失败是数据库错误
            logger.error("邮箱：" + user.getMailbox() + "修改用户点赞记录时，数据库出错");
            throw new DataBaseException("邮箱：" + user.getMailbox() + "修改用户点赞记录时，数据库出错");
        }
        State state = new State();
        state.setState(1);
        return state;
    }

}