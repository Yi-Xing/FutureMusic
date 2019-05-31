package service.music;

import entity.Activity;
import entity.Music;
import entity.Play;
import entity.SongList;
import mapper.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author  蒋靓峣
 */
@Service(value = "ActivityService")
public class ActivityService {

@Resource(name = "ActivityMapper")
private ActivityMapper activityMapper;
    @Resource(name="MusicMapper")
    MusicMapper musicMapper;
    @Resource(name = "SongListMapper")
    SongListMapper songListMapper;
    @Resource(name = "PlayMapper")
    PlayMapper playMapper;

    /**
     * 查找播放记录
     * 1表示是音乐的播放历史  2表示是MV的播放历史
     * @param type
     * @return List<Play>
     */
    private List<Play> getPlayMusic(int type, int id){
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
//    /**
//     * 将map按照value的值降序排列 仅用于Map<Integer,Integer>
//     * @param map 要排序的map
//     * @return 排好序的map
//     */
//    public Map<Integer,Integer> sortByValueDescending(Map<Integer,Integer> map){
//        if(map.size()==0){
//            return null;
//        }
//        Map<Integer,Integer> integerIntegerMap = new LinkedHashMap<>();
//        int size = map.size();
//        int[] ints = new int[size];
//        int i = 0;
//        //将map中得键存到一个数组里，然后将数组排序
//        for (int key : map.keySet()) {
//            ints[i] = map.get(key);
//            i++;
//        }
//        Arrays.sort(ints);
//        //从value中找对应的key，存到将要返回的Map中，LinkedHashMap有序
//        for (int j = ints.length ;j>0;j--){
//            for(Integer key:map.keySet()){
//                if(map.get(key).equals(ints[j-1])){
//                    integerIntegerMap.put(key,map.get(key));
//                    map.put(key,-1);
//                    System.out.println(integerIntegerMap.get(key));
//                    break;
//                }
//            }
//        }
//        return integerIntegerMap;
//    }
//    /**
//     * 传入一个音乐的集合，并获取各自的浏览量，指定是音乐集合
//     */
//    public Map<Integer,Integer> getMusicPlayCount(List<Music> musicList){
//        if(musicList.size()==0) {
//            return null;
//        }else {
//            Map<Integer,Integer> musicCount = new HashMap<>(16);
//            System.out.println("musicList----------------"+musicList);
//            for (Music m : musicList) {
//                Play play = new Play();
//                play.setMusicId(m.getId());
//                play.setType(1);
//                System.out.println(playMapper);
//                System.out.println(musicMapper);
//                System.out.println(songListMapper);
//                System.out.println(activityMapper);
//                System.out.println("play数据库开始查询"+play);
//                List<Play> playss = playMapper.selectListPlay(new Play());
//                System.out.println("我执行完了");
//                List<Play> plays =new ArrayList<>();
//                System.out.println("数据库查询成功"+plays);
//                musicCount.put(m.getId(), plays.size());
//            }
//            return musicCount;
//        }
//    }
//
//    /**
//     * 根据浏览量排序音乐
//     */
//    public List<Music> sortMusicByPlay(List<Music> musicList){
//        if(musicList.size()==0){
//            return null;
//        }
//        System.out.println("我准备执行了");
//        Map<Integer,Integer> musicPlay = sortByValueDescending(getMusicPlayCount(musicList));
//        System.out.println("musicPlay"+musicPlay);
//        List<Integer> musicIds = new ArrayList<>();
//        for(Integer musicId:musicPlay.keySet()){
//            musicIds.add(musicId);
//        }
//        List<Music> resultMusicList = musicMapper.listIdSelectListMusic(musicIds);
//        return resultMusicList;
//    }
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
     * 显示活动的详细信息包括活动的详细信息、参与活动的歌曲或者专辑
     * @param activity 封装activity的id
     * @return 返回一系列的相关信息
     */
    public Map<String,Object> showActivity(Activity activity) {
        Map<String,Object> activityMap = new HashMap<>(3);
        Activity resultActivity = activityMapper.selectListActivity(activity).get(0);
        activityMap.put("activity",resultActivity);
        int activityId = resultActivity.getId();
        //取出活动对应的音乐
        Music music = new Music();
        music.setActivity(activityId);
        List<Music> musicList = musicMapper.selectListMusic(music);
        if(musicList.size()==0){
            activityMap.put("musicList",null);
        }else{
            activityMap.put("musicList",musicList);
        }
        //取出活动对应的专辑
        SongList songList = new SongList();
        songList.setActivity(activityId);
        List<SongList> songListList = songListMapper.selectListSongList(songList);
        if(songListList.size()==0){
            activityMap.put("songListList",null);
        }else{
            activityMap.put("songListList",songListList);
        }
        return activityMap;
    }

}
