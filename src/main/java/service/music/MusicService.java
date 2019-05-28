package service.music;

import entity.Music;
import entity.Play;
import entity.User;
import mapper.*;
import org.springframework.stereotype.Service;
import util.JudgeIsOverdueUtil;

import javax.annotation.Resource;
import java.util.*;
@Service(value = "MusicService")
public class MusicService {
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
     * 查找7天内上架且播放量最高的歌曲
     */
    public List<Music> selectListMusicByNewSong() {
        Map<Music, User> musicSingerMap = new HashMap<>(16);
        Music music = new Music();
        //获取符合条件的音乐集合，播放次数最多
        //先获取所有音乐七天内上架的音乐
        List<Music> musicList = musicMapper.selectListMusic(music);
        Date musicDate;
        User user = new User();
        for (int i = 0; i < musicList.size(); i++) {
            music = musicList.get(i);
            musicDate = music.getDate();
            if (JudgeIsOverdueUtil.reduceDay(JudgeIsOverdueUtil.toDateSting(musicDate)) < 7) {
                musicList.remove(i);
                i = i - 1;
            }
        }
        Play play = new Play();
        Map<Integer,Integer> musicCount = new HashMap<>();
        for (Music m : musicList) {
            play.setMusicId(m.getId());
            List<Play> plays = playMapper.selectListPlay(play);
            int count  = plays.size();
            musicCount.put(m.getId(),count);
        }
        //歌曲id、播放量
        List<Music> musics = new ArrayList<>();
        for(Integer musicId:musicCount.keySet()){
            Music temp = new Music();
            temp.setId(musicId);
            musics.add(musicMapper.selectListMusic(temp).get(0));
        }
        //获取符合条件得分类对象
        return musics;
    }

    /**
     * 搜索音乐
     */

}
