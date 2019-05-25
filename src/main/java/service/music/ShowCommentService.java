package service.music;

import entity.Comment;
import mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 蒋靓峣 5.23创建
 */
@Service(value = "ShowCommentService")
public class ShowCommentService {
    private static final Logger logger = LoggerFactory.getLogger(SearchService.class);
    @Resource(name = "CommentMapper")
    CommentMapper  commentMapper;

    /**
     * 查找音乐对应的精彩评论即点赞最多的评论
     * 果评论不是独立评论，显示出来回复的是哪个评论
     * @param musicId
     * @return
     */
    public Map<Comment,Comment> commentByMusicId(String musicId){
        Comment comment =new Comment();
        comment.setMusicId(Integer.parseInt(musicId));
        comment.setType(1);
        comment.setFabulous(1);
        return selectComment(comment);
    }
    /**
     * 查找音乐的最新评论
     * @param musicId
     * @return  Map<Comment,Comment> 返回评论和回复的对应
     */
    public Map<Comment,Comment> commentLastByMusicId(String musicId){
        Comment comment =new Comment();
        comment.setMusicId(Integer.parseInt(musicId));
        comment.setType(1);
        comment.setDate((new Date()));
        return selectComment(comment);
    }
    /**
     * 查找MV对应的精彩评论即点赞最多的评论
     * 果评论不是独立评论，显示出来回复的是哪个评论
     * @param musicVideoId MV的id
     * @return Map<Comment,Comment> 评论与对应的回复
     */
    public Map<Comment,Comment> commentByMusicVideoId(String musicVideoId){
        Comment comment =new Comment();
        comment.setMusicId(Integer.parseInt(musicVideoId));
        comment.setType(2);
        comment.setFabulous(1);
        return selectComment(comment);
    }

    /**
     * 查找MV的最新评论
     * @param musicVideoId
     * @return  Map<Comment,Comment> 返回评论和回复的对应
     */
    public Map<Comment,Comment> commentLastByMusicVideoId(String musicVideoId){
        Comment comment =new Comment();
        comment.setMusicId(Integer.parseInt(musicVideoId));
        comment.setType(2);
        comment.setDate((new Date()));
        return selectComment(comment);
    }
    /**
     * 查找MV对应的精彩评论即点赞最多的评论
     * 果评论不是独立评论，显示出来回复的是哪个评论
     * @param listSongId
     * @return
     */
    public Map<Comment,Comment> commentByListSongId(String listSongId){
        Comment comment =new Comment();
        comment.setMusicId(Integer.parseInt(listSongId));
        comment.setType(3);
        comment.setFabulous(1);
        return selectComment(comment);
    }

    /**
     * 查找MV的最新评论
     * @param listSongId
     * @return  Map<Comment,Comment> 返回评论和回复的对应
     */
    public Map<Comment,Comment> commentLastByListSongId(String listSongId){
        Comment comment =new Comment();
        comment.setMusicId(Integer.parseInt(listSongId));
        comment.setType(3);
        comment.setDate((new Date()));
        return selectComment(comment);
    }
    /**
     * 从这里开始以及下面的都是不能直接用的
     * 查找一个评论（主评论）的全部回复
     * @param reply 要下哈寻的著评论的id
     * @return List<Comment> 返回查找到的主评论下的所有评论集合
     */
    public List<Comment> allReply(int reply) {
        //先根据主评论查找出全部的id
        // 再将所有评论查找出全部的对应关系和所需要的对应数据
        Comment comment = new Comment();
        comment.setReply(reply);
        List<Comment> commentList = new ArrayList<>();
        List<Comment> comments = commentMapper.selectListComment(comment);
        if (comments != null) {
            commentList.addAll(comments);
            return getAllReply(comments,commentList);
        }
        return null;
    }

    /**
     * 递归实现遍历主评论下的所有评论
     * @param start 主评论下的第一级回复
     * @param result 主评论下的所有回复
     * @return
     */
    private List<Comment> getAllReply(List<Comment> start,List<Comment> result){
        if(start==null){
            return null;
        } else {
            for(Comment c:start){
                Comment flagComment = new Comment();
                flagComment.setReply(c.getId());
                List<Comment> comments =commentMapper.selectListComment(flagComment);
                result.addAll(comments);
                return getAllReply(comments, result);
            }
            return result;
        }
    }

    /**
     * 查找评论 公共可用
     * 如果评论不是独立评论，显示出来回复的是哪个评论
     * @param comment
     * @return
     */
    private Map<Comment,Comment> selectComment(Comment comment){
        List<Comment> commentList = commentMapper.selectListComment(comment);
        Map<Comment,Comment> commentReplyMap = new HashMap<>(16);
        if(commentList.size()==0){
            return null;
        }
        for(Comment c:commentList){
            if(c.getReply()==0){
                commentReplyMap.put(c,null);
            }
            if(c.getReply()!=0){
                Comment c2 = new Comment();
                c2.setId(c.getReply());
                commentReplyMap.put(c,this.getCommentFromTable(c2));
            }
        }
        return commentReplyMap;
    }

    /**
     * 从数据库中查找符合条件的第一个Comment
     * @param comment
     * @return
     */
    private Comment getCommentFromTable(Comment comment){
        List<Comment> commentList = commentMapper.selectListComment(comment);
        if(commentList==null){
            return null;
        }
        return commentList.get(0);
    }
}
