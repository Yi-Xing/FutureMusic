package service.music;

import entity.*;
import mapper.ActivityMapper;
import mapper.ClassificationMapper;
import mapper.MusicMapper;
import mapper.UserMapper;
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
@Service(value = "ExhibitionService")
public class ExhibitionService {
    private static final Logger logger = LoggerFactory.getLogger(ExhibitionService.class);
    @Resource(name="MusicMapper")
    MusicMapper musicMapper;
    @Resource(name="ClassificationMapper")
    ClassificationMapper classificationMapper;
    @Resource(name = "UserMapper")
    UserMapper userMapper;
    @Resource(name = "ActivityMapper")
    ActivityMapper activityMapper;

    /**
     * 查找7天内播放量最高的歌曲
     * 按播放量排列
     * @return
     */
    public Map<Music,User> selectListMusicByNewSong(){
        Map<Music,User> musicSingerMap = new HashMap<>(16);
        Music music = new Music();
        List<Music> musicList = musicMapper.selectListMusic(music);
        Date musicDate;
        User user = new User();
        for(int i=0;i<musicList.size();i++){
            music = musicList.get(i);
            musicDate = music.getDate();
            if( JudgeIsOverdueUtil.reduceDay(JudgeIsOverdueUtil.toDateSting(musicDate))>0){
                musicList.remove(i);
                i=i-1;
            }
        }
        for (Music m:musicList) {
            user.setId(m.getSingerId());
            musicSingerMap.put(m,userMapper.selectUser(user).get(0));
        }
        //获取符合条件得分类对象
        return musicSingerMap;
    }
    /**
     * 音乐的流派榜
     * @param type 根据音乐的流派分类查找信息
     * @return  Map<Music,User>
     */
    public Map<Music,User> selectListMusicByMusicType(String type){
        Classification classification = new Classification();
        classification.setType(type);
        return this.selectListMusicByClassification(classification);
    }
    /**
     * 地区榜
     * @param region 根据音乐的分类的地区查找信息
     * @return Map<Music,User> 音乐和对应的歌手集合
     */
    public Map<Music,User> selectListMusicByRegion(String region){
        Classification classification = new Classification();
        classification.setRegion(region);
        return this.selectListMusicByClassification(classification);
    }
    /**
     * 语言榜
     * @param language 根据音乐的分类的地区查找信息
     * @return Map<Music,User> 音乐和对应的歌手集合
     */
    public Map<Music,User> selectListMusicByLanguage(String language){
        Classification classification = new Classification();
        classification.setRegion(language);
        return this.selectListMusicByClassification(classification);
    }
    /**
     * 展示活动的页面
     *    封装信息搜索的关键字
     * @return List<Activity>  返回查找到的MV
     */
    public List<Activity> selectActivity(){
        Activity activity = new Activity();
        activity.setEndDate(new Date());
        List<Activity> activityList = activityMapper.selectListActivity(activity);
        return activityList;
    }

    /**
     * @param videoName 按照指定规则查找指定MV
     *           封装信息搜索的关键字
     * @return List<MusicVideo>  返回查找到的MV
     */
    public List<MusicVideo> selectListMusicVideoByVideoName(String videoName){
        return null;
    }

    /**
     * 提取的公共代码
     * 因为用静态变量会报错，没有写道公共类里
     * @param classification 按照指定规则查找指定歌曲
     *                封装信息：分类
     * @return  Map<Music,User>  返回查找到的歌曲
     */
    public  Map<Music,User> selectListMusicByClassification(Classification classification){
        //获取符合条件得分类对象
        List<Integer> classificationIds = new ArrayList<>();
        Map<Music, User> musicSingerMap = new HashMap<>();
        User user = new User();
        List<Classification> classificationList = classificationMapper.selectListClassification(classification);
        for (Classification clf : classificationList) {
            //List获取对应得分类id
            classificationIds.add(clf.getId());
        }
        List<Music> musicList = musicMapper.listClassificationIdSelectListMusic(classificationIds);
        for (Music music : musicList) {
            user.setId(music.getSingerId());
            musicSingerMap.put(music, userMapper.selectUser(user).get(0));
        }
        return musicSingerMap;
    }

//    /**
//     *
//     * @param singerName 按照指定规则查找指定歌曲
//     *                封装信息：歌手名字
//     * @return List<MusicVideo>  返回查找到的MV
//     */
//    public List<MusicVideo> selectListMusicVideoBySingerName(String singerName){
//        return null;
//    }
//    /**
//     * @param userId 查看用户收藏的MV
//     *                封装信息：用户id
//     * @return List<Music>  返回查找到的MV
//     */
//    public List<MusicVideo> selectListMusicVideoByUserId(String userId){
//        return null;
//    }
//    /**
//     * 查看播放量最高的前n首歌曲（看前端的页面）
//     *          封装信息：无
//     * @return List<MusicVideo>  返回查找到的歌曲
//     */
//    public List<MusicVideo> selectPlayMusicVideoByPlayCount(){
//        return null;
//    }
//    /**
//     * @param musicVideoId 修改指定musicId的歌曲
//     *          封装信息：musicId
//     * @return int  返回执行结果：
//     *                              1为成功，0为失败，-1为系统异常
//     */
//    public int updateMusicVideoByMusicVideoId(String musicVideoId){
//        return 0;
//    }
//    /**
//     * @param musicVideoId 下载指定musicId的歌曲
//     *          封装信息：musicId
//     * @return int  返回执行结果：
//     *                             1为成功，0为失败，-1为系统异常
//     */
//    public int downLoadMusicVideoByMusicVideoId(String musicVideoId){
//        return 0;
//    }
//    /**
//     * @param musicVideo 管理员上传歌曲
//     *          封装信息：musicId
//     * @return int  返回执行结果：
//     *                             1为成功，0为失败，-1为系统异常
//     */
//    public int addMusic(MusicVideo musicVideo){
//        return 0;
//    }
//    /**
//     * @param address 上传歌曲
//     *          封装信息：musicId
//     * @return int  返回执行结果：
//     *                             1为成功，0为失败，-1为系统异常
//     */
//    public int upLoadMusicVideo(String address){
//        return 0;
//    }
//    /**
//     * @param musicVideoId 查看歌曲评论(consumer和admin）
//     *                封装信息：musicId
//     * @return <Comment> 返回对应歌曲的所有评论
//     *
//     */
//    public List<Comment> selectCommentByMusicVideoId(String musicVideoId){
//        return null;
//    }
//    /**
//     * @param commentId 删除指定评论(admin）
//     *                封装信息：commentId
//     * @return int      返回执行结果：0、1、-1
//     */
//    public int deleteCommentByCommentId(String commentId) {
//        return 0;
//    }

}
