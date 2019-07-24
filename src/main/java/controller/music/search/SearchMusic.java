package controller.music.search;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.Classification;
import entity.Music;
import entity.MusicExt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.MusicService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 根据分类搜索
 * 歌曲、歌单、MV
 * @author 蒋靓峣 5.21  7.19修改
 */
@Controller
public class SearchMusic {
    @Resource(name = "MusicService")
    private MusicService musicService;

    /**
     * 显示歌曲的详细信息,包括歌词，
     * @return Map<String,Object>
     */
    @RequestMapping(value = "/showMusicDetail")
    @ResponseBody
    public Map<String,Object> showMusicDetail(HttpServletRequest request){
        String musicId = request.getParameter("musicId");
        Music music = new Music();
        music.setId(Integer.parseInt(musicId));
        return musicService.showMusic(music);
    }
    /**
     * 根据分类中的地区查找歌曲 排行榜
     */
    @RequestMapping("/searchMusicByRegion")
    @ResponseBody
    public PageInfo searchMusicByRegion(HttpServletRequest request) {
        int pn = Integer.parseInt(request.getParameter("pn"));
        String musicRegion = request.getParameter("musicRegion");
        PageHelper.startPage(pn, 10);
        List<MusicExt> musicList= musicService.selectListMusicByRegion(musicRegion);
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
        List<MusicExt> musicList= musicService.selectListMusicByRegion(type);
        return new PageInfo(musicList);
    }
    /**
     * 根据名字模糊搜索歌曲
     */
    @RequestMapping("/searchMusicByName")
    @ResponseBody
    public PageInfo searchSingerByRegion(HttpServletRequest request) {
        int pn = Integer.parseInt(request.getParameter("pn"));
        PageHelper.startPage(pn, 10);
        String keyWord = request.getParameter("keyWord");
        List<MusicExt> musics = musicService.searchMusicByName(keyWord);
        return new PageInfo(musics);
    }
}