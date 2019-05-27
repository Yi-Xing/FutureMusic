package controller.music.exhibition;

import entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.ExhibitionService;
import service.music.MusicVideoService;
import service.music.SingerService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 页面展示相关的层
 * 1.音乐人、MV、排行榜
 * @author 5.16 蒋靓峣创建
 * */
@Controller
public class Exhibition {
    @Resource(name = "ExhibitionService")
    private ExhibitionService exhibitionService;
    @Resource(name = "SingerService")
    SingerService singerService;
    @Resource(name = "MusicVideoService")
    MusicVideoService musicVideoService;


    private final String musicType = "电音";
    private final String region1 = "华语";
    private final String region2 = "日韩";
    private final String region3 = "欧美";

    private final String singerAddress1 = "华语";
    private final String singerAddress2 = "欧美";
    private final String singerAddress3 = "日韩";

    @RequestMapping("/indexExhibition")
    @ResponseBody
    public String indexExhibition(Model model,HttpServletRequest request){
        //首页显示的活动
        model.addAttribute("activities",activity());
        //首页显示的音乐人
        model.addAttribute("singerAddress1",singerService.exhibitionSingersByRegion(singerAddress1));
        model.addAttribute("singerAddress2",singerService.exhibitionSingersByRegion(singerAddress2));
        model.addAttribute("singerAddress3",singerService.exhibitionSingersByRegion(singerAddress3));
        //显示的MV显示15首
        model.addAttribute("musicVideo",musicVideoService.exhibitionMusicVideo());
        //新歌前三首歌曲
        model.addAttribute("rankingNewSong",rankingListByNewSong());
        //电音前三歌曲
        model.addAttribute("rankingByMusicType", musicType);
        //首页的歌曲地区排行榜(包括欧美、日韩、华语）
        model.addAttribute("rankingByMusicRegion",rankingListByRegion(region1));
        model.addAttribute("rankingByMusicRegion",rankingListByRegion(region2));
        model.addAttribute("rankingByMusicRegion",rankingListByRegion(region3));
        return "index";
    }
    /**
     * @param request 展示指定的歌曲（巅峰榜）
     *      功能：首页的流派排行榜
     *          只显示3条数据
     * @return List<Music> 返回符合条件的歌曲集合
     */
    @RequestMapping(value = "/rankingListByMusicType")
    @ResponseBody
    public Map<Music,User> rankingListByMusicType(HttpServletRequest request){
        String musicType = request.getParameter("musicType");
        Map<Music,User> musicSingerList = exhibitionService.selectListMusicByMusicType(musicType);
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
    public Map<Music,User> rankingListByRegion(String region){
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
