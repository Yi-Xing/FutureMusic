/*
package controller.music.search;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.ShowSongListExt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

*/
/**
 * @author 蒋靓峣
 *//*

@Controller
public class SearchSongList {
    @Resource(name = "SongListService")
    SongListService songListService;

    */
/**
     * 显示歌单的详细信息
     *//*

    @RequestMapping(value = "/showSongListDetail")
    @ResponseBody
    public Map <String, Object> showSongListDetail(HttpServletRequest request,Model model) {
        String songListId = request.getParameter("songListId");
        Map <String, Object> songListDetail = songListService.showSongListDetail(songListId);
        model.addAttribute("songListDetail", songListDetail);
        return songListDetail;
    }
    */
/**
     * 通过名字点击搜索歌单或专辑，返回扩展类
     * 专歌单或专辑的id、名字、图片、音乐数、收听次数
     *//*

    public PageInfo searchSongListByName(HttpServletRequest request){
        String keyWord = request.getParameter("keyWord");
        List<ShowSongListExt> showSongListExts = songListService.
    }
    */
/**
     * 点击搜索专辑/专辑，ajax
     * @return List<SongList>返回匹配到的歌曲
     *//*

    @RequestMapping(value = "/showSongListByClassification")
    public String showSongListByClassification(HttpServletRequest request, Model model) {
        String classification = request.getParameter("classification");
        String pn = request.getParameter("pn");
        PageHelper.startPage(Integer.parseInt(pn), 10);
        Map <String, Object> showSongList = songListService.showSongListByClassification(classification);
        List <Map <String, Object>> resultSongListMap = new ArrayList <>();
        resultSongListMap.add(showSongList);
        PageInfo page = new PageInfo(resultSongListMap, 5);
        model.addAttribute("musicListPage", page);
        return "testjjy";
    }
}
*/
