package entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 收藏的MV或音乐
 * @author 5月9日 张易兴创建
 */
public class MusicCollect implements Serializable {
    /**
     * 主键
     */
    private int id=0;
    /**
     * 用户的id
     */
    private int userId=0;
    /**
     * 音乐/MV的id
     */
    private int musicId=0;
    /**
     * 1表示是音乐的收藏  2表示是MV的收藏
     */
    private int type=0;
    /**
     * 1是已拥有，其他表示没有购买
     */
    private int have=0;
    /**
     * 歌手的id
     */
    private int singerId=0;
    /**
     * 专辑的id
     */
    private int albumId=0;
    /**
     * 分类的id
     */
    private int classificationId=0;
    /**
     * 音乐在何时收藏  年月日时分秒
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date=null;

    @Override
    public String toString() {
        return "MusicCollect{" +
                "id=" + id +
                ", userId=" + userId +
                ", musicId=" + musicId +
                ", type=" + type +
                ", have=" + have +
                ", singerId=" + singerId +
                ", albumId=" + albumId +
                ", classificationId=" + classificationId +
                ", date=" + date +
                '}';
    }

    public MusicCollect() {
    }

    public MusicCollect(int id, int userId, int musicId, int type, int have, int singerId, int albumId, int classificationId, Date date) {
        this.id = id;
        this.userId = userId;
        this.musicId = musicId;
        this.type = type;
        this.have = have;
        this.singerId = singerId;
        this.albumId = albumId;
        this.classificationId = classificationId;
        this.date = date;
    }

    public int getHave() {
        return have;
    }

    public void setHave(int have) {
        this.have = have;
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

    public int getClassifictionId() {
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
