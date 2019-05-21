package controller.music.exhibition;

import entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.ExhibitionService;
import service.music.SearchService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 歌曲的controller层
 *      歌曲详细信息
 *      歌曲的评论
 * @author 5.12 蒋靓峣创建
 * */

@Controller
public class Details {
    private static final Logger logger = LoggerFactory.getLogger(Details.class);
    @Resource(name = "SearchService")
    private SearchService songService;
    @Resource(name = "ExhibitionService")
    private ExhibitionService exhibitionService;
    public static State state;

    /**
     * @param request 点击执行根据关键字模糊搜索专辑
     *          ajax请求，只更新下面的结果
     * @return List<Music> 返回符合条件的音乐集合
     * */
    @RequestMapping(value = "/searchListSongLista")
    @ResponseBody
    public List<SongList> searchListSongLista(HttpServletRequest request){
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
    @RequestMapping(value = "/searchListMusicVideoa")
    @ResponseBody
    public List<MusicVideo> searchListMusicVideoa(HttpServletRequest request){
        String keyWord = request.getParameter("keyWord");
        List<MusicVideo> musicVideos = exhibitionService.selectListMusicVideoByVideoName(keyWord);
        return musicVideos;
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
    @RequestMapping(value = "/searchSongListb")
    @ResponseBody
    public MusicSongList searchSongListb(String musicSongListId){
        return null;
    }
    /**
     * 点击歌单或专辑封面执行此方法
     *
     * @param   request      接收搜索的musicId
     *
//     * @return List<Music> 返回匹配到的歌单或专辑的具体信息
//     */
//    @RequestMapping(value = "/lookMusicInfo")
//    @ResponseBody
//    public Music lookMusicInfo(HttpServletRequest request){
//        String musicId = request.getParameter("musicId");
//        Music music = songService.selectMusicById(musicId);
//        return music;
//    }
////    下面的我也不知道是不是在这个模块，音乐和MV我先写好些
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
