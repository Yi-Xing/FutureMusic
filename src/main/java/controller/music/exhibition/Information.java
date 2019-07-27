package controller.music.exhibition;

import entity.Activity;
import entity.CommentExt;
import entity.MusicVideoExt;
import entity.SongListExt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 蒋靓峣 5.25创建
 * */
@Controller
public class Information {
    @Resource(name = "ActivityService")
    ActivityService activityService;
    @Resource(name = "MusicVideoService")
    MusicVideoService musicVideoService;
    @Resource(name="SingerService")
    SingerService singerService;
    @Resource(name= "SongListService")
    SongListService songListService;
    /**
     * 显示活动的详细信息
     * @param request  页面请求，activityId
     * @return String 返回包含详细信息的页面
     */
    @RequestMapping(value = "/showActivityInformation")
    public String showActivityInformation(HttpServletRequest request,Model model){
        int activityId = Integer.parseInt(request.getParameter("activityId"));
        Activity activity = new Activity();
        activity.setId(activityId);
        Map<String,Object> activityInformation = activityService.showActivity(activity);
        model.addAttribute("activity",activityInformation);
        return "activityInfo";
    }
    /**
     * 显示MV的详细信息
     * @param request  页面请求，接收参数musicVideoId
     * @return String 返回包含详细信息的页面
     */
    @RequestMapping(value = "/musicVideoInformation")
    public  String showMusicVideoInformation(HttpServletRequest request,Model model){
        String musicVideoId = request.getParameter("musicVideoId");
        Map<MusicVideoExt,List<CommentExt>> musicVideoInformation = musicVideoService.getMusicVideoInformation(Integer.parseInt(musicVideoId));
        model.addAttribute("musicVideoInformation",musicVideoInformation);
        System.out.println(musicVideoInformation);
        return "mvPlayer";
    }

    /**
     * 显示歌手的详细信息
     * @param request  页面请求，接收参数singerId
     * @return String 返回包含详细信息的页面
     */
    /**
     * 5条音乐人
     */
    @RequestMapping("/singerInformation")
    public String singerInformation(HttpServletRequest request,Model model){
        int singerId = Integer.parseInt(request.getParameter("singerId"));
<<<<<<< HEAD
        singerService.searchSingerInformation(singerId,model);
        return "artist";
=======
        Map<String,Object> singerInformation = singerService.searchSingerInformation(singerId);
        model.addAttribute("information",singerInformation);
        return "artists";
>>>>>>> 9a2b357b915d0975a9b767e03ab81f5ea6dd30ce
    }
    /**
     * 显示歌单的详细信息
     * @param request  页面请求，接收参数songListId
     * @return String 返回包含详细信息的页面
     */
    @RequestMapping("/songListInformation")
    public String songListInformation(HttpServletRequest request,Model model){
        int songListId = Integer.parseInt(request.getParameter("songListId"));
        SongListExt songListExt = songListService.songListDetail(songListId);
        model.addAttribute("songList",songListExt);
        System.out.println(songListExt);
        System.out.println("==================");
        return "musicList";
    }
}
