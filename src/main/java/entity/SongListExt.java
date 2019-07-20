package entity;

import java.util.ArrayList;
import java.util.List;

public class SongListExt {
    /**
     *专辑的信息
     */
    private SongList songList;
    /**
     *专辑的图片
     */
    private String songListPhoto;
    /**
     *专辑的歌手，如果是歌单，显示歌单创建者的信息
     */
    private int userId;
    /**
     *专辑的歌手，如果是歌单，显示歌单创建者的信息
     */
    private String usersName;
    /**
     *歌单的分类
     */
    private Classification classification;
    /**
     *包含的歌曲列表
     */
    private List<MusicExt> musicExtList = new ArrayList<>();
    /**
     *播放次数
     */
    private int playCount;
    /**
     *收藏次数
     */
    private int collectCount;
    /**
     * 评论
     */
    private List<CommentExt> commentExts;

    public SongListExt(SongList songList, String songListPhoto, int userId, String usersName, Classification classification, List<MusicExt> musicExtList, int playCount, int collectCount, List<CommentExt> commentExts) {
        this.songList = songList;
        this.songListPhoto = songListPhoto;
        this.userId = userId;
        this.usersName = usersName;
        this.classification = classification;
        this.musicExtList = musicExtList;
        this.playCount = playCount;
        this.collectCount = collectCount;
        this.commentExts = commentExts;
    }

    public SongList getSongList() {
        return songList;
    }

    public void setSongList(SongList songList) {
        this.songList = songList;
    }

    public String getSongListPhoto() {
        return songListPhoto;
    }

    public void setSongListPhoto(String songListPhoto) {
        this.songListPhoto = songListPhoto;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsersName() {
        return usersName;
    }

    public void setUsersName(String usersName) {
        this.usersName = usersName;
    }

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public List<MusicExt> getMusicExtList() {
        return musicExtList;
    }

    public void setMusicExtList(List<MusicExt> musicExtList) {
        this.musicExtList = musicExtList;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public int getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(int collectCount) {
        this.collectCount = collectCount;
    }

    public List<CommentExt> getCommentExts() {
        return commentExts;
    }

    public void setCommentExts(List<CommentExt> commentExts) {
        this.commentExts = commentExts;
    }
}
