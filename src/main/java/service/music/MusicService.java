package service.music;

import entity.*;
import mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import util.JudgeIsOverdueUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
    @Resource(name = "PlayService")
    PlayService playService;
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
        //获取音乐的信息
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
        return musicAllInformationMap;
    }

    /**
     * 流派榜
     *
     * @param type 根据音乐的流派分类查找信息
     * @return Map<Music, User>
     */
    public List<MusicExt> selectListMusicByMusicType(String type) {
        Classification classification = new Classification();
        classification.setType(type);
        List<Music> musicList = selectListMusicByClassification(classification);
        return transformMusics(musicList);
    }
    /**
     * 地区榜
     *
     * @param region 根据音乐的分类的地区查找信息
     * @return Map<Music, User> 音乐和对应的
     * 歌手集合
     */
    public List<MusicExt> selectListMusicByRegion(String region) {
        Classification classification = new Classification();
        classification.setRegion(region);
        List<Music> musicList = selectListMusicByClassification(classification);
        List<Music> resultMusic = playService.sortMusicByPlay(musicList);
        return transformMusics(musicList);
    }
    /**
     * 语言榜
     *
     * @param language 根据音乐的分类的地区查找信息
     * @return Map<Music, User> 音乐和对应的歌手集合
     */
    public List<MusicExt> selectListMusicByLanguage(String language) {
        Classification classification = new Classification();
        classification.setRegion(language);
        List<Music> musicList = selectListMusicByClassification(classification);
        List<Music> resultMusic = playService.sortMusicByPlay(musicList);
        return transformMusics(resultMusic);
    }
    /**
     * 合唱榜
     * 男声、女声、合唱
     */
    public List<MusicExt> selectListMusicByGender(String gender){
        Classification classification = new Classification();
        classification.setGender(gender);
        List<Music> musicList = selectListMusicByClassification(classification);
        List<Music> resultMusic = playService.sortMusicByPlay(musicList);
        return transformMusics(resultMusic);
    }
    /**
     * 新歌榜
     * 查找7天内上架且播放量最高的歌曲
     */
    public List<MusicExt> selectListMusicByNewSong() {
        Music music = new Music();
        List<Music> tempMusicList = new ArrayList<>();
        //获取符合条件的音乐集合，播放次数最多
        //先获取所有音乐七天内上架的音乐，tempMusicList
        List<Music> musicList = musicMapper.selectListMusic(music);
        System.out.println(musicList);
        for (Music m : musicList) {
            Date musicDate = m.getDate();
            if (JudgeIsOverdueUtil.reduceDay(JudgeIsOverdueUtil.toDateSting(musicDate)) <= 70) {
                tempMusicList.add(m);
            }
            //一直数不出来是不是因为数据库使用的问题
            //向不管了
        }
            System.out.println("个数"+""+tempMusicList.size());
            System.out.println("信息"+""+tempMusicList);
            System.out.println("看看有没有歌曲");
        List<Music> sortMusicByPlayCount = playService.sortMusicByPlay(tempMusicList);
        List<MusicExt> musicExts = transformMusics(sortMusicByPlayCount);
      /*  //获取所有歌曲的播放量，存放到Map集合中（第一个元素存放音乐id，第二个元素存放播放量）
        //用HashMap存放，则会按照放入的顺序来存储数据
        Map<Integer, Integer> musicIdAndPlayCount = new HashMap<>();
        for (Music m : musicList) {
            Play play = new Play();
            play.setMusicId(m.getId());
            List<Play> plays = playMapper.selectListPlay(play);
            int count = plays.size();
            musicIdAndPlayCount.put(m.getId(), count);
        }
        //Map怎么根据value将key排序？
        //歌曲id、播放量
        List<Music> musics = new ArrayList<>();
        for (Integer musicId : musicIdAndPlayCount.keySet()) {
            Music temp = new Music();
            temp.setId(musicId);
            musics.add(musicMapper.selectListMusic(temp).get(0));
        }*/
        System.out.println("---------------------"+musicExts);
        return musicExts;
    }
    /**
     * 搜索音乐
     */
    public List<MusicExt> searchMusicByName(String musicName) {
        Music music = new Music();
        music.setName(musicName);
        List<Music> musicList = musicMapper.selectListMusic(music);
        return transformMusics(musicList);
}

    /**
     * 传入一个音乐集合，返回音乐需要展示的信息
     */
    public List<MusicExt> transformMusics(List<Music> musics) {
        if(musics==null || musics.size()==0){
            return null;
        }
        List<MusicExt> musicExts = new ArrayList<>();
        for (Music music : musics) {
            MusicExt musicExt = transformMusic(music);
            musicExts.add(musicExt);
        }
        return musicExts;
    }
    /**
     * 传入入一个音乐，返回需要显示的信息
     * 歌名、id、歌手、id、专辑、id、mv、id
     *
     */
    public MusicExt transformMusic(Music music){
        MusicExt musicExt = new MusicExt();
        int musicId = music.getId();
        int singerId = music.getSingerId();
        int albumId = music.getAlbumId();
        int musicVideoId = music.getMusicVideoId();
        String musicName = music.getName();
        boolean hasMusicVideo = music.getMusicVideoId()!=0;
        BigDecimal musicPrice = music.getPrice();
        int musicLevel = music.getLevel();
        //歌手名字
        User user = new User();
        user.setId(singerId);
        User singer = userMapper.selectUser(user).get(0);
        String singerName = singer.getName();
        //专辑名字
        SongList songList = new SongList();
        songList.setId(albumId);
        SongList album = songListMapper.selectListSongList(songList).get(0);
        String albumName = album.getName();
        musicExt.setMusicId(musicId);
        musicExt.setMusicName(musicName);
        musicExt.setMusicLevel(musicLevel);
        musicExt.setMusicPrice(musicPrice);
        musicExt.setMusicVideoId(musicVideoId);
        musicExt.setAlbumId(albumId);
        musicExt.setAlbumName(albumName);
        musicExt.setSingerId(singerId);
        musicExt.setSingerName(singerName);
        musicExt.setHasMusicVideo(hasMusicVideo);
        return musicExt;
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
