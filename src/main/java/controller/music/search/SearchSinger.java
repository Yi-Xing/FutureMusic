package controller.music.search;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.SingerExt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.ActivityService;
import service.music.MusicService;
import service.music.MusicVideoService;
import service.music.SingerService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 蒋靓峣 5.30 修改
 */
@Controller
public class SearchSinger {

    @Resource(name = "SingerService")
    SingerService singerService;
    @Resource(name = "MusicVideoService")
    MusicVideoService musicVideoService;
    @Resource(name = "MusicService")
    MusicService musicService;
    @Resource(name = "ActivityService")
    ActivityService activityService;

    /**
     * * 点击搜索歌手，ajax
      *@param keyWord 接收请求
     * @return PageInfo 返回匹配到的专辑、歌曲、歌手、MV信息
     */
    @RequestMapping(value = "/searchListSingerByName")
    @ResponseBody
    public PageInfo searchListSingerByName(@RequestParam(required = false,value = "pn", defaultValue = "1") Integer pn,
                                     @RequestParam(value = "keyWord",defaultValue = "")String keyWord) {
        PageHelper.startPage(pn, 10);
        List<SingerExt> singerList = singerService.exhibitionSingersByName(keyWord);
        PageInfo page = new PageInfo(singerList, 5);
        return page;
    }
    /**
     * 搜索音乐人
     */
    @RequestMapping(value = "/exhibitionSingersByRegion")
    @ResponseBody
    public List<SingerExt> exhibitionSingersByRegion(@RequestParam(value = "singerRegion") String  singerRegion){
        return singerService.exhibitionSingersByRegion(singerRegion);
    }
}
