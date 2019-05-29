package service.music;

import entity.*;
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

    /**
     * 音乐的流派榜
     * @param type 根据音乐的流派分类查找信息
     * @return Map<Music, User>
     */
    public List<Music> selectListMusicByMusicType(String type) {
        Classification classification = new Classification();
        classification.setType(type);
        Map<Music,User> musicSingerMap = (new PlayService()).selectListMusicByClassification(classification);
        List<Music> musicList = (new PlayService()).sortMusicByPlay(musicSingerMap);
        return musicList;
    }

    /**
     * 地区榜
     *
     * @param region 根据音乐的分类的地区查找信息
     * @return Map<Music, User> 音乐和对应的
     * 歌手集合
     */
    public List<Music> selectListMusicByRegion(String region) {
        Classification classification = new Classification();
        classification.setRegion(region);
        Map<Music,User> musicSingerMap = (new PlayService()).selectListMusicByClassification(classification);
        List<Music> musicList = (new PlayService()).sortMusicByPlay(musicSingerMap);
        return musicList;
    }

    /**
     * 语言榜
     *
     * @param language 根据音乐的分类的地区查找信息
     * @return Map<Music, User> 音乐和对应的歌手集合
     */
    public List<Music> selectListMusicByLanguage(String language) {
        Classification classification = new Classification();
        classification.setRegion(language);
        Map<Music,User> musicSingerMap = (new PlayService()).selectListMusicByClassification(classification);
        List<Music> musicList = (new PlayService()).sortMusicByPlay(musicSingerMap);
        return musicList;
    }

    /**
     * 查找7天内上架且播放量最高的歌曲
     */
    public List<Music> selectListMusicByNewSong() {
        Music music = new Music();
        //获取符合条件的音乐集合，播放次数最多
        //先获取所有音乐七天内上架的音乐
        List<Music> musicList = musicMapper.selectListMusic(music);
        Date musicDate;
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
     * 搜索音乐显示歌名、歌手、专辑、mv(如果有，传mv的id）
     */
    public List<Map<String,String[]>> searchMusicByName(String musicName){
            Music music =new Music();
            music.setName(musicName);
            List<Music> musicList = musicMapper.selectListMusic(music);
            return transformationMusic(musicList);
    }
    /**
     * 传入一个音乐集合，返回歌名、id、歌手、id、专辑、id、mv、id
     */
    public List<Map<String,String[]>> transformationMusic(List<Music> musics){
        List<Map<String,String[]>> musicList = new ArrayList<>();
        for(Music music:musics){
            int musicId = music.getId();
            int singerId  = music.getSingerId();
            int albumId = music.getAlbumId();
            int musicVideoId = music.getMusicVideoId();
            Map<String,String[]> m = new HashMap<>();
            String[] musicMessage = new String[2];
            musicMessage[0] = musicId+"";
            musicMessage[1] = music.getName();
            m.put("musicMessage",musicMessage);
            User user = new User();
            user.setId(singerId);
            User singer = userMapper.selectUser(user).get(0);
            String[] singerMessage = new String[2];
            singerMessage[0] = singer.getId()+"";
            singerMessage[1] = singer.getName();
            m.put("singerMessage",singerMessage);
            SongList songList = new SongList();
            songList.setId(albumId);
            SongList album = songListMapper.selectListSongList(songList).get(0);
            String[] albumMessage = new String[2];
            albumMessage[0] = album.getId()+"";
            albumMessage[1] = album.getName();
            m.put("albumMessage",albumMessage);
            if(musicVideoId==0) {
                m.put("musicVideoMessage", null);
            }else {
                MusicVideo tempMusicVideo = new MusicVideo();
                tempMusicVideo.setId(musicVideoId);
                MusicVideo musicVideo = musicVideoMapper.selectListMusicVideo(tempMusicVideo).get(0);
                String[] musicVideoMessage = new String[2];
                musicMessage[0] = musicVideoId+"";
                musicMessage[1] = musicVideo.getName();
                m.put("musicVideoMessage",musicVideoMessage);
            }
            musicList.add(m);
        }
        return musicList;
    }
}
