package service.music;

import entity.*;
import mapper.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 对MV的操作的Service
 *
 * @author 蒋靓峣 5.11创建
 * */
@Service(value = "PlayService")
public class PlayService {
    @Resource(name = "MusicMapper")
     MusicMapper musicMapper;
    @Resource(name = "PlayMapper")
     PlayMapper playMapper;
    @Resource(name = "UserMapper")
    UserMapper userMapper;

    /**
     * 传入一个play集合，并查询浏览量  用于MV
     */
    public Map<Integer,Integer> getMostPlayMusic(List<Play> playList){
        Map<Integer,Integer> musicPlay = new HashMap<>();
        for(Play play:playList){
                musicPlay.put(play.getMusicId(), playMapper.selectPlays(play));
        }
        return musicPlay;
    }

    /**
     * 传入一个音乐的集合，并获取各自的浏览量，指定是音乐集合
     */
    public Map<Integer,Integer> getMusicPlayCount(List<Music> musicList){
        if(musicList.size()==0) {
            return null;
        }else {
            Map<Integer,Integer> musicCount = new HashMap<>(16);
                Play play = new Play();
                play.setType(1);
            for (Music m : musicList) {
                play.setMusicId(m.getId());
                int plays = playMapper.selectPlays(play);
                musicCount.put(m.getId(), plays);
            }
            return musicCount;
        }
    }
    //下面两个都是辅助方法
    /**
     * 将map按照value的值降序排列 仅用于Map<Integer,Integer>
     * @param map 要排序的map
     * @return 排好序的map
     */
    public List<Map.Entry<Integer,Integer>> sortByValueDescending(Map<Integer,Integer> map) {
        if (map == null || map.size() == 0) {
            return null;
        }
        System.out.println(map);
        System.out.println(map.size()+"555555555555");
        // 逆序
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1,
                               Map.Entry<Integer, Integer> o2) {
                return (o2.getValue() - o1.getValue());
            }
        });
        return list;
    }

    /**
     * 根据浏览量排序音乐
     */
    public List<Music> sortMusicByPlay(List<Music> musicList){
        if(musicList==null ||musicList.size()==0){
            return null;
        }
        //Entry内部类
        List<Map.Entry<Integer,Integer>>  musicPlay = sortByValueDescending(getMusicPlayCount(musicList));
        List<Integer> musicIds = new ArrayList<>();
        for(Map.Entry<Integer,Integer> musicId:musicPlay){
            musicIds.add(musicId.getKey());
        }
        List<Music> resultMusicList = musicMapper.listIdSelectListMusic(musicIds);
        return resultMusicList;
    }
}
