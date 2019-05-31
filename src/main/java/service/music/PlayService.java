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
@Service(value = "PlayService")
public class PlayService {
    private static final Logger logger = LoggerFactory.getLogger(PlayService.class);
    @Resource(name = "MusicMapper")
    MusicMapper musicMapper;
    @Resource(name = "ClassificationMapper")
    ClassificationMapper classificationMapper;
    @Resource(name = "UserMapper")
    UserMapper userMapper;
    @Resource(name = "SongListMapper")
    SongListMapper songListMapper;
    @Resource(name = "PlayMapper")
    PlayMapper playMapper;
//
//    /**
//     * 根据分类查找歌单
//     * 浏览量
//     * @param classification 查找歌单
//     */
//    public Map<SongList, User> selectListSongListByClassification(Classification classification) {
//        //获取符合条件得分类对象
//        List<Integer> classificationIds = new ArrayList<>();
//        Map<SongList, User> musicSingerMap = new HashMap<>();
//        User user = new User();
//        List<Classification> classificationList = classificationMapper.selectListClassification(classification);
//        for (Classification clf : classificationList) {
//            //List获取对应得分类id
//            classificationIds.add(clf.getId());
//        }
//        List<SongList> songLists = songListMapper.listIdSelectListSongList(classificationIds);
//        for (SongList songList : songLists) {
//            user.setId(songList.getUserId());
//            musicSingerMap.put(songList, userMapper.selectUser(user).get(0));
//        }
//        return musicSingerMap;
//    }
    /**
     * 查找播放记录
     * 1表示是音乐的播放历史  2表示是MV的播放历史
     * @param type
     * @return List<Play>
     */
    private List<Play> getPlayMusic(int type,int id){
        Play play = new Play();
        play.setType(type);
        play.setMusicId(id);
        List<Play> playList = playMapper.selectListPlay(play);
        return playList;
    }

    /**
     * 传入一个play集合，并查询浏览量 这个查询的是所有的
     */
    public Map<Integer,Integer> getMostPlayMusic(List<Play> playList){
        Map<Integer,Integer> musicPlay = new HashMap<>();
        for(Play play:playList){
            if(!musicPlay.containsKey(play.getMusicId())) {
                musicPlay.put(play.getMusicId(), 1);
            }else{
                musicPlay.put(play.getMusicId(),musicPlay.get(play.getMusicId())+1);
            }
        }
        return musicPlay;
    }
    /**
     * 将map按照value的值降序排列 仅用于Map<Integer,Integer>
     * @param map 要排序的map
     * @return 排好序的map
     */
    public Map<Integer,Integer> sortByValueDescending(Map<Integer,Integer> map){
        if(map.size()==0){
            return null;
        }
        Map<Integer,Integer> integerIntegerMap = new LinkedHashMap<>();
        int size = map.size();
        int[] ints = new int[size];
        int i = 0;
        //将map中得键存到一个数组里，然后将数组排序
        for (int key : map.keySet()) {
            ints[i] = map.get(key);
            i++;
        }
        Arrays.sort(ints);
        //从value中找对应的key，存到将要返回的Map中，LinkedHashMap有序
        for (int j = ints.length ;j>0;j--){
            for(Integer key:map.keySet()){
                if(map.get(key).equals(ints[j-1])){
                    integerIntegerMap.put(key,map.get(key));
                    map.put(key,-1);
                    System.out.println(integerIntegerMap.get(key));
                    break;
                }
            }
        }
        return integerIntegerMap;
    }
    /**
     * 传入一个音乐的集合，并获取各自的浏览量，指定是音乐集合
     */
    public Map<Integer,Integer> getMusicPlayCount(List<Music> musicList){
        System.out.println("9999999999999999");
        if(musicList.size()==0) {
            System.out.println("rrrrrrrrrrrrrrrrr");
            return null;
        }else {
        Map<Integer,Integer> musicCount = new HashMap<>(16);
            for (Music m : musicList) {
                Play play = new Play();
                play.setMusicId(m.getId());
                System.out.println("gggggggggggggggg");
                List<Play> plays = playMapper.selectListPlay(play);
                System.out.println("SSSSSSSSSSSSSSss");
                musicCount.put(m.getId(), plays.size());
                System.out.println("PPPPPPPPPPPPP");
            }
            return musicCount;
        }
    }

    /**
     * 根据浏览量排序音乐
     */
    public List<Music> sortMusicByPlay(List<Music> musicList){
        if(musicList.size()==0){
            return null;
        }
        Map<Integer,Integer> musicPlay = sortByValueDescending(getMusicPlayCount(musicList));
        List<Integer> musicIds = new ArrayList<>();
        for(Integer musicId:musicPlay.keySet()){
            musicIds.add(musicId);
        }
        List<Music> resultMusicList = musicMapper.listIdSelectListMusic(musicIds);
        return resultMusicList;
    }

}
