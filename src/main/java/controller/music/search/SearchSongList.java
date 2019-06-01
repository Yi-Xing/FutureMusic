package controller.music.search;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.ShowSinger;
import entity.SongList;
import entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  蒋靓峣
 */
@Controller
public class SearchSongList {
    @Resource(name = "SingerService")
    SingerService singerService;
    @Resource(name = "MusicVideoService")
    MusicVideoService musicVideoService;
    @Resource(name = "SongListService")
    SongListService songListService;
    @Resource(name = "ActivityService")
    ActivityService activityService;
    /**
     * 显示歌单的详细信息
     * @param songListId 传入歌单或专辑的id
     * @return Map<String,Object>
     */
    @RequestMapping(value = "/showSongListDetail")
    @ResponseBody
    public Map<String,Object> showSongListDetail(@RequestParam(value = "songListId",defaultValue = "1")int songListId, Model model){
        SongList songList = new SongList();
        songList.setId(songListId);
        Map<String,Object> songListDetail = songListService.showSongList(songList);
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
    @ResponseBody
    public PageInfo showSongListByClassification(@RequestParam(required = false,value = "pn", defaultValue = "1") Integer pn,
                                       @RequestParam(value = "classification",defaultValue = "国语")String classification) {
        PageHelper.startPage(pn, 10);
        Map<String,Object>  showSongList= songListService.showSongListByClassification(classification);
        List<Map<String,Object>> resultSongListMap = new ArrayList<>();
        resultSongListMap.add(showSongList);
        PageInfo page = new PageInfo(resultSongListMap, 5);
        return page;
    }
}
