package service.user.consumer;

import entity.*;
import org.springframework.transaction.annotation.Transactional;
import service.user.IdExistence;
import service.user.SpecialFunctions;
import util.exception.DataBaseException;
import mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 收藏音乐，收藏MV，添加历史播放记录，评论，点赞，用户播放过的音乐
 *
 * @author 5月15日 张易兴创建
 */
@Service(value = "AboutMusicService")
public class AboutMusicService {
    private static final Logger logger = LoggerFactory.getLogger(AboutMusicService.class);
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
    ExistenceService existenceService;
    @Resource(name = "MusicMapper")
    MusicMapper musicMapper;
    @Resource(name = "MusicVideoMapper")
    MusicVideoMapper musicVideoMapper;
    @Resource(name = "OrderMapper")
    OrderMapper orderMapper;
    @Resource(name = "TransactionService")
    TransactionService transactionService;
    @Resource(name = "IdExistence")
    IdExistence idExistence;

    /**
     * 添加显示用户收藏的所有音乐，显示用户收藏的所有MV
     *
     * @param type 1表示查找音乐收藏 2表示查找MV收藏
     */
    public List<MusicCollect> showUserCollectionMusic(Integer type, HttpSession session) {
        //得到会话上的用户
        User user = specialFunctions.getUser(session);
        MusicCollect musicCollect = new MusicCollect();
        musicCollect.setType(type);
        musicCollect.setUserId(user.getId());
        return musicCollectMapper.selectListMusicCollect(musicCollect);
    }

    /**
     * 显示用户购买过的音乐，显示用户购买过的MV
     *
     * @param type 1表示查找音乐购买 2表示查找MV购买
     */
    public List showUserPurchaseMusic(Integer type, HttpSession session) {
        //得到会话上的用户
        User user = specialFunctions.getUser(session);
        Order order = new Order();
        order.setType(type);
        order.setUserId(user.getId());
        List<Order> list = orderMapper.selectListOrder(order);
        if (list.size() != 0) {
            List<Integer> idList = new ArrayList<>();
            // 得到所有音乐或MV的id
            for (Order o : list) {
                idList.add(o.getMusicId());
            }
            if (type == 1) {
                // 查找所有的音乐
                return musicMapper.listIdSelectListMusic(idList);
            } else {
                // 查找所有的MV
                return musicVideoMapper.listIdSelectListMusicVideo(idList);
            }
        }
        return list;
    }

    /**
     * 收藏和取消收藏音乐或MV,
     *
     * @param musicCollectInformation 封装音乐或MV收藏的信息
     *                                musicId               获取收藏音乐或MV的id
     *                                type             获取类型1是音乐2是MV
     *                                have             表示是音乐或MV是否已经购买，1表示购买，2表示没购买(不用传该数据)  判断指定用户没有有购买指定音乐或MV
     *                                singerId         表示是歌手的id
     *                                albumId          表示是专辑的id
     *                                classificationId 表示是音乐或MV的分类的id
     *                                session          获取当前会话
     */
    public State collectionMusic(MusicCollect musicCollectInformation, HttpSession session) throws DataBaseException {
        //得到会话上的用户
        User user = specialFunctions.getUser(session);
        MusicCollect musicCollect = existenceService.isUserCollectionMusic(user.getId(), musicCollectInformation.getMusicId(), musicCollectInformation.getType());
        if (musicCollect != null) {
            // 如果不为null表示已经收藏则需要用取消收藏
            if (musicCollectMapper.deleteMusicCollect(musicCollect.getId()) < 1) {
                // 如果失败是数据库错误
                logger.error("邮箱：" + user.getMailbox() + "删除收藏的音乐或MV时，数据库出错");
                throw new DataBaseException("邮箱：" + user.getMailbox() + "删除收藏的音乐或MV时，数据库出错");
            }
        } else {
            // 为null表示没有收藏，需要添加收藏
            int have = 0;
            // 判断用户是否购买了该MV或音乐
            Order order = transactionService.isPurchaseMusic(musicCollectInformation.getMusicId(), musicCollectInformation.getType(), user);
            if (order != null) {
                have = 1;
            }
            // 添加收藏
            collectionAndPlay(have, musicCollectInformation, null, user);
        }
        return new State(1);
    }

