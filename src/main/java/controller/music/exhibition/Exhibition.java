package controller.music.exhibition;

import entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.ExhibitionService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 页面展示相关的层
 *  2.点击显示歌曲排行榜
 *  3.页面展示各种分类的显示
 * @author 5.16 蒋靓峣创建
 * */
@Controller
public class Exhibition {
    private static final Logger logger = LoggerFactory.getLogger(Exhibition.class);
    @Resource(name = "ExhibitionService")
    private ExhibitionService exhibitionService;

    /**
     * @param request 展示指定的歌曲（巅峰榜）
     *      功能：首页的流派排行榜
     *      只显示3条数据
     * @return List<Music> 返回符合条件的歌曲集合
     */
    @RequestMapping(value = "/rankingListByMusicType")
    @ResponseBody
    public Map<Music,User> rankingListByMusicType(HttpServletRequest request){
        String type = request.getParameter("classification");
        Map<Music,User> musicSingerList = exhibitionService.selectListMusicByMusicType(type);
        return musicSingerList;
    }
    /**
     *      功能：首页的新歌排行榜
     *      根据七天内上传的新歌曲的播放量算
     *      只显示3条数据
     * @return List<Music> 返回符合条件的歌曲集合
     */
    @RequestMapping(value = "/rankingListByNewSong")
    @ResponseBody
    public Map<Music,User> rankingListByNewSong(){
        Map<Music,User> musicSingeMap = exhibitionService.selectListMusicByNewSong();
        return musicSingeMap;
    }

    /**
     *      功能：首页的地区排行榜
     *      只显示3条数据
     * @return List<Music> 返回符合条件的歌曲集合
     */
    @RequestMapping(value = "/rankingListByRegion")
    @ResponseBody
    public Map<Music,User> rankingListByRegion(HttpServletRequest request){
        String region = request.getParameter("classification");
        Map<Music,User> musicList = exhibitionService.selectListMusicByRegion(region);
        return musicList;
    }
    /**
     *      功能：首页的地区排行榜
     *      只显示3条数据
     * @return List<Music> 返回符合条件的歌曲集合
     */
    @RequestMapping(value = "/rankingListByLanguage")
    @ResponseBody
    public Map<Music,User> rankingListByLanguage(HttpServletRequest request){
        String region = request.getParameter("classification");
        Map<Music,User> musicList = exhibitionService.selectListMusicByLanguage(region);
        return musicList;
    }

    /**
     * 首页推荐专辑的显示
     * @return List<SongList> 返回专辑的集合
     */
    @RequestMapping(value = "/activity")
    @ResponseBody
    public List<Activity> activity(){
        List<Activity> activityList = exhibitionService.selectActivity();
        return activityList;
    }
}
