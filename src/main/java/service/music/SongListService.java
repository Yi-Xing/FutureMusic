package service.music;

import entity.*;
import mapper.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 蒋靓峣
 */
@Service(value = "SongListService")
public class SongListService {
    @Resource(name="ClassificationMapper")
    ClassificationMapper classificationMapper;
    @Resource(name = "UserMapper")
    UserMapper userMapper;
    @Resource(name = "SongListMapper")
    SongListMapper songListMapper;
    @Resource(name = "MusicSongListMapper")
    MusicSongListMapper musicSongListMapper;
    @Resource(name = "PlayMapper")
    PlayMapper playMapper;
    @Resource(name = "SongListCollectMapper")
    SongListCollectMapper songListCollectMapper;
    @Resource(name = "MusicService")
    MusicService musicService;
    @Resource(name = "MusicMapper")
    MusicMapper musicMapper;
    @Resource(name = "CommentService")
    CommentService commentService;
    /**
     * 显示歌单的详细信息
     * @param songListId 传入参数songListId
     * @return SongListExt 要显示的详细信息
     */
    public SongListExt songListDetail(int songListId){
        SongListExt songListExt = new SongListExt();
        SongList songList1 = new SongList();
        songList1.setId(songListId);
        SongList songList2 = songListMapper.selectListSongList(songList1).get(0);
        songListExt.setSongList(songList2);
        //通过歌手的id查找歌手的名字
        User user = new User();
        user.setId(songList2.getUserId());
        User artist = userMapper.selectUser(user).get(0);
        songListExt.setUserId(artist.getId());
        songListExt.setUsersName(artist.getName());
        //获取分类的详细信息
        Classification classification = new Classification();
        classification.setId(songList2.getClassificationId());
        Classification resultClassification = classificationMapper.selectListClassification(classification).get(0);
        songListExt.setClassification(resultClassification);
        //查找歌单中的歌曲列表
        MusicSongList musicSongList = new MusicSongList();
        musicSongList.setBelongId(songList2.getId());
        List<MusicSongList> musicSongLists = musicSongListMapper.selectListMusicSongList(musicSongList);
        List<MusicExt> musicExts = transformMusics(musicSongLists);
        songListExt.setMusicExtList(musicExts);
        //歌单或专辑的播放次数
        Play play = new Play();
        play.setAlbumId(songList2.getId());
        int playCount = playMapper.selectPlays(play);
        songListExt.setPlayCount(playCount);
        //歌单或专辑的收藏量
        SongListCollect songListCollect = new SongListCollect();
        songListCollect.setMusicId(songList2.getId());
        int collectCount = songListCollectMapper.selectListSongListCollect(songListCollect).size();
        songListExt.setCollectCount(collectCount);
        //显示专辑的评论
        Comment comment = new Comment();
        comment.setId(songListId);
        comment.setType(3);
        songListExt.setCommentExts(commentService.getCommentExt(comment));
        return songListExt;
    }
    /**
     *根据分类搜索歌单
     * @param classification 分类信息
     * @return  List<String[]> 返回歌单信息的集合
     */
    public List<String[]> showSongListByClassification(Classification classification){
        List<String[]> showSongLists = new ArrayList<>();
        List<Classification> classificationList = classificationMapper.selectListClassification(classification);
        for(Classification clf:classificationList){
            SongList songList = new SongList();
            songList.setClassificationId(clf.getId());
            List<SongList> songLists = songListMapper.selectListSongList(songList);
            if(songLists==null&&songLists.size()==0){
                return null;
            }
            showSongLists.addAll(transformShowSongLists(songLists));
        }
        return showSongLists;
    }
    /**
     * 根据名字搜索歌单
     * @param keyWord 搜索的关键字
     * @return List<String[]> 歌单信息的集合
     */
    public List<String[]> searchSongListByName(String keyWord){
        SongList songList = new SongList();
        songList.setName(keyWord);
        List<SongList> songLists = songListMapper.selectListSongList(songList);
        return transformShowSongLists(songLists);
    }
    /**
     * 将歌单集合转化为要显示的信息
     * @param songLists 要转换信息的集合
     * @return List<String[]> 歌单信息的集合
     */
     public List<String[]> transformShowSongLists(List<SongList> songLists){
         List<String[]> showSongLists = new ArrayList<>();
        for(SongList songList:songLists){
            String[] showSongList = transformShowSongList(songList);
            showSongLists.add(showSongList);
        }
        return showSongLists;
    }
    /**
     * 将一个歌单对象转化为要显示的信息
     * @param songList 歌单对象
     * @return String[] 要显示的信息，用数组存放
     *                          专辑id String[0]
     *                          专辑名字 String[1]
     *                          专辑图片 String[2]
     *                          查找专辑音乐数 String[3]
     *                          专辑播放量 String[4]
     */
    public String[] transformShowSongList(SongList songList){
        String[] showSongList = new String[5];
        //专辑id
        showSongList[0] = songList.getId()+"";
        //专辑名字
        showSongList[1] = songList.getName();
        //专辑图片
        showSongList[2] = songList.getPicture();
        //查找专辑音乐数
        MusicSongList musicSongList = new MusicSongList();
        musicSongList.setBelongId(songList.getId());
        int musicCount = musicSongListMapper.selectListMusicSongList(musicSongList).size();
        showSongList[3] = musicCount+"";
        //查找专辑的播放量
        Play play = new Play();
        play.setAlbumId(songList.getId());
        play.setType(3);
        int playCount = playMapper.selectPlays(play);
        showSongList[4] = playCount + "";
        return showSongList;
    }
     /**
     * 查找专辑或者歌单中的歌曲
      * @param musicSongLists
      * @return List<MusicExt> 歌单中的音乐列表
     */
    public  List<MusicExt> transformMusics(List<MusicSongList> musicSongLists){
       List<Music> musicList = new ArrayList<>();
        for(MusicSongList musicSongList:musicSongLists){
            Music music = new Music();
            music.setId(musicSongList.getMusicId());
            Music m = musicMapper.selectListMusic(music).get(0);
            musicList.add(m);
        }
        return musicService.transformMusics(musicList);
    }
}
