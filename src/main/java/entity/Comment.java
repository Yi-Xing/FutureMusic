package entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 用来存储音乐或mv或专辑的评论
 * @author 5月9日 张易兴创建
 */
public class Comment implements Serializable {
    /**
     * 主键
     */
    private int id=0;
    /**
     * 音乐的id或MV的id或专辑的id
     */
    private int musicId=0;
    /**
     * 1是音乐，2是MV  3是专辑
     */
    private int type=0;
    /**
     * 用户的id
     */
    private int userId=0;
    /**
     * 评论的内容
     */
    private String content=null;
    /**
     * 评论的时间年月日时分秒
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date=null;
    /**
     * 回复那个评论的id, 0为独立评论
     */
    private int reply=0;
    /**
     * 点赞的次数
     */
    private int fabulous=0;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", musicId=" + musicId +
                ", type=" + type +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", reply=" + reply +
                ", fabulous=" + fabulous +
                '}';
    }

    public Comment() {
    }

    public Comment(int id, int musicId, int type, int userId, String content, Date date, int reply, int fabulous) {
        this.id = id;
        this.musicId = musicId;
        this.type = type;
        this.userId = userId;
        this.content = content;
        this.date = date;
        this.reply = reply;
        this.fabulous = fabulous;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMusicId() {
        return musicId;
    }

    public void setMusicId(int musicId) {
        this.musicId = musicId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getReply() {
        return reply;
    }

    public void setReply(int reply) {
        this.reply = reply;
    }

    public int getFabulous() {
        return fabulous;
    }

    public void setFabulous(int fabulous) {
        this.fabulous = fabulous;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id == comment.id &&
                musicId == comment.musicId &&
                type == comment.type &&
                userId == comment.userId &&
                reply == comment.reply &&
                fabulous == comment.fabulous &&
                Objects.equals(content, comment.content) &&
                Objects.equals(date, comment.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, musicId, type, userId, content, date, reply, fabulous);
    }
}
