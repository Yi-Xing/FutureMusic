package service.music;

import entity.Music;
import entity.SongList;
import mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 点击显示详细信息的功能
 *
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

    /**
     * 显示歌曲的音乐、专辑图片、歌手信息、分类信息、评论、MV(如果有，显示mv的信息，如果无，显示其他相关歌单）
     * @param music 封装音乐id
     * @return Map<String,Object>
     *     音乐、专辑图片、歌手信息、分类信息、评论的详细信息的Map集合
     */
    public Map<String,Object> showMusic(Music music){
        Map<String,Object> musicAllInformationMap = new HashMap<>(5);
        Music resultMusic = musicMapper.selectListMusic(music).get(0);
        musicAllInformationMap.put("music",resultMusic);
        int musicAlbumId = resultMusic.getAlbumId();
        int classificationId = resultMusic.getClassificationId();
        int singerId  = resultMusic.getSingerId();
        int musicVideoId = resultMusic.getMusicVideoId();
        //获取评论的方法，直接调用
        SongList songList = new SongList();
        songList.setId(musicAlbumId);
        songListMapper.selectListSongList(songList).get(0);
        return null;
    }
}
