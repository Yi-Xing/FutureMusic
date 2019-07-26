package controller.music.search;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.MusicVideoService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *  MV的相关搜索
 * @author 5.21 蒋靓峣创建
 * */
@Controller
public class SearchMusicVideo {
    @Resource(name = "MusicVideoService")
    MusicVideoService musicVideoService;
    /**
     *分类查找MV 根据名字查找MV
     */
    @RequestMapping("/searchMusicVideoByClassification")
    public String searchMusicVideoByClassification(@RequestParam(value = "pn",defaultValue = "1")Integer pn,
                                                     @RequestParam(value = "language",defaultValue = "")String language ,
                                                     @RequestParam(value = "region",defaultValue = "")String region ,
                                                     @RequestParam(value = "type",defaultValue = "")String type,
                                                     @RequestParam(value = "gender",defaultValue = "")String gender,
                                                     Model model)  {
        PageHelper.startPage(pn, 10);
        Classification classification = new Classification();
        classification.setLanguages(language);
        classification.setRegion(region);
        classification.setGender(gender);
        classification.setType(type);
        List<MusicVideoExt> musicVideoList = musicVideoService.searchMusicVideoByClassification(classification);
        model.addAttribute("musicVideoList",musicVideoList);
        PageInfo page = new PageInfo(musicVideoList);
        model.addAttribute("pageNum",page.getPageNum());
        return "musicVideo";
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
        List<MusicVideoExt> musicVideoExts = musicVideoService.selectListMusicVideoByVideoName(keyWord);
       if(musicVideoExts==null||musicVideoExts.size()==0){
           return null;
       }
        return new PageInfo(musicVideoExts);
    }
}
