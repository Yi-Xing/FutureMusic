package controller.music.song;
import entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.ExhibitionService;
import service.music.SearchService;
import service.music.video.VideoService;
import util.CookieUtil;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 搜索相关的层
 *  1.搜索框智能提示歌曲、歌手、专辑、MV
 *          显示歌名、唱作者、传入musicId
 *          点击后显示歌曲（或专辑）得详细信息、所有信息
 * @author 5.12 蒋靓峣创建
 * */
@Controller
public class Search {
    private static final Logger logger = LoggerFactory.getLogger(Song.class);
    @Resource(name = "ServiceService")
    private SearchService searchService;
    @Resource(name = "ExhibitionService")
    private ExhibitionService exhibitionService;


    /**
     * 点击搜索框且在输入关键字之前执行
     * 获取本地cookie
     * ajax
     * @retutn String[] 返回搜索历史字符串
     */
    @RequestMapping(value = "/searchRecord")
    @ResponseBody
    private String[] searchRecord(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String record = "";
        Cookie cookie = CookieUtil.obtainCookie(cookies,"searchRecordCookie");
        record = cookie.getValue();
        String[] records = record.split("#");
        return records;
    }

    /**
     * 点击搜索框且在输入关键字之前执行
     * 获取本地cookie
     * ajax
     */
    @RequestMapping(value = "/addSearchRecord")
    @ResponseBody
    private void addSearchRecord(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if ("searchRecordCookie".equals(cookie.getName())) {
                    String searchRecord = cookie.getName();
                    searchRecord = request.getParameter("keyWord") + "#"+searchRecord;
                    //修改的话直接覆盖
                    Cookie newCookie = new Cookie("searchRecordCookie", searchRecord);
                    newCookie.setMaxAge(60 * 60 * 24 * 7);
                    newCookie.setComment("/*");
                    response.addCookie(newCookie);
                    logger.debug("搜索记录已添加" + searchRecord);
                }
            }
        }
    }

    /**
     * 点击搜索框、搜索记录的删除执行
     */
    @RequestMapping(value = "/deleteSearchRecord")
    @ResponseBody
    private void deleteSearchRecord(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        Cookie cookie = CookieUtil.obtainCookie(cookies, "searchRecordCookie");
        // 删除浏览记录
        if (cookie != null) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            logger.debug("搜索记录已删除");
        }
    }

    /**
     * 输入关键字执行此方法，ajax
     *
     * @param request 接收搜索的关键字
     *                搜索专辑、歌曲、歌手、MV（存放具体信息，可以直接搜到对应得作品）
     *                加判断，不能输入特殊符号"#"
     * @return List[]返回匹配到的专辑、歌曲、歌手、MV信息
     */
    @RequestMapping(value = "/searchListAll")
    @ResponseBody
    public List[] searchListAll(HttpServletRequest request) {
        String keyWord = request.getParameter("keyWord");
        List<SongList> songLists = searchService.selectListSongListByName(keyWord);
        List<Music> musicList = searchService.selectListMusicByName(keyWord);
        List<User> singerList = searchService.selectSingerByName(keyWord);
        List<MusicVideo> musicVideos = searchService.selectListMusicVideoByVideoName(keyWord);
        List[] lists = new List[4];
        lists[0] = songLists;
        lists[1] = musicList;
        lists[2] = singerList;
        lists[3] = musicVideos;
        return lists;
    }

    /**
     * 点击搜索执行此方法
     * 或者是搜索下面的歌曲
     * @param request 接收搜索的关键字
     * @return List<Music>返回匹配到的专辑、歌曲、歌手、MV信息
     *                      如果有歌手，那么名字必须得相同，不能是模糊匹配
     *                  歌曲在最下歌手在上
     */
    @RequestMapping(value = "/searchListAll")
    @ResponseBody
    public List[] searchListMusic(HttpServletRequest request) {
        String keyWord = request.getParameter("keyWord");
        List<Music> musicList = searchService.selectListMusicByName(keyWord);
        //这个应该是精确匹配
        List<User> singerList = searchService.selectSingerByName(keyWord);
        List[] lists = new List[2];
        lists[0] = musicList;
        lists[1] = singerList;
        return lists;
    }
    /**
     * 点击搜索MV，ajax
     *
     * @param request 接收请求
     * @return List<MusicVideo>返回匹配到的专辑、歌曲、歌手、MV信息
     */
    @RequestMapping(value = "/searchListMusicVideo")
    @ResponseBody
    public List<MusicVideo> searchListMusicVideo(HttpServletRequest request) {
        String keyWord = request.getParameter("keyWord");
        List<MusicVideo> musicVideos = searchService.selectListMusicVideoByVideoName(keyWord);
        return musicVideos;
    }

    /**
     * 点击搜索歌手，ajax
     *
     * @param request 接收请求
     * @return List<MusicVideo>返回匹配到的专辑、歌曲、歌手、MV信息
     */
    @RequestMapping(value = "/searchListSinger")
    @ResponseBody
    public List<User> searchListSinger(HttpServletRequest request) {
        String keyWord = request.getParameter("keyWord");
        List<User> singerList = searchService.selectSingerByName(keyWord);
        return singerList;
    }
    /**
     * 点击搜索专辑，ajax
     *
     * @param request 接收请求
     * @return List<SongList>返回匹配到的歌曲
     */
    @RequestMapping(value = "/searchListSongList")
    @ResponseBody
    public List<User> searchListSongList(HttpServletRequest request) {
        String keyWord = request.getParameter("keyWord");
        List<User> singerList = searchService.selectSingerByName(keyWord);
        return singerList;
    }
    /**
     * 点击搜索歌词，ajax
     *
     * @param request 接收请求
     * @return List<MusicVideo>返回匹配到的专辑、歌曲、歌手、MV信息
     */

    /**
     * 点击ajax推荐的结果，直接跳到歌曲的详细信息
     */


}
