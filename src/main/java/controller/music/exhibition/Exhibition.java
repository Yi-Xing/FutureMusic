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
    public Map<String,Object> indexExhibition(){
        Map<String,Object> model = new HashMap<>(16);
        //首页显示的活动
        model.put("activities",activity());
        //首页显示的音乐人
        model.put("singerAddress1",singerService.exhibitionSingersByRegion(singerAddress1));
        model.put("singerAddress2",singerService.exhibitionSingersByRegion(singerAddress2));
        model.put("singerAddress3",singerService.exhibitionSingersByRegion(singerAddress3));
        //显示的MV显示15首
        model.put("musicVideo",musicVideoService.exhibitionMusicVideo());
        //新歌前三首歌曲
        model.put("rankingNewSong",rankingListByNewSong());
        //电音前三歌曲
        model.put("rankingByMusicType", rankingListByMusicType(musicType));
        //首页的歌曲地区排行榜(包括欧美、日韩、华语）
//        model.put("rankingByMusicRegion",rankingListByRegion(region1));
//        model.put("rankingByMusicRegion",rankingListByRegion(region2));
//        model.put("rankingByMusicRegion",rankingListByRegion(region3));
        return model;
    }
    /**
     *      功能：首页的流派排行榜 供总方法调用
     *          只显示3条数据
     * @return List<Music> 返回符合条件的歌曲集合
     */
    public List<Map<String, String[]>> rankingListByMusicType(String musicType){
        List<Map<String, String[]>> musicList = musicService.selectListMusicByMusicType(musicType);
        return getThree(musicList);
    }
    /**
     *      功能：首页的新歌排行榜
     *      根据七天内上传的新歌曲的播放量算
     *      只显示3条数据
     * @return List<Music> 返回符合条件的歌曲集合
     */
    public List<Music> rankingListByNewSong(){
        List<Music> musicList = musicService.selectListMusicByNewSong();
        int limit  = 0;
        List<Music> rankingMusicList = new ArrayList<>();
        for(Music m:musicList){
            rankingMusicList.add(m);
            limit++;
            if(limit>2){
                break;
            }
        }
        return rankingMusicList;
    }
    /**
     *      功能：首页的地区排行榜
     *      只显示3条数据
     * @return List<Music> 返回符合条件的歌曲集合
     */
    public List<Map<String, String[]>> rankingListByRegion(String region){
        List<Map<String, String[]>> musicList = musicService.selectListMusicByRegion(region);
        return getThree(musicList);
    }
    /**
     * 首页推荐活动的显示
     * @return List<SongList> 返回专辑的集合
     */
    public List<Activity> activity(){
        List<Activity> activityList = activityService.selectActivity();
        return activityList;
    }

    /**
     * 获取三条数据
     */
    public List<Map<String, String[]>> getThree(List<Map<String, String[]>> musicList) {
        int limit = 0;
        List<Map<String, String[]>> rankingMusicList = new ArrayList<>();
        for (Map<String, String[]> threeMusic : musicList) {
            rankingMusicList.add(threeMusic);
            limit++;
            if (limit >2) {
            break;
            }
        }
        return rankingMusicList;
    }
}
