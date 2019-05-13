package controller.music.song;

import entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.song.SongService;
import service.music.video.VideoService;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 歌曲的controller层
 *  1.搜索框智能提示歌曲、歌手、专辑、MV
 *  2.点击显示歌曲排行榜
 *      歌曲详细信息（包括歌词）
 *      歌曲的评论
 * @author 5.12 蒋靓峣创建
 * */

@Controller
public class Song {
    private static final Logger logger = LoggerFactory.getLogger(Song.class);
    @Resource(name = "SongService")
    private SongService songService;
    @Resource(name = "VideoService")
    private VideoService videoService;
    public static State state;
    /**
     * 点击搜索框且在输入关键字之前执行
     *                      使用cookie存到本地
     *                      ajax
     * @return List<String> 返回历史搜索记录
     */
    @RequestMapping(value = "/searchRecord")
    @ResponseBody
    public List<String> searchRecord(HttpServletRequest request){
        List<String> searchRecords = new ArrayList<>();
        Cookie[] cookies = request.getCookies();
        if(cookies!=null&&cookies.length>0) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if ("searchRecordCookies".equals(cookie.getName())) {
                    searchRecords.add(cookie.getValue());
                }
            }
        }
        return searchRecords;
    }
    /**
     * 点击搜索框且在输入关键字之前执行
     *                     向歌手推荐音乐，一般情况下是新歌
     *                      ajax
     *                      这个还没有实现
     * @return List<String> 返回歌曲名字
     */
    @RequestMapping(value = "/recommendSong")
    @ResponseBody
    public List<String> recommendSong(){

        return null;
    }
    /**
     * 点击删除之后执行
     *                   删除cookie
     * @return State
     */
    @RequestMapping(value = "/deleteSearchRecord")
    @ResponseBody
    public State deleteSearchRecord(String deleteOperation){
        if(deleteOperation!=null){
            return null;
        }
        return null;
    }
    /**
     * 点击排行榜执行此方法
     *
     * @return List<Music> 返回匹配到的歌名
     */
    @RequestMapping(value = "/rankingList")
    @ResponseBody
    public List<String> rankingList(){
        return null;
    }
    /**
     * 点击搜索执行此方法，ajax
     *
     * @param request         接收搜索的关键字
     *                          搜索专辑、歌曲、歌手、MV（存放具体信息，可以直接搜到对应得作品）
     *                         加到cookie里
     *                         加判断，不能输入特殊符号
     * @return List<Music> 返回匹配到的歌曲信息
     */
    @RequestMapping(value = "/searchListAll")
    @ResponseBody
    public List<String[]> searchListAll(HttpServletRequest request){
        String keyWord = request.getParameter("keyWord");
        List<SongList> songLists = songService.selectListSongListByName(keyWord);
        List<Music> musicList = songService.selectListMusicByName(keyWord);
        List<User> singerList = songService.selectSingerByName(keyWord);
        List<MusicVideo> musicVideos = videoService.selectListMusicVideoByVideoName(keyWord);
        List<String[]>  searchNames = new ArrayList<>();
        String[] strings1 = songLists.toArray(new String[songLists.size()]);
        String[] strings2 = musicList.toArray(new String[musicList.size()]);
        String[] strings3 = singerList.toArray(new String[singerList.size()]);
        String[] strings4 = musicVideos.toArray(new String[musicVideos.size()]);
        searchNames.add(strings1);
        searchNames.add(strings2);
        searchNames.add(strings3);
        searchNames.add(strings4);
        return searchNames;
    }
    /**
     * @param request 点击执行根据关键字模糊搜索专辑
     *          ajax请求，只更新下面的结果
     * @return List<Music> 返回符合条件的音乐集合
     * */
    @RequestMapping(value = "/searchListSongList")
    @ResponseBody
    public List<SongList> searchListSongList(HttpServletRequest request){
        String keyWord = request.getParameter("keyWord");
        List<SongList> songLists = songService.selectListSongListByName(keyWord);
        return songLists;
    }
    /**
     * @param request 点击执行根据关键字模糊搜索歌曲
     *          ajax请求，只更新下面的结果
     * @return List<Music> 返回符合条件的音乐集合
     * */
    @RequestMapping(value = "/searchListSong")
    @ResponseBody
    public List<Music> searchListSong(HttpServletRequest request){
        String keyWord = request.getParameter("keyWord");
        List<Music> musicList = songService.selectListMusicByName(keyWord);
        return musicList;
    }

    /**
     * @param request 点击执行根据关键字模糊搜索MV
     *          ajax请求，只更新下面的结果
     * @return List<MusicVideo> 返回符合条件的MV集合
     * */
    @RequestMapping(value = "/searchListMusicVideo")
    @ResponseBody
    public List<MusicVideo> searchListMusicVideo(HttpServletRequest request){
        String keyWord = request.getParameter("keyWord");
        List<MusicVideo> musicVideos = videoService.selectListMusicVideoByVideoName(keyWord);
        return musicVideos;
    }

    /**
     * @param request 根据指定分类查找歌曲或专辑
             *          ajax请求，只更新下面的结果
     * @return List<MusicVideo> 返回符合条件的MV集合
     * */
    @RequestMapping(value = "/searchListMusicByClassification")
    @ResponseBody
    public List<Music> searchListMusicByClassification(HttpServletRequest request){
        String classification = request.getParameter("classification");
        List<Music> musicList = songService.selectListMusicByClassification(classification);
        return musicList;
    }

