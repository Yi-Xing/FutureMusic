package entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * MV表 存储MV的信息
 *
 * @author 5月10号 张易兴创建
 * 5月18日 张易兴修改 将价钱的类型转为BigDecimal，添加字段playCount MV的播放次数
 */
public class MusicVideo implements Serializable {
    /**
     * 主键
     */
    private int id = 0;
    /**
     * 名字
     */
    private String name = null;
    /**
     * 存放的路径
     */
    private String path = null;
    /**
     * 介绍
     */
    private String introduction = null;
    /**
     * 上传日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date = null;
    /**
     * 等级 0免费听免费下载   1免费听vip免费下载	2为vip免费听vip免费下载
     * 3为vip免费听下载收费 4为无论听还是下载都要收费
     */
    private int level = 0;
    /**
     * 价格等级为3和4的可设置
     */
    private BigDecimal price = new BigDecimal("0");
    /**
     * 该MV的音乐id
     */
    private int musicId = 0;
    /**
     * 歌手的id
     */
    private int singerId = 0;
    /**
     * 分类的id
     */
    private int classificationId = 0;
    /**
     * 活动的id
     */
    private int activity = 0;
    /**
     * MV是否可听
     */
    private int available = 0;

    /**
     * MV的播放次数
     */
    private int playCount = 0;
    /**
     * MV图片的存放路径
     */
    private String picture = null;

    /**
     * MV的图片
     */
    private String musicVideoPhoto;

    public String getMusicVideoPhoto() {
        return musicVideoPhoto;
    }

    public void setMusicVideoPhoto(String musicVideoPhoto) {
        this.musicVideoPhoto = musicVideoPhoto;
    }

    @Override
    public String toString() {
        return "MusicVideo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", introduction='" + introduction + '\'' +
                ", date=" + date +
                ", level=" + level +
                ", price=" + price +
                ", musicId=" + musicId +
                ", singerId=" + singerId +
                ", classificationId=" + classificationId +
                ", activity=" + activity +
                ", available=" + available +
                ", playCount=" + playCount +
                ", picture='" + picture + '\'' +
                '}';
    }

    public MusicVideo() {
    }

    public MusicVideo(int id, String name, String path, String introduction, Date date, int level, BigDecimal price, int musicId, int singerId, int classificationId, int activity, int available, int playCount, String picture) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.introduction = introduction;
        this.date = date;
        this.level = level;
        this.price = price;
        this.musicId = musicId;
        this.singerId = singerId;
        this.classificationId = classificationId;
        this.activity = activity;
        this.available = available;
        this.playCount = playCount;
        this.picture = picture;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getMusicId() {
        return musicId;
    }

    public void setMusicId(int musicId) {
        this.musicId = musicId;
    }

    public int getSingerId() {
        return singerId;
    }

    public void setSingerId(int singerId) {
        this.singerId = singerId;
    }

    public int getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(int classificationId) {
        this.classificationId = classificationId;
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }
}
