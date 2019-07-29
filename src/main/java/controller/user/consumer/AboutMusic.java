package controller.user.consumer;

import entity.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import service.user.consumer.AboutPlayService;
import util.exception.DataBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.consumer.AboutMusicService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 收藏和取消收藏音乐或MV，添加历史播放记录，评论，删除评论，点赞
 *
 * @author 5月15日 张易兴创建
 */
@Controller
@RequestMapping(value = "/user")
public class AboutMusic {
    @Resource(name = "AboutMusicService")
    AboutMusicService aboutMusicService;
    @Resource(name = "AboutPlayService")
    AboutPlayService aboutPlayService;
    private static final Logger logger = LoggerFactory.getLogger(AboutMusic.class);

    /**
     *  显示用户的喜欢页面 统计喜欢歌单的个数，专辑的个数，音乐的个数，MV的个数
     */
    @RequestMapping(value = "/showUserLike")
    public String showUserLike(HttpSession session, Model model){
        return aboutMusicService.showUserLike(session,model);
    }

    /**
     * 显示用户喜欢MV的播放页面
     */
    @RequestMapping(value = "/playCollectMusicVideo")
    public String playCollectMusicVideo(HttpSession session, Model model){
        return aboutPlayService.playCollectMusicVideo(session,model);
    }

    /**
     * 显示用户喜欢音乐的播放页面
     */
    @RequestMapping(value = "/playCollectMusic")
    public String playCollectMusic(HttpSession session, Model model){
        return aboutPlayService.playCollectMusic(session,model);
    }

    /**
     * 播放指定MV
     */
    @RequestMapping(value = "/playMusicVideoId")
    public String playMusicVideoId(Integer id, Model model){
        return aboutPlayService.playMusicVideoId(id,model);
    }

    /**
     * 显示用户收藏的所有音乐，显示用户收藏的所有MV  (需要更改)
     *
     * @param type 1表示查找音乐收藏 2表示查找MV收藏
     */
    @RequestMapping(value = "/showUserCollectionMusic")
    @ResponseBody
    public List<MusicCollect> showUserCollectionMusic(Integer type, HttpSession session) {
        logger.trace("showUserCollectionMusic方法开始执行");
        return aboutMusicService.showUserCollectionMusic(type, session);
    }

    /**
     * 显示用户购买过的音乐，显示用户购买过的MV   （需要更改）
     *
     * @param type 1表示查找音乐购买 2表示查找MV购买
     */
    @RequestMapping(value = "/showUserPurchaseMusic")
    @ResponseBody
    public List showUserPurchaseMusic(Integer type, HttpSession session) {
        logger.trace("showUserPurchaseMusic方法开始执行");
        return aboutMusicService.showUserPurchaseMusic(type, session);
    }

    /**
     * 收藏和取消收藏音乐或MV,ajax
     *
     * @param musicCollectInformation 封装音乐或MV收藏的信息
     *                                musicId          获取收藏音乐或MV的id
     *                                type             获取类型1是音乐2是MV
     *                                session          获取当前会话
     */
    @RequestMapping(value = "/collectionMusic")
    @ResponseBody
    public State collectionMusic(@RequestBody MusicCollect musicCollectInformation, HttpSession session) throws DataBaseException {
        logger.trace("collectionMusic方法开始执行");
        return aboutMusicService.collectionMusic(musicCollectInformation, session);
    }

    /**
     * 添加音乐或MV的历史播放记录,ajax
     *
     * @param play    封装评论的信息
     *                musicId          获取收藏音乐或MV的id
     *                type             获取类型1是音乐2是MV
     * @param session 获取当前会话
     */
    @RequestMapping(value = "/musicPlay")
    @ResponseBody
    public State musicPlay(@RequestBody Play play, HttpSession session) throws DataBaseException {
        logger.trace("musicPlay方法开始执行");
        return aboutMusicService.musicPlay(play, session);
    }

    /**
     * 显示用户的音乐或MV的历史播放记录,ajax （需要更改）
     *
     * @param type 1表示音乐 2表示MV
     * @param session 获取当前会话
     */
    @RequestMapping(value = "/showMusicPlay")
    @ResponseBody
    public List<?> showMusicPlay(Integer type, HttpSession session) {
        logger.trace("showMusicPlay方法开始执行");
        return aboutMusicService.showMusicPlay(type, session);
    }

    /**
     * 音乐或MV或专辑的评论，ajax
     *
     * @param commentInformation 封装评论的信息
     *                           获取musicId音乐或MV或专辑的id
     *                           获取type类型1是音乐，2是MV，3是专辑
     *                           获取content评论的内容
     *                           获取reply回复哪个评论的id, 0为独立评论
     * @param session            获取当前会话
     */
    @RequestMapping(value = "/comment")
    @ResponseBody
    public State comment(@RequestBody Comment commentInformation, HttpSession session) throws DataBaseException {
        logger.trace("comment方法开始执行");
        return aboutMusicService.comment(commentInformation, session);
    }

    /**
     * 删除指定评论及他的子评论
     *
     * @param id 获取要删除评论的id
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

    /**
     * 播放音乐判断用户是否拥有该音乐
     * @param id 音乐的id
     */
    @RequestMapping(value = "/playMusic")
    @ResponseBody
    public Music playMusic(Integer id,HttpSession session){
        return aboutMusicService.playMusic(id,session);
    }
    /**
     * 播放MV判断用户是否购买该MV
     * @param id MV的id
     */
    @RequestMapping(value = "/playMusicVideo")
    @ResponseBody
    public MusicVideo playMusicVideo(Integer id,HttpSession session){
        return aboutMusicService.playMusicVideo(id,session);
    }
}
