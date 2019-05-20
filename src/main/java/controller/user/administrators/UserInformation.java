package controller.user.administrators;

import entity.State;
import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.administrators.UserInformationServiceService;
import util.exception.DataBaseException;

import javax.annotation.Resource;

/**
 * 管理员对用户信息的操作
 *
 * @author 5月20日 张易兴创建
 */
@Controller
public class UserInformation {
    private static final Logger logger = LoggerFactory.getLogger(UserInformation.class);
    @Resource(name = "UserInformationService")
    UserInformationServiceService userInformationService;

    /**
     * 显示用户信息
     *
     * @param pageNum 表示当前第几页
     */
    @RequestMapping(value = "/showUser")
    public String showUser(Integer pageNum, Model model) {
        return userInformationService.showUser(pageNum,model);
    }

    /**
     * 修改用户信息，ajax
     */
    @RequestMapping(value = "/modifyUser")
    @ResponseBody
    public State modifyUser(@RequestBody User user) throws DataBaseException {
        return userInformationService.modifyUser(user);
    }
}
