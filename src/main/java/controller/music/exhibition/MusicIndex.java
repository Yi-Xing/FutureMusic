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
    @Resource(name ="SingerService")
    SingerService singerService;
    @Resource(name = "MusicService")
    MusicService musicService;
    @Resource(name = "ActivityService")
    ActivityService activityService;
    @Resource(name = "CommentService")
    CommentService commentService;



    @RequestMapping("/indexExhibition")
    public String indexExhibition(HttpServletRequest request, Model model) {

        String musicType = request.getParameter("musicType");
        String region1 = request.getParameter("musicRegion1");
        String region2 = request.getParameter("musicRegion2");
        String region3 = request.getParameter("musicRegion3");

        String singerAddress1 = request.getParameter("singerAddress1");
        String singerAddress2 = request.getParameter("singerAddress2");
        String singerAddress3 = request.getParameter("singerAddress3");
        //首页显示的活动
        model.addAttribute("activities", activity());
        //首页显示的音乐人
        model.addAttribute(singerAddress1, singerService.exhibitionSingersByRegion(singerAddress1));
        model.addAttribute(singerAddress2, singerService.exhibitionSingersByRegion(singerAddress2));
        model.addAttribute(singerAddress3, singerService.exhibitionSingersByRegion(singerAddress3));
        //显示的MV显示15首
        //音乐排行榜的歌曲
        model.addAttribute("newSong", rankingListByNewSong());
        model.addAttribute(musicType, rankingListByMusicType(musicType));
        model.addAttribute(region1, rankingListByRegion(region1));
        model.addAttribute(region2, rankingListByRegion(region2));
        model.addAttribute(region3, rankingListByRegion(region3));
        return "index";
    }
    /**
     * 显示MV
     */
    @RequestMapping("/musicVideoIndex")
    @ResponseBody
    public List<MusicVideoExt> musicVideoIndex(){
        return musicVideoService.exhibitionMusicVideo();
    }
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
        System.out.println(rankingMusicList);
        return rankingMusicList;
    }

    @RequestMapping("/getAllReply")
    @ResponseBody
    public List<CommentExt> getReply(HttpServletRequest request){
        int musicId =  Integer.parseInt(request.getParameter("musicId"));
        List<CommentExt> commentExts = commentService.getAllReply(musicId,new ArrayList<CommentExt>());
        List<CommentExt> resultcommentExts = commentService.sortComment(commentExts);
        return resultcommentExts;
    }
}
