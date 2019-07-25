package service.music;

import entity.*;
import mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
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
    @Resource(name = "MusicVideoService")
    MusicVideoService musicVideoService;
    @Resource(name = "PlayMapper")
    PlayMapper playMapper;
    @Resource(name = "MusicCollectMapper")
    MusicCollectMapper musicCollectMapper;
    @Resource(name = "PlayService")
    PlayService playService;
    @Resource(name = "CommentService")
    CommentService commentService;
    @Resource(name = "MusicVideoMapper")
    MusicVideoMapper musicVideoMapper;
    public Map<String,Object> showMusic(Integer musicId) {
        Map<String,Object> model = new HashMap<>();
        Music music = new Music();
        music.setId(musicId);
        Music resultMusic = musicMapper.selectListMusic(music).get(0);
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
        model.put("classification",getClassification(classificationId));
        //获取歌曲的播放量
        model.put("musicPlayCount",getPlayCount(musicId));
        //获取歌曲的收藏量
        model.put("musicCollectCount",getCollectCount(musicId));
        //获取mv的详细信息
        model.put("musicVideo",getMusicVideo(musicVideoId));
        return model;
    }
    /**
     * 显示歌曲的详细信息
     *     音乐、专辑图片、歌手信息、分类信息、评论（精彩评论、最新评论）、MV(如果有，显示mv的信息，如果无，显示其他相关歌单）
     * @param musicId 封装音乐id
     * @param model model
     * @return String
     *     音乐、专辑图片、歌手信息、分类信息、评论的详细信息的Map集合
     */
    public String showMusicth(Integer musicId, Model model) {
        Music music = new Music();
        music.setId(musicId);
        Music resultMusic = musicMapper.selectListMusic(music).get(0);
        //获取音乐的信息
        model.addAttribute("music", resultMusic);
        int musicAlbumId = resultMusic.getAlbumId();
        int classificationId = resultMusic.getClassificationId();
        int singerId = resultMusic.getSingerId();
        int musicVideoId = resultMusic.getMusicVideoId();
        //获取评论
        model.addAttribute("comment",getMusicComment(musicId));
        return "music";
    }
    /**
     * 获取歌曲的评论
     */
    public List<CommentExt> getMusicComment(int musicId){
        return commentService.searchCommentByMusicId(musicId,1);
    }
    /**
     * 获取歌曲的MV
     */
    public MusicVideo getMusicVideo(int musicVideoId){
        MusicVideo musicVideo = new MusicVideo();
        musicVideo.setId(musicVideoId);
        List<MusicVideo> musicVideoList = musicVideoMapper.selectListMusicVideo(musicVideo);
        if(musicVideoList==null||musicVideoList.size()==0){
            return null;
        }
        return musicVideoList.get(0);
    }
    /**
     * 获取歌曲的播放量
     */
    public int getPlayCount(int musicId){
        Play p = new Play();
        p.setMusicId(musicId);
        p.setType(1);
        return playMapper.selectPlays(p);
    }
    /**
     * 获取歌曲的收藏量
     */
    public int getCollectCount(int musicId){
        MusicCollect musicCollect = new MusicCollect();
        musicCollect.setMusicId(musicId);
        List<MusicCollect> musicCollects = musicCollectMapper.selectListMusicCollect(musicCollect);
        return musicCollects.size();
    }
    /**
     * 获取歌曲的分类信息
     */
    public Classification getClassification(int classificationId){
        Classification classification = new Classification();
        classification.setId(classificationId);
        List<Classification> classificationList = classificationMapper.selectListClassification(classification);
        if(classificationList.size()==0){
            return null;
        }
        return classificationList.get(0);
    }
    /**
     * 获取歌手的详细信息
     */
    public User getSinger(int singerId){
        User user = new User();
        user.setId(singerId);
        user.setLevel(2);
        List<User> singerList = userMapper.selectUser(user);
        if(singerList==null||singerList.size()==0){
            return null;
        }
        return singerList.get(0);
    }
    /**
     * 获取专辑的详细信息
     */
    public SongList getAlbum(int musicAlbumId){
        SongList songList = new SongList();
        songList.setId(musicAlbumId);
        List<SongList> albums = songListMapper.selectListSongList(songList);
        if(albums==null||albums.size()==0){
            return null;
        }        return  albums.get(0);

    }
    /**
     * 流派榜
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
        return transformMusics(resultMusic);
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
        List<Music> musicList = musicMapper.selectListMusic(music);
        System.out.println(musicList);
        for (Music m : musicList) {
            Date musicDate = m.getDate();
            if (JudgeIsOverdueUtil.reduceDay(JudgeIsOverdueUtil.toDateSting(musicDate)) <= 70) {
                tempMusicList.add(m);
            }
        }
        List<Music> sortMusicByPlayCount = playService.sortMusicByPlay(tempMusicList);
        List<MusicExt> musicExts = transformMusics(sortMusicByPlayCount);
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
