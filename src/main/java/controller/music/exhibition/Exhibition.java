package controller.music.exhibition;

import entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.*;
import sun.nio.ch.AbstractPollArrayWrapper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 页面展示相关的层
 * 1.音乐人、MV、排行榜
 * @author 5.16 蒋靓峣创建
 * */
@Controller
public class Exhibition {
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

//    @RequestMapping("/indexExhibition")
//    @ResponseBody
//    public Map<String, Object> indexExhibition() {
//        Map<String, Object> indexMessage = new HashMap<>(16);
//        //首页显示的活动
////        indexMessage.put("activities", activity());
////        //首页显示的音乐人
////        indexMessage.put(singerAddress1, singerService.exhibitionSingersByRegion(singerAddress1));
////        indexMessage.put(singerAddress2, singerService.exhibitionSingersByRegion(singerAddress2));
////        indexMessage.put(singerAddress3, singerService.exhibitionSingersByRegion(singerAddress3));
//        //显示的MV显示15首
////        indexMessage.put("musicVideo", musicVideoService.exhibitionMusicVideo());
//        //新歌前三首歌曲，这是个bug
//        indexMessage.put("rankingNewSong", rankingListByNewSong());
//        //电音前三歌曲
////        indexMessage.put("rankingByMusicType", rankingListByMusicType(musicType));
//        //首页的歌曲地区排行榜(包括欧美、日韩、华语）
////        indexMessage.put("rankingByMusicRegion", rankingListByRegion(region1));
////        indexMessage.put("rankingByMusicRegion", rankingListByRegion(region2));
////        indexMessage.put("rankingByMusicRegion", rankingListByRegion(region3));
//        return indexMessage;
//    }

    /**
     * 功能：首页的流派排行榜 供总方法调用
     * 只显示3条数据
     * @return List<Music> 返回符合条件的歌曲集合
     */
    public List<MusicExt> rankingListByMusicType(String musicType) {
        List<MusicExt> musicList = musicService.selectListMusicByMusicType(musicType);
        return getThreeMusic(musicList);
    }
    /**
     * 功能：首页的新歌排行榜
     * 根据七天内上传的新歌曲的播放量算
     * 只显示3条数据
     *
     * @return List<Music> 返回符合条件的歌曲集合
     */
    @RequestMapping("/newSong")
    @ResponseBody
    public List<MusicExt> rankingListByNewSong() {
        System.out.println("我执行力");
        List<MusicExt> musicList = musicService.selectListMusicByNewSong();
        return getThreeMusic(musicList);
    }

    /**
     * 功能：首页的地区排行榜
     * 只显示3条数据
     *
     * @return List<Music> 返回符合条件的歌曲集合
     */
    public List<MusicExt> rankingListByRegion(String region) {
        List<MusicExt> musicList = musicService.selectListMusicByRegion(region);
        return getThreeMusic(musicList);
    }
    /**
     * 首页推荐活动的显示
     *
     * @return List<SongList> 返回专辑的集合
     */
    public List<Activity> activity() {
        List<Activity> activityList = activityService.selectActivity();
        return activityList;
    }
    /**
     * 获取三条数据音乐集合
     */
    public List<MusicExt>  getThreeMusic(List<MusicExt> musicList) {
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
