package controller.music.exhibition;

import entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.ShowCommentService;
import service.music.SearchService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 歌曲的controller层
 *      歌曲详细信息专辑、歌单、歌曲、歌手
 *      歌曲的评论
 * @author 5.12 蒋靓峣创建
 * */

@Controller
public class CommentInformation {
    private static final Logger logger = LoggerFactory.getLogger(CommentInformation.class);
    @Resource(name = "SearchService")
    private SearchService searchService;
    @Resource(name = "ShowCommentService")
    private ShowCommentService informationService;
    public static State state;

    /**
     * @param musicId 根据id查询详细信息
     * @return Music 返回所有信息
     * */
    @RequestMapping(value = "/musicInformation")
    @ResponseBody
    public Music musicInformation(@RequestParam(value = "musicId",defaultValue = "-1")String musicId){
       return searchService.selectMusicById(musicId);
    }
    /**
     * @param songListId 根据id查询详细信息专辑或歌单
     * @return SongList 返回所有信息
     * */
    @RequestMapping(value = "/songListInformation")
    @ResponseBody
    public SongList songListInformation(@RequestParam(value = "songListId",defaultValue = "-1")String songListId){
        return searchService.selectSongListById(songListId);
    }
    /**
     * @param musicVideoId 根据id查询详细信息专辑或歌单
     * @return MusicVideo 返回所有信息
     * */
    @RequestMapping(value = "/musicVideoInformation")
    @ResponseBody
    public MusicVideo musicVideoInformation(@RequestParam(value = "musicVideoId",defaultValue = "-1")String musicVideoId){
        return searchService.selectMusicVideoById(musicVideoId);
    }
    /**
     * @param musicId 根据id查询详细信息专辑或歌单
     * @return List<Comment> 返回所有信息
     * */
    @RequestMapping(value = "/musicCommentInformation")
    @ResponseBody
    public List<Map<Comment,Comment>> musicCommentInformation(@RequestParam(value = "musicId",defaultValue = "-1")String musicId){
        informationService.commentByMusicId(musicId);
        return null;
    }
}
