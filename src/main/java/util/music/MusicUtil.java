//package util.music;
//
//import entity.Classification;
//import entity.Music;
//import entity.User;
//import mapper.ClassificationMapper;
//import mapper.MusicMapper;
//import mapper.UserMapper;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author 蒋靓峣  5.20创建
// * 根据分类查找音乐的1工具类
// */
//public class MusicUtil {
//    @Resource(name="MusicMapper")
//    static  MusicMapper musicMapper;
//    @Resource(name="ClassificationMapper")
//    static ClassificationMapper classificationMapper;
//    @Resource(name = "UserMapper")
//    static  UserMapper userMapper;
//    public static Map<Music, User> selectListMusicByClassification(Classification classification) {
//        //获取符合条件得分类对象
//        List<Integer> classificationIds = new ArrayList<>();
//        Map<Music, User> musicSingerMap = new HashMap<>();
//        User user = new User();
//        System.out.println("ceshi1");
//        List<Classification> classificationList = classificationMapper.selectListClassification(classification);
//        System.out.println("ceshi2");
//        for (Classification clf : classificationList) {
//            //List获取对应得分类id
//            classificationIds.add(clf.getId());
//        }
//        List<Music> musicList = musicMapper.listClassificationIdSelectListMusic(classificationIds);
//        System.out.println("ceshi3");
//        for (Music music : musicList) {
//            user.setId(music.getSingerId());
//            musicSingerMap.put(music, userMapper.selectUser(user).get(0));
//        }
//        System.out.println("测试");
//        return musicSingerMap;
//    }
//}
