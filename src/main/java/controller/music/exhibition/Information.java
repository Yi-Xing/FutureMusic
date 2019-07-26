package controller.music.exhibition;

import entity.Activity;
import entity.SongListExt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
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
    @ResponseBody
    public  List<Object> showMusicVideoInformation(HttpServletRequest request){
        String musicVideoId = request.getParameter("musicVideoId");
        List<Object> musicVideoInformation = musicVideoService.getMusicVideoInformation(Integer.parseInt(musicVideoId));
        return musicVideoInformation;
    }

    /**
     * 显示歌手的详细信息
     * @param request  页面请求，接收参数singerId
     * @return String 返回包含详细信息的页面
     */
    @RequestMapping("/singerInformation")
    public String singerInformation(HttpServletRequest request,Model model){
        int singerId = Integer.parseInt(request.getParameter("singerId"));
        Map<String,Object> singerInformation = singerService.searchSingerInformation(singerId);
        model.addAttribute("information",singerInformation);
        return "singerInfo";
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
        return "songListInfo";
    }
}
