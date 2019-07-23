package service.user;

import entity.State;
import entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import util.exception.DataBaseException;
import mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import util.CookieUtil;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 修改密码，设置密保，找回密保，修改密保（原密保修改，邮箱修改）
 *
 * @author 5月17日 张易兴创建
 */
@Service(value = "SafetyCenterService")
public class SafetyCenterService {
    private static final Logger logger = LoggerFactory.getLogger(SafetyCenterService.class);
    @Resource(name = "SpecialFunctions")
    SpecialFunctions specialFunctions;
    @Resource(name = "ValidationInformation")
    ValidationInformation validationInformation;
    @Resource(name = "LoginService")
    LoginService loginService;
    @Resource(name = "UserMapper")
    UserMapper userMapper;

    /**
     * 如果会话上绑定过用户邮箱则跳转到安全中心首页，否则跳转到绑定邮箱页面
     */
    public String safetyCenterPage(HttpSession session) {
        //得到会话上的用户信息
        User user = specialFunctions.getUser(session);
        if (user != null) {
            // 如果会话上绑定有用户信息则不需要绑定
            session.setAttribute("userMailbox", user.getMailbox());
        }
        String userMailbox = specialFunctions.getUserMailbox(session);
        if (userMailbox != null) {
            return "";
        }
        return "";
    }


    /**
     * 进入输入密码页面如果没有绑定邮箱，则跳转到绑定邮箱页面
     */
    public String passwordVerification(HttpSession session) {
    return null;
    }
    /**
     * 绑定邮箱，如果成功进行页面跳转否则还是本页
     */
    public State bindingMailbox(String mailbox, HttpSession session) {
        State state = new State();
        if (validationInformation.isMailbox(mailbox)) {
            if (validationInformation.isMailboxExistence(mailbox)) {
                session.setAttribute("userMailbox", mailbox);
                state.setState(1);
                logger.info("邮箱：" + mailbox + "绑定成功");
            } else {
                logger.debug("邮箱：" + mailbox + "邮箱不存在");
                state.setInformation("邮箱不存在");
            }
        } else {
            logger.debug("邮箱：" + mailbox + "邮箱格式有误");
            state.setInformation("邮箱格式有误");
        }
        return state;
    }

    /**
     * 取消邮箱绑定
     *
     * @return State 状态码为1时跳转到安全中心首页
     */
    public String cancelBinding(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        //得到会话上的用户信息
        User user = specialFunctions.getUser(session);
        if (user != null) {
            // 如果会话上绑定有用户信息则需要先删除会话上的用户信息
            loginService.signOutLogin(request, response, session);
        }
        // 删除绑定的邮箱
        session.removeAttribute("userMailbox");
        logger.info("邮箱解绑成功");
        return null;
    }

    /**
     * 修改密码
     *
     * @param originalPassword 获取用户输入的原密码
     * @param password         接收输入的新密码
     * @param passwordAgain    接收再次输入的新密码
     * @param session          获取当前会话的对象
     */
    public State changePassword(String originalPassword, String password, String passwordAgain, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws DataBaseException {
        State state = new State();
        User user = (User) session.getAttribute("userInformation");
        // 判断修改后的密码格式是否正确
        if (validationInformation.isPassword(password)) {
            // 判断两次密码是否相同
            if (password.equals(passwordAgain)) {
                // 判断用户密码是否正确，如果正确则修改数据库中的密码以及cookie的密码
                if (isMailboxAndPassword(user.getMailbox(), originalPassword, password, request, response)) {
                    logger.info(user.getMailbox(), "的密码修改成功");
                    state.setState(1);
                } else {
                    logger.debug(user.getMailbox(), "密码错误");
                    state.setInformation("密码错误");
                }
            } else {
                logger.debug(user.getMailbox(), "两次输入的密码不相同");
                state.setInformation("两次输入的密码不相同");
            }
        } else {
            logger.debug(user.getMailbox(), "密码格式有误");
            state.setInformation("密码格式有误");
        }
        return state;
    }

    /**
     * 判断用户密码是否正确，如果正确则修改数据库中的密码以及cookie的密码
     *
     * @param mailbox          接收再次输入的新密码
     * @param originalPassword 获取用户输入的原密码
     * @param password         接收输入的新密码
     * @return boolean 返回是否修改成功
     */
    private boolean isMailboxAndPassword(String mailbox, String originalPassword, String password, HttpServletRequest request, HttpServletResponse response) throws DataBaseException {
        // 对密码进行加密然后去数据查找
        User user = loginService.isMailboxAndPassword(mailbox, specialFunctions.encryptionMD5(originalPassword));
        if (user != null) {
            password = specialFunctions.encryptionMD5(password);
            // 对密码进行加密
            // 密码验证成功，修改密码
            user.setPassword(password);
            // 修改数据库中的信息
            if (userMapper.updateUser(user) > 0) {
                // 修改cookie中的用户密码
                Cookie[] cookies = request.getCookies();
                // 得到用户原来的cookie
                Cookie originalCookie = CookieUtil.obtainCookie(cookies, "userInformation");
                // 有Cookie在执行
                if (originalCookie != null) {
                    //得到原cookie还剩的时间
                    int time = originalCookie.getMaxAge();
                    Cookie userCookie = new Cookie("userInformation", mailbox + "#" + password);
                    userCookie.setMaxAge(time);
                    userCookie.setComment("/*");
                    // 重新添加cookie
                    response.addCookie(userCookie);
                    logger.info("邮箱：" + user.getMailbox() + "cookie中的密码修改成功");
                }
                return true;
            } else {
                // 如果失败是数据库错误
                logger.error("邮箱：" + mailbox + "修改密码时，数据库出错");
                throw new DataBaseException("邮箱：" + mailbox + "修改密码时，数据库出错");
            }
        }
        return false;
    }
}
