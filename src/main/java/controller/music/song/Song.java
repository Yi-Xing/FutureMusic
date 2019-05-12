package controller.music.song;

import entity.Music;
import entity.MusicSongList;
import entity.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.song.SongService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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
    public static State state;
    /**
     * 点击搜索框且在输入关键字之前执行
     *                      使用cookie存到本地
     *                      ajax
     * @return List<String> 返回历史搜索记录
     */
    @RequestMapping(value = "/searchRecord")
    @ResponseBody
    public List<String> searchRecord(){
        return songService.searchRecord();
    }
    /**
     * 点击搜索框且在输入关键字之前执行
     *                     向歌手推荐音乐，一般情况下是新歌
     *                      ajax
     * @return List<String> 返回歌曲名字
     */
    @RequestMapping(value = "/searchRecord")
    @ResponseBody
    public List<String> recommendSong(){

        return null;
    }
    /**
     * 点击删除之后执行
     *                   删除cookie
     * @return State
     */
    @RequestMapping(value = "/searchRecord")
    @ResponseBody
    public State deleteSearchRecord(){

        return null;
    }
    /**
     * keyup执行此方法，ajax
     *
     * @param keyWord         接收搜索的关键字(歌名或歌手)
     *
     * @return List<String> 返回匹配到的歌名
     */
    @RequestMapping(value = "/searchListSongName")
    @ResponseBody
    public List<String> searchListSongName(String keyWord, HttpSession httpSession){
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
     * @param keyWord         接收搜索的关键字
     *
     * @return List<Music> 返回匹配到的歌曲信息
     */
    @RequestMapping(value = "/searchListSong")
    @ResponseBody
    public List<Music> searchListSong(String keyWord){
        return null;
    }
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

//    下面的我也不知道是不是在这个模块，音乐和MV我先写好些
    /**
     * 点击播放执行此方法
     *
     * @param musicId         传参：歌曲的id
     *
     * @return String 返回歌曲的各种信息，也可用于展示歌词，此过程还要判断用户是否登录
     *                 歌曲是收费或是否有权限
     */
    @RequestMapping(value = "/listenSong")
    @ResponseBody
    public Music listenSong(String musicId){
        return null;
    }
    /**
     * 点击下载执行此方法，ajax
     *
     * @param musicId         传参：歌曲的id
     *
     * @return String 返回歌曲的各种信息，也可用于展示歌词，此过程还要判断用户是否登录
     *                   歌曲是收费或是否有权限
     */
    @RequestMapping(value = "/downloadSong")
    @ResponseBody
    public int downloadSong(String musicId){
        return 0;
    }

//    /**
//     *
//     * @param musicId        传参：歌曲的各种信息封装成Music
//     *
//     * @return int 返回歌曲是否修改成功的状态
//     */
//    @RequestMapping(value = "/updateSong")
//    @ResponseBody
//    public List<Comment> selectSongComment(Music musicId){
//        return null;
//    }
    /**
     * 点击添加按钮执行此方法，ajax
     *
     * @param music        传参：歌曲的各种信息封装成Music
     *
     * @return int 返回歌曲是否添加成功的状态
     */
    @RequestMapping(value = "/addSong")
    @ResponseBody
    public int addSong(Music music){
        return 0;
    }
//    /**
//     * 点击修改执行此方法，ajax
//     *
//     * @param music        传参：歌曲的各种信息封装成Music
//     *
//     * @return int 返回歌曲是否修改成功的状态
//     */
//    @RequestMapping(value = "/updateSong")
//    @ResponseBody
//    public int updateSong(Music music){
//        return 0;
//    }
}
