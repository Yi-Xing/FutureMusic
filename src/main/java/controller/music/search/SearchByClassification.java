package controller.music.search;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.ExhibitionService;
import service.music.SearchService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 根据分类搜索
 * 歌曲、歌单、MV
 * @author 蒋靓峣 5.21
 */
@Controller
public class SearchByClassification {
    @Resource(name = "SearchService")
    private SearchService searchService;
    @Resource(name = "ExhibitionService")
    private ExhibitionService exhibitionService;

    /**
     * 根据分类中的地区查找歌曲
     * @param pn
     * @param region
     * @return
     */
    @RequestMapping("/searchMusicByRegion")
    @ResponseBody
    public PageInfo searchMusicByRegion(@RequestParam(required = false,value = "pn", defaultValue = "1") Integer pn,
                                                @RequestParam(value = "region",defaultValue = "")String region) {
        PageHelper.startPage(pn, 10);
        Map<Music, User> musicSingerMap= exhibitionService.selectListMusicByRegion(region);
        List<Map<Music, User>> list=new ArrayList<>();
        list.add(musicSingerMap);
        return new PageInfo(list);
    }
    /**
     * 根据分类中的流派查找歌曲
     * @param pn
     * @param type
     * @return
     */
    @RequestMapping("/searchMusicByType")
    @ResponseBody
    public PageInfo searchMusicByType(@RequestParam(required = false,value = "pn", defaultValue = "1") Integer pn,
                                      @RequestParam(value = "type",defaultValue = "")String type) {
        PageHelper.startPage(pn, 10);
        Map<Music, User> musicSingerMap= exhibitionService.selectListMusicByMusicType(type);
        List<Map<Music, User>> list=new ArrayList<>();
        list.add(musicSingerMap);
        return new PageInfo(list);
    }

    /**
     * 根据分类查找歌单
     ** @param pn
     * @param classification
     * @return
     */
    @RequestMapping("/searchMusicByClassification")
    @ResponseBody
    public PageInfo searchSongListByClassification(@RequestParam(required = false,value = "pn", defaultValue = "1") Integer pn,
                                                   @RequestParam(value = "classification")Classification classification) {
        PageHelper.startPage(pn, 10);
        Map<SongList, User> songListSingerMap= exhibitionService.selectListSongListByClassification(classification);
        List<Map<SongList, User>> list=new ArrayList<>();
        list.add(songListSingerMap);
        return new PageInfo(list);
    }

    /**
     * 根据分类查找MV
     ** @param pn
     * @param classification
     * @return
     */
    @RequestMapping("/searchMusicVideoByClassification")
    @ResponseBody
    public PageInfo searchMusicVideoByClassification(@RequestParam(required = false,value = "pn", defaultValue = "1") Integer pn,
                                                   @RequestParam(value = "classification")Classification classification) {
        PageHelper.startPage(pn, 10);
        Map<MusicVideo, User> musicVideoSingerMap= exhibitionService.selectListMusicVideoListByClassification(classification);
        List<Map<MusicVideo, User>> list=new ArrayList<>();
        list.add(musicVideoSingerMap);
        return new PageInfo(list);
    }
}
