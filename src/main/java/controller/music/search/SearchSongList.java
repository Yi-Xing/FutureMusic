
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
 * @author 蒋靓峣 7/23创建
*/

@Controller
public class SearchSongList {
    @Resource(name = "SongListService")
    SongListService songListService;

    /**
     * 通过名字点击搜索歌单或专辑，返回扩展类
     * @param request 页面请求，接收参数页数、关键字
     * @return PageInfo 包含分页的歌单列表
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

    /**
     * 分类搜索歌单
     * @param request 页面请求，接收参数
     */
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
        PageInfo page = new PageInfo(showSongList, 5);
        return page;
    }
}
