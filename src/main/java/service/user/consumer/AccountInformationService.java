package service.user.consumer;

import entity.State;
import entity.User;
import exception.DataBaseException;
import mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import service.user.LoginService;
import service.user.SpecialFunctions;
import service.user.ValidationInformation;
import util.CookieUtil;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 修改用户信息的业务逻辑
 * 修改密码，修改用户名，判断是否设置密保，设置修改密保，判断密保是否正确
 *
 * @author 5月11日 张易兴创建
 */
@Service(value = "AccountInformationService")
public class AccountInformationService {
    private static final Logger logger = LoggerFactory.getLogger(AccountInformationService.class);
    @Resource(name = "ValidationInformation")
    ValidationInformation validationInformation;
    @Resource(name = "LoginService")
    private LoginService loginService;
    @Resource(name = "SpecialFunctions")
    SpecialFunctions specialFunctions;
    @Resource(name = "UserMapper")
    UserMapper userMapper;

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
                // 判断用户密码是否正确，如果正确则修改数据库中的密码和会话上的密码以及cookie的密码
                if (isMailboxAndPassword(user.getMailbox(), originalPassword, password, session, request, response)) {
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
     * 判断用户密码是否正确，如果正确则修改数据库中的密码和会话上的密码以及cookie的密码
     *
     * @param mailbox          接收再次输入的新密码
     * @param originalPassword 获取用户输入的原密码
     * @param password         接收输入的新密码
     * @return boolean 返回是否修改成功
     */
    private boolean isMailboxAndPassword(String mailbox, String originalPassword, String password, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws DataBaseException {
        // 对密码进行加密然后去数据查找
        User user = loginService.isMailboxAndPassword(mailbox, specialFunctions.encryptionMD5(originalPassword));
        if (user != null) {
            password = specialFunctions.encryptionMD5(password);
            // 对密码进行加密
            // 密码验证成功，修改密码
            user.setPassword(password);
            // 修改数据库中的信息
            if (userMapper.updateUser(user) > 0) {
                // 修改会话上的用户信息（密码）
                session.setAttribute("userInformation", user);
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

    /**
     * 用于修改用户的用户名
     * 并修改会话上的用户名
     *
     * @param userName 修改后的用户名
     */
    public State changeUserName(String userName, HttpSession session) throws DataBaseException {
        User user = (User) session.getAttribute("userInformation");
        user.setName(userName);
        State state = new State();
        // 判断用户名是否合法
        if (validationInformation.isUserName(userName)) {
            // 修改数据库中的用户信息
            if (userMapper.updateUser(user) > 0) {
                // 修改会话上的用户信息
                session.setAttribute("userInformation", user);
                logger.info("邮箱：" + user.getMailbox() + "用户名：" + userName + "修改成功");
                state.setState(1);
            } else {
                // 如果失败是数据库错误
                logger.error("邮箱：" + user.getMailbox() + "修改用户名时，数据库出错");
                throw new DataBaseException("邮箱：" + user.getMailbox() + "修改用户名时，数据库出错");
            }
        } else {
            logger.debug("用户名：" + userName + "用户名格式有误");
            state.setInformation("用户名格式有误");
        }
        return state;
    }

    /**
     * 开通个人空间或关闭个人空间
     *
     * @param session 获取当前会话
     */
    public State privacy(HttpSession session) throws DataBaseException {
        State state = new State();
        User user = (User) session.getAttribute("userInformation");
        int secret = user.getSecret();
        if (secret == 0) {
            secret = 1;
        } else {
            secret = 0;
        }
        // 修改用户的空间状态
        user.setSecret(secret);
        if (userMapper.updateUser(user) > 0) {
            logger.info("邮箱：" + user.getMailbox() + "用户的空间状态修改成功");
            // 修改会话上的用户信息
            session.setAttribute("userInformation", user);
            state.setState(1);
        } else {
            // 如果失败是数据库错误
            logger.error("邮箱：" + user.getMailbox() + "修改用户空间状态时，数据库出错");
            throw new DataBaseException("邮箱：" + user.getMailbox() + "修改用户空间状态时，数据库出错");
        }
        return state;
    }
}
