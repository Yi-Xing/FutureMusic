package service.user;

import entity.State;
import entity.User;
import mapper.UserMapper;
import util.listener.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import util.ConstantUtil;
import util.CookieUtil;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录账号的业务逻辑
 *
 * @author 5月17日 张易兴创建
 */

@Service(value = "LoginService")
public class LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
    @Resource(name = "ValidationInformation")
    ValidationInformation validationInformation;
    @Resource(name = "UserMapper")
    UserMapper userMapper;
    @Resource(name = "SpecialFunctions")
    SpecialFunctions specialFunctions;

    /**
     * 登录
     *
     * @param mailbox   给该邮箱号发送验证码
     * @param password  获取当前会话的对象
     * @param automatic 获取用户是否选择了7天自动登陆的复选框
     * @param session   获取当前会话
     */
    public State login(String mailbox, String password, boolean automatic, HttpServletResponse response, HttpSession session) {
        State state = new State();
        //先判断邮箱是否合法
        if (validationInformation.isMailbox(mailbox)) {
            // 对密码进行加密
            password = specialFunctions.encryptionMD5(password);
            // 验证邮箱和密码是否正确,如果正确返回用户信息, 不正确返回null
            User user = isMailboxAndPassword(mailbox, password);
            if (user != null) {
                // 判断账号是否已经冻结
                if (user.getReport() < ConstantUtil.Two_Hundred.getIntValue()) {
                    // 登录成功将信息存到会话中
                    // 实现唯一登录
                    isUserLogin(session,user);
                    session.setAttribute("userInformation", user);
                    //判断是否设置7天内自动登登录
                    if (automatic) {
                        // 将账号密码存到cookie中去
                        userInformationCookie(mailbox, password, response);
                    }
                    if (user.getLevel() >= 3) {
                        state.setState(2);
                    } else {
                        state.setState(1);
                    }
                    logger.info("邮箱：" + mailbox + "登录成功");
                } else {
                    logger.debug("邮箱：" + mailbox + "登录失败，账号已被冻结");
                    state.setInformation("该账号已被冻结，具体联系客服");
                }
            } else {
                logger.debug("邮箱：" + mailbox + "登录失败，邮箱或密码有误");
                state.setInformation("邮箱或密码有误");
            }
        } else {
            logger.debug("邮箱：" + mailbox + "邮箱格式有误");
            state.setInformation("邮箱格式有误");
        }
        return state;
    }

    /**
     * 点击退出登录执行此方法，ajax
     * 删除7天自动登录，删除session中的用户信息
     *
     * @param request  获取客户端对象
     * @param response 获取服务器端对象
     * @param session  获取当前会话的对象
     */
    public State signOutLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        User user = specialFunctions.getUser(session);
        // 删除监听器上的用户信息
        SessionListener.delSession(session);
        Cookie[] cookies = request.getCookies();
        Cookie cookie = CookieUtil.obtainCookie(cookies, "userInformation");
        // 删除7天登录
        if (cookie != null) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            logger.debug("邮箱：" + user.getMailbox() + "的7天自动登录撒好删除成功");
        }
        logger.info("邮箱：" + user.getMailbox() + "退出成功");
        return new State(1);
    }

    /**
     * 用于验证密码是否正确,用于登录和修改密码
     * 也用修改密码
     *
     * @param mailbox  需要验证的邮箱
     * @param password 需要验证的密码
     * @return boolean 返回是否登录成功
     */
     User isMailboxAndPassword(String mailbox, String password) {
        // 从数据库中查找指定邮箱的用户
        User user = userMapper.selectUserMailbox(mailbox);
        // 再判断密码是否相同，密码再前面加过密了
        if (user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    /**
     * 用于将账号密码存储到cookie中
     *
     * @param mailbox  需要存储的邮箱
     * @param password 需要存储的密码
     * @param response 存储cookie的对象
     */
    private void userInformationCookie(String mailbox, String password, HttpServletResponse response) {
        Cookie userInformationCookie = new Cookie("userInformation", mailbox + "#" + password);
        userInformationCookie.setMaxAge(60 * 60 * 24 * 7);
        userInformationCookie.setComment("/*");
        response.addCookie(userInformationCookie);
        logger.debug("邮箱：" + mailbox + "已存入cookie中");
    }

    /**
     * 用于判断用户是否已经登录过了
     *
     * @param session 得到当前会话
     */
    private void isUserLogin(HttpSession session,User user) {

        // 将会话中的用户信息取出来
        HttpSession originalSession1 = SessionListener.sessionMap.get(user.getMailbox());
        // 判断该用户是否已经登录
        if (originalSession1 != null) {
            logger.debug(user.getMailbox() + "用户再次登录");
            // 将原用户的信息删除
            SessionListener.forceUserLogout(user.getMailbox());
            // 强制关掉会话，并删除会话上所有的绑定对象,会话被销毁后，执行监听器的销毁方法
//            originalSession1.invalidate()
        }
        // 将用户的信息存储的session监听器中
        SessionListener.sessionMap.put(user.getMailbox(), session);
    }


}