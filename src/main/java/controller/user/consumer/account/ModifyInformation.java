package controller.user.consumer.account;

import entity.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 修改密码找回密码修改用户名
 *
 * @author 5月12日 张易兴创建
 */
@Controller
public class ModifyInformation {
    private static final Logger logger = LoggerFactory.getLogger(ModifyInformation.class);

    /**
     * 点击修改密码执行此方法，ajax
     *
     * @param originalPassword 获取用户输入的原密码
     * @param password         接收输入的新密码
     * @param passwordAgain    接收再次输入的新密码
     * @param session      获取当前会话的对象
     */
    @RequestMapping(value = "/changePassword")
    @ResponseBody
    public State changePassword(String originalPassword, String password, String passwordAgain, HttpSession session) {
        State state = new State();
        return state;
    }
}
