package service.music;

import entity.*;
import mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import util.CookieUtil;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
/**
 * 对搜索的操作的Service
 *
 * @author 蒋靓峣 5.11创建
 * */
@Service(value = "SearchService")
public class SearchService {
        private static final Logger logger = LoggerFactory.getLogger(SearchService.class);
        @Resource(name="MusicMapper")
        MusicMapper musicMapper;
        @Resource(name="MusicVideoMapper")
        MusicVideoMapper musicVideoMapper;
        @Resource(name = "UserMapper")
        UserMapper userMapper;
        @Resource(name = "SongListMapper")
        SongListMapper songListMapper;

    /**
     *获取搜索记录
     * @param keyWord 关键字
     * @param cookies  Cookie数组
     * @param response 响应
     * @return String 返回获得的搜索记录
     */
    public String addSearchRecord(String keyWord, Cookie[] cookies, HttpServletResponse response){
        String searchRecord = keyWord;
        String cookieName = "searchRecordCookie";
        if(CookieUtil.getCookieByName(cookies,cookieName)!=null){
            Cookie cookie = CookieUtil.getCookieByName(cookies,"searchRecordCookie");
            searchRecord = searchRecord + "#" + cookie.getValue();
            //修改的话直接覆盖
            Cookie newCookie = new Cookie("searchRecordCookie", searchRecord);
            newCookie.setMaxAge(60 * 60 * 24 * 7);
            newCookie.setComment("/*");
            response.addCookie(newCookie);
            logger.info("搜索记录已添加" + searchRecord);
        } else {
            Cookie newCookie = new Cookie("searchRecordCookie", searchRecord);
            newCookie.setMaxAge(60 * 60 * 24 * 7);
            newCookie.setComment("/*");
            response.addCookie(newCookie);
            logger.info("搜索记录已添加" + searchRecord);
        }
        return searchRecord;
    }
    /**
     * 搜索框中鞥提示音乐、专辑、歌单、MV
     * @param keyWord 输入的关键字
     * @return List[] 返回搜索到的集合
     */
    public List[] searchListAll(String keyWord){
        List<SongList> songLists = selectListSongListByName(keyWord);
        List<Music> musicList = selectListMusicByName(keyWord);
        List<User> singerList = selectSingerByName(keyWord);
        List<MusicVideo> musicVideos = selectListMusicVideoByVideoName(keyWord);
        List[] lists = new List[4];
        lists[0] = songLists;
        lists[1] = musicList;
        lists[2] = singerList;
        lists[3] = musicVideos;
        return lists;
    }
    /**
     * @return List<SongList>  返回查找到的歌曲
     *                       设置显示条数，也可用于智搜索框能提示，只显示名字
     */
    private List<SongList> selectListSongListByName(String keyWord){
        SongList songList =new SongList();
        songList.setName(keyWord);
        return songListMapper.selectListSongList(songList);
    }
    /**
     * @return List<MusicVideo>  返回查找到的MV
     *                       设置显示条数，也可用于智搜索框能提示，只显示名字
     */
    public List<MusicVideo> selectListMusicVideoByVideoName(String keyWord){
        MusicVideo musicVideo = new MusicVideo();
        musicVideo.setName(keyWord);
        return musicVideoMapper.selectListMusicVideo(musicVideo);
    }
    /**
     * @param keyWord 按照指定规则查找指定歌曲
     *                封装信息：歌曲名字
     * @return List<String>  设置显示条数，用于智搜索框能提示，只显示名字
     */
    public List<Music> selectListMusicByName(String keyWord){
        Music music =new Music();
        music.setName(keyWord);
        return  musicMapper.selectListMusic(music);
    }

    /**
     * @param singerName 按照指定规则查找指定歌曲
     *                封装信息：歌手名字
     * @return List<Music>  返回查找到的歌曲
     */
    public List<User> selectSingerByName(String singerName){
        User user = new User();
        user.setName(singerName);
        user.setLevel(2);
        List<User> userList = userMapper.selectUser(user);
        if(userList.size()==0){
            return null;
        }
        return userList;
    }
}