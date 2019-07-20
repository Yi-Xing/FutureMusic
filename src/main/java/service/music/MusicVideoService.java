/*
package service.music;

import entity.*;
import mapper.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

*/
/**
 * @author 蒋靓峣
 *//*

@Service(value = "MusicVideoService")
public class MusicVideoService {
    @Resource(name = "ClassificationMapper")
    ClassificationMapper classificationMapper;
    @Resource(name = "UserMapper")
    UserMapper userMapper;
    @Resource(name = "MusicVideoMapper")
    MusicVideoMapper musicVideoMapper;
    @Resource(name = "MusicMapper")
    MusicMapper musicMapper;
    @Resource(name = "PlayMapper")
    PlayMapper playMapper;
    @Resource(name = "ShowCommentService")
    ShowCommentService showCommentService;
    @Resource(name =  "PlayService")
    PlayService playService;


    */
/**
     * 点击显示MV的详细信息
     *      MV、歌手、评论
     * @param musicVideo 封装要搜索的MVid
     * @return  Map<String,Object> 各类信息
     *//*

    public Map<String,Object> showMusicVideo(MusicVideo musicVideo){
        Map<String,Object> musicVideoMap = new HashMap<>(10);
        List<MusicVideo> musicVideos = musicVideoMapper.selectListMusicVideo(musicVideo);
        musicVideoMap.put("musicVideo",musicVideos.get(0));
        int singerId = musicVideos.get(0).getSingerId();
        User user = new User();
        user.setId(singerId);
        musicVideoMap.put("singer",userMapper.selectUser(user).get(0));
        musicVideoMap.put("goodComment",showCommentService.commentByMusicVideoId(musicVideo.getId()));
        musicVideoMap.put("lastComment",showCommentService.commentLastByMusicVideoId(musicVideo.getId()));
        return musicVideoMap;
    }

    */
/**
     * 展示主页的MV
     * 排行榜前15
     * MV的id、播放量、MV的图片、name、歌手的name、歌手id
     * MV的id
     *//*

    public List<MusicVideoExt> exhibitionMusicVideo(){
        List<MusicVideoExt> musicVideoExts = new ArrayList<>();
        //查找出所MV的播放记录
        Play play = new Play();
        play.setType(2);
        List<Play> playList = playMapper.selectListPlay(play);
        //查找播放最多的MV对应的MV id和播放量 排好序
        Map<Integer,Integer> allMusicVideo = playService.getMostPlayMusic(playList);
        Map<Integer,Integer> rankingMusicVideo = playService.sortByValueDescending(allMusicVideo);
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
    */
/**
     * @return List<MusicVideo>  返回查找到的MV
     *                       设置显示条数，也可用于智搜索框能提示，只显示名字
     *//*

    public List<String[]> selectListMusicVideoByVideoName(MusicVideo musicVideo){
        List<MusicVideo> musicVideoList = musicVideoMapper.selectListMusicVideo(musicVideo);
        return searchMusicVideo(musicVideoList);
    }
    */
/**
     * 通过分类查找MV
     * mv 的 id 、播放量、name、歌手图片、歌手名字
     * 需要play、user、mv三个类的信息
     * *//*

    public List<String[]> searchMusicVideoByClassification(Classification classification){
        List<Classification> classificationList =
                classificationMapper.selectListClassification(classification);
        List<MusicVideo> musicVideoList = new ArrayList<>();
        MusicVideo musicVideo = new MusicVideo();
        for(Classification c:classificationList){
            musicVideo.setClassificationId(c.getId());
            musicVideoList.add(musicVideoMapper.selectListMusicVideo(musicVideo).get(0));
        }
        return searchMusicVideo(musicVideoList);
    }

    */
/**
     *  传入音乐或mv的id，获得mv或音乐的id 播放量
    public Map<Integer,Integer> singerMusicVideo(List<Integer> musicVideos){
        Map<Integer,Integer> musicCounts = new HashMap<>(16);
        for(Integer musicId:musicVideos){
            Play play = new Play();
            play.setMusicId(musicId);
            musicCounts.put(musicId,playMapper.selectListPlay(play).size());
        }
        return musicCounts;
    }*//*

    */
/**
     * 传入音乐id，获取对应歌手
     *//*

    public Map<Integer,User> musicSinger(List<Integer> musicVideos){
        Map<Integer,User> musicSinger = new HashMap<>(16);
        System.out.println(musicVideos);
        for(Integer musicVideoId:musicVideos){
            Music m = new Music();
            m.setId(musicVideoId);
            List<Music> musicList = musicMapper.selectListMusic(m);
            System.out.println(musicList);
            if(musicList.size()==0){
                musicSinger.put(musicVideoId,null);
            }else {
                User user = new User();
                user.setId(musicList.get(0).getSingerId());
                List<User> users =  userMapper.selectUser(user);
                System.out.println(users);
                if(users.size()!=0) {
                    User singer = users.get(0);
                    musicSinger.put(musicVideoId,singer);
                }else{
                    musicSinger.put(musicVideoId,null);
                }
            }
        }
        return musicSinger;
    }
    */
/**
     * 传入MV返回需要显示的信息，扩展类
     *//*

    public MusicVideoExt searchMusicVideo(int musicVideoId){
        //mv的id和播放量
        List<String[]> allMusicVideo = new ArrayList<>();
        for(Integer musicVideoId:musicSinger.keySet()){
            MusicVideo temp = new MusicVideo();
            temp.setId(musicVideoId);
            String[] showMusicVideo = new String[5];
            List<MusicVideo> musicVideos1 = musicVideoMapper.selectListMusicVideo(temp);
            if(musicVideos1.size()!=0) {
                MusicVideo musicVideo = musicVideos1.get(0);
                String musicVideoName = musicVideo.getName();
                showMusicVideo[2] = musicVideoName;
            }else{
                showMusicVideo[2] = null;
            }
//            音乐id、播放量、mv的名字
            String playCount = musicVideoCount.get(musicVideoId)+"";
            if(musicSinger.get(musicVideoId)!=null) {
                String singerPortrait = musicSinger.get(musicVideoId).getHeadPortrait();
                showMusicVideo[3] = singerPortrait;
                String singerName = musicSinger.get(musicVideoId).getName();
                showMusicVideo[1] = playCount;
                showMusicVideo[4] = singerName;
                showMusicVideo[0] = musicVideoId+"";
                allMusicVideo.add(showMusicVideo);
            }else{
                allMusicVideo.add(null);
            }
        }
        return allMusicVideo;
    }
}
*/
