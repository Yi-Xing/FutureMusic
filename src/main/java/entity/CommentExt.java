package entity;

import java.util.List;

public class CommentExt {

    /**
     * 评论的信息，与Comment类中的信息一致
     * 可以从这里获得点赞次数、时间等
     */
    private Comment comment;
    /**
     * 评论的用户的id
     */
    private int commentId;
    /**
     *评论的用户的头像
     */
    private int commentPhoto;
    /**
     * 评论的用户的name
     */
    private int commentUserName;
    /**
     * 判断是否有回复，有设为1，没有，设为0
     */
    private int hasReply;

    /**
     *回复的那个人的id
     */
    private int replyId;
    /**
     *回复的那个人的头像
     */
    private String replyPhoto;
    /**
     *回复的那个人的name；
     */
    private String replyName;
    /**
     *这个评论下带的所有回复
     */
    private List<CommentExt> allSubCommentExtList = null;

    public CommentExt(Comment comment, int commentId, int commentPhoto, int commentUserName, int hasReply, int replyId, String replyPhoto, String replyName, List<CommentExt> allSubCommentExtList) {
        this.comment = comment;
        this.commentId = commentId;
        this.commentPhoto = commentPhoto;
        this.commentUserName = commentUserName;
        this.hasReply = hasReply;
        this.replyId = replyId;
        this.replyPhoto = replyPhoto;
        this.replyName = replyName;
        this.allSubCommentExtList = allSubCommentExtList;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getCommentPhoto() {
        return commentPhoto;
    }

    public void setCommentPhoto(int commentPhoto) {
        this.commentPhoto = commentPhoto;
    }

    public int getCommentUserName() {
        return commentUserName;
    }

    public void setCommentUserName(int commentUserName) {
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
}
