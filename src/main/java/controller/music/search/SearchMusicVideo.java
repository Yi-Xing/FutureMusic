package controller.music.search;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.MusicVideoService;

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
     * @return  展示mv的信息
     */
    @RequestMapping("/searchMusicVideoByClassification")
    @ResponseBody
    public PageInfo searchMusicVideoByClassification(HttpServletRequest request){
        int pn = Integer.parseInt(request.getParameter("pn"));
        PageHelper.startPage(pn, 10);
        String musicVideoType =request.getParameter("musicVideoType");
        Classification classification = new Classification();
        String musicVideoRegion = request.getParameter("musicVideoRegion");
        if(musicVideoType!=null){
            classification.setType(musicVideoType);
        }
        if(musicVideoRegion!=null){
            classification.setRegion(musicVideoRegion);
        }
        List<MusicVideoExt> musicVideoList = musicVideoService.searchMusicVideoByClassification(classification);
        return new PageInfo(musicVideoList);
    }
    @RequestMapping("/selectMusicVideoByName")
    @ResponseBody
    public PageInfo selectListMusicVideoByVideoName(HttpServletRequest request){
        int pn = Integer.parseInt(request.getParameter("pn"));
        PageHelper.startPage(pn, 10);
        String keyWord = request.getParameter("keyWord");
        MusicVideo musicVideo = new MusicVideo();
        musicVideo.setName(keyWord);
        List<MusicVideoExt> musicVideoExts = musicVideoService.selectListMusicVideoByVideoName(musicVideo);
        return new PageInfo(musicVideoExts);
    }
}
