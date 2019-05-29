package service.music;

import entity.*;
import mapper.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service(value =  "SingerService")
public class SingerService {
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
    @Resource(name = "FocusMapper")
    FocusMapper focusMapper;

    /**
     * 通过名字查找歌手
     */
    public List<ShowSinger> exhibitionSingersByName(String singerName){
        System.out.println("1+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        User user = new User();
        user.setName(singerName);
        return searchSingersBySinger(user);
    }

    /**
     * 通过地区查找歌手
     * 也用这个分类查找歌手
     */
    public List<ShowSinger> exhibitionSingersByRegion(String region){
        System.out.println("2+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        User user = new User();
        user.setAddress(region);
        return searchSingersBySinger(user);
    }
    /**
     * 封装歌手信息查找歌手
     */
    public List<ShowSinger> searchSingersBySinger(User user){
        System.out.println("3+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        List<ShowSinger> showSingerList = new ArrayList<>();
        Map<Integer,Integer> singerCountMap = new HashMap<>(16);
        List<User> userList = userMapper.selectUser(user);
        List<Integer> singerIds = new ArrayList<>();
        Play play = new Play();
        int singerId;
        //查找热门歌手的id
        //遍历搜索到的歌手，并添加播放次数，查找最热门的歌手 (根据被播放的次数)
        for(User u:userList){
            singerId = u.getId();
            singerIds.add(singerId);
            play.setSingerId(singerId);
            int playCount = playMapper.selectListPlay(play).size();
            singerCountMap.put(singerId,playCount);
            System.out.println(u.getName());
        }
        Map<Integer,Integer> resultMap = (new PlayService()).sortByValueDescending(singerCountMap);
        //将热门歌手存到List里
        List<Integer> rankingSingers = new ArrayList<>();
        for(Integer integer:resultMap.keySet()){
            rankingSingers.add(integer);
        }
        //根据歌手查找歌手的名字、id、和图片路径
        User singer = new User();
        for(Integer rankingSingerId:rankingSingers){
            ShowSinger showSinger = new ShowSinger();
            singer.setId(rankingSingerId);
            singer.setLevel(2);
            List<User> userList1 = userMapper.selectUser(singer);
            if(userList1.size()!=0) {
                showSinger.setSingerId(rankingSingerId);
                showSinger.setSingerName(userList1.get(0).getName());
                showSinger.setPortrait(userList1.get(0).getHeadPortrait());
            }
            //根据歌手查找粉丝数
            Focus focus = new Focus();
            focus.setUserType(1);
            focus.setUserFocusId(rankingSingerId);
            int count = focusMapper.selectUserFocusCount(focus);
            showSinger.setFocus(count);
            //根据一个歌手id查找热门歌曲，将歌曲的id和名字添加到两个数组里
            List<Music> musicList = popularMusicBySinger(rankingSingerId);
            if(musicList.size()!=0) {
                showSinger.setMusicName(musicList.get(0).getName());
                showSinger.setMusic(musicList);
                showSingerList.add(showSinger);
            }
        }
        return showSingerList;
    }

    /**
     * 查找歌手的热门歌曲
     * @param singerId 歌手id
     */
    public List<Music> popularMusicBySinger(int singerId) {
        Music music = new Music();
        music.setSingerId(singerId);
        Map<Integer, Integer> musicAndPlayCountMap = new HashMap<>(16);
        Play play = new Play();
        List<Music> musicList = musicMapper.selectListMusic(music);
        for (Music m : musicList) {
            play.setMusicId(m.getId());
            List<Play> plays = playMapper.selectListPlay(play);
            musicAndPlayCountMap.put(m.getId(), plays.size());
        }
        Map<Integer, Integer> resultMap =
                (new PlayService().sortByValueDescending(musicAndPlayCountMap));
        List<Integer> musicIds = new ArrayList<>();
        for (Integer integer:resultMap.keySet()) {
                musicIds.add(integer);
        }
        List<Music> musics = musicMapper.listIdSelectListMusic(musicIds);
        return musics;
    }


}
