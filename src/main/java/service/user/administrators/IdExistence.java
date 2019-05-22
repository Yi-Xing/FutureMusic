package service.user.administrators;

import entity.*;
import mapper.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用于判断各种id是否存在
 *  用户，分类，音乐，MV，歌单和专辑，评论，活动
 * @author 5月22日张易兴 创建
 */
@Service(value = "IdExistence")
public class IdExistence {
    @Resource(name = "UserMapper")
    UserMapper userMapper;
    @Resource(name = "ClassificationMapper")
    ClassificationMapper classificationMapper;
    @Resource(name = "MusicMapper")
    MusicMapper musicMapper;
    @Resource(name = "MusicVideoMapper")
    MusicVideoMapper musicVideoMapper;
    @Resource(name = "SongListMapper")
    SongListMapper songListMapper;
    @Resource(name = "CommentMapper")
    CommentMapper commentMapper;
    @Resource(name = "ActivityMapper")
    ActivityMapper activityMapper;

    /**
     * 用于判断指定用户id是否存在,存在返回信息，不存在返回null
     */
    public User isUserId(Integer id) {
        User user=new User();
        user.setId(id);
        // 查找数据库是否有该用户
        List<User> list=userMapper.selectUser(user);
        if(list.size()==0){
            return null;
        }
        return list.get(0);
    }

    /**
     * 用于判断指定分类id是否存在,存在返回信息，不存在返回null
     */
    public Classification isClassificationId(Integer id) {
        Classification classification=new Classification();
        classification.setId(id);
        List<Classification> list=classificationMapper.selectListClassification(classification);
        if(list.size()==0){
            return null;
        }
        return list.get(0);
    }

    /**
     * 用于判断指定音乐id是否存在,存在返回信息，不存在返回null
     */
    public Music isMusicId(Integer id) {
        Music music =new Music();
        music.setId(id);
        List<Music> list=musicMapper.selectListMusic(music);
        if(list.size()==0){
            return null;
        }
        return list.get(0);
    }

    /**
     * 用于判断指定MVid是否存在,存在返回信息，不存在返回null
     */
    public MusicVideo isMusicVideoId(Integer id) {
        MusicVideo musicVideo=new MusicVideo();
        musicVideo.setId(id);
        List<MusicVideo> list=musicVideoMapper.selectListMusicVideo(musicVideo);
        if(list.size()==0){
            return null;
        }
        return list.get(0);
    }

    /**
     * 用于判断指定专辑或歌单id是否存在,存在返回信息，不存在返回null
     */
    public SongList isSongListId(Integer id) {
        SongList songList=new SongList();
        songList.setId(id);
        List<SongList> list=songListMapper.selectListSongList(songList);
        if(list.size()==0){
            return null;
        }
        return list.get(0);
    }
    /**
     * 用于判断指定评论id是否存在,存在返回信息，不存在返回null
     */
    public Comment isCommentId(Integer id) {
        Comment comment=new Comment();
        comment.setId(id);
        List<Comment> list=commentMapper.selectListComment(comment);
        if(list.size()==0){
            return null;
        }
        return list.get(0);
    }
    /**
     * 用于判断指定活动id是否存在,存在返回信息，不存在返回null
     */
    public Activity isActivityId(Integer id) {
        Activity activity=new Activity();
        activity.setId(id);
        List<Activity> list=activityMapper.selectListActivity(activity);
        if(list.size()==0){
            return null;
        }
        return list.get(0);
    }
}
