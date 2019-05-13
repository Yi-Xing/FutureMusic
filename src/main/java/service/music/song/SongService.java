package service.music.song;

import entity.Comment;
import entity.Music;
import entity.SongList;
import entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * 对歌曲的操作的Service
 *
 * @author 蒋靓峣 5.11创建
 * */
@Service(value = "SongService")
public class SongService {
    /**
     * @return List<Music>  返回查找到的歌曲
     *                       设置显示条数，也可用于智搜索框能提示，只显示名字
     */
    public List<Music> selectListMusicRecommend(){
        return null;
    }
    /**
     * @return List<SongList>  返回查找到的歌曲
     *                       设置显示条数，也可用于智搜索框能提示，只显示名字
     */
    public List<SongList> selectListSongListByName(String keyWord){
        return null;
    }
    /**
     * @param keyWord 按照指定规则查找指定歌曲
     *                封装信息：歌曲名字
     * @return List<String>  设置显示条数，用于智搜索框能提示，只显示名字
     */
    public List<Music> selectListMusicByName(String keyWord){
        return null;
    }
    /**
     *
     * @param singerName 按照指定规则查找指定歌曲
     *                封装信息：歌手名字
     * @return List<Music>  返回查找到的歌曲
     */
    public List<Music> selectListMusicBySingerName(String singerName){
        return null;
    }
    /**
     *
     * @param singerName 按照指定规则查找指定歌曲
     *                封装信息：歌手名字
     * @return List<Music>  返回查找到的歌曲
     */
    public List<User> selectSingerByName(String singerName){
        return null;
    }
    /**
     *
     * @param classification 按照指定规则查找指定歌曲
     *                封装信息：歌手名字
     * @return List<Music>  返回查找到的歌曲
     */
    public List<Music> selectListMusicByClassification(String classification){
        return null;
    }
    /**
     * @param userId 查看用户歌单
     *                封装信息：用户id
     * @return List<Music>  返回查找到的歌曲
     */
    public List<Music> selectListMusicByUserId(String userId){
        return null;
    }
    /**
     * @param userId 查看歌曲详细信息
     *                封装信息：音乐d
     * @return Music  返回查找到的歌曲
     */
    public Music selectMusicById(String userId){
        return null;
    }
    /**
     * 查看用户收藏的歌曲
     *
     * @param userId 查看用户收藏
     *                封装信息：用户id
     * @return List<Music>  返回查找到的歌曲
     */
    public List<Music> selectListMusicCollectByUserId(String userId){
        return null;
    }
    /**
     * @param userId 查看用户浏览历史
     *                封装信息：用户id
     * @return List<Music>  返回查找到的歌曲
     */
    public List<Music> selectPlayMusicByUserId(String userId){
        return null;
    }
    /**
     * 查看播放量最高的前n首歌曲（看前端的页面）
     *          封装信息：无
     * @return List<Music>  返回查找到的歌曲
     */
    public List<Music> selectPlayMusicByPlayCount(){
        return null;
    }
    /**
     * @param musicId 修改指定musicId的歌曲
     *          封装信息：musicId
     * @return int  返回执行结果：
     *                              1为成功，0为失败，-1为系统异常
     */
    public int updateMusicByMusicId(String musicId){
        return 0;
    }
    /**
     * @param musicId 下载指定musicId的歌曲
     *          封装信息：musicId
     * @return int  返回执行结果：
     *                             1为成功，0为失败，-1为系统异常
     */
    public int downLoadMusicByMusicId(String musicId){
        return 0;
    }
    /**
     * @param music 管理员上传歌曲
     *          封装信息：musicId
     * @return int  返回执行结果：
     *                             1为成功，0为失败，-1为系统异常
     */
    public int addMusic(Music music){
        return 0;
    }
    /**
     * @param address 上传歌曲
     *          封装信息：musicId
     * @return int  返回执行结果：
     *                             1为成功，0为失败，-1为系统异常
     */
    public int upLoadMusic(String address){
        return 0;
    }
    /**
     * @param musicId 查看歌曲评论(consumer和admin）
     *                封装信息：musicId
     * @return <Comment> 返回对应歌曲的所有评论
     *
     */
    public List<Comment> selectCommentByMusicId(String musicId){
        return null;
    }
    /**
     * @param keyWord 查看歌曲评论(admin）
     *                封装信息：musicId
     * @return <Comment> 返回对应歌曲的所有评论
     *
     */
    public List<Comment> selectCommentBykeyWord(String keyWord){
        return null;
    }
    /**
     * @param commentId 删除指定评论(admin）
     *                封装信息：commentId
     * @return int      返回执行结果：0、1、-1
     */
    public int deleteCommentByCommentId(String commentId){
        return 0;
    }
}