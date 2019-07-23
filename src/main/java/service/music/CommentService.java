package service.music;

import com.fasterxml.jackson.annotation.JsonFormat;
import entity.Comment;
import entity.CommentExt;
import entity.User;
import mapper.CommentMapper;
import mapper.UserMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 蒋靓峣 7.23 创建
 */
@Service("CommentService")
public class CommentService {

    @Resource(name = "CommentMapper")
    CommentMapper commentMapper;
    @Resource(name = "UserMapper")
    UserMapper userMapper;
    /**
     * 查找评论的内容
     */
    public List<CommentExt> getCommentExt(Comment comment){
        List<Comment> comments = commentMapper.selectListComment(comment);
        List<CommentExt> commentExts = new ArrayList<>();
        for(Comment c:comments){
            CommentExt commentExt = transform(c);
            commentExts.add(commentExt);
        }
        return commentExts;
    }
    /**
     * 主评论下的所有回复
     */
    public CommentExt transform(Comment c){
        CommentExt commentExt = new CommentExt();
        commentExt.setCommentId(c.getId());
        commentExt.setContent(c.getContent());
        commentExt.setDate(c.getDate());
        commentExt.setFabulous(c.getFabulous());
        commentExt.setHasReply(c.getReply());
        //获取评论的用户的id、头像和名字
        User user = new User();
        user.setId(c.getUserId());
        User u = userMapper.selectUser(user).get(0);
        commentExt.setCommentUserName(u.getName());
        commentExt.setCommentPhoto(u.getHeadPortrait());
        //获取回复者的 id 名字
        if(c.getReply()!=0){
            Comment com = new Comment();
            com.setId(c.getReply());
            Comment comment = commentMapper.selectListComment(com).get(0);
            User user1 = new User();
            user1.setId(comment.getUserId());
            User user2 = userMapper.selectUser(user1).get(0);
            commentExt.setReplyId(comment.getUserId());
            commentExt.setReplyName(user2.getName());
            commentExt.setReplyPhoto(user2.getHeadPortrait());
        }
        commentExt.setAllSubCommentExtList(getAllReply(c.getId(),new ArrayList<CommentExt>()));
        return commentExt;
    }
    /**
     * 获取评论的全部回复
     */
    public List<CommentExt> getAllReply(int commentId,List<CommentExt> commentExts){
        Comment comment = new Comment();
        comment.setReply(commentId);
        List<Comment> comments = commentMapper.selectListComment(comment);
        System.out.println(comments);
        if(comment!=null||comments.size()!=0) {
            for (Comment c : comments) {
                commentExts.add(transform(c));
                getAllReply(c.getId(),commentExts);
            }
            return commentExts;
        }
        return null;
    }

    /**
     * 第一个传入评论，第二个辅助传值
     */
    public List<CommentExt> getCommentExtsList(List<CommentExt> commentExts1,List<CommentExt> commentExts2) {
        //先将这些评论全部取出来
        for(CommentExt ce:commentExts1){
            if(ce.getAllSubCommentExtList()!=null){
                commentExts2.add(ce);
                getCommentExtsList(ce.getAllSubCommentExtList(),commentExts2);
            }
            commentExts2.add(ce);
        }
        return commentExts2;
    }
    /**
     * 传入评论，将评论按照日期排序
     */
    public List<CommentExt> sortComment(List<CommentExt> commentExts){
        List<CommentExt> sortCommentExts = new ArrayList<>();
        return sortCommentExts;
    }
}
