

package service.music;

import entity.*;
import mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import util.JudgeIsOverdueUtil;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 蒋靓峣
 */
@Service(value = "MusicService")
public class MusicService {
    private static final Logger logger = LoggerFactory.getLogger(MusicService.class);
    @Resource(name = "MusicMapper")
    MusicMapper musicMapper;
    @Resource(name = "ClassificationMapper")
    ClassificationMapper classificationMapper;
    @Resource(name = "UserMapper")
    UserMapper userMapper;
    @Resource(name = "SongListMapper")
    SongListMapper songListMapper;
    @Resource(name = "MusicVideoMapper")
    MusicVideoMapper musicVideoMapper;
    @Resource(name = "PlayMapper")
    PlayMapper playMapper;
    @Resource(name = "MusicCollectMapper")
    MusicCollectMapper musicCollectMapper;
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
        if (albums.size()!=0) {
            musicAllInformationMap.put("album", albums.get(0));
        } else {
            musicAllInformationMap.put("album", null);
        }
        //获取歌手的详细信息
        User user = new User();
        user.setId(singerId);
        user.setLevel(2);
        List<User> singerList = userMapper.selectUser(user);
        if(singerList.size()!=0) {
            musicAllInformationMap.put("singer", singerList.get(0));
        }else{
            musicAllInformationMap.put("singer",null);
            logger.error("音乐"+music.getId()+"缺少歌手信息");
        }
        //获取分类的详细信息
        Classification classification = new Classification();
        classification.setId(classificationId);
        List<Classification> classificationList = classificationMapper.selectListClassification(classification);
        if(classificationList.size()!=0){
            musicAllInformationMap.put("classification",
                    classificationList.get(0));
        }else if(classificationList.size()==0){
            musicAllInformationMap.put("classification",classificationList.get(0));
        }
        //获取歌曲的播放量
        Play p = new Play();
        p.setMusicId(resultMusic.getId());
        p.setType(1);
        List<Play> plays = playMapper.selectListPlay(p);
        musicAllInformationMap.put("musicPlayCount",plays.size());
        //获取歌曲的收藏量
        MusicCollect musicCollect = new MusicCollect();
        musicCollect.setMusicId(resultMusic.getId());
        List<MusicCollect> musicCollects = musicCollectMapper.selectListMusicCollect(musicCollect);
        musicAllInformationMap.put("musicCollectCount",musicCollects.size());
        //获取mv的详细信息
        MusicVideo musicVideo = new MusicVideo();
        musicVideo.setId(musicVideoId);
        List<MusicVideo> musicVideos = musicVideoMapper.selectListMusicVideo(musicVideo);
        if(musicVideos.size()!=0){
            musicAllInformationMap.put("musicVideo",musicVideos.get(0));
        }else{
            musicAllInformationMap.put("musicVideo",null);
        }
        //获取精彩评论详细信息
        musicAllInformationMap.put("goodComment",
                new ShowCommentService().commentByMusicId(resultMusic.getId()));
        System.out.println("2222222222222");
        //获取最新评论
        musicAllInformationMap.put("lastComment",
                new ShowCommentService().commentLastByMusicId(resultMusic.getId()));
        System.out.println("33333333333");
        return musicAllInformationMap;
    }
//    /**
//     * 音乐的流派榜
//     *
//     * @param type 根据音乐的流派分类查找信息
//     * @return Map<Music, User>
//     */
//    public List<Map<String, String[]>> selectListMusicByMusicType(String type) {
//        Classification classification = new Classification();
//        classification.setType(type);
//        List<Music> musicList = selectListMusicByClassification(classification);
//        return transformationMusic(musicList);
//    }
    /**
     * 音乐的流派榜
     *
     * @param type 根据音乐的流派分类查找信息
     * @return Map<Music, User>
     */
    public List<Music> selectListMusicByMusicType(String type) {
        Classification classification = new Classification();
        classification.setType(type);
        List<Music> musicList = selectListMusicByClassification(classification);
        return musicList;
    }
