package controller.music.search;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.MusicService;
import service.music.MusicVideoService;
import service.music.SearchService;
import service.music.SingerService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  根据名字搜索
 * @author 5.21 蒋靓峣创建
 * */
@Controller
public class SearchMusicVideo {
    @Resource(name = "MusicVideoService")
    MusicVideoService musicVideoService;
    /**
     * 显示MV的详细信息
     */
    @RequestMapping(value = "/showMusicVideoDetail")
    public  String showMusicVideoDetail(HttpServletRequest request, Model model){
        String musicVideoId = request.getParameter("musicVideoId");
        MusicVideo musicVideo = new MusicVideo();
        musicVideo.setId(Integer.parseInt(musicVideoId));
        Map<String,Object> musicVideoDetail = musicVideoService.showMusicVideo(musicVideo);
        model.addAttribute("musicVideoDetail",musicVideoDetail);
        return "index";
    }
   /**
     * 根据分类查找MV
     * @param pn 分页
     * @param region 地区
     * @param type 流派
     * @return  展示mv的信息
     */
    @RequestMapping("/searchMusicVideoByClassification")
    @ResponseBody
    public PageInfo searchMusicVideoByClassification(@RequestParam(required = false,value = "pn", defaultValue = "1") Integer pn,
                                                     @RequestParam(value = "region",defaultValue = "1")String region,
                                                     @RequestParam(value = "type",defaultValue = "1")String type) {
        PageHelper.startPage(pn, 10);
        Classification classification = new Classification();
        if(type!=null){
            classification.setType(type);
        }
        if(region!=null){
            classification.setRegion(region);
        }
        List<String[]> musicVideoList = musicVideoService.searchMusicVideoByClassification(classification);
        return new PageInfo(musicVideoList);
    }
}
