package controller.music.exhibition;

import entity.Music;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import service.music.DetailsService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 蒋靓峣 5.25创建
 * 点击显示歌曲、MV 、专辑、歌单的MV
 * */
@Controller
public class Detail {
    private static final Logger logger = LoggerFactory.getLogger(Detail.class);
    @Resource(name = "DetailsService")
    private DetailsService detailsService;

    public Map<String,Object> showMusic(@RequestParam(value = "musicId")String musicId){
        Music music = new Music();
        music.setId(Integer.parseInt(musicId));
        return detailsService.showMusic(music);
    }
}
