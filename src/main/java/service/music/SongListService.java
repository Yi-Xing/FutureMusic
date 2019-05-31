package service.music;

import entity.Classification;
import entity.MusicSongList;
import entity.SongList;
import entity.User;
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

}
