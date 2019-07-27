package service.music;

import entity.Activity;
import entity.Music;
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

    /**
     * 在首页中展示活动 图片和id
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
