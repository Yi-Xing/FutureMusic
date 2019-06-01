package controller.music.search;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.SongList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author  蒋靓峣
 */
@Controller
public class SearchSongList {
    @Resource(name = "SongListService")
    SongListService songListService;
    /**
     * 显示歌单的详细信息
     * @param songListId 传入歌单或专辑的id
     * @return Map<String,Object>
     */
    @RequestMapping(value = "/showSongListDetail")
    @ResponseBody
    public Map<String,Object> showSongListDetail(@RequestParam(value = "songListId",defaultValue = "1")String songListId, Model model){
        Map<String,Object> songListDetail = songListService.showSongListDetail(songListId);
        model.addAttribute("songListDetail",songListDetail);
        return songListDetail;
    }

    /**
     * 点击搜索专辑/专辑，ajax
     *
     * @param pn 接收请求
     * @return List<SongList>返回匹配到的歌曲
     */
    @RequestMapping(value = "/showSongListByClassification")
    public String showSongListByClassification(@RequestParam(required = false,value = "pn", defaultValue = "1") Integer pn,
                                       @RequestParam(value = "classification",defaultValue = "国语")String classification,Model model) {
        PageHelper.startPage(pn, 10);
        Map<String,Object>  showSongList= songListService.showSongListByClassification(classification);
        List<Map<String,Object>> resultSongListMap = new ArrayList<>();
        resultSongListMap.add(showSongList);
        PageInfo page = new PageInfo(resultSongListMap, 5);
        model.addAttribute("musicListPage",page);
        return "testjjy";
    }
}
