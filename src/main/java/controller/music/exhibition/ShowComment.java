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

    /**
     * 音乐的评论
     * @param musicId 音乐的id
     * @return Map<Comment,Comment> 返回评论以及对应的回复
     */
    @RequestMapping(value = "/showMusicComment")
    @ResponseBody
    public Map<Comment,Comment> showMusicComment(@RequestParam(value = "musicId",defaultValue = "1")int musicId){
        return  showCommentService.commentByMusicId(musicId);
    }

    /**
     * MV的评论
     * @param musicVideoId MV的id
     * @return Map<Comment,Comment> 返回评论以及对应的回复
     */
    @RequestMapping(value = "/showMusicVideoComment")
    @ResponseBody
    public Map<Comment,Comment> showMusicVideoComment(@RequestParam(value = "musicVideoId",defaultValue = "1")int musicVideoId){
        return showCommentService.commentByMusicVideoId(musicVideoId);
    }

    /**
     * 歌单或专辑的评论
     * @param songListId 歌单的id
     * @return Map<Comment,Comment> 返回评论以及对应的回复
     */
    @RequestMapping(value = "/showSongListComment")
    @ResponseBody
    public Map<Comment,Comment> showSongListComment(@RequestParam(value = "songListId",defaultValue = "1")int songListId){
        return showCommentService.commentByListSongId(songListId);
    }

    /**
     * 音乐的最新评论
     * @param musicId 音乐的id
     * @return Map<Comment,Comment> 返回评论以及对应的回复
     */
    @RequestMapping(value = "/showMusicLastComment")
    @ResponseBody
    public Map<Comment,Comment> showMusicLastComment(@RequestParam(value = "musicId",defaultValue = "1")int musicId){
        return  showCommentService.commentLastByMusicId(musicId);
    }

    /**
     * MV的评论
     * @param musicVideoId MV的评论
     * @return Map<Comment,Comment> 返回评论以及对应的回复
     */
    @RequestMapping(value = "/showMusicVideoLastComment")
    @ResponseBody
    public Map<Comment,Comment> showMusicVideoLastComment(@RequestParam(value = "musicVideoId",defaultValue = "1")int musicVideoId){
        return showCommentService.commentLastByMusicVideoId(musicVideoId);
    }

    /**
     * 歌单对应的全部评论
     * @param songListId 歌单的id
     * @return Map<Comment,Comment> 返回评论以及对应的回复
     */
    @RequestMapping(value = "/showSongListLastComment")
    @ResponseBody
    public Map<Comment,Comment> showSongListLastComment(@RequestParam(value = "songListId",defaultValue = "1")int songListId){
        return showCommentService.commentLastByListSongId(songListId);
    }

    /**
     * 主评论下对应的全部id
     * @param reply 主评论的id
     * @return 全部的回复，没有排序
     */
    @RequestMapping(value = "/showAllReply")
    @ResponseBody
    public List<Comment> showAllReply(@RequestParam(value = "replyId",defaultValue = "1")int reply){
        return showCommentService.allReply(reply);
    }
}
