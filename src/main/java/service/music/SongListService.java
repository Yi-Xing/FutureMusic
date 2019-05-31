package service.music;

import entity.*;
import mapper.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service(value = "SongListService")
public class SongListService {
    @Resource(name="MusicMapper")
    MusicMapper musicMapper;
    @Resource(name="ClassificationMapper")
    ClassificationMapper classificationMapper;
    @Resource(name = "UserMapper")
    UserMapper userMapper;
    @Resource(name = "SongListMapper")
    SongListMapper songListMapper;
    @Resource(name = "MusicVideoMapper")
    MusicVideoMapper musicVideoMapper;
    @Resource(name = "ShowCommentService")
    ShowCommentService showCommentService;
    @Resource(name = "MusicSongListMapper")
    MusicSongListMapper musicSongListMapper;
    @Resource(name = "PlayMapper")
    PlayMapper playMapper;
    @Resource(name = "SongListCollectMapper")
    SongListCollectMapper songListCollectMapper;
    /**
     *根据分类搜索歌单
     */
    public Map<String,Object> showSongListByClassification(String classification){
        Classification cl = new Classification();
        cl.setType(classification);
        Map<String,Object> songListMap = new HashMap<>();
        List<Classification> classificationList = classificationMapper.selectListClassification(cl);
        for(Classification clf:classificationList){
            SongList songList = new SongList();
            songList.setClassificationId(clf.getId());
            List<SongList> songLists = songListMapper.selectListSongList(songList);
            if(songLists.size()!=0){
                SongList resultSongList = songLists.get(0);
                songListMap.putAll(showSongList(resultSongList));
            }
        }
        return songListMap;
    }
    /**
     * 显示歌单或专辑的详细信息
     *          分类、歌手、列表歌曲、评论
     * @param songList   将条件封装
     * @return  Map<String,Object> 返回和歌单关联的所有信息
     */
    public Map<String,Object> showSongList(SongList songList){
        Map<String,Object> songListMap  = new HashMap<>(10);
        List<SongList> resultSongLists = songListMapper.selectListSongList(songList);
        if(resultSongLists.size()==0){
            return null;
        }
        SongList resultSongList = resultSongLists.get(0);
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
        songListMap.put("musicSongListComment",showCommentService.commentByListSongId(resultSongList.getId()));
        songListMap.put("musicSongListLastComment",showCommentService.commentLastByListSongId(resultSongList.getId()));
        Play play = new Play();
        play.setAlbumId(resultSongList.getId());
        List<Play> plays = playMapper.selectListPlay(play);
        songListMap.put("songListPlayCount",plays.size());
        SongListCollect songListCollect = new SongListCollect();
        songListCollect.setMusicId(resultSongList.getId());
        List<SongListCollect> songListCollects = songListCollectMapper.selectListSongListCollect(songListCollect);
        songListMap.put("songListCollectCount",songListCollects.size());
        return songListMap;
    }

}
