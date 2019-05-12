package service.user.consumer.account;

import Listener.SessionListener;
import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录账号的业务逻辑
 *
 * @author 5月11日 张易兴创建
 */
@Service(value = "LoginService")
public class LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    /**
     * 用于验证邮箱密码是否正确,用于登录
     *
     * @param mailbox  需要验证的密码
     * @param password 需要验证的密码
     * @return boolean 返回是否登录成功
     */
    public User isLogin(String mailbox, String password) {
        return null;
    }

    /**
     * 用于将账号密码存储到cookie中
     *
     * @param mailbox  需要存储的邮箱
     * @param password 需要存储的密码
     * @param response 存储cookie的对象
     */
    public void userInformationCookie(String mailbox, String password, HttpServletResponse response) {
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
    public void isUserLogin(HttpSession session) {
        // 将会话中的用户信息取出来
        User user = (User) session.getAttribute("userInformation");
        // 判断该用户是否已经登录
        if (SessionListener.sessionMap.get(user.getMailbox()) != null) {
            // 该用户已登录，删除原会话
            SessionListener.forceUserLogout(user.getMailbox());
        }
        // 将用户的信息存储的session过滤器中
        SessionListener.sessionMap.put(user.getMailbox(), session);
    }
}