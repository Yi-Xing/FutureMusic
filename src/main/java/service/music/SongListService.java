//package service.music;
//
//import entity.*;
//import mapper.*;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author 蒋靓峣
// */
//@Service(value = "SongListService")
//public class SongListService {
//    @Resource(name="ClassificationMapper")
//    ClassificationMapper classificationMapper;
//    @Resource(name = "UserMapper")
//    UserMapper userMapper;
//    @Resource(name = "SongListMapper")
//    SongListMapper songListMapper;
//    @Resource(name = "ShowCommentService")
//    ShowCommentService showCommentService;
//    @Resource(name = "MusicSongListMapper")
//    MusicSongListMapper musicSongListMapper;
//    @Resource(name = "PlayMapper")
//    PlayMapper playMapper;
//    @Resource(name = "SongListCollectMapper")
//    SongListCollectMapper songListCollectMapper;
//    @Resource(name = "MusicService")
//    MusicService musicService;
//    @Resource(name = "MusicMapper")
//    MusicMapper musicMapper;
////    /**
////     * 显示歌单某个的详细信息
////     */
////    public Map<String,Object> showSongListDetail(String songListId){
////        SongList songList = new SongList();
////        songList.setId(Integer.parseInt(songListId));
////        return showSongList(songListMapper.selectListSongList(songList).get(0));
////    }
////    /**
////     *根据分类搜索歌单
////     */
////    public Map<String,Object> showSongListByClassification(String classification){
////        Classification cl = new Classification();
////        cl.setType(classification);
////        Map<String,Object> songListMap = new HashMap<>(16);
////        List<Classification> classificationList = classificationMapper.selectListClassification(cl);
////        for(Classification clf:classificationList){
////            SongList songList = new SongList();
////            songList.setClassificationId(clf.getId());
////            List<SongList> songLists = songListMapper.selectListSongList(songList);
////            if(songLists.size()!=0){
////                SongList resultSongList = songLists.get(0);
////                songListMap.putAll(showSongList(resultSongList));
////            }
////        }
////        return songListMap;
////    }
//    /**
//     * 根据名字搜索歌单
//     */
//    public List<ShowSongListExt> searchSongListByName(String keyWord){
//        SongList songList = new SongList();
//        songList.setName(keyWord);
//        List<SongList> songLists =
//    }
//     /**
//     * 将MusicSongList转化成显示的歌曲
//     */
//    public  List<MusicExt> transformationMusic(List<MusicSongList> musicSongLists){
//       List<Music> musicList = new ArrayList<>();
//        for(MusicSongList musicSongList:musicSongLists){
//            Music music = new Music();
//            music.setId(musicSongList.getMusicId());
//            Music m = musicMapper.selectListMusic(music).get(0);
//            musicList.add(m);
//        }
//        return musicService.transformMusics(musicList);
//    }
//}
