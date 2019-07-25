package controller.music.exhibition;

import entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 页面展示相关的层
 * 1.音乐人、MV、排行榜
 * @author 5.16 蒋靓峣创建
 * */
@Controller
public class MusicIndex {
    @Resource(name = "MusicVideoService")
    MusicVideoService musicVideoService;
    @Resource(name = "SingerService")
    SingerService singerService;
    @Resource(name = "MusicService")
    MusicService musicService;
    @Resource(name = "ActivityService")
    ActivityService activityService;
    @RequestMapping("/indexInformation")
    /**
     * 传入7个参数，三个音乐人的分类，四个音乐得分类
     * 变量名singerRegion1、singerRegion2、singerRegion3
     * musicType1、musicRegion1、musicRegion2、musicRegion3
     */
    public String indexInformation(HttpServletRequest request,Model model){
        //活动
        model.addAttribute("activities",activity());
        //歌手
        String singerRegion1 = request.getParameter("singerRegion1");
        String singerRegion2 = request.getParameter("singerRegion2");
        String singerRegion3 = request.getParameter("singerRegion3");
        model.addAttribute("singers",singerIndex(singerRegion1));
        model.addAttribute("singers",singerIndex(singerRegion2));
        model.addAttribute("singers",singerIndex(singerRegion3));
        //MV
        model.addAttribute("musicVideo",musicVideoIndex());
        //音乐
        model.addAttribute("newSong",rankingListByNewSong());
        String musicType1 = request.getParameter("musicType1");
        String musicRegion1 = request.getParameter("musicRegion1");
        String musicRegion2 = request.getParameter("musicRegion2");
        String musicRegion3 = request.getParameter("musicRegion3");
        model.addAttribute("musicType1",rankingListByMusicType(musicType1));
        model.addAttribute("musicRegion1",rankingListByRegion(musicRegion1));
        model.addAttribute("musicRegion2",rankingListByRegion(musicRegion2));
        model.addAttribute("musicRegion3",rankingListByRegion(musicRegion3));
        return "index";
    }
    /**
     * 首页推荐活动的显示
     *不传参
     * @return List<SongList> 返回活动的集合
     */
    @RequestMapping("/activity")
    @ResponseBody
    public List<Activity> activity() {
        List<Activity> activityList = activityService.selectActivity();
        return activityList;
    }

    /**
     * 首页显示音乐人
     * @return List<SingerExt> 歌手列表
     */
    @RequestMapping("/singerIndex")
    @ResponseBody
    public List<SingerExt> singerIndex(String region){
        return singerService.exhibitionSingersByRegion(region);
    }

    /**
     * 显示MV
     * 不传参
     * @return List<MusicVideoExt> 返回要显示的MV的集合
     */
    @RequestMapping("/musicVideoIndex")
    @ResponseBody
    public List<MusicVideoExt> musicVideoIndex() {
        return musicVideoService.exhibitionMusicVideo();
    }

    /**
     * 功能：首页的流派排行榜
     * @return List<Music> 返回符合条件的歌曲集合
     */
    @RequestMapping("/musicType")
    @ResponseBody
    public List<MusicExt> rankingListByMusicType(String musicType) {
        List<MusicExt> musicList = musicService.selectListMusicByMusicType(musicType);
        return getThreeMusic(musicList);
    }

    /**
     * 首页的新歌排行榜
     * 不传参
     * @return List<Music> 返回符合条件的歌曲集合
     */
    @RequestMapping("/newSong")
    @ResponseBody
    public List<MusicExt> rankingListByNewSong() {
        List<MusicExt> musicList = musicService.selectListMusicByNewSong();
        return getThreeMusic(musicList);
    }

    /**
     * 功能：首页的地区排行榜
     * @return List<Music> 返回符合条件的歌曲集合
     */
    @RequestMapping("/musicRegion")
    @ResponseBody
    public List<MusicExt> rankingListByRegion(String region) {
        List<MusicExt> musicList = musicService.selectListMusicByRegion(region);
        return getThreeMusic(musicList);
    }

    /**
     * 获取三条数据音乐集合 不是接口
     * @param musicList 音乐列表
     * @return  List<MusicExt> 返回三条数据
     */
    public List<MusicExt> getThreeMusic(List<MusicExt> musicList) {
        int limit = 0;
        List<MusicExt> rankingMusicList = new ArrayList<>();
        for (MusicExt music : musicList) {
            rankingMusicList.add(music);
            limit++;
            if (limit > 2) {
                break;
            }
        }
        return rankingMusicList;
    }
}
