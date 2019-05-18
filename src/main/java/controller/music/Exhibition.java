package controller.music;

import entity.Music;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.ExhibitionService;

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
    private static final Logger logger = LoggerFactory.getLogger(Details.class);
    @Resource(name = "ExhibitionService")
    private ExhibitionService exhibitionService;
    /**
     * @param request 展示指定的歌曲（巅峰榜）
     *              分类：新歌、电音、华语、欧美、日韩
     *                只显示3条数据
     * @return List<Music> 返回符合条件的歌曲集合
     * */
    @RequestMapping(value = "/searchListMusicByClassification")
    @ResponseBody
    public List<Music> searchListMusicByClassification(HttpServletRequest request){
        String classification = request.getParameter("classification");
        List<Music> musicList = exhibitionService.selectListMusicByClassification(classification);
        return musicList;
    }
}
