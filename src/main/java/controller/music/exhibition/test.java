package controller.music.exhibition;

import entity.Music;
import entity.Play;
import mapper.MusicMapper;
import mapper.PlayMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.music.DetailsService;
import service.music.PlayService;

import javax.annotation.Resource;

/**
 * @author 蒋靓峣
 */
@Controller
public class test {
    @Resource(name = "PlayService")
    PlayService playService;
     /**
     * 评论查出来回复但是没有对回复排序
     *  ajax智能提示倒是完全能用
     *分类查询音乐出错，出错、出错、出错、重写？？重写
     * 重写
     *
     *play数据库查询差不了
     * musicService语言查找没有实现，应该是数据库的原因
     */
    @RequestMapping(value = "/WWWW")
    public String www(){
        playService.test();
        return "index";
    }
}
