package controller.user.administrators;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import service.user.administrators.UserService;

import javax.annotation.Resource;

/**
 * 管理员对用户信息的操作
 * @author 5月20日 张易兴创建
 */
@Controller
public class User {
    @Resource(name = "UserService")
    UserService userService;
    /**
     * 显示用户信息
     */
    @RequestMapping(value = "/showUser")
    public String showUser(Model model){
        return null;
    }
}
