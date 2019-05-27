package service.music;

import entity.MusicVideo;
import entity.Play;
import mapper.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service(value = "MusicVideoService")
public class MusicVideoService {
    @Resource(name = "MusicMapper")
    MusicMapper musicMapper;
    @Resource(name = "ClassificationMapper")
    ClassificationMapper classificationMapper;
    @Resource(name = "UserMapper")
    UserMapper userMapper;
    @Resource(name = "ActivityMapper")
    ActivityMapper activityMapper;
    @Resource(name = "SongListMapper")
    SongListMapper songListMapper;
    @Resource(name = "MusicVideoMapper")
    MusicVideoMapper musicVideoMapper;
    @Resource(name = "PlayMapper")
    PlayMapper playMapper;
    @Resource(name = "FocusMapper")
    FocusMapper focusMapper;

    /**
     * 展示主页的MV
     * MV的图片、MV的id、name、播放量、歌手的name、歌手id、专辑得收藏量
     */
    public List<MusicVideo> exhibitionMusicVideo(){
        MusicVideo musicVideo = new MusicVideo();
        Play play = new Play();
        play.setType(2);
        List<Play> playList = playMapper.selectListPlay(play);
        //查找播放最多的MV对应的音乐id
        //播放量、音乐id
        Map<Integer,Integer> rankingMusicVideo = (new ExhibitionService()).getMostPlayMusic(playList);
        for(Integer musicId:rankingMusicVideo.keySet()){

        }
        return null;
    }

}
