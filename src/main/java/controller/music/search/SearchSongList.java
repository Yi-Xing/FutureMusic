
package controller.music.search;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.Classification;
import entity.SongListExt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.SongListService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
/**
 * @author 蒋靓峣 7/23创建
*/

@Controller
public class SearchSongList {
    @Resource(name = "SongListService")
    SongListService songListService;

    /**
     * 通过名字点击搜索歌单或专辑，返回扩展类
     * @return PageInfo 包含分页的歌单列表
    */
    @RequestMapping("/searchSongListByName")
    @ResponseBody
    public PageInfo searchSongListByName(@RequestParam(value = "pn",defaultValue = "1")Integer pn,@RequestParam(value = "keyWord")String keyWord){
        List<String[]> showSongList = songListService.searchSongListByName(keyWord);
        PageHelper.startPage(pn, 10);
        PageInfo page = new PageInfo(showSongList, 5);
        return page;
    }

    /**
     * 分类搜索歌单
     * @return PageInfo 带页数的歌单列表
     */
    @RequestMapping(value = "/showSongListByClassification")
    public String showSongListByClassification(@RequestParam(value = "pn",defaultValue = "1")Integer pn,
                                                 @RequestParam(value = "language",defaultValue = "")String language ,
                                                 @RequestParam(value = "region",defaultValue = "")String region ,
                                                 @RequestParam(value = "type",defaultValue = "")String type,
                                                 @RequestParam(value = "gender",defaultValue = "")String gender,
                                               Model model) {
        Classification classification = new Classification();
        classification.setLanguages(language);
        classification.setRegion(region);
        classification.setGender(gender);
        classification.setType(type);
       List<String[]> showSongList = songListService.showSongListByClassification(classification);
        PageHelper.startPage(pn, 10);
        PageInfo page = new PageInfo(showSongList, 5);
        model.addAttribute("page",page);
        model.addAttribute("songList",showSongList);
        return "songList";
    }
}
