package controller.music.search;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.Music;
import entity.ShowSinger;
import entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.MusicService;
import service.music.MusicVideoService;
import service.music.SearchService;
import service.music.SingerService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  根据名字搜索
 * @author 5.21 蒋靓峣创建
 * */
@Controller
public class SearchByName {
    @Resource(name = "SearchService")
    private SearchService searchService;
    @Resource(name = "MusicVideoService")
    MusicVideoService musicVideoService;
    @Resource(name = "SingerService")
    private SingerService singerService;
    @Resource(name = "MusicService")
    MusicService musicService;
    /**
     * ajax搜索框智能提示名字（临时测试）
     * @param keyWord 传入的关键字
     * @return List<String> 返回的音乐名称
     * 还会有个显示包括专辑、歌单、歌手、歌曲的名字和用id做连接
     */
    @RequestMapping(value = "/searchByKeyWord")
    @ResponseBody
    public List<Music> searchByKeyWord(@RequestParam(value = "keyWord")String keyWord){
        List<Music> musicList = searchService.selectListMusicByName(keyWord);
        return musicList;
    }
    /**
     * 根据名字模糊搜索歌曲
     * 或者点击导航栏歌曲执行此方法
     * @param keyWord 接收搜索的关键字
     * @param pn      接收搜索的关键字
     * @return PageInfo 返回匹配到的歌曲
     */
    @RequestMapping(value = "/searchListMusic")
    @ResponseBody
    public PageInfo searchListMusic(@RequestParam(required = false, value = "pn", defaultValue = "1") Integer pn,
                                    @RequestParam(value = "keyWord", defaultValue = "") String keyWord) {
        PageHelper.startPage(pn, 10);
        List<Music> musicList = searchService.selectListMusicByName(keyWord);
        PageInfo page = new PageInfo(musicList, 5);
        return page;
    }

    /**
     * 点击搜索MV
     * @param pn keyWord 分页显示，接收参数和关键字
     * @return PageInfo 返回带MV信息的分页数据
     */
    @RequestMapping(value = "/searchListMusicVideo")
    @ResponseBody
    public PageInfo searchListMusicVideo(@RequestParam(required = false,value = "pn", defaultValue = "1") Integer pn,
                                         @RequestParam(value = "keyWord",defaultValue = "")String keyWord) {
        PageHelper.startPage(pn, 10);
        List<String[]> musicVideos = musicVideoService.exhibitionMusicVideo();
        PageInfo page = new PageInfo(musicVideos, 5);
        return page;
    }

    /**
     * 点击搜索歌手，ajax
     * @param keyWord 接收请求
     * @return PageInfo 返回匹配到的专辑、歌曲、歌手、MV信息
     */
    @RequestMapping(value = "/searchListSinger")
    @ResponseBody
    public PageInfo searchListSinger(@RequestParam(required = false,value = "pn", defaultValue = "1") Integer pn,
                                     @RequestParam(value = "keyWord",defaultValue = "")String keyWord) {
        PageHelper.startPage(pn, 10);
        List<ShowSinger> singerList = singerService.exhibitionSingersByName(keyWord);
        PageInfo page = new PageInfo(singerList, 5);
        return page;
    }
    /**
     * 点击搜索专辑，ajax
     *
     * @param pn 接收请求
     * @return List<SongList>返回匹配到的歌曲
     */
    @RequestMapping(value = "/searchListSongList")
    @ResponseBody
    public PageInfo searchListSongList(@RequestParam(required = false,value = "pn", defaultValue = "1") Integer pn,
                                       @RequestParam(value = "keyWord",defaultValue = "")String keyWord) {
        PageHelper.startPage(pn, 10);
        List<User> singerList = searchService.selectSingerByName(keyWord);
        PageInfo page = new PageInfo(singerList, 5);
        return page;
    }

    /**
     * 搜索专辑
     */
    public List<Map<String,String[]>> searchSonglistByName(@RequestParam(required = false,value = "pn", defaultValue = "1") Integer pn,
                                         @RequestParam(value = "keyWord",defaultValue = "")String keyWord){

        return null;
    }
    /**
     * 搜索音乐
     * @param pn 当前页数
     * @param keyWord 关键字
     */
    @RequestMapping("/searchMusicByName")
    @ResponseBody
    public List<Map<String, String[]>> searchMusicByName(@RequestParam(required = false,value = "pn", defaultValue = "1") Integer pn,
                                     @RequestParam(value = "keyWord",defaultValue = "")String keyWord) {
        List<Map<String, String[]>> singerList = musicService.searchMusicByName(keyWord);
        return singerList;
    }
    /**
     * 搜索歌手(通过名字）
     */
    @RequestMapping(value = "/searchSingerByNames")
    @ResponseBody
    public List<ShowSinger> searchSingerByNames(@RequestParam(required = false,value = "pn", defaultValue = "1") Integer pn,
                                               @RequestParam(value = "keyWord",defaultValue = "")String keyWord){
        List<ShowSinger> singers = singerService.exhibitionSingersByName(keyWord);
        return singers;
    }
}