    /**
     * 音乐或MV的历史播放记录
     *
     * @param playInformation 封装评论的信息
     *                        musicId               获取收藏音乐或MV的id
     *                        type             获取类型1是音乐2是MV
     *                        singerId         表示是歌手的id
     *                        albumId          表示是专辑的id
     *                        classificationId 表示是音乐或MV的分类的id
     * @param session         获取当前会话
     */
    public State musicPlay(Play playInformation, HttpSession session) throws DataBaseException {
        //得到会话上的用户
        User user = specialFunctions.getUser(session);
        Play play = existenceService.isUserMusicPlay(user.getId(), playInformation.getMusicId(), playInformation.getType());
        // 先判断该用户是否听过该音乐或MV，如果听过只需要更新时间
        if (play != null) {
            // 如果不为null表示已经播放过
            play.setDate(new Date());
            if (playMapper.updatePlay(play) < 1) {
                // 如果失败是数据库错误
                logger.error("邮箱：" + user.getMailbox() + "更新音乐或MV的播放记录时，数据库出错");
                throw new DataBaseException("邮箱：" + user.getMailbox() + "更新音乐或MV的播放记录时，数据库出错");
            }
        } else {
            // 没有播放过需要添加播放记录
            collectionAndPlay(0, null, playInformation, user);
        }
        return new State(1);
    }

    /**
     * 为指定的音乐或MV，添加收藏或播放记录
     *
     * @param have         只有收藏有
     * @param musicCollect 音乐或MV收藏信息
     * @param play         音乐或MV播放信息
     * @param user         用户信息
     */
    private void collectionAndPlay(int have, MusicCollect musicCollect, Play play, User user) throws DataBaseException {
        // 该音乐或MV的歌手
        int singerId;
        // 该音乐的专辑 mv没有
        int albumId = 0;
        // 该音乐或MV的分类
        int classificationId;
        // 存储类型1表示音乐 2表示MV
        int type;
        // 音乐或MV的id
        int musicId;
        if (musicCollect != null) {
            type = musicCollect.getType();
            musicId = musicCollect.getMusicId();
        } else {
            type = play.getType();
            musicId = play.getMusicId();
        }
        // 表示音乐，去音乐表查询
        if (type == 1) {
            Music music = new Music();
            music.setId(musicId);
            // 查找指定id信息
            music = musicMapper.selectListMusic(music).get(0);
            singerId = music.getSingerId();
            albumId = music.getAlbumId();
            classificationId = music.getClassificationId();
        } else {
            //表示MV，去MV表查找
            MusicVideo musicVideo = new MusicVideo();
            musicVideo.setId(musicId);
            // 查找指定id信息
            musicVideo = musicVideoMapper.selectListMusicVideo(musicVideo).get(0);
            singerId = musicVideo.getSingerId();
            classificationId = musicVideo.getClassificationId();
        }
        if (musicCollect != null) {
            musicCollect.setUserId(user.getId());
            musicCollect.setMusicId(musicId);
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
        } else {
            play.setUserId(user.getId());
            play.setMusicId(musicId);
            play.setType(type);
            play.setSingerId(singerId);
            play.setAlbumId(albumId);
            play.setClassificationId(classificationId);
            play.setDate(new Date());
            // 添加该音乐或MV的播放记录
            if (playMapper.insertPlay(play) < 1) {
                // 如果失败是数据库错误
                logger.debug("邮箱：" + user.getMailbox() + "添加音乐或MV的播放记录时，数据库出错");
                throw new DataBaseException("邮箱：" + user.getMailbox() + "添加音乐或MV的播放记录时，数据库出错");
            }
        }
    }

    /**
     * 显示用户的音乐或MV的历史播放记录,ajax
     *
     * @param type    1表示音乐 2表示MV
     * @param session 获取当前会话
     */
    public List showMusicPlay(Integer type, HttpSession session) {
        //得到会话上的用户
        User user = specialFunctions.getUser(session);
        Play play = new Play();
        play.setType(type);
        play.setUserId(user.getId());
        // 查找用户播放过的音乐或MV
        List<Play> list = playMapper.selectListPlay(play);
        // 用来存放音乐或MV的id
        List<Integer> idList = new ArrayList<>();
        for (Play p : list) {
            idList.add(p.getMusicId());
        }
        if (type == 1) {
            // 1表示音乐
            return musicMapper.listIdSelectListMusic(idList);
        } else {
            // 2表示MV
            return musicMapper.listIdSelectListMusic(idList);
        }
    }

