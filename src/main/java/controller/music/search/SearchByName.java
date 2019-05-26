package controller.music.search;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.Music;
import entity.MusicVideo;
import entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.SearchService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *  根据名字搜索
 * @author 5.21 蒋靓峣创建
 * */
@Controller
public class SearchByName {
    @Resource(name = "SearchService")
    private SearchService searchService;

    /**
     * ajax搜索框智能提示名字
     * @param keyWord 传入的关键字
     * @return List<String> 返回的音乐名称
     */
    @RequestMapping(value = "/searchByKeyWord")
    @ResponseBody
    public List<String> searchByKeyWord(@RequestParam(value = "keyWord")String keyWord){
        List<Music> musicList = searchService.selectListMusicByName(keyWord);
        List<String> names = new ArrayList<>();
        for(Music music:musicList){
            names.add(music.getName());
        }
        return names;
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
        //使用分页插件进行分页查询
        //传入页码和页面的大小
        PageHelper.startPage(pn, 10);
        //start后面紧跟的查询，就是分页查询
        List<Music> musicList = searchService.selectListMusicByName(keyWord);
        //包装查询结果，只需将pageinfo交给页面
        //包括我们查询的数据
        //第二个，传入连续显示的页数
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
        List<MusicVideo> musicVideos = searchService.selectListMusicVideoByVideoName(keyWord);
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
        List<User> singerList = searchService.selectSingerByName(keyWord);
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
}
