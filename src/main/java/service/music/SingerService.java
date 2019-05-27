package service.music;

import entity.Focus;
import entity.Music;
import entity.Play;
import entity.User;
import mapper.*;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 包含信息：歌手名字、粉丝数、图片地址、id、热门歌曲和id、代表作
     * 数组，第一个是歌手id，第二个是
     * @param region
     * @return
     */
    public List<String[]> exhibitionSingersByRegion(String region){
        User user = new User();
        user.setAddress(region);
        Map<Integer,Integer> singerCountMap = new HashMap<>();
        List<User> userList = userMapper.selectUser(user);
        List<Integer> singerIds = new ArrayList<>();
        List<String[]> show = new ArrayList<>();
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
        }
        Map<Integer,Integer> resultMap = (new ExhibitionService()).sortByValueDescending(singerCountMap);
        //将热门歌手存到List里
        List<Integer> rankingSingers = new ArrayList<>();
        for(Integer integer:resultMap.keySet()){
            rankingSingers.add(integer);
        }
        //根据歌手查找歌手的名字和图片路径
        List<String> singerName = new ArrayList<>();
        List<String> singerPicture = new ArrayList<>();
        User singer = new User();
        for(Integer integer:rankingSingers){
            singer.setId(integer);
            singer.setLevel(2);
            User temp = userMapper.selectUser(singer).get(0);
            singerName.add(temp.getName());
            singerPicture.add(temp.getHeadPortrait());
        }
        //根据歌手查找粉丝数
        Focus focus = new Focus();
        focus.setUserType(1);
        List<Integer> focusCount = new ArrayList<>();
        for(int rankingSingerId:rankingSingers){
            focus.setUserFocusId(rankingSingerId);
            int count = focusMapper.selectUserFocusCount(focus);
            focusCount.add(count);
        }
        //根据一个歌手id查找热门歌曲，将歌曲的id和名字添加到两个数组里
        Map<Integer,List<Music>> integerMusicMap = new HashMap<>();
        for(int rankingSingerId:rankingSingers) {
            List<Music> musicList = popularMusicBySinger(rankingSingerId);
            //将歌曲的id和名字添加到两个数组里
            integerMusicMap.put(rankingSingerId,musicList);
        }
        return null;
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
                (new ExhibitionService().sortByValueDescending(musicAndPlayCountMap));
        List<Integer> musicIds = new ArrayList<>();
        for (Integer integer:resultMap.keySet()) {
                musicIds.add(integer);
        }
        List<Music> musics = musicMapper.listIdSelectListMusic(musicIds);
        return musics;
    }
}