    /**
     * 音乐或MV或专辑的评论，ajax
     *
     * @param commentInformation 封装平路的信息
     *                           获取音乐或MV或专辑的id
     *                           获取类型1是音乐，2是MV，3是专辑
     *                           获取评论的内容
     *                           获取回复哪个评论的id, 0为独立评论
     * @param session            获取当前会话
     */
    public State comment(Comment commentInformation, HttpSession session) throws DataBaseException {
        State state = new State();
        if (commentInformation.getContent().length() != 0) {
            if (commentInformation.getContent().length() <= 200) {
                //得到会话上的用户
                User user = specialFunctions.getUser(session);
                Comment comment = new Comment();
                comment.setMusicId(commentInformation.getMusicId());
                comment.setType(commentInformation.getType());
                comment.setUserId(user.getId());
                comment.setContent(commentInformation.getContent());
                comment.setDate(new Date());
                comment.setReply(commentInformation.getReply());
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
     * 删除指定评论及他的子评论
     *
     * @param id 获取要删除评论的id
     */
    public State deleteComment(Integer id) throws DataBaseException {
        // 判断是否有子评论，返回0
        State state = new State();
        int replyId = existenceService.isComment(id);
        if (replyId == 0) {
            state.setState(1);
            return state;
        } else {
            // 删除指定id的评论
            if (commentMapper.deleteComment(replyId) < 1) {
                // 如果失败是数据库错误
                logger.error("删除评论时，数据库出错");
                throw new DataBaseException("删除评论时，数据库出错");
            }
            // 递归删除该评论的所有子评论
            deleteComment(replyId);
        }
        state.setInformation("删除失败");
        return state;
    }


    /**
     * 评论点赞或取消点赞（更改用户的点赞记录，更改评论被点赞的次数），事务处理
     *
     * @param id      评论的id
     * @param session 获取当前会话
     */
    @Transactional
    public synchronized State commentFabulous(String id, HttpSession session) throws DataBaseException {
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
        return new State(1);
    }


    /**
     * 播放音乐判断用户是否拥有该音乐
     *
     * @param id 音乐的id
     */
    public Music playMusic(Integer id, HttpSession session) {
        //得到会话上的用户
        User user = specialFunctions.getUser(session);
        // 查找指定的音乐信息
        Music music = idExistence.isMusicId(id);
        // 得到音乐的等级
        int level = music.getLevel();
        // 先判断该音乐有没有版权 0表示有版权
        if (music.getAvailable() == 0) {
            if (0 < level && level < 4) {
                // 判断用户是不是VIP
                if (user.getVipDate().getTime() >= System.currentTimeMillis()) {
                    return music;
                }
            } else if (level == 4) {
                // 判断用户有没有购买，不为null表示购买
                if (transactionService.isPurchaseMusic(id, 1, specialFunctions.getUser(session)) != null) {
                    return music;
                }
            } else {
                // 等级为0 ，表示免费
                return music;
            }
        }
        return null;
    }

    /**
     * 播放MV判断用户是否购买该MV
     *
     * @param id MV的id
     */
    public MusicVideo playMusicVideo(Integer id, HttpSession session) {
        //得到会话上的用户
        User user = specialFunctions.getUser(session);
        // 查找指定的音乐信息
        MusicVideo musicVideo = idExistence.isMusicVideoId(id);
        // 得到音乐的等级
        int level = musicVideo.getLevel();
        // 先判断该音乐有没有版权 0表示有版权
        if (musicVideo.getAvailable() == 0) {
            if (0 < level && level < 4) {
                // 判断用户是不是VIP
                if (user.getVipDate().getTime() >= System.currentTimeMillis()) {
                    return musicVideo;
                }
            } else if (level == 4) {
                // 判断用户有没有购买，不为null表示购买
                if (transactionService.isPurchaseMusic(id, 2, specialFunctions.getUser(session)) != null) {
                    return musicVideo;
                }
            } else {
                // 等级为0 ，表示免费
                return musicVideo;
            }
        }
        return null;

    }
}