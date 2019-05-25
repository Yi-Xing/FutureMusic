package service.music;

import entity.*;
import mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 点击显示详细信息的功能
 *  音乐、歌单、专辑、歌手
 * @author 蒋靓峣 5.11创建
 * */
@Service(value = "DetailsService")
public class DetailsService {
    private static final Logger logger = LoggerFactory.getLogger(ExhibitionService.class);
    @Resource(name="MusicMapper")
    MusicMapper musicMapper;
    @Resource(name="ClassificationMapper")
    ClassificationMapper classificationMapper;
    @Resource(name = "UserMapper")
    UserMapper userMapper;
    @Resource(name = "ActivityMapper")
    ActivityMapper activityMapper;
    @Resource(name = "SongListMapper")
    SongListMapper songListMapper;
    @Resource(name = "MusicVideoMapper")
    MusicVideoMapper musicVideoMapper;
    @Resource(name = "ShowCommentService")
    ShowCommentService showCommentService;
    @Resource(name = "MusicSongListMapper")
    MusicSongListMapper musicSongListMapper;

     /**
     * 显示歌曲的详细信息
      *     音乐、专辑图片、歌手信息、分类信息、评论（精彩评论、最新评论）、MV(如果有，显示mv的信息，如果无，显示其他相关歌单）
     * @param music 封装音乐id
     * @return Map<String,Object>
     *     音乐、专辑图片、歌手信息、分类信息、评论的详细信息的Map集合
     */
    public Map<String,Object> showMusic(Music music) {
        Map<String, Object> musicAllInformationMap = new HashMap<>(5);
        Music resultMusic = musicMapper.selectListMusic(music).get(0);
        ///获取音乐的信息
        musicAllInformationMap.put("music", resultMusic);
        int musicAlbumId = resultMusic.getAlbumId();
        int classificationId = resultMusic.getClassificationId();
        int singerId = resultMusic.getSingerId();
        int musicVideoId = resultMusic.getMusicVideoId();
        //获取评论的方法，直接调用
        //获取专辑的信息
        SongList songList = new SongList();
        songList.setId(musicAlbumId);
        List<SongList> albums = songListMapper.selectListSongList(songList);
        if (albums != null) {
            musicAllInformationMap.put("album", albums.get(0));
        } else {
            musicAllInformationMap.put("album", null);
        }
        //获取歌手的详细信息
        User user = new User();
        user.setId(singerId);
        user.setLevel(2);
        musicAllInformationMap.put("singer",userMapper.selectUser(user).get(0));
        //获取分类的详细信息
        Classification classification = new Classification();
        classification.setId(classificationId);
        musicAllInformationMap.put("classification",
                classificationMapper.selectListClassification(classification).get(0));
        //获取mv的详细信息
        MusicVideo musicVideo = new MusicVideo();
        musicVideo.setId(musicVideoId);
        //按浏览量搜索
        musicVideo.setPlayCount(1);
        List<MusicVideo> musicVideos = musicVideoMapper.selectListMusicVideo(musicVideo);
        if(musicVideos!=null){
            musicAllInformationMap.put("musicVideo",musicVideos.get(0));
        }else{
            musicAllInformationMap.put("musicVideo",null);
        }
        //获取精彩评论详细信息
        musicAllInformationMap.put("goodComment",
                showCommentService.commentByMusicId(resultMusic.getId()));
        //获取最新评论
        musicAllInformationMap.put("lastComment",
                showCommentService.commentLastByMusicId(resultMusic.getId()));
    return musicAllInformationMap;
    }

    /**
     * 点击显示MV的详细信息
     *      MV、歌手、评论
     * @param musicVideo 封装要搜索的MVid
     * @return  Map<String,Object> 各类信息
     */
    public Map<String,Object> showMusicVideo(MusicVideo musicVideo){
        Map<String,Object> musicVideoMap = new HashMap<>(10);
        List<MusicVideo> musicVideos = musicVideoMapper.selectListMusicVideo(musicVideo);
        musicVideoMap.put("musicVideo",musicVideos.get(0));
        int singerId = musicVideos.get(0).getSingerId();
        User user = new User();
        user.setId(singerId);
        musicVideoMap.put("singer",userMapper.selectUser(user).get(0));
        musicVideoMap.put("goodComment",showCommentService.commentByMusicVideoId(musicVideo.getId()));
        musicVideoMap.put("lastComment",showCommentService.commentLastByMusicVideoId(musicVideo.getId()));
        return musicVideoMap;
    }

    /**
     * 显示歌单或专辑的详细信息
     *          分类、歌手、列表歌曲、评论
     * @param songList   将条件封装
     * @return  Map<String,Object> 返回和歌单关联的所有信息
     */
    public Map<String,Object> showSongList(SongList songList){
        Map<String,Object> songListMap  = new HashMap<>(10);
        SongList resultSongList = songListMapper.selectListSongList(songList).get(0);
        songListMap.put("songList",resultSongList);
        Classification classification  = new Classification();
        classification.setId(resultSongList.getClassificationId());
        songListMap.put("classification",
                classificationMapper.selectListClassification(classification).get(0));
        User user = new User();
        user.setId(resultSongList.getUserId());
        songListMap.put("singer",userMapper.selectUser(user).get(0));
        MusicSongList musicSongList   = new MusicSongList();
        musicSongList.setBelongId(resultSongList.getId());
        List<MusicSongList> musicSongLists = musicSongListMapper.selectListMusicSongList(musicSongList);
        songListMap.put("musicSongList",musicSongLists);
        return songListMap;
    }

    /**
     * 显示活动的详细信息包括活动的详细信息、参与活动的歌曲或者专辑
     * @param activity 封装activity的id
     * @return 返回一系列的相关信息
     */
    public Map<String,Object> showActivity(Activity activity) {
        Map<String,Object> activityMap = new HashMap<>(3);
        Activity resultActivity = activityMapper.selectListActivity(activity).get(0);
        activityMap.put("activity",resultActivity);
        int activityId = resultActivity.getId();
        //取出活动对应的音乐
        Music music = new Music();
        music.setActivity(activityId);
        List<Music> musicList = musicMapper.selectListMusic(music);
        if(musicList.size()==0){
            activityMap.put("musicList",null);
        }else{
            activityMap.put("musicList",musicList);
        }
        //取出活动对应的专辑
        SongList songList = new SongList();
        songList.setActivity(activityId);
        List<SongList> songListList = songListMapper.selectListSongList(songList);
        if(songListList.size()==0){
            activityMap.put("songListList",null);
        }else{
            activityMap.put("songListList",songListList);
        }
        return activityMap;
    }
}
