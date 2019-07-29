package controller;

import controller.user.LoginAndRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.Emergency;
import service.user.SafetyCenterService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 显示游客页面
 */
@Controller
public class Tourist {
    @Resource(name = "SafetyCenterService")
    SafetyCenterService safetyCenterService;
    @Resource(name = "Emergency")
    Emergency emergency;
    private static final Logger logger = LoggerFactory.getLogger(Tourist.class);

    /**
     * 显示首页
     */
    @RequestMapping(value = "/homePage")
    public String homePage( Model model) {
        return emergency.homePage( model);
    }

    /**
     * 查找所有的音乐分页显示
     */
    @RequestMapping(value = "/musicPage")
    public String musicPage(@RequestParam(defaultValue = "1")Integer page, Model model){
        return emergency.getMusicList( page,model);
    }

    /**
     * 查找所有的MV分页显示
     */
    @RequestMapping(value = "/musicVideoPage")
    public String musicVideoPage(@RequestParam(defaultValue = "1")Integer page, Model model){
        return emergency.getMusicVideoList( page,model);
    }

    /**
     * 查找所有的专辑分页显示
     */
    @RequestMapping(value = "/albumPage")
    public String albumPage(@RequestParam(defaultValue = "1")Integer page, Model model){
        return emergency.getSongListList( page,model);
    }
}

