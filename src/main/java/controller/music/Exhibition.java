package controller.music;

import entity.Music;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.ExhibitionService;
import service.music.SearchService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 页面展示相关的层
 *  2.点击显示歌曲排行榜
 *  3.页面展示各种分类的显示
 * @author 5.16 蒋靓峣创建
 * */
public class Exhibition {
    private static final Logger logger = LoggerFactory.getLogger(Song.class);
    @Resource(name = "SearchService")
    private SearchService songService;
    @Resource(name = "ExhibitionService")
    private ExhibitionService exhibitionService;
    /**
     * @param request 根据指定分类查找歌曲或专辑
     *          ajax请求，只更新下面的结果
     * @return List<MusicVideo> 返回符合条件的MV集合
     * */
    @RequestMapping(value = "/searchListMusicByClassification")
    @ResponseBody
    public List<Music> searchListMusicByClassification(HttpServletRequest request){
        String classification = request.getParameter("classification");
        List<Music> musicList = songService.selectListMusicByClassification(classification);
        return musicList;
    }
}
