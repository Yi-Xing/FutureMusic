package entity;

/**
 * mv名字、歌手名字\播放量
 */
public class MusicVideoExt {
    /**
     * MV的id
     */
    private int musicVideoId=0;
    /**
     * MV的名字
     */
    private String musicVideoName = null;
    /**
     * MV的图片，用歌手的图片代替
     */
    private String musicVideoPhoto=null;
    /**
     * 歌手名字
     */
    private String singerName = null;
    /**
     * 歌手Id
     */
    private int singerId = 0;
    /**
     * MV的播放量
     */
    private int playCount = 0;
    /**
     * 权限，判断是否可下载
     */
    /**
     * 等级 0免费听免费下载   1免费听vip免费下载	2为vip免费听vip免费下载
     * 3为vip免费听下载收费 4为无论听还是下载都要收费
     */
    private int level=0;
    /**
     * MV是否可听
     */
    private int available=0;

    public MusicVideoExt() {
    }

    public MusicVideoExt(int musicVideoId, String musicVideoName, String musicVideoPhoto, String singerName, int singerId, int playCount, int level, int available) {
        this.musicVideoId = musicVideoId;
        this.musicVideoName = musicVideoName;
        this.musicVideoPhoto = musicVideoPhoto;
        this.singerName = singerName;
        this.singerId = singerId;
        this.playCount = playCount;
        this.level = level;
        this.available = available;
    }

    public int getMusicVideoId() {
        return musicVideoId;
    }

    public void setMusicVideoId(int musicVideoId) {
        this.musicVideoId = musicVideoId;
    }

    public String getMusicVideoName() {
        return musicVideoName;
    }

    public void setMusicVideoName(String musicVideoName) {
        this.musicVideoName = musicVideoName;
    }

    public String getMusicVideoPhoto() {
        return musicVideoPhoto;
    }

    public void setMusicVideoPhoto(String musicVideoPhoto) {
        this.musicVideoPhoto = musicVideoPhoto;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public int getSingerId() {
        return singerId;
    }

    public void setSingerId(int singerId) {
        this.singerId = singerId;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    @Override
    public String toString() {
        return "MusicVideoExt{" +
                "musicVideoId=" + musicVideoId +
                ", musicVideoName='" + musicVideoName + '\'' +
                ", musicVideoPhoto='" + musicVideoPhoto + '\'' +
                ", singerName='" + singerName + '\'' +
                ", singerId=" + singerId +
                ", playCount=" + playCount +
                ", level=" + level +
                ", available=" + available +
                '}';
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }
}
