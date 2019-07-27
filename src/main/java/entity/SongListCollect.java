package entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 收藏的专辑或歌单
 * @author 5月10日 张易兴创建
 */
public class SongListCollect implements Serializable {
    /**
     * 主键
     */
    private int id=0;
    /**
     * 收藏的账歌单或专辑的id
     */
    private int musicId=0;
    /**
     * 那个用户收藏的id
     */
    private int userId=0;
    /**
     * 专辑或歌单分类的id
     */
    private int classificationId=0;
    /**
     * 创建歌单或专辑的用户的id
     */
    private int userCollectId=0;
    /**
     * 1表示是歌单的收藏 2表示是专辑的收藏
     */
    private int type=0;
    /**
     * 何时收藏 年月日时分秒
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date=null;

    @Override
    public String toString() {
        return "SongListCollect{" +
                "id=" + id +
                ", musicId=" + musicId +
                ", userId=" + userId +
                ", classificationId=" + classificationId +
                ", userCollectId=" + userCollectId +
                ", type=" + type +
                ", date=" + date +
                '}';
    }

    public SongListCollect() {
    }

    public SongListCollect(int id, int musicId, int userId, int classificationId, int userCollectId, int type, Date date) {
        this.id = id;
        this.musicId = musicId;
        this.userId = userId;
        this.classificationId = classificationId;
        this.userCollectId = userCollectId;
        this.type = type;
        this.date = date;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(int classificationId) {
        this.classificationId = classificationId;
    }

    public int getUserCollectId() {
        return userCollectId;
    }

    public void setUserCollectId(int userCollectId) {
        this.userCollectId = userCollectId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