//    /**
//     * @param request 点击执行根据关键字模糊搜索MV
//     *          ajax请求，只更新下面的结果
//     * @return List<User> 返回符合条件的MV集合
//     * */
//    @RequestMapping(value = "/searchListSinger")
//    @ResponseBody
//    public List<User> searchListSinger(HttpServletRequest request){
//        String keyWord = request.getParameter("keyWord");
//        List<User> singers = userService.selectListSingerByName(keyWord);
//        return singers;
//    }
    /**
     * 点击歌单或专辑封面执行此方法
     *
     * @param   musicSongListId      接收搜索的关键字
     *
     * @return List<Music> 返回匹配到的歌单或专辑的具体信息
     */
    @RequestMapping(value = "/searchSongList")
    @ResponseBody
    public MusicSongList searchSongList(String musicSongListId){
        return null;
    }
    /**
     * 点击歌单或专辑封面执行此方法
     *
     * @param   request      接收搜索的musicId
     *
     * @return List<Music> 返回匹配到的歌单或专辑的具体信息
     */
    @RequestMapping(value = "/lookMusicInfo")
    @ResponseBody
    public Music lookMusicInfo(HttpServletRequest request){
        String musicId = request.getParameter("musicId");
        Music music = songService.selectMusicById(musicId);
        return music;
    }
//    下面的我也不知道是不是在这个模块，音乐和MV我先写好些
//    /**
//     * 点击播放执行此方法
//     *
//     * @param musicId         传参：歌曲的id
//     *
//     * @return String 返回歌曲的各种信息，也可用于展示歌词，此过程还要判断用户是否登录
//     *                 歌曲是收费或是否有权限
//     */
//    @RequestMapping(value = "/listenSong")
//    @ResponseBody
//    public Music listenSong(String musicId){
//        return null;
//    }
//    /**
//     * 点击下载执行此方法，ajax
//     *
//     * @param musicId         传参：歌曲的id
//     *
//     * @return String 返回歌曲的各种信息，也可用于展示歌词，此过程还要判断用户是否登录
//     *                   歌曲是收费或是否有权限
//     */
//    @RequestMapping(value = "/downloadSong")
//    @ResponseBody
//    public int downloadSong(String musicId){
//        return 0;
//    }
}
