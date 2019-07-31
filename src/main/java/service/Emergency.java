package service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.*;
import mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.*;

/**
 * 救急业务逻辑速度成接口，佛祖保佑，无bug一遍过
 *
 * @author 考核前天 23:59创建
 */
@Service(value = "Emergency")
public class Emergency {
    private static final Logger logger = LoggerFactory.getLogger(Emergency.class);
    @Resource(name = "SongListMapper")
    SongListMapper songListMapper;
    @Resource(name = "MusicMapper")
    MusicMapper musicMapper;
    @Resource(name = "UserMapper")
    UserMapper userMapper;
    @Resource(name = "ActivityMapper")
    ActivityMapper activityMapper;
    @Resource(name = "MusicVideoMapper")
    MusicVideoMapper musicVideoMapper;
    @Resource(name = "MusicCollectMapper")
    MusicCollectMapper musicCollectMapper;
    @Resource(name = "SongListCollectMapper")
    SongListCollectMapper songListCollectMapper;

    // 需求 数据库至少有3个活动  5个音乐人，每个人至少5个音乐  至少有5个MV
    // 遍历所有   MV 音乐 专辑（每个至少18个）

    /**
     * 显示首页
     */
    public String homePage(Model model) {
        // 3个活动信息 List<Activity>
        model.addAttribute("Activity", getActivity());
        // 5个音乐人，每个人5个音乐 Map<User, List<Music>>
        model.addAttribute("userMusic", getUserAndMusic());
        // 查找4个MV  Map<Integer, List<Music>>
        model.addAttribute("musicVideo", getMusicVideo());
        // 查找15首个分5份来存放  Map<Integer, List<Music>>
        Map<Integer, List<Music>> music = getMusic();
        model.addAttribute("music1", music.get(0));
        model.addAttribute("music2", music.get(1));
        model.addAttribute("music3", music.get(2));
        model.addAttribute("music4", music.get(3));
        model.addAttribute("music5", music.get(4));
        return "music/index";
    }

    /**
     * 查找所有的音乐分页显示
     */
    public String getMusicList(Integer page, String condition, Model model) {
        Music music = new Music();
        if (condition != null && !"".equals(condition)) {
            music.setName(condition);
        }
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(page, 20);
        // 根据条件查找音乐信息
        List<Music> list = musicMapper.selectListMusic(music);
        PageInfo pageInfo = new PageInfo<>(list);
        // 传入页面信息
        logger.debug("查找到的音乐" + list);
        model.addAttribute("condition", condition);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("pages", pageInfo.getNavigatepageNums());
        model.addAttribute("pageCount", pageInfo.getNavigatepageNums().length);
        return "music/music";
    }

    /**
     * 查找所有的MV分页显示
     */
    public String getMusicVideoList(Integer page, Model model) {
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(page, 12);
        // 根据条件查找MV信息
        List<MusicVideo> list = musicVideoMapper.selectListMusicVideo(new MusicVideo());
        PageInfo pageInfo = new PageInfo<>(list);
        // 传入页面信息
        logger.debug("查找到的MV" + list);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("pages", pageInfo.getNavigatepageNums());
        model.addAttribute("pageCount", pageInfo.getNavigatepageNums().length);
        return "music/musicVideo";
    }

    /**
     * 查找所有的专辑分页显示
     */
    public String getSongListList(Integer page, Model model) {
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(page, 12);
        SongList songList = new SongList();
        songList.setType(2);
        // 根据条件查找专辑信息
        List<SongList> list = songListMapper.selectListSongList(songList);
        PageInfo pageInfo = new PageInfo<>(list);
        // 传入页面信息
        logger.debug("查找到的专辑" + list);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("pages", pageInfo.getNavigatepageNums());
        model.addAttribute("pageCount", pageInfo.getNavigatepageNums().length);
        return "music/album";
    }

    /**
     * 从数据库去活动3个活动信息
     */
    public List<Activity> getActivity() {
        List<Activity> list = activityMapper.selectListActivity(new Activity());
        List<Activity> listActivity = new ArrayList<>();
        if (list.size() >= 3) {
            listActivity.add(list.get(2));
            listActivity.add(list.get(0));
            listActivity.add(list.get(1));
            listActivity.add(list.get(2));
            listActivity.add(list.get(0));
        }
        return listActivity;
    }

