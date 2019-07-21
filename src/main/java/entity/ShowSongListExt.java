package entity;

public class ShowSongListExt {
    /**
     * 歌单或专辑的id、名字、图片、音乐数、收听次数
     */
    private int songListId;
    private String songListName;
    private String songListPicture;
    private int musicCount;
    private int playCount;

    public ShowSongListExt(int songListId, String songListName, String songListPicture, int musicCount, int playCount) {
        this.songListId = songListId;
        this.songListName = songListName;
        this.songListPicture = songListPicture;
        this.musicCount = musicCount;
        this.playCount = playCount;
    }

    public int getSongListId() {
        return songListId;
    }

    public ShowSongListExt() {
    }

    public void setSongListId(int songListId) {
        this.songListId = songListId;
    }

    public String getSongListName() {
        return songListName;
    }

    public void setSongListName(String songListName) {
        this.songListName = songListName;
    }

    public String getSongListPicture() {
        return songListPicture;
    }

    public void setSongListPicture(String songListPicture) {
        this.songListPicture = songListPicture;
    }

    public int getMusicCount() {
        return musicCount;
    }

    public void setMusicCount(int musicCount) {
        this.musicCount = musicCount;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }
}
