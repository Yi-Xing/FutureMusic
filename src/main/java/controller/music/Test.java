package controller.music;

import entity.Music;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.TestService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 测试层
 * 用来测试写的
 */
@Controller
public class Test {
    @Resource(name = "TestService")
    private TestService testService;
    /**
     * @param map 展示指定的歌曲（巅峰榜）
     *              分类：新歌、电音、华语、欧美、日韩
     *                只显示3条数据
     * @return List<Music> 返回符合条件的歌曲集合
     * */
    @RequestMapping(value = "/exhibitionTest")
    @ResponseBody
    public String exhibitionTest(ModelMap map){
        map.put("testText","测试成功");
        return "test";
    }
}
