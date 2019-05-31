package controller.music.search;
import entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.SearchService;
import util.CookieUtil;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *  搜索框智能提示
 * @author 5.12 蒋靓峣创建
 * */
@Controller
public class SearchBefore {
    private static final Logger logger = LoggerFactory.getLogger(Music.class);
    @Resource(name = "SearchService")
    private SearchService searchService;

    /**
     * 获取搜索记录 ajax
     *
     * @retutn String[] 返回搜索历史字符串
     */
    @RequestMapping(value = "/searchMyRecord")
    @ResponseBody
    public String[] searchMyRecord(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie cookie = CookieUtil.obtainCookie(cookies, "searchRecordCookie");
        String searchRecord = cookie.getValue();
        String[] records = searchRecord.split("#");
        return records;
    }

    /**
     * 点击搜索执行
     * 如果存在历史记录，则修改相应的值
     * 不存在则增加一个"searchRecordCookie"名字的cookie
     * ajax
     */
    @RequestMapping(value = "/addSearchRecord")
    @ResponseBody
    public String addSearchRecord(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String keyWord = request.getParameter("keyWord");
        String searchRecord = searchService.addSearchRecord(keyWord, cookies, response);
        return searchRecord;
    }

    /**
     * 删除历史搜索记录
     */
    @RequestMapping(value = "/deleteSearchRecord")
    @ResponseBody
    public void deleteSearchRecord(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        Cookie cookie = CookieUtil.obtainCookie(cookies, "searchRecordCookie");
        if (cookie != null) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    /**
     * 根据关键字智能提示
     *
     * @param keyWord 接收搜索的关键字
     * @return List[] 返回匹配到的专辑、歌曲、歌手、MV信息
     */
    @RequestMapping(value = "/searchListAll")
    @ResponseBody
    public List[] searchListAll(@RequestParam(value = "keyWord") String keyWord) {
        return searchService.searchListAll(keyWord);
    }

    /**
     * 测试cookie用的
     */
    @RequestMapping(value = "/testCookie")
    @ResponseBody
    public String[] testCookie(HttpServletRequest request) {
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