    /**
     * 5个音乐人，每个人5个音乐
     */
    public Map<User, List<Music>> getUserAndMusic() {
        Map<User, List<Music>> map = new HashMap<>();
        User user = new User();
        user.setLevel(2);
        List<User> listUsers = userMapper.selectUsers(user);
        List<User> listUser = new ArrayList<>();
        // 得到5个用户
        System.out.println("个数" + listUsers.size());
        if (listUsers.size() >= 5) {
            listUser.add(listUsers.get(0));
            listUser.add(listUsers.get(1));
            listUser.add(listUsers.get(2));
            listUser.add(listUsers.get(3));
            listUser.add(listUsers.get(4));
        }
        Music music = new Music();
        // 用来存储每个用户的5个音乐
        // 遍历用户查找音乐
        for (User u : listUser) {
            List<Music> listMusic = new ArrayList<>();
            music.setSingerId(u.getId());
            // 查找到指定用户的所有音乐
            List<Music> list = musicMapper.selectListMusic(music);
            if (list.size() >= 5) {
                u.setPassword(list.get(0).getName());
                listMusic.add(list.get(0));
                listMusic.add(list.get(1));
                listMusic.add(list.get(2));
                listMusic.add(list.get(3));
                listMusic.add(list.get(4));
                map.put(u, listMusic);
            }
        }
        return map;
    }

    /**
     * 查找15首个分5份来存放
     */
    public Map<Integer, List<Music>> getMusic() {
        Map<Integer, List<Music>> map = new HashMap<>();
        // 查找到所有音乐
        List<Music> list = musicMapper.selectListMusic(new Music());
        if (list.size() >= 15) {
            List<Music> listMusic = new ArrayList<>();
            listMusic.add(list.get(0));
            listMusic.add(list.get(1));
            listMusic.add(list.get(2));
            map.put(0, listMusic);
            listMusic = new ArrayList<>();
            listMusic.add(list.get(3));
            listMusic.add(list.get(4));
            listMusic.add(list.get(5));
            map.put(1, listMusic);
            listMusic = new ArrayList<>();
            listMusic.add(list.get(6));
            listMusic.add(list.get(7));
            listMusic.add(list.get(8));
            map.put(2, listMusic);
            listMusic = new ArrayList<>();
            listMusic.add(list.get(9));
            listMusic.add(list.get(10));
            listMusic.add(list.get(11));
            map.put(3, listMusic);
            listMusic = new ArrayList<>();
            listMusic.add(list.get(12));
            listMusic.add(list.get(13));
            listMusic.add(list.get(14));
            map.put(4, listMusic);
        }
        return map;
    }

    /**
     * 查找4个MV
     */
    public List<MusicVideo> getMusicVideo() {
        List<MusicVideo> musicVideoList = new ArrayList<>();
        List<MusicVideo> list = musicVideoMapper.selectListMusicVideo(new MusicVideo());
        if (list.size() >= 4) {
            musicVideoList.add(list.get(0));
            musicVideoList.add(list.get(1));
            musicVideoList.add(list.get(2));
            musicVideoList.add(list.get(3));
        }
        return musicVideoList;
    }

    /**
     * 判断指定音乐是否已被收藏  1为音乐   （1为收藏  0为没收藏）
     */
    public List<Music> getMusicCollect(User user, List<Music> list) {
        MusicCollect musicCollect = new MusicCollect();
        // 设置用户Id
        musicCollect.setUserId(user.getId());
        musicCollect.setType(1);
        for (Music music : list) {
            musicCollect.setMusicId(music.getId());
            int count = musicCollectMapper.selectUserMusicCollectCount(musicCollect);
            if (count != 0) {
                music.setClassificationId(1);
            } else {
                music.setClassificationId(0);
            }
        }
        return list;
    }

    /**
     * 判断指定MV是否已被收藏  2为MV
     */
    public List<MusicVideo> getMusicVideoCollect(User user, List<MusicVideo> list) {
        MusicCollect musicCollect = new MusicCollect();
        // 设置用户Id
        musicCollect.setUserId(user.getId());
        musicCollect.setType(2);
        for (MusicVideo music : list) {
            musicCollect.setMusicId(music.getId());
            int count = musicCollectMapper.selectUserMusicCollectCount(musicCollect);
            if (count != 0) {
                music.setClassificationId(1);
            } else {
                music.setClassificationId(0);
            }
            System.out.println(music);
        }
        return list;
    }


    /**
     * 判断指定专辑或歌单是否收藏
     */
    public SongList getSongListCollect(User user, SongList songList) {
        SongListCollect songListCollect = new SongListCollect();
        songListCollect.setUserId(user.getId());
        songListCollect.setMusicId(songList.getId());
        if (songListCollectMapper.selectUserSongListCollectCount(songListCollect) != 0) {
            songList.setClassificationId(1);
        } else {
            songList.setClassificationId(0);
        }
        return songList;
    }

    /**
     * 模糊查找指定音乐
     */
    public List<Music> getSearchMusic(String search) {
        Music music = new Music();
        music.setName(search);
        List<Music> list = new ArrayList<>();
        // 循环遍历控制循环次数最多5次
        int i = 1;
        for (Music m : musicMapper.selectListMusic(music)) {
            if (i <= 5) {
                list.add(m);
                i++;
            } else {
                break;
            }
        }
        logger.debug("搜索到的内容"+list);
        return list;
    }

}
