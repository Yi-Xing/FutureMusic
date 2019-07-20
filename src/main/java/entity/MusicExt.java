package entity;

import java.math.BigDecimal;

/**
 * 音乐的扩展类
 * 歌名、id、歌手、id、专辑、id、mv、id、音乐等级
 */
public class MusicExt {
    private  int musicId;
    private String MusicName;
    private int singerId = 0;
    private String singerName;
    private int albumId;
    private String albumName;
    private boolean hasMusicVideo;
    private int musicVideoId = 0;
    private int musicLevel = 0;
    private BigDecimal musicPrice;

    public MusicExt() {
    }

    public MusicExt(int musicId, String musicName, int singerId, String singerName, int albumId, String albumName, boolean hasMusicVideo, int musicVideoId, int musicLevel, BigDecimal musicPrice) {
        this.musicId = musicId;
        MusicName = musicName;
        this.singerId = singerId;
        this.singerName = singerName;
        this.albumId = albumId;
        this.albumName = albumName;
        this.hasMusicVideo = hasMusicVideo;
        this.musicVideoId = musicVideoId;
        this.musicLevel = musicLevel;
        this.musicPrice = musicPrice;
    }

    public int getMusicId() {
        return musicId;
    }

    public void setMusicId(int musicId) {
        this.musicId = musicId;
    }

    public String getMusicName() {
        return MusicName;
    }

    public void setMusicName(String musicName) {
        MusicName = musicName;
    }

    public int getSingerId() {
        return singerId;
    }

    public void setSingerId(int singerId) {
        this.singerId = singerId;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public boolean isHasMusicVideo() {
        return hasMusicVideo;
    }

    public void setHasMusicVideo(boolean hasMusicVideo) {
        this.hasMusicVideo = hasMusicVideo;
    }

    public int getMusicVideoId() {
        return musicVideoId;
    }

    public void setMusicVideoId(int musicVideoId) {
        this.musicVideoId = musicVideoId;
    }

    public int getMusicLevel() {
        return musicLevel;
    }

    public void setMusicLevel(int musicLevel) {
        this.musicLevel = musicLevel;
    }

    public BigDecimal getMusicPrice() {
        return musicPrice;
    }

    public void setMusicPrice(BigDecimal musicPrice) {
        this.musicPrice = musicPrice;
    }
}
