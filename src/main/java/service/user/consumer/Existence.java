package service.user.consumer;

import entity.*;
import mapper.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用于验证是否存在
 * 判断用户是否关注或访问指定用户
 * 判断用户是否收藏指定音乐或MV
 * 判断用户是否播放指定音乐或MV
 * 判断用户是否收藏指定歌单或专辑
 * 判断指定指定歌单或专辑是否存在
 *
 * @author 5月19日 张易兴创建
 */
@Service(value = "Existence")
public class Existence {
    @Resource(name = "FocusMapper")
    FocusMapper focusMapper;
    @Resource(name = "MusicCollectMapper")
    MusicCollectMapper musicCollectMapper;
    @Resource(name = "PlayMapper")
    PlayMapper playMapper;
    @Resource(name = "SongListCollectMapper")
    SongListCollectMapper songListCollectMapper;
    @Resource(name = "SongListMapper")
    SongListMapper songListMapper;
    @Resource(name = "CommentMapper")
    CommentMapper commentMapper;
    /**
     * 判断用户是否已经关注或访问过该用户，如果有返回信息，没有返回null
     */
    public Focus isUserFollow(int userId, int userFocusId, int userType) {
        Focus focus = new Focus();
        focus.setUserId(userId);
        focus.setUserFocusId(userFocusId);
        focus.setUserType(userType);
        // 去数据库查找是否访问或关注
        List<Focus> list = focusMapper.selectListFocus(focus);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 判断用户是否收藏指定音乐或MV，如果有返回信息，没有返回null
     */
    public MusicCollect isUserCollectionMusic(int userId, int musicId, int type) {
        MusicCollect musicCollect = new MusicCollect();
        musicCollect.setUserId(userId);
        musicCollect.setMusicId(musicId);
        musicCollect.setType(type);
        // 去数据库查找是否收藏指定音乐或MV
        List<MusicCollect> list = musicCollectMapper.selectListMusicCollect(musicCollect);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 判断用户是否播放指定音乐或MV，如果有返回信息，没有返回null
     */
    public Play isUserMusicPlay(int userId, int musicId, int type) {
        Play play = new Play();
        play.setUserId(userId);
        play.setMusicId(musicId);
        play.setType(type);
        // 去数据库查找是否播放指定音乐或MV
        List<Play> list = playMapper.selectListPlay(play);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 判断用户是否收藏指定歌单或专辑，如果有返回信息，没有返回null
     */
    public SongListCollect isUserCollectionSongList(int userId, int musicId, int type) {
        SongListCollect songListCollect = new SongListCollect();
        songListCollect.setUserId(userId);
        songListCollect.setMusicId(musicId);
        songListCollect.setType(type);
        // 去数据库查找是否收藏指定歌单或专辑
        List<SongListCollect> list = songListCollectMapper.selectListSongListCollect(songListCollect);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 判断指定指定歌单或专辑是否存在,如果存在返回信息，不存在返回null
     */
    public SongList isSongListExistence(int id) {
        SongList songList = new SongList();
        songList.setId(id);
        List<SongList> list = songListMapper.selectListSongList(songList);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
    /**
     * 判断指定是否有子评论，有返回子评论的id，没有返回0
     */
    public int isComment(int id) {
        Comment comment=new Comment();
        comment.setReply(id);
        // 查找指定评论的子评论
        List<Comment> list=commentMapper.selectListComment(comment);
        if(list.size()!=0){
            return list.get(0).getId();
        }
        return 0;
    }
}
