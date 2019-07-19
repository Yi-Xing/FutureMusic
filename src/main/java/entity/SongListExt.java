package entity;

import java.util.ArrayList;
import java.util.List;

public class SongListExt {
    //专辑的信息
    private SongList songList;
    //专辑的图片
    private String SongListPhoto;
    //专辑的歌手，如果是歌单，显示歌单创建者的信息
    private int userId;
    //专辑的歌手，如果是歌单，显示歌单创建者的信息
    private String usersName;
    //歌单的分类
    private Classification classification;
    //包含的歌曲列表
    private List<MusicExt> musicExtList = new ArrayList<>();
    //播放次数
    private int playCount;
    //收藏次数
    private int collectCount;
    //评论
    private List<CommentExt> commentExts;

}
