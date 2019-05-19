package controller.user.consumer;

import entity.State;
import util.exception.DataBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.consumer.AboutMusicService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 收藏音乐，收藏MV，收藏歌单，收藏专辑，添加历史播放记录，评论，点赞
 *
 *
 * @author 5月15日 张易兴创建
 */
@Controller
public class AboutMusic {
    @Resource(name = "AboutMusicService")
    AboutMusicService aboutMusicService;
    private static final Logger logger = LoggerFactory.getLogger(AboutMusic.class);

    /**
     * 创建歌单或专辑
     *
     * @param name           获取歌单或专辑的标题
     * @param picture        获取歌单或专辑的封面的图片路径
     * @param introduction   获取歌单或专辑的介绍
     * @param classification 获取分类
     * @param type           获取类型1是歌单2是专辑
     * @param session        获取当前会话
     */
    @RequestMapping(value = "/createMusicSongList")
    @ResponseBody
    public State createMusicSongList(String name, String picture, String introduction, String classification, Integer type, HttpSession session) {
        logger.trace("createMusicSongList方法开始执行");
        return null;
    }


    /**
     * 收藏歌单或专辑,ajax
     *
     * @param id               获取收藏歌单或专辑的id
     * @param type             获取类型1是歌单2是专辑
     * @param classificationId 分类的id
     * @param userCollectId    歌单创建者的用户id
     * @param session          获取当前会话
     */
    @RequestMapping(value = "/collectionSongList")
    @ResponseBody
    // 判断是否已经收藏过了或用更新
    public State collectionSongList(Integer id, Integer type, Integer classificationId, Integer userCollectId, HttpSession session) throws DataBaseException {
        logger.trace("collectionSongList方法开始执行");
        return aboutMusicService.collectionSongList(id, type, classificationId, userCollectId, session);
    }


    /**
     * 收藏音乐或MV,ajax
     *
     * @param id               获取收藏音乐或MV的id
     * @param type             获取类型1是音乐2是MV
     * @param have             表示是音乐或MV是否已经购买，1表示购买，2表示没购买
     * @param singerId         表示是歌手的id
     * @param albumId          表示是专辑的id
     * @param classificationId 表示是音乐或MV的分类的id
     * @param session          获取当前会话
     */
    @RequestMapping(value = "/collectionMusic")
    @ResponseBody
    // 判断是否已经收藏过了或用更新
    public State collectionMusic(Integer id, Integer type, Integer have, Integer singerId, Integer albumId, Integer classificationId, HttpSession session) throws DataBaseException {
        logger.trace("collectionMusic方法开始执行");
        return aboutMusicService.collectionMusic(id, type, have, singerId, albumId, classificationId, session);
    }

    /**
     * 音乐或MV的历史播放记录,ajax
     *
     * @param id               获取收藏音乐或MV的id
     * @param type             获取类型1是音乐2是MV
     * @param singerId         表示是歌手的id
     * @param albumId          表示是专辑的id
     * @param classificationId 表示是音乐或MV的分类的id
     * @param session          获取当前会话
     */
    @RequestMapping(value = "/musicPlay")
    @ResponseBody
    // 更该音乐的播放次数
    public State musicPlay(Integer id, Integer type, Integer singerId, Integer albumId, Integer classificationId, HttpSession session) throws DataBaseException {
        logger.trace("musicPlay方法开始执行");
        return aboutMusicService.musicPlay(id, type, singerId, albumId, classificationId, session);
    }


    /**
     * 音乐或MV或专辑的评论，ajax
     *
     * @param id      获取音乐或MV或专辑的id
     * @param type    获取类型1是音乐，2是MV，3是专辑
     * @param content 获取评论的内容
     * @param reply   获取回复哪个评论的id, 0为独立评论
     * @param session 获取当前会话
     */
    @RequestMapping(value = "/comment")
    @ResponseBody
    public State comment(Integer id, Integer type, String content, Integer reply, HttpSession session) throws DataBaseException {
        logger.trace("comment方法开始执行");
        return aboutMusicService.comment(id, type, content, reply, session);
    }

    /**
     * 评论点赞或取消点赞，ajax
     *
     * @param id      评论的id
     * @param session 获取当前会话
     */
    @RequestMapping(value = "/commentFabulous")
    @ResponseBody
    public State commentFabulous(String id, HttpSession session) throws DataBaseException {
        logger.trace("commentFabulous方法开始执行");
        return aboutMusicService.commentFabulous(id, session);
    }
}
