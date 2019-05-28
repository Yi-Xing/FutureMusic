package controller.music.search;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.MusicService;
import service.music.MusicVideoService;
import service.music.PlayService;
import service.music.SingerService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 根据分类搜索
 * 歌曲、歌单、MV
 * @author 蒋靓峣 5.21
 */
@Controller
public class SearchByClassification {
    @Resource(name = "PlayService")
    private PlayService playService;
    @Resource(name = "MusicVideoService")
    private MusicVideoService musicVideoService;
    @Resource(name = "SingerService")
    private SingerService singerService;
    private MusicService musicService;
    /**
     * 根据分类中的地区查找歌曲 排行榜
     */
    @RequestMapping("/searchMusicByRegion")
    @ResponseBody
    public PageInfo searchMusicByRegion(@RequestParam(required = false,value = "pn", defaultValue = "1") Integer pn,
                                                @RequestParam(value = "region",defaultValue = "")String region) {
        PageHelper.startPage(pn, 10);
        List<Music> musicList= musicService.selectListMusicByRegion(region);
        return new PageInfo(musicList);
    }
    /**
     * 根据分类中的流派查找歌曲
     */
    @RequestMapping("/searchMusicByType")
    @ResponseBody
    public PageInfo searchMusicByType(@RequestParam(required = false,value = "pn", defaultValue = "1") Integer pn,
                                      @RequestParam(value = "type",defaultValue = "")String type) {
        PageHelper.startPage(pn, 10);
        List<Music> musicList= musicService.selectListMusicByMusicType(type);
        return new PageInfo(musicList);
    }

    /**
     * 根据分类查找歌手
     */
    @RequestMapping("/searchSingerByRegion")
    @ResponseBody
    public PageInfo searchSingerByRegion(@RequestParam(required = false,value = "pn", defaultValue = "1") Integer pn,
                                          @RequestParam(value = "singerRegion")String singerRegion){
        PageHelper.startPage(pn,10);
        List<ShowSinger> showSingerList = singerService.exhibitionSingersByRegion(singerRegion);
        return new PageInfo(showSingerList) ;
    }
//    /**
//     * 根据分类查找歌单
//     ** @param pn
//     * @param classification
//     * @return
//     */
//    @RequestMapping("/searchMusicByClassification")
//    @ResponseBody
//    public PageInfo searchSongListByClassification(@RequestParam(required = false,value = "pn", defaultValue = "1") Integer pn,
//                                                   @RequestParam(value = "classification")Classification classification) {
//        PageHelper.startPage(pn, 10);
//        Map<SongList, User> songListSingerMap= playService.selectListSongListByClassification(classification);
//        List<Map<SongList, User>> list=new ArrayList<>();
//        list.add(songListSingerMap);
//        return new PageInfo(list);
//    }

    /**
     * 根据分类查找MV
     * @param pn 分页
     * @param region 地区
     * @param type 流派
     * @return  展示mv的信息
     */
    @RequestMapping("/searchMusicVideoByClassification")
    @ResponseBody
    public PageInfo searchMusicVideoByClassification(@RequestParam(required = false,value = "pn", defaultValue = "1") Integer pn,
                                                   @RequestParam(value = "region")String region,@RequestParam(value = "type")String type) {
        PageHelper.startPage(pn, 10);
        Classification classification = new Classification();
        if(type!=null){
            classification.setType(type);
        }
        if(region!=null){
            classification.setRegion(region);
        }
        List<String[]> musicVideoList = musicVideoService.searchMusicVideoByClassification(classification);
//        Map<MusicVideo, User> musicVideoSingerMap= playService.selectListMusicVideoListByClassification(classification);
//        List<Map<MusicVideo, User>> list=new ArrayList<>();
//        list.add(musicVideoSingerMap);
        return new PageInfo(musicVideoList);
    }
}
