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
    public List<SingerExt> exhibitionSingersByName(String singerName) {
        User user = new User();
        user.setName(singerName);
        user.setLevel(2);
        List<User> singerList = userMapper.selectUser(user);
        List<SingerExt> showSingerList = transformSingers(singerList);
        return showSingerList;
    }

    /**
     * 通过地区查找歌手  bug查不到歌手名字
     * 也用这个分类查找歌手
     */
    public List<SingerExt> exhibitionSingersByRegion(String region) {
        User user = new User();
        user.setAddress(region);
        List<User> singerList = userMapper.selectUser(user);
        System.out.println(singerList);
        List<SingerExt> showSingerList = transformSingers(singerList);
        return showSingerList;
    }

    /**
     * 封装list歌手查找对应的信息
     */
    public List<SingerExt> transformSingers(List<User> singerList){
        List<SingerExt> showSingerList = new ArrayList<>();
        for(User u:singerList){
            SingerExt showSinger = transformSingerExt(u);
            showSingerList.add(showSinger);
        }
        return showSingerList;

    }

    /**
     * 封装歌手信息查找歌手
     */
    public SingerExt transformSingerExt(User user) {
        SingerExt showSinger = new SingerExt();
        showSinger.setSingerId(user.getId());
        String userName =user.getName();
        showSinger.setSingerName(userName);
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
        List<Music> allMusic = musicMapper.selectListMusic(tempMusic);
        Map<Integer, Integer> playCount = playService.getMusicPlayCount(allMusic);
        if(playCount==null||playCount.size()==0){
            return null;
        }else {
            List<Map.Entry<Integer,Integer>> sortPlayCount = playService.sortByValueDescending(playCount);
            List<Music> hotMusic = new ArrayList<>();
            for (Map.Entry<Integer,Integer> musicId : sortPlayCount) {
                Music musicCondition = new Music();
                musicCondition.setId(musicId.getKey());
                List<Music> musicList = musicMapper.selectListMusic(musicCondition);
                if (musicList.size() != 0) {
                    hotMusic.add(musicList.get(0));
                }
            }
            return hotMusic;
        }
    }


}