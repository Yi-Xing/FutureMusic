package controller.user.consumer.account;

import entity.State;
import entity.User;
import listener.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.consumer.account.LoginService;
import service.user.consumer.account.RegisterService;
import util.CookieUtil;
import util.EncryptionUtil;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录和退出登录
 *
 * @author 5月12日 张易兴创建
 */
public class Login {
    @Resource(name = "LoginService")
    private LoginService loginService;
    @Resource(name = "RegisterService")
    private RegisterService registerService;
    private static final Logger logger = LoggerFactory.getLogger(Login.class);

    /**
     * 点击登录执行此方法，ajax
     *
     *      * @param mailbox   给该邮箱号发送验证码
     *      * @param password  获取当前会话的对象
     *      * @param automatic 获取用户是否选择了7天自动登陆的复选框
     * @param session   获取当前会话
     */
    @RequestMapping(value = "/loginAccount")
    @ResponseBody
    public State loginAccount(String mailbox, String password, boolean automatic, HttpServletResponse response, HttpSession session) {
        State state = new State();
        //先判断邮箱是否合法
        if (registerService.isMailbox(mailbox)) {
            // 对密码进行加密
            password = EncryptionUtil.encryptionMD5(password);
            // 验证邮箱和密码是否正确,如果正确返回用户信息, 不正确返回null
            User user = loginService.isMailboxAndPassword(mailbox, password);
            if (user != null) {
                // 实现唯一登录
                loginService.isUserLogin(session);
                //判断是否设置7天内自动登登录
                if (automatic) {
                    // 将账号密码存到cookie中去
                    loginService.userInformationCookie(mailbox, password, response);
                }
                // 登录成功将信息存到会话中
                session.setAttribute("userInformation", user);
                state.setState(1);
                logger.debug("邮箱：" + mailbox + "登录成功");
            } else {
                logger.debug("邮箱：" + mailbox + "登录失败");
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
    @RequestMapping(value = "/signOutLogin")
    public void signOutLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        // 退出登录将信息从域对象中删除
        session.removeAttribute("userInformation");
        Cookie[] cookies = request.getCookies();
        Cookie cookie = CookieUtil.obtainCookie(cookies, "userInformation");
        // 删除监听器上的用户信息
        SessionListener.delSession(session);
        // 删除7天登录
        if (cookie != null) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            logger.debug("退出成功，并删除7天自动登陆");
        }
    }
}
