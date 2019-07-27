package entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author 蒋靓峣 7/23
 */
public class CommentExt {

    /**
     *评论的点赞次数
     */
    private int fabulous=0;
    /**
     * 评论的内容
     */
    private String content="";
    /**
     * 评论的时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;
    /**
     * 评论的用户的id
     */
    private int commentId=0;
    /**
     *评论的用户的头像
     */
    private String commentPhoto="";
    /**
     * 评论的用户的name
     */
    private String commentUserName="";
    /**
     * 判断是否有回复，有设为1，没有，设为0
     */
    private int hasReply=0;
    /**
     *回复的那个人的id
     */
    private int replyId=0;
    /**
     *回复的那个人的头像
     */
    private String replyPhoto="";
    /**
     *回复的那个人的name；
     */
    private String replyName="";
    /**
     *这个评论下带的所有回复
     */
    private List<CommentExt> allSubCommentExtList = null;

    public CommentExt() {
    }

    public int getFabulous() {
        return fabulous;
    }

    public void setFabulous(int fabulous) {
        this.fabulous = fabulous;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getCommentPhoto() {
        return commentPhoto;
    }

    public void setCommentPhoto(String commentPhoto) {
        this.commentPhoto = commentPhoto;
    }

    public String getCommentUserName() {
        return commentUserName;
    }

    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }

    public int getHasReply() {
        return hasReply;
    }

    public void setHasReply(int hasReply) {
        this.hasReply = hasReply;
    }

    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    public String getReplyPhoto() {
        return replyPhoto;
    }

    public void setReplyPhoto(String replyPhoto) {
        this.replyPhoto = replyPhoto;
    }

    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }

    public List<CommentExt> getAllSubCommentExtList() {
        return allSubCommentExtList;
    }

    public void setAllSubCommentExtList(List<CommentExt> allSubCommentExtList) {
        this.allSubCommentExtList = allSubCommentExtList;
    }

    @Override
    public String toString() {
        return "CommentExt{" +
                "fabulous=" + fabulous +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", commentId=" + commentId +
                ", commentPhoto='" + commentPhoto + '\'' +
                ", commentUserName='" + commentUserName + '\'' +
                ", hasReply=" + hasReply +
                ", replyId=" + replyId +
                ", replyPhoto='" + replyPhoto + '\'' +
                ", replyName='" + replyName + '\'' +
                ", allSubCommentExtList=" + allSubCommentExtList +
                '}';
    }
}
