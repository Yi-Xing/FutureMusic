package controller.music.search;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.Music;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.MusicService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
     * @param  request response 传入歌曲的id
     * @return Map<String,Object>
     */
    @RequestMapping(value = "/showMusicDetail")
    @ResponseBody
    public Map<String,Object> showMusicDetail(HttpServletRequest request, HttpServletResponse response){
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
        String pn = request.getParameter("pn");
        String musicRegion = request.getParameter("musicRegion");
        PageHelper.startPage(Integer.parseInt(pn), 10);
        List<Music> musicList= musicService.selectListMusicByRegion(musicRegion);
        return new PageInfo(musicList);
    }
    /**
     * 根据分类中的流派查找歌曲
     */
    @RequestMapping("/searchMusicByType")
    @ResponseBody
    public PageInfo searchMusicByType(HttpServletRequest request) {
        String pn = request.getParameter("pn");
        PageHelper.startPage(Integer.parseInt(pn), 10);
        String musicType = request.getParameter("musicType");
        List<Music> musicList= musicService.selectListMusicByRegion(musicType);
        return new PageInfo(musicList);
    }
    /**
     * 根据分类中的语言查找歌曲
     */
    @RequestMapping("/searchMusicByLanguage")
    @ResponseBody
    public PageInfo searchMusicByLanguage(HttpServletRequest request) {
        String pn = request.getParameter("pn");
        PageHelper.startPage(Integer.parseInt(pn), 10);
        String musicLanguage = request.getParameter("language");
        List<Map<String, String[]>> musicList= musicService.selectListMusicByLanguage(musicLanguage);
        return new PageInfo(musicList);
    }

    /**
     * 根据名字模糊搜索歌曲
     */
    @RequestMapping("/searchMusicByName")
    @ResponseBody
    public PageInfo searchSingerByRegion(HttpServletRequest request) {
        String pn = request.getParameter("pn");
        PageHelper.startPage(Integer.parseInt(pn), 10);
        String keyWord = request.getParameter("keyWord");
        List<Map<String, String[]>> musics = musicService.searchMusicByName(keyWord);
        return new PageInfo(musics);
    }

}
