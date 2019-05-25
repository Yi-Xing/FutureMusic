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
 * 收藏和取消收藏音乐或MV，添加历史播放记录，评论，删除评论，点赞
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
     * 收藏和取消收藏音乐或MV,ajax
     *
     * @param id               获取收藏音乐或MV的id
     * @param type             获取类型1是音乐2是MV
     * @param have             表示是音乐或MV是否已经购买，1表示购买，2表示没购买(不用传该数据)  判断指定用户没有有购买指定音乐或MV
     * @param singerId         表示是歌手的id
     * @param albumId          表示是专辑的id
     * @param classificationId 表示是音乐或MV的分类的id
     * @param session          获取当前会话
     */
    @RequestMapping(value = "/collectionMusic")
    @ResponseBody
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
     * 删除指定评论及他的子评论
     * @param id      获取要删除评论的id
     */
    @RequestMapping(value = "/deleteComment")
    @ResponseBody
    public State deleteComment(Integer id) throws DataBaseException {
        logger.trace("deleteComment方法开始执行");
        return aboutMusicService.deleteComment(id);
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