//    /**
//     * 地区榜
//     *
//     * @param region 根据音乐的分类的地区查找信息
//     * @return Map<Music, User> 音乐和对应的
//     * 歌手集合
//     */
//    public List<Map<String, String[]>> selectListMusicByRegion(String region) {
//        Classification classification = new Classification();
//        classification.setRegion(region);
//        List<Music> musicList = selectListMusicByClassification(classification);
//        List<Music> resultMusic = (new PlayService()).sortMusicByPlay(musicList);
//        return transformationMusic(resultMusic);
//    }
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
        List<Music> musicList = selectListMusicByClassification(classification);
        List<Music> resultMusic = (new PlayService()).sortMusicByPlay(musicList);
        return resultMusic;
    }
    /**
     * 语言榜
     *
     * @param language 根据音乐的分类的地区查找信息
     * @return Map<Music, User> 音乐和对应的歌手集合
     */
    public List<Map<String, String[]>> selectListMusicByLanguage(String language) {
        Classification classification = new Classification();
        classification.setRegion(language);
        List<Music> musicList = selectListMusicByClassification(classification);
        List<Music> resultMusic = (new PlayService()).sortMusicByPlay(musicList);
        return transformationMusic(resultMusic);
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
        Map<Integer, Integer> musicCount = new HashMap<>();
        for (Music m : musicList) {
            play.setMusicId(m.getId());
            List<Play> plays = playMapper.selectListPlay(play);
            int count = plays.size();
            musicCount.put(m.getId(), count);
        }
        //歌曲id、播放量
        List<Music> musics = new ArrayList<>();
        for (Integer musicId : musicCount.keySet()) {
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
    public List<Map<String, String[]>> searchMusicByName(String musicName) {
        Music music = new Music();
        music.setName(musicName);
        List<Music> musicList = musicMapper.selectListMusic(music);
        return transformationMusic(musicList);
    }

    /**
     * 传入一个音乐集合，返回歌名、id、歌手、id、专辑、id、mv、id
     */
    public List<Map<String, String[]>> transformationMusic(List<Music> musics) {
        if(musics.size()==0){
            return null;
        }
        List<Map<String, String[]>> musicList = new ArrayList<>();
        for (Music music : musics) {
            int musicId = music.getId();
            int singerId = music.getSingerId();
            int albumId = music.getAlbumId();
            int musicVideoId = music.getMusicVideoId();
            Map<String, String[]> m = new HashMap<>(16);
            String[] musicMessage = new String[2];
            musicMessage[0] = musicId + "";
            musicMessage[1] = music.getName();
            m.put("musicMessage", musicMessage);
            User user = new User();
            user.setId(singerId);
            User singer = userMapper.selectUser(user).get(0);
            String[] singerMessage = new String[2];
            singerMessage[0] = singer.getId() + "";
            singerMessage[1] = singer.getName();
            m.put("singerMessage", singerMessage);
            SongList songList = new SongList();
            songList.setId(albumId);
            List<SongList> songLists = songListMapper.selectListSongList(songList);
            if(songLists.size()!=0) {
                SongList album = songLists.get(0);
                String[] albumMessage = new String[2];
                albumMessage[0] = album.getId() + "";
                albumMessage[1] = album.getName();
                m.put("albumMessage", albumMessage);
            }else{
                m.put("albumMessage", null);
            }
            if (musicVideoId == 0) {
                m.put("musicVideoMessage", null);
            } else {
                MusicVideo tempMusicVideo = new MusicVideo();
                tempMusicVideo.setId(musicVideoId);
                List<MusicVideo> musicVideoList = musicVideoMapper.selectListMusicVideo(tempMusicVideo);
                if(musicVideoList.size()!=0) {
                    MusicVideo musicVideo = musicVideoList.get(0);
                    String[] musicVideoMessage = new String[2];
                    musicMessage[0] = musicVideoId + "";
                    musicMessage[1] = musicVideo.getName();
                    m.put("musicVideoMessage", musicVideoMessage);
                }else{
                    m.put("musicVideoMessage", null);
                }
            }
            musicList.add(m);
        }
        return musicList;
    }

    /**
     * 根据分类查找音乐集合
     */
    public List<Music> selectListMusicByClassification(Classification classification){
        List<Integer> classificationIds = new ArrayList<>();
        List<Classification> classificationList = classificationMapper.selectListClassification(classification);
        if(classificationList.size()!=0&&classification!=null) {
            for (Classification clf : classificationList) {
                //List获取对应得分类id
                classificationIds.add(clf.getId());
            }
        }
        List<Music> musicList = musicMapper.listClassificationIdSelectListMusic(classificationIds);
        return musicList;
    }
}
