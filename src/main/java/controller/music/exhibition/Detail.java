package controller.music.exhibition;

import entity.Activity;
import entity.Music;
import entity.MusicVideo;
import entity.SongList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.music.DetailsService;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 蒋靓峣 5.25创建
 * 点击显示歌曲、MV 、专辑、歌单的MV
 * */
@Controller
public class Detail {
    @Resource(name = "DetailsService")
    private DetailsService detailsService;

    /**
     * 显示歌曲的详细信息
     * @param musicId 传入歌曲的id
     * @return Map<String,Object>
     */
    @RequestMapping(value = "/showMusicDetail")
    @ResponseBody
    public Map<String,Object> showMusicDetail(@RequestParam(value = "musicId",defaultValue = "1")int musicId){
        Music music = new Music();
        music.setId(musicId);
        System.out.println("进来了");
        return detailsService.showMusic(music);
    }

    /**
     * 显示MV的详细信息
     * @param musicVideoId 传入MV的id
     * @return Map<String,Object>
     */
    @RequestMapping(value = "/showMusicVideoDetail")
    @ResponseBody
    public Map<String,Object> showMusicVideoDetail(@RequestParam(value = "musicVideoId",defaultValue = "1")int musicVideoId){
        MusicVideo musicVideo = new MusicVideo();
        musicVideo.setId(musicVideoId);
        return detailsService.showMusicVideo(musicVideo);
    }

    /**
     * 显示歌单的详细信息
     * @param songListId 传入歌单或专辑的id
     * @return Map<String,Object>
     */
    @RequestMapping(value = "/showSongListDetail")
    @ResponseBody
    public Map<String,Object> showSongListDetail(@RequestParam(value = "songListId",defaultValue = "1")int songListId){
        SongList songList = new SongList();
        songList.setId(songListId);
        return detailsService.showSongList(songList);
    }

    /**
     * 显示活动的详细信息
     * @param activityId 活动的id
     * @return 相关信息
     */
    @RequestMapping(value = "/showActivityDetail")
    @ResponseBody
    public String showActivityDetail(@RequestParam(value = "activityId",defaultValue = "0")int activityId){
        ModelAndView modelAndView = new ModelAndView();
        Activity activity = new Activity();
        activity.setId(activityId);
        Map<String,Object> stringObjectMap = detailsService.showActivity(activity);
        Activity activity1 = (Activity)stringObjectMap.get("activity");
        String activityWebsite = activity1.getWebsite();
        modelAndView.addObject("activityInfo",stringObjectMap);
        return activityWebsite;
    }
}
