package controller.music.search;

import entity.Comment;
import entity.CommentExt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import service.music.CommentService;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class SearchComment {
    @Resource(name = "CommentService")
    CommentService commentService;
    /**
     * 查找评论音乐、专辑、MV通用
     * type= 0
     */
    @RequestMapping("/searchCommentByMusicId")
    public List<CommentExt> searchCommentByMusicId(int musicId,int type){
        Comment comment = new Comment();
        comment.setMusicId(musicId);
        comment.setType(type);
        return commentService.getCommentExt(comment);
    }

}
