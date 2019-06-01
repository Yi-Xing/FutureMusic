package entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 已购买的MV或音乐
 * @author 5月10日 张易兴创建
 *          5月18日 张易兴修改 将价钱的类型转为BigDecimal
 */
public class Order implements Serializable {
    /**
     * 主键
     */
    private int id=0;
    /**
     * 用户的id
     */
    private int userId=0;
    /**
     * 音乐的id
     */
    private int musicId=0;
    /**
     * 1表示是音乐 2表示是MV
     */
    private int type=0;
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
     * 购买前的原价
     */
    private BigDecimal originalPrice=new BigDecimal("0");
    /**
     * 购买时候的价格
     */
    private BigDecimal price=new BigDecimal("0");
    /**
     * 支付方式
     */
    private String mode=null;
    /**
     * 何时购买 年月日时分秒
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date=null;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", musicId=" + musicId +
                ", type=" + type +
                ", singerId=" + singerId +
                ", albumId=" + albumId +
                ", classificationId=" + classificationId +
                ", originalPrice=" + originalPrice +
                ", price=" + price +
                ", mode='" + mode + '\'' +
                ", date=" + date +
                '}';
    }

    public Order() {
    }

    public Order(int id, int userId, int musicId, int type, int singerId, int albumId, int classificationId, BigDecimal originalPrice, BigDecimal price, String mode, Date date) {
        this.id = id;
        this.userId = userId;
        this.musicId = musicId;
        this.type = type;
        this.singerId = singerId;
        this.albumId = albumId;
        this.classificationId = classificationId;
        this.originalPrice = originalPrice;
        this.price = price;
        this.mode = mode;
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

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
