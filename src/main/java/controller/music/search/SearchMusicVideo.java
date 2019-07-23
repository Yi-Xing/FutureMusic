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
        Map<String,Object> musicVideoInformation = musicVideoService.showMusicVideo(musicVideo);
        model.addAttribute("musicVideoInfo",musicVideoInformation);
        return "musicVideoInfo";
    }

    /**
     *分类查找MV
     * @param request 页面请求
     * @return PageInfo 分页显示所有的信息
     */
    @RequestMapping("/searchMusicVideoByClassification")
    @ResponseBody
    public PageInfo searchMusicVideoByClassification(HttpServletRequest request){
        int pn = Integer.parseInt(request.getParameter("pn"));
        PageHelper.startPage(pn, 10);
        String language = request.getParameter("language");
        String region = request.getParameter("region");
        String gender = request.getParameter("gender");
        String type = request.getParameter("type");
        Classification classification = new Classification();
        classification.setLanguages(language);
        classification.setRegion(region);
        classification.setGender(gender);
        classification.setType(type);
        List<MusicVideoExt> musicVideoList = musicVideoService.searchMusicVideoByClassification(classification);
        return new PageInfo(musicVideoList);
    }
    /**
     *根据名字搜索MV
     * @param request 页面请求
     * @return PageInfo 分页显示MV信息
     */
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
