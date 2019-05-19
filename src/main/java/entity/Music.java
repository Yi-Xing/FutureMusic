package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 音乐表用于存储音乐的信息
 * @author 5月10日 张易兴创建
 *         5月18日 张易兴修改 将价钱的类型转为BigDecimal，添加字段playCount音乐的播放次数
 */
public class Music implements Serializable {
    /**
     * 主键
     */
    private int id=0;
    /**
     * 音乐的名字
     */
    private String name=null;
    /**
     * 音乐的等级 0免费听免费下载   1免费听vip免费下载 2为vip免费听vip免费下载
     * 3为vip免费听下载收费 4为无论听还是下载都要收费
     */
    private int level=0;
    /**
     * 音乐的价格只有等级为3和4的才可以设置
     */
    private BigDecimal price=null;
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
     * 音乐上传日期 年月日
     */
    private Date date=null;
    /**
     * 音乐的存放的路径
     */
    private String path=null;
    /**
     * 音乐歌词的存放的路径
     */
    private String lyricPath=null;
    /**
     * 音乐是否有MV,如果没MV则是0，如果有则是本歌最好的MV的id
     */
    private int musicVideoId=0;
    /**
     * 活动的id
     */
    private int activity=0;
    /**
     * 该音乐是否可听
     */
    private int available=0;
    /**
     * 音乐的播放次数
     */
    private int playCount=0;


    @Override
    public String toString() {
        return "Music{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", price=" + price +
                ", singerId=" + singerId +
                ", albumId=" + albumId +
                ", classificationId=" + classificationId +
                ", date=" + date +
                ", path='" + path + '\'' +
                ", lyricPath='" + lyricPath + '\'' +
                ", musicVideoId=" + musicVideoId +
                ", activity=" + activity +
                ", available=" + available +
                ", playCount=" + playCount +
                '}';
    }

    public Music() {
    }

    public Music(int id, String name, int level, BigDecimal price, int singerId, int albumId, int classificationId, Date date, String path, String lyricPath, int musicVideoId, int activity, int available, int playCount) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.price = price;
        this.singerId = singerId;
        this.albumId = albumId;
        this.classificationId = classificationId;
        this.date = date;
        this.path = path;
        this.lyricPath = lyricPath;
        this.musicVideoId = musicVideoId;
        this.activity = activity;
        this.available = available;
        this.playCount = playCount;
    }

    public String getLyricPath() {
        return lyricPath;
    }

    public void setLyricPath(String lyricPath) {
        this.lyricPath = lyricPath;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getMusicVideoId() {
        return musicVideoId;
    }

    public void setMusicVideoId(int musicVideoId) {
        this.musicVideoId = musicVideoId;
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
