package controller.music.search;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.Music;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.MusicService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 根据分类搜索
 * 歌曲、歌单、MV
 * @author 蒋靓峣 5.21
 */
@Controller
public class SearchMusic {
    @Resource(name = "MusicService")
    private MusicService musicService;

    /**
     * 显示歌曲的详细信息
     * @param musicId 传入歌曲的id
     * @return Map<String,Object>
     */
    @RequestMapping(value = "/showMusicDetail")
    @ResponseBody
    public Map<String,Object> showMusicDetail(@RequestParam(value = "musicId",defaultValue = "1")String musicId){
        Music music = new Music();
        music.setId(Integer.parseInt(musicId));
        return musicService.showMusic(music);
    }

    /**
     * 根据分类中的地区查找歌曲 排行榜
     */
    @RequestMapping("/searchMusicByRegion")
    @ResponseBody
    public PageInfo searchMusicByRegion(@RequestParam(required = false,value = "pn", defaultValue = "1") Integer pn,
                                                @RequestParam(value = "region",defaultValue = "1")String region) {
        PageHelper.startPage(pn, 10);
        List<Map<String, String[]>> musicList= musicService.selectListMusicByRegion(region);
        return new PageInfo(musicList);
    }
    /**
     * 根据分类中的流派查找歌曲
     */
    @RequestMapping("/searchMusicByType")
    @ResponseBody
    public PageInfo searchMusicByType(@RequestParam(required = false,value = "pn", defaultValue = "1") Integer pn,
                                      @RequestParam(value = "type",defaultValue = "1")String type) {
        PageHelper.startPage(pn, 10);
        List<Map<String, String[]>> musicList= musicService.selectListMusicByRegion(type);
        return new PageInfo(musicList);
    }
    /**
     * 根据分类中的语言查找歌曲
     */
    @RequestMapping("/searchMusicByLanguage")
    @ResponseBody
    public PageInfo searchMusicByLanguage(@RequestParam(required = false,value = "pn", defaultValue = "1") Integer pn,
                                      @RequestParam(value = "language",defaultValue = "1")String language) {
        PageHelper.startPage(pn, 10);
        List<Map<String, String[]>> musicList= musicService.selectListMusicByLanguage(language);
        return new PageInfo(musicList);
    }

    /**
     * 根据名字模糊搜索歌曲
     */
    @RequestMapping("/searchMusicByName")
    @ResponseBody
    public PageInfo searchSingerByRegion(@RequestParam(required = false,value = "pn", defaultValue = "1") Integer pn,@RequestParam(value = "keyWord")String keyWord){
        PageHelper.startPage(pn,10);
        List<Map<String, String[]>> musics = musicService.searchMusicByName(keyWord);
        return new PageInfo(musics);
    }

}
