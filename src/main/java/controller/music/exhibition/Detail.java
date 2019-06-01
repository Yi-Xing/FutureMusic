package controller.music.exhibition;

import entity.Activity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.music.ActivityService;
import service.music.MusicService;
import service.music.MusicVideoService;

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
    @Resource(name = "ActivityService")
    private ActivityService activityService;
    @Resource(name = "MusicService")
    MusicService musicService;
    @Resource(name = "MusicVideoService")
    MusicVideoService musicVideoService;




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
        Map<String,Object> stringObjectMap = activityService.showActivity(activity);
        Activity activity1 = (Activity)stringObjectMap.get("activity");
        String activityWebsite = activity1.getWebsite();
        modelAndView.addObject("activityInfo",stringObjectMap);
        return activityWebsite;
    }
}
