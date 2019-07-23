package service.music;

import entity.*;
import mapper.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    /**
     * 显示歌单的详细信息
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
        //查找歌单中的所有歌曲
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
        return songListExt;
    }
    /**
     *根据分类搜索歌单
     * type
     */
    public List<String[]> showSongListByClassification(String type){
        List<String[]> showSongLists = new ArrayList<>();
        Classification classification = new Classification();
        classification.setType(type);
        List<Classification> classificationList = classificationMapper.selectListClassification(classification);
        for(Classification clf:classificationList){
            SongList songList = new SongList();
            songList.setClassificationId(clf.getId());
            List<SongList> songLists = songListMapper.selectListSongList(songList);
            if(songLists!=null&&songLists.size()!=0){
                showSongLists.addAll(transformShowSongLists(songLists));
            }
        }
        return showSongLists;
    }
    /**
     * 根据名字搜索歌单
     */
    public List<String[]> searchSongListByName(String keyWord){
        SongList songList = new SongList();
        songList.setName(keyWord);
        List<SongList> songLists = songListMapper.selectListSongList(songList);
        List<String[]> showSongList = transformShowSongLists(songLists);
        System.out.println("============");
        return showSongList;
    }
    /**
     * 将歌单集合转化为要显示的信息
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
     * 专歌单或专辑的id、名字、图片、音乐数、收听次数
     */
    public String[] transformShowSongList(SongList songList){
        String[] showSongList = new String[5];
        showSongList[0] = songList.getId()+"";
        showSongList[1] = songList.getName();
        showSongList[2] = songList.getPicture();
        //查找专辑音乐数
        MusicSongList musicSongList = new MusicSongList();
        musicSongList.setBelongId(songList.getId());
        int musicCount = musicSongListMapper.selectListMusicSongList(musicSongList).size();
        showSongList[3] = musicCount+"";
        //查找专辑的播放次数
        Play play = new Play();
        play.setAlbumId(songList.getId());
        int playCount = playMapper.selectPlays(play);
        showSongList[4] = playCount + "";
        return showSongList;
    }
     /**
     * 将MusicSongList转化成显示的歌曲
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
