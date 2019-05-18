package entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 歌单或专辑的存放音乐的表
 * @author 5月10 张易兴创建
 */
public class MusicSongList implements Serializable {
    /**
     * 主键
     */
    private int id=0;
    /**
     * 所属的歌单或专辑的id
     */
    private int belongId=0;
    /**
     * 1是歌单2是专辑
     */
    private int type=0;
    /**
     * 1是已拥有2是没购买
     */
    private int have=0;
    /**
     * 音乐的id
     */
    private int musicId=0;
    /**
     * 音乐的分类的id
     */
    private int classificationId=0;
    /**
     * 歌手的id
     */
    private int singerId=0;
    /**
     * 存储该音乐在何时存储到了歌单或专辑中 年月日时分秒
     */
    private Date date=null;


    @Override
    public String toString() {
        return "MusicSongList{" +
                "id=" + id +
                ", belongId=" + belongId +
                ", type=" + type +
                ", have=" + have +
                ", musicId=" + musicId +
                ", classificationId=" + classificationId +
                ", singerId=" + singerId +
                ", date=" + date +
                '}';
    }

    public MusicSongList() {
    }

    public MusicSongList(int id, int belongId, int type, int have, int musicId, int classificationId, int singerId, Date date) {
        this.id = id;
        this.belongId = belongId;
        this.type = type;
        this.have = have;
        this.musicId = musicId;
        this.classificationId = classificationId;
        this.singerId = singerId;
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getBelongId() {
        return belongId;
    }

    public void setBelongId(int belongId) {
        this.belongId = belongId;
    }

    public int getMusicId() {
        return musicId;
    }

    public void setMusicId(int musicId) {
        this.musicId = musicId;
    }

    public int getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(int classificationId) {
        this.classificationId = classificationId;
    }

    public int getSingerId() {
        return singerId;
    }

    public void setSingerId(int singerId) {
        this.singerId = singerId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
