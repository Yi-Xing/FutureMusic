package controller.user.consumer;

import entity.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.consumer.AboutMusicService;
import service.user.consumer.AboutSongListService;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 创建编辑歌单或专辑，收藏或取消收藏歌单或专辑，添加历史播放记录
 *
 * @author 5月15日 张易兴创建
 */
@Controller
public class AboutSongList {
    @Resource(name = "AboutMusicService")
    AboutMusicService aboutMusicService;
    @Resource(name = "AboutSongListService")
    AboutSongListService aboutSongListService;
    private static final Logger logger = LoggerFactory.getLogger(AboutSongList.class);

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
     * 编辑歌单或专辑
     *
     * @param name           获取歌单或专辑的标题
     * @param picture        获取歌单或专辑的封面的图片路径
     * @param introduction   获取歌单或专辑的介绍
     * @param classification 获取分类
     * @param type           获取类型1是歌单2是专辑
     * @param session        获取当前会话
     */
    @RequestMapping(value = "/editMusicSongList")
    @ResponseBody
    public State editMusicSongList(String name, String picture, String introduction, String classification, Integer type, HttpSession session) {
        logger.trace("editMusicSongList方法开始执行");
        return null;
    }

    /**
     * 删除歌单或专辑，ajax
     *
     * @param id 需要删除歌单或专辑的id
     */
    @RequestMapping(value = "/deleteMusicSongList")
    @ResponseBody
    public State deleteMusicSongList(Integer id) throws DataBaseException {
        logger.trace("deleteMusicSongList方法开始执行");
        return aboutSongListService.deleteMusicSongList(id);
    }

    /**
     * 收藏或取消收藏歌单或专辑,ajax
     *
     * @param id               获取收藏歌单或专辑的id
     * @param type             获取类型1是歌单2是专辑
     * @param classificationId 分类的id
     * @param userCollectId    歌单创建者的用户id
     * @param session          获取当前会话
     */
    @RequestMapping(value = "/collectionSongList")
    @ResponseBody
    public State collectionSongList(Integer id, Integer type, Integer classificationId, Integer userCollectId, HttpSession session) throws DataBaseException {
        logger.trace("collectionSongList方法开始执行");
        return aboutSongListService.collectionSongList(id, type, classificationId, userCollectId, session);
    }
}
