package service.music;

import entity.*;
import mapper.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 蒋靓峣
 */
@Service(value =  "SingerService")
public class SingerService {
    @Resource(name = "MusicMapper")
    MusicMapper musicMapper;
    @Resource(name = "UserMapper")
    UserMapper userMapper;
    @Resource(name = "FocusMapper")
    FocusMapper focusMapper;
    @Resource(name = "PlayService")
    PlayService playService;

    /**
     * 通过名字查找歌手
     */
    public List<ShowSinger> exhibitionSingersByName(String singerName) {
        User user = new User();
        user.setName(singerName);
        List<User> singerList = userMapper.selectUser(user);
        List<ShowSinger> showSingerList = searchSingersListBySinger(singerList);
        return showSingerList;
    }

    /**
     * 通过地区查找歌手
     * 也用这个分类查找歌手
     */
    public List<ShowSinger> exhibitionSingersByRegion(String region) {
        User user = new User();
        user.setAddress(region);
        List<User> singerList = userMapper.selectUser(user);
        System.out.println("userMapper"+userMapper);
        List<ShowSinger> showSingerList = searchSingersListBySinger(singerList);
        return showSingerList;
    }

    /**
     * 封装list歌手查找对应的信息
     */
    public List<ShowSinger> searchSingersListBySinger(List<User> singerList){
        List<ShowSinger> showSingerList = new ArrayList<>();
        for(User u:singerList){
            ShowSinger showSinger = searchSingersBySinger(u);
            showSingerList.add(showSinger);
        }
        return showSingerList;

    }

    /**
     * 封装歌手信息查找歌手
     */
    public ShowSinger searchSingersBySinger(User user) {
        ShowSinger showSinger = new ShowSinger();
        showSinger.setSingerId(user.getId());
        showSinger.setMusicName(user.getName());
        showSinger.setPortrait(user.getHeadPortrait());
        //查找歌手的热门音乐5首
        List<Music> hotMusic = popularMusicBySinger(user);
        List<Music> limitHotMusic = new ArrayList<>();
        int limit=1;
        if(hotMusic==null||hotMusic.size()==0){
            showSinger.setMusic(null);
            showSinger.setMusicName(null);
        }else{
            for(Music m:hotMusic){
                limitHotMusic.add(m);
                limit++;
                if(limit>5){
                    break;
                }
            }
            showSinger.setMusic(limitHotMusic);
            if (hotMusic.size() != 0) {
                showSinger.setMusicName(hotMusic.get(0).getName());
            }else{
                showSinger.setMusicName(null);
            }
        }
        //根据歌手查找粉丝数
        Focus focus = new Focus();
        focus.setUserType(1);
        focus.setUserFocusId(user.getId());
        int count = focusMapper.selectUserFocusCount(focus);
        showSinger.setFocus(count);
        return showSinger;
    }

    /**
     * 查找歌手的热门歌曲
     *封装歌手的信息
     * @param user 歌手id
     */
    public List<Music> popularMusicBySinger(User user) {
        //查找歌手的热门音乐5首
        Music tempMusic = new Music();
        tempMusic.setSingerId(user.getId());
        System.out.println("222222222222222");
        List<Music> allMusic = musicMapper.selectListMusic(tempMusic);
        System.out.println("3333333333333333");
        Map<Integer, Integer> playCount = playService.getMusicPlayCount(allMusic);
        System.out.println("4444444444444444");
        if(playCount==null||playCount.size()==0){
            return null;
        }else {
            System.out.println("55555555555555");
            Map<Integer, Integer> sortPlayCount = playService.sortByValueDescending(playCount);
            List<Music> hotMusic = new ArrayList<>();
            for (Integer musicId : sortPlayCount.keySet()) {
                Music musicCondition = new Music();
                musicCondition.setId(musicId);
                List<Music> musicList = musicMapper.selectListMusic(musicCondition);
                if (musicList.size() != 0) {
                    hotMusic.add(musicList.get(0));
                }
            }
            return hotMusic;
        }
    }

    /**
     * 显示歌手的详细信息
     */

}