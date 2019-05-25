package controller.music.exhibition;

import entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.ShowCommentService;

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
public class ShowComment {
    @Resource(name = "ShowCommentService")
    private ShowCommentService showCommentService;

    @RequestMapping(value = "/showMusicComment")
    @ResponseBody
    public Map<Comment,Comment> showMusicComment(@RequestParam(value = "musicId",defaultValue = "1")int musicId){
        return  showCommentService.commentByMusicId(musicId);
    }
    @RequestMapping(value = "/showMusicVideoComment")
    @ResponseBody
    public Map<Comment,Comment> showMusicVideoComment(@RequestParam(value = "musicVideoId",defaultValue = "1")int musicVideoId){
        return showCommentService.commentByMusicVideoId(musicVideoId);
    }
    @RequestMapping(value = "/showSongListComment")
    @ResponseBody
    public Map<Comment,Comment> showSongListComment(@RequestParam(value = "songListId",defaultValue = "1")int songListId){
        return showCommentService.commentByListSongId(songListId);
    }
    @RequestMapping(value = "/showMusicLastComment")
    @ResponseBody
    public Map<Comment,Comment> showMusicLastComment(@RequestParam(value = "musicId",defaultValue = "1")int musicId){
        return  showCommentService.commentLastByMusicId(musicId);
    }
    @RequestMapping(value = "/showMusicVideoLastComment")
    @ResponseBody
    public Map<Comment,Comment> showMusicVideoLastComment(@RequestParam(value = "musicVideoId",defaultValue = "1")int musicVideoId){
        return showCommentService.commentLastByMusicVideoId(musicVideoId);
    }
    @RequestMapping(value = "/showSongListLastComment")
    @ResponseBody
    public Map<Comment,Comment> showSongListLastComment(@RequestParam(value = "songListId",defaultValue = "1")int songListId){
        return showCommentService.commentLastByListSongId(songListId);
    }
    @RequestMapping(value = "/showAllReply")
    @ResponseBody
    public List<Comment> showAllReply(@RequestParam(value = "replyId",defaultValue = "1")int reply){
        return showCommentService.allReply(reply);
    }
}
