package service.music;

import entity.*;
import mapper.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Service(value = "MusicVideoService")
public class MusicVideoService {
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
     * 展示主页的MV
     * 排行榜前15
     * MV的id、播放量、MV的图片、name、歌手的name、歌手id
     * MV的id
     */
    public List<String[]> exhibitionMusicVideo(){
        List<String[]> showMusicVideoList = new ArrayList<>();
        MusicVideo musicVideo = new MusicVideo();
        Play play = new Play();
        play.setType(2);
        List<Play> playList = playMapper.selectListPlay(play);
        //查找播放最多的MV对应的MVid和播放量
        Map<Integer,Integer> rankingMusicVideo = (new PlayService()).getMostPlayMusic(playList);
        for(Integer musicId:rankingMusicVideo.keySet()){
            String[] showMusicVideo = new String[6];
            //MV的id
            showMusicVideo[0] = musicId+"";
            //mv的播放量
            showMusicVideo[1] = rankingMusicVideo.get(musicId)+"";
            //mv的name、歌手id
            MusicVideo musicVideo1 = new MusicVideo();
            musicVideo1.setId(musicId);
            MusicVideo musicVideo2 = musicVideoMapper.selectListMusicVideo(musicVideo).get(0);
            showMusicVideo[2] = musicVideo2.getName();
            showMusicVideo[3] = musicVideo2.getSingerId()+"";
            //mv的图片，显示歌手的图片、歌手的名字
            User singer = new User ();
            singer.setId(musicVideo2.getSingerId());
            User singer1 = userMapper.selectUser(singer).get(0);
            showMusicVideo[4] = singer1.getHeadPortrait();
            showMusicVideo[5] = singer1.getName();
            showMusicVideoList.add(showMusicVideo);
    }
        return showMusicVideoList;
    }



    /**
     * @return List<MusicVideo>  返回查找到的MV
     *                       设置显示条数，也可用于智搜索框能提示，只显示名字
     */
    public List<MusicVideo> selectListMusicVideoByVideoName(String keyWord){
        MusicVideo musicVideo = new MusicVideo();
        musicVideo.setName(keyWord);
        return musicVideoMapper.selectListMusicVideo(musicVideo);
    }
    /**
     * 通过分类查找MV
     * mv 的 id 、播放量、name、歌手图片、歌手名字
     * 需要play、user、mv三个类的信息
     * */
    public List<String[]> searchMusicVideoByClassification(Classification classification){
        List<Classification> classificationList =
                classificationMapper.selectListClassification(classification);
        List<Integer> musicVideoList = new ArrayList<>();
        for(Classification c:classificationList){
            MusicVideo musicVideo = new MusicVideo();
            musicVideo.setClassificationId(c.getId());
            musicVideoList.add(musicVideoMapper.selectListMusicVideo(musicVideo).get(0).getId());
        }
        //mv的id和播放量
        Map<Integer,Integer> musicVideoCount = singerMusicVideo(musicVideoList);
        Map<Integer,User> musicSinger = musicSinger(musicVideoList);
        List<String[]> allMusicVideo = new ArrayList<>();
        for(Integer musicVideoId:musicSinger.keySet()){
            MusicVideo temp = new MusicVideo();
            temp.setId(musicVideoId);
            MusicVideo musicVideo = musicVideoMapper.selectListMusicVideo(temp).get(0);
//            音乐id、播放量、mv的名字
            String playCount = musicVideoCount.get(musicVideoId)+"";
            String musicVideoName = musicVideo.getName();
            String singerPortrait = musicSinger.get(musicVideoId).getHeadPortrait();
            String singerName = musicSinger.get(musicVideoId).getName();
            String[] showMusicVideo = new String[5];
            showMusicVideo[0] = musicVideoId+"";
            showMusicVideo[1] = playCount;
            showMusicVideo[2] = musicVideoName;
            showMusicVideo[3] = singerPortrait;
            showMusicVideo[4] = singerName;
            allMusicVideo.add(showMusicVideo);
        }
        return allMusicVideo;
    }
    /**
     *  传入音乐或mv的id，获得mv或音乐的id 播放量
     */
    public Map<Integer,Integer> singerMusicVideo(List<Integer> musicVideos){
        Map<Integer,Integer> musicCounts = new HashMap<>(16);
        for(Integer musicId:musicVideos){
            Play play = new Play();
            play.setMusicId(musicId);
            musicCounts.put(musicId,playMapper.selectListPlay(play).size());
        }
        return musicCounts;
    }
    /**
     * 传入音乐id，获取对应歌手
     */
    public Map<Integer,User> musicSinger(List<Integer> musicVideos){
        Map<Integer,User> musicSinger = new HashMap<>(16);
        for(Integer musicVideoId:musicVideos){
            User user = new User();
            user.setId(musicVideoId);
            User singer = userMapper.selectUser(user).get(0);
            musicSinger.put(musicVideoId,singer);
        }
        return musicSinger;
    }
}
