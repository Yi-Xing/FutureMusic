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
 * 对MV的操作的Service
 *
 * @author 蒋靓峣 5.11创建
 * */
@Service(value = "ExhibitionService")
public class ExhibitionService {
    private static final Logger logger = LoggerFactory.getLogger(ExhibitionService.class);
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
     * 查找7天内播放量最高的歌曲
     * 按播放量排列
     *
     * @return
     */
    public Map<Music, User> selectListMusicByNewSong() {
        Map<Music, User> musicSingerMap = new HashMap<>(16);
        Music music = new Music();
        List<Music> musicList = musicMapper.selectListMusic(music);
        Date musicDate;
        User user = new User();
        for (int i = 0; i < musicList.size(); i++) {
            music = musicList.get(i);
            musicDate = music.getDate();
            if (JudgeIsOverdueUtil.reduceDay(JudgeIsOverdueUtil.toDateSting(musicDate)) > 0) {
                musicList.remove(i);
                i = i - 1;
            }
        }
        for (Music m : musicList) {
            user.setId(m.getSingerId());
            musicSingerMap.put(m, userMapper.selectUser(user).get(0));
        }
        //获取符合条件得分类对象
        return musicSingerMap;
    }

    /**
     * 音乐的流派榜
     *
     * @param type 根据音乐的流派分类查找信息
     * @return Map<Music, User>
     */
    public Map<Music, User> selectListMusicByMusicType(String type) {
        Classification classification = new Classification();
        classification.setType(type);
        return this.selectListMusicByClassification(classification);
    }

    /**
     * 地区榜
     *
     * @param region 根据音乐的分类的地区查找信息
     * @return Map<Music, User> 音乐和对应的歌手集合
     */
    public Map<Music, User> selectListMusicByRegion(String region) {
        Classification classification = new Classification();
        classification.setRegion(region);
        return this.selectListMusicByClassification(classification);
    }

    /**
     * 语言榜
     *
     * @param language 根据音乐的分类的地区查找信息
     * @return Map<Music, User> 音乐和对应的歌手集合
     */
    public Map<Music, User> selectListMusicByLanguage(String language) {
        Classification classification = new Classification();
        classification.setRegion(language);
        return this.selectListMusicByClassification(classification);
    }

    /**
     * 在首页中展示活动
     * @return List<Activity>  返回查找到的活动
     */
    public List<Activity> selectActivity() {
        Activity activity = new Activity();
        activity.setEndDate(new Date());
        List<Activity> activityList = activityMapper.selectListActivity(activity);
        return activityList;
    }
    /**
     * @param videoName 按照指定规则查找指定MV
     *                  封装信息搜索的关键字
     * @return List<MusicVideo>  返回查找到的MV
     */
    public List<MusicVideo> selectListMusicVideoByVideoName(String videoName) {
        return null;
    }

    /**
     * 提取的公共代码
     * 因为用静态变量会报错，没有写道公共类里
     *
     * @param classification 按照指定规则查找指定歌曲
     *                       封装信息：分类
     * @return Map<Music, User>  返回查找到的歌曲
     */
    private Map<Music, User> selectListMusicByClassification(Classification classification) {
        //获取符合条件得分类对象
        List<Integer> classificationIds = new ArrayList<>();
        Map<Music, User> musicSingerMap = new HashMap<>(16);
        User user = new User();
        List<Classification> classificationList = classificationMapper.selectListClassification(classification);
        for (Classification clf : classificationList) {
            //List获取对应得分类id
            classificationIds.add(clf.getId());
        }
        List<Music> musicList = musicMapper.listClassificationIdSelectListMusic(classificationIds);
        for (Music music : musicList) {
            user.setId(music.getSingerId());
            musicSingerMap.put(music, userMapper.selectUser(user).get(0));
        }
        return musicSingerMap;
    }

    /**
     * 根据分类查找歌单
     *
     * @param classification
     */

    public Map<SongList, User> selectListSongListByClassification(Classification classification) {
        //获取符合条件得分类对象
        List<Integer> classificationIds = new ArrayList<>();
        Map<SongList, User> musicSingerMap = new HashMap<>();
        User user = new User();
        List<Classification> classificationList = classificationMapper.selectListClassification(classification);
        for (Classification clf : classificationList) {
            //List获取对应得分类id
            classificationIds.add(clf.getId());
        }
        List<SongList> songLists = songListMapper.listIdSelectListSongList(classificationIds);
        for (SongList songList : songLists) {
            user.setId(songList.getUserId());
            musicSingerMap.put(songList, userMapper.selectUser(user).get(0));
        }
        return musicSingerMap;
    }

    /**
     * 根据分类查找MV
     *
     * @param classification
     */
    public Map<MusicVideo, User> selectListMusicVideoListByClassification(Classification classification) {
        //获取符合条件得分类对象
        List<Integer> classificationIds = new ArrayList<>();
        Map<MusicVideo, User> musicSingerMap = new HashMap<>();
        User user = new User();
        List<Classification> classificationList = classificationMapper.selectListClassification(classification);
        for (Classification clf : classificationList) {
            classificationIds.add(clf.getId());
        }
        List<MusicVideo> musicVideoList = musicVideoMapper.listIdSelectListMusicVideo(classificationIds);
        for (MusicVideo musicVideo : musicVideoList) {
            user.setId(musicVideo.getSingerId());
            musicSingerMap.put(musicVideo, userMapper.selectUser(user).get(0));
        }
        return musicSingerMap;
    }

    /**
     * 查找播放记录最多的音乐或MV
     * @param id
     * @param type
     * @return List<Music>
     */
    private List<Play> getPlayMusic(int id,int type){
        Play play = new Play();
        play.setMusicId(id);
        List<Play> playList = playMapper.selectListPlay(play);
        return playList;
    }

    /**
     * 统计一个集合中音乐id出现的次数,按照大小排序
     * @param playList
     * @return
     */
    private Map<Integer,Integer> getMostPlay(List<Play> playList){
        Map<Integer,Integer> musicCountMap = new HashMap<>(16);
        for(Play play:playList){
            int musicId = play.getMusicId();
            if(musicCountMap.containsKey(musicId)){
                musicCountMap.put(musicId,musicCountMap.get(musicId)+1);
            }else{
                musicCountMap.put(musicId,1);
            }
        }
        musicCountMap = sortByValueDescending(musicCountMap);
        return musicCountMap;
    }

    /**
     * 将map按照value的值降序排列
     * @param map
     * @return
     */
    public Map<Integer,Integer> sortByValueDescending(Map<Integer,Integer> map){
        
        return null;
    }
}
