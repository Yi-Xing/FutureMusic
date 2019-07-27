package entity;

import util.music.FileUtil;

import java.util.HashMap;
import java.util.Map;

public class ShowMusic {
    private Music music;
    private SongList album;
    private User singer;
    private Classification classification;
  /*
    Map <String, Object> model = new HashMap <>();
    Music music = new Music();
            music.setId(musicId);
    Music resultMusic = musicMapper.selectListMusic(music).get(0);
    String lyrics = FileUtil.readInfoStream(resultMusic.getLyricPath());
            resultMusic.setLyricPath(lyrics);
    //获取音乐的信息
            model.put("music", resultMusic);
    int musicAlbumId = resultMusic.getAlbumId();
    int classificationId = resultMusic.getClassificationId();
    int singerId = resultMusic.getSingerId();
    int musicVideoId = resultMusic.getMusicVideoId();
    //获取专辑的信息
            model.put("album", getAlbum(musicAlbumId));
    //获取歌手的详细信息
            model.put("singer", getSinger(singerId));
    //获取分类的详细信息
            model.put("classification", getClassification(classificationId));
    //获取歌曲的播放量
            model.put("musicPlayCount", getPlayCount(musicId));
    //获取歌曲的收藏量
            model.put("musicCollectCount", getCollectCount(musicId));
    //获取mv的详细信息
            model.put("musicVideo", getMusicVideo(musicVideoId));*/
}
