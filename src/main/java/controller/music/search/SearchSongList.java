
package controller.music.search;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.Classification;
import entity.SongListExt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.SongListService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
/**
 * @author 蒋靓峣
*/



@Controller
public class SearchSongList {
    @Resource(name = "SongListService")
    SongListService songListService;

/**
 * 显示歌单的详细信息
*/

    @RequestMapping(value = "/showSongListDetail")
    @ResponseBody
    public SongListExt showSongListDetail(HttpServletRequest request,Model model) {
        String songListId = request.getParameter("songListId");
        SongListExt songListDetail = songListService.songListDetail(Integer.parseInt(songListId));
        model.addAttribute("songListDetail", songListDetail);
        return songListDetail;
    }
/**
     * 通过名字点击搜索歌单或专辑，返回扩展类
     * 专歌单或专辑的id、名字、图片、音乐数、收听次数
*/
    @RequestMapping("/searchSongListByName")
    @ResponseBody
    public PageInfo searchSongListByName(HttpServletRequest request){
        String keyWord = request.getParameter("keyWord");
        List<String[]> showSongList = songListService.searchSongListByName(keyWord);
        String pn = request.getParameter("pn");
        PageHelper.startPage(Integer.parseInt(pn), 10);
        PageInfo page = new PageInfo(showSongList, 5);
        return page;
    }

    @RequestMapping(value = "/showSongListByClassification")
    @ResponseBody
    public PageInfo showSongListByClassification(HttpServletRequest request) {
        String pn = request.getParameter("pn");
        PageHelper.startPage(Integer.parseInt(pn), 10);
        String gender = request.getParameter("gender");
        String type = request.getParameter("type");
        String language = request.getParameter("language");
        String region = request.getParameter("region");
        Classification classification = new Classification();
        classification.setLanguages(language);
        classification.setRegion(region);
        classification.setGender(gender);
        classification.setType(type);
       List<String[]> showSongList = songListService.showSongListByClassification(classification);
        List <List<String[]>> resultSongListMap = new ArrayList <>();
        resultSongListMap.add(showSongList);
        PageInfo page = new PageInfo(resultSongListMap, 5);
        return page;
    }


}
