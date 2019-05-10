package entity;

import java.util.Date;

/**
 * 活动表
 * @author 5月9日 张易兴创建
 */
public class Activity {
    /**
     * 主键
     */
    private int id=0;
    /**
     * 活动的名字
     */
    private String name=null;
    /**
     * 活动图片的路径
     */
    private String picture=null;
    /**
     * 折扣百分比
     */
    private float discount=0;
    /**
     * 1是折扣是针对音乐，为2的时候是针对购买vip的  3是针对专辑
     */
    private int type=0;
    /**
     * 活动的详细信息
     */
    private String content=null;
    /**
     * 活动的页面网站地址
     */
    private String website=null;
    /**
     * 开始时间
     */
    private Date startDate=null;
    /**
     * 结束时间
     */
    private Date endDate=null;

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                ", discount=" + discount +
                ", type=" + type +
                ", content='" + content + '\'' +
                ", website='" + website + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    public Activity() {
    }

    public Activity(int id, String name, String picture, float discount, int type, String content, String website, Date startDate, Date endDate) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.discount = discount;
        this.type = type;
        this.content = content;
        this.website = website;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
