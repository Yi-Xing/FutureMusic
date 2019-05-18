package entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 歌单和专辑的基本信息
 * @author 5月10日 张易兴创建
 */
public class SongList implements Serializable {
    /**
     * 主键
     */
    private int id=0;
    /**
     * 创建者的id
     */
    private int userId=0;
    /**
     * 标题
     */
    private String name=null;
    /**
     * 封面图片
     */
    private String picture=null;
    /**
     * 介绍
     */
    private String introduction=null;
    /**
     * 何时创建年月日
     */
    private Date date=null;
    /**
     * 分类的id
     */
    private int classificationId=0;
    /**
     * 1是专辑2是歌单
     */
    private int type=0;
    /**
     * 活动的id,只有专辑有
     */
    private int activity=0;

    @Override
    public String toString() {
        return "SongList{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", introduction='" + introduction + '\'' +
                ", date=" + date +
                ", classificationId=" + classificationId +
                ", type=" + type +
                ", activity=" + activity +
                '}';
    }

    public SongList() {
    }

    public SongList(int id, int userId, String name, String picture, String introduction, Date date, int classificationId, int type, int activity) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.picture = picture;
        this.introduction = introduction;
        this.date = date;
        this.classificationId = classificationId;
        this.type = type;
        this.activity = activity;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    public int getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(int classificationId) {
        this.classificationId = classificationId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getActivity() {
        return activity;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }
}
