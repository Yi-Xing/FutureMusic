package entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 播放过的MV或音乐
 * @author 5月10号 张易兴创建
 */
public class Play implements Serializable {
    /**
     * 主键
     */
    private int id=0;
    /**
     * 用户id
     */
    private int userId=0;
    /**
     * 音乐id
     */
    private int musicId=0;
    /**
     * 1表示是音乐的播放历史  2表示是MV的播放历史
     */
    private int type=0;
    /**
     * 歌手id
     */
    private int singerId=0;
    /**
     * 专辑id
     */
    private int albumId=0;
    /**
     * 分类id
     */
    private int classificationId=0;
    /**
     * 何时播放年月日时分秒
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date=null;

    @Override
    public String toString() {
        return "Play{" +
                "id=" + id +
                ", userId=" + userId +
                ", musicId=" + musicId +
                ", type=" + type +
                ", singerId=" + singerId +
                ", albumId=" + albumId +
                ", classificationId=" + classificationId +
                ", date=" + date +
                '}';
    }

    public Play() {
    }

    public Play(int id, int userId, int musicId, int type, int singerId, int albumId, int classificationId, Date date) {
        this.id = id;
        this.userId = userId;
        this.musicId = musicId;
        this.type = type;
        this.singerId = singerId;
        this.albumId = albumId;
        this.classificationId = classificationId;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getSingerId() {
        return singerId;
    }

    public void setSingerId(int singerId) {
        this.singerId = singerId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(int classificationId) {
        this.classificationId = classificationId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
