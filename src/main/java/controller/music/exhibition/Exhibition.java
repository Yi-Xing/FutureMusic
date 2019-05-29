package controller.music.exhibition;

import entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * 页面展示相关的层
 * 1.音乐人、MV、排行榜
 * @author 5.16 蒋靓峣创建
 * */
@Controller
public class Exhibition {
    @Resource(name = "SingerService")
    SingerService singerService;
    @Resource(name = "MusicVideoService")
    MusicVideoService musicVideoService;
    @Resource(name = "MusicService")
    MusicService musicService;
    @Resource(name = "ActivityService")
    ActivityService activityService;


    private final String musicType = "电音";
    private final String region1 = "华语";
    private final String region2 = "日韩";
    private final String region3 = "欧美";

    private final String singerAddress1 = "华语";
    private final String singerAddress2 = "欧美";
    private final String singerAddress3 = "日韩";

    @RequestMapping("/indexExhibition")
    @ResponseBody
    public String indexExhibition(Model model){
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
        model.addAttribute("rankingByMusicType", rankingListByMusicType(musicType));
        //首页的歌曲地区排行榜(包括欧美、日韩、华语）
        model.addAttribute("rankingByMusicRegion",rankingListByRegion(region1));
        model.addAttribute("rankingByMusicRegion",rankingListByRegion(region2));
        model.addAttribute("rankingByMusicRegion",rankingListByRegion(region3));
        return "index";
    }
    /**
     *      功能：首页的流派排行榜
     *          只显示3条数据
     * @return List<Music> 返回符合条件的歌曲集合
     */
    @RequestMapping(value = "/rankingListByMusicType")
    @ResponseBody
    public List<Music> rankingListByMusicType(String musicType){
        List<Music> musicList = musicService.selectListMusicByMusicType(musicType);
        return musicList;
    }
    /**
     *      功能：首页的新歌排行榜
     *      根据七天内上传的新歌曲的播放量算
     *      只显示3条数据
     * @return List<Music> 返回符合条件的歌曲集合
     */
    @RequestMapping(value = "/rankingListByNewSong")
    @ResponseBody
    public List<Music> rankingListByNewSong(){
        List<Music> musicList = musicService.selectListMusicByNewSong();
        return musicList;
    }
    /**
     *      功能：首页的地区排行榜
     *      只显示3条数据
     * @return List<Music> 返回符合条件的歌曲集合
     */
    @RequestMapping(value = "/rankingListByRegion")
    @ResponseBody
    public List<Music> rankingListByRegion(String region){
        List<Music> musicList = musicService.selectListMusicByRegion(region);
        return musicList;
    }
    /**
     *      功能：首页的语种排行榜
     *      只显示3条数据
     * @return List<Music> 返回符合条件的歌曲集合
     */
    @RequestMapping(value = "/rankingListByLanguage")
    @ResponseBody
    public List<Music> rankingListByLanguage(HttpServletRequest request){
        String region = request.getParameter("classification");
        List<Music> musicList = musicService.selectListMusicByLanguage(region);
        return musicList;
    }

    /**
     * 首页推荐活动的显示
     * @return List<SongList> 返回专辑的集合
     */
    @RequestMapping(value = "/activity")
    @ResponseBody
    public List<Activity> activity(){
        List<Activity> activityList = activityService.selectActivity();
        return activityList;
    }
    /**
     * 搜索音乐人
     */
    @RequestMapping(value = "exhibitionSingersByRegion")
    @ResponseBody
    public List<ShowSinger> exhibitionSingersByRegion(String  region){
        return singerService.exhibitionSingersByRegion("2");
    }
}
