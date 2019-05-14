package controller.user.consumer.account;

import entity.State;
import entity.User;
import exception.DataBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.consumer.account.ModifyInformationService;
import service.user.consumer.account.RegisterService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 修改用户信息
 * 修改用户名，修改密码，找回密码，设置密保，修改密保，找回密保（邮箱验证）
 *
 * @author 5月12日 张易兴创建
 */
@Controller
public class ModifyInformation {
    @Resource(name = "RegisterService")
    private RegisterService registerService;
    @Resource(name = "ModifyInformationService")
    private ModifyInformationService modifyInformationService;
    private static final Logger logger = LoggerFactory.getLogger(ModifyInformation.class);

    /**
     * 点击修改用户名执行此方法，ajax
     *
     * @param userName 修改后的用户名
     */
    @RequestMapping(value = "/changeUserName")
    @ResponseBody
    public State changeUserName(String userName, HttpSession session) throws DataBaseException {
        State state = new State();
        // 判断用户名是否合法
        if (registerService.isUserName(userName)) {
            // 修改数据库中的用户信息
            modifyInformationService.changeUserName(userName, session);
            logger.debug("用户名：" + userName + "修改成功");
            state.setState(1);
        } else {
            state.setInformation("用户名格式有误");
        }
        return state;
    }

    /**
     * 点击修改密码执行此方法，ajax
     *
     * @param originalPassword 获取用户输入的原密码
     * @param password         接收输入的新密码
     * @param passwordAgain    接收再次输入的新密码
     * @param session          获取当前会话的对象
     */
    @RequestMapping(value = "/changePassword")
    @ResponseBody
    public State changePassword(String originalPassword, String password, String passwordAgain, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws DataBaseException {
        State state = new State();
        User user = (User) session.getAttribute("userInformation");
        // 判断修改后的密码格式是否正确
        if (registerService.isPassword(password)) {
            // 判断两次密码是否相同
            if (password.equals(passwordAgain)) {
                // 判断用户密码是否正确，如果正确则修改数据库中的密码和会话上的密码以及cookie的密码
                if (modifyInformationService.isMailboxAndPassword(user.getMailbox(), originalPassword, password, session, request, response)) {
                    logger.debug(user.getMailbox(), "的密码修改成功");
                    state.setState(1);
                } else {
                    state.setInformation("密码错误");
                }
            } else {
                state.setInformation("两次输入的密码不相同");
            }
        } else {
            state.setInformation("密码格式有误");
        }
        return state;
    }

    /**
     * 点击设置密保或修改密保执行此方法，ajax
     * 设置密保也需要验证码
     *
     * @param verificationCode 用户输入的验证码
     * @param gender           密保——性别
     * @param age              密保——年龄
     * @param birthday         密保——出生日期
     * @param address          密保——住址
     * @param session          当前会话
     */
    @RequestMapping(value = "/changeSecretProtection")
    @ResponseBody
    public State changeSecretProtection(String verificationCode, String gender, String age, String birthday, String address, HttpSession session) throws DataBaseException {
        State state = new State();
        // 验证邮箱的验证码是否正确
        if (registerService.isMailboxVerificationCode(session, verificationCode)) {
            User user = modifyInformationService.changeSecretProtection(gender, age, birthday, address, session);
            //成功用户信息，失败返回null
            if (user != null) {
                state.setState(1);
            } else {
                // 如果失败是数据库错误
                String mailbox = ((User) session.getAttribute("userInformation")).getMailbox();
                logger.debug("邮箱：" + mailbox + "设置密保时，数据库出错");
                throw new DataBaseException("邮箱：" + mailbox + "设置密保时，数据库出错");
            }
        } else {
            state.setInformation("验证码错误");
        }
        return null;
    }


    /**
     * 找回密码（通过密保找回，忘记密保通过邮箱验证找回）
     * 第一步，先进行账号验证，验证账号是否存在
     *
     * @param mailbox 获取要找回密码的账号
     */
    @RequestMapping(value = "/verificationAccount")
    @ResponseBody
    public State verificationAccount(String mailbox) {
        State state = new State();
        if (registerService.isMailboxExistence(mailbox)) {
            state.setState(1);
        } else {
            state.setInformation(mailbox + "邮箱不存在");
        }
        return state;
    }

    /**
     * 找回密码
     * 第二步，验证账号的密保是否正确
     *
     * @param mailbox  需要验证的邮箱
     * @param gender   密保——性别
     * @param age      密保——年龄
     * @param birthday 密保——出生日期
     * @param address  密保——住址
     */
    @RequestMapping(value = "/verificationSecretProtection")
    @ResponseBody
    public State verificationSecretProtection(String mailbox, String gender, String age, String birthday, String address) {
        State state = new State();
        // 验证密保是否正确
        if (modifyInformationService.isSecretProtection(mailbox, gender, age, birthday, address)) {
            state.setState(1);
        } else {
            logger.debug("邮箱：" + mailbox + "的密保验证有误");
            state.setInformation("密保有误");
        }
        return state;
    }
}
