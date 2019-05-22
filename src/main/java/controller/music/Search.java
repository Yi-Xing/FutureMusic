package controller.music;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.ExhibitionService;
import service.music.SearchService;
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
    private static final Logger logger = LoggerFactory.getLogger(Music.class);
    @Resource(name = "SearchService")
    private SearchService searchService;
    @Resource(name ="ExhibitionService")
    private ExhibitionService exhibitionService;
    /**
     * 点击搜索框且在输入关键字之前执行
     * 获取本地cookie
     * ajax
     * @retutn String[] 返回搜索历史字符串
     */
    @RequestMapping(value = "/searchMyRecord")
    @ResponseBody
    private String[] searchMyRecord(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie cookie = CookieUtil.obtainCookie(cookies,"searchRecordCookie");
        String searchRecord = cookie.getValue();
        String[] records = searchRecord.split("#");
        return records;
    }

    /**
     * 点击搜索执行
     * 如果存在历史记录，则修改相应的值
     * 不存在则增加一个"searchRecordCookie"名字的cookie
     * 获取本地cookie
     * ajax
     */
    @RequestMapping(value = "/addSearchRecord")
    @ResponseBody
    private String addSearchRecord(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String searchRecord = request.getParameter("keyWord");
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
        return searchRecord ;
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
     * @param keyWord 接收搜索的关键字
     *                搜索专辑、歌曲、歌手、MV（存放具体信息，可以直接搜到对应得作品）
     *                加判断，不能输入特殊符号"#"
     * @return List[] 返回匹配到的专辑、歌曲、歌手、MV信息
     */
    @RequestMapping(value = "/searchListAll")
    @ResponseBody
    public List[] searchListAll(String keyWord) {
       return searchService.searchListAll(keyWord);
    }

    /**
     * 点击搜索执行此方法
     * 或者点击导航栏歌曲
     * @param keyWord 接收搜索的关键字
     * @param pn 接收搜索的关键字
     * @return List<Music>返回匹配到的歌曲
     */
    @RequestMapping(value = "/searchListMusic")
    @ResponseBody
    public PageInfo searchListMusic(@RequestParam(required = false,value = "pn", defaultValue = "1") Integer pn,@RequestParam(value = "keyWord",defaultValue = "") String keyWord) {
        //使用分页插件进行分页查询
        //传入页码和页面的大小
        PageHelper.startPage(pn,2);
        //start后面紧跟的查询，就是分页查询
        List<Music> musicList = searchService.selectListMusicByName(keyWord);
        //包装查询结果，只需将pageinfo交给页面
        //包括我们查询的数据
        //第二个，传入连续显示的页数
        PageInfo page = new PageInfo(musicList,5);
        return page;
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

    @RequestMapping(value = "/testCookie")
    @ResponseBody
    public String[] testCookie(HttpServletRequest request) {
        String keyWord = request.getParameter("keyWord");
        Cookie[] cookies = request.getCookies();
        String[] str = new String[cookies.length*2];
        int i=0;
        for(Cookie cookie:cookies){
            str[i] = cookie.getValue();
            str[i+1] = cookie.getName();
            i=i+2;
        }
        return str;
    }
}
