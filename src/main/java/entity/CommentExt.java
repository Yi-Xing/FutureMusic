package entity;

import java.util.ArrayList;
import java.util.List;

public class CommentExt {

    /**
     * 评论的信息
     * 可以从这里获得点赞次数、时间等
     */
    private Comment comment;
    //评论的用户的id
    private int commentId;
    //评论的用户的头像
    private int commentPhoto;
    //评论的用户的name
    private int commentUserName;
    //判断是否有回复，有设为1，没有，设为0
    private int hasReply;
    //回复的那个人的id
    private int replyId;
    //回复的那个人的头像
    private String replyPhoto;
    //回复的那个人的name；
    private String replyName;
    //这个评论下带的所有回复
    private List<CommentExt> allSubCommentExts = new ArrayList<>();

}
