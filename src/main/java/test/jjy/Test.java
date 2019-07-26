package test.jjy;

import controller.music.exhibition.MusicIndex;
import entity.Music;
import entity.MusicVideoExt;
import mapper.MusicMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.MusicVideoService;
import service.music.SearchService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 蒋靓峣
 */
@Controller
public class Test {
    @Resource(name = "MusicVideoService")
    MusicVideoService musicVideoService;
    @RequestMapping("/musicVideoTest")
    @ResponseBody
    public  String musicVideoIndex(HttpServletRequest request, Model model) {
        List<MusicVideoExt> musicVideoExts =  musicVideoService.exhibitionMusicVideo();
        model.addAttribute("test",musicVideoExts);
        return "jjy";
    }



}
