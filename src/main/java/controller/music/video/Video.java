package controller.music.video;

import controller.music.song.Song;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import service.music.video.VideoService;

import javax.annotation.Resource;

/**
 * MV的controller层
 *
 * @author 5.11 蒋靓峣创建
 * */
@Controller
public class Video {
    private static final Logger logger = LoggerFactory.getLogger(Song.class);
    @Resource(name = "VideoService")
    private VideoService videoService;

    /**
     * keyup执行此方法，ajax
     *
     * @param keyWord         接收搜索的关键字(歌名)
     */
}
