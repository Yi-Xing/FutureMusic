package service.user.consumer.account;

import listener.SessionListener;
import entity.User;
import mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 登录账号的业务逻辑
 *
 * @author 5月11日 张易兴创建
 */
@Service(value = "LoginService")
public class LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
    @Resource(name = "UserMapper")
    UserMapper userMapper;

    /**
     * 用于验证密码是否正确,用于登录和修改密码
     *
     * @param mailbox  需要验证的密码
     * @param password 需要验证的密码
     * @return boolean 返回是否登录成功
     */
    public User isMailboxAndPassword(String mailbox, String password) {
        User user = new User();
        user.setMailbox(mailbox);
        // 从数据库中查找指定用户
        List<User> list = userMapper.selectUser(user);
        // 因为邮箱号是模糊查询，先判断该用户存不存在
        for (User listUser : list) {
            if (listUser.getMailbox().equals(mailbox)) {
                // 再判断密码是否相同，密码再前面加过密了
                if (listUser.getPassword().equals(password)) {
                    return user;
                }
            }
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
        HttpSession originalSession1 = SessionListener.sessionMap.get(user.getMailbox());
        // 判断该用户是否已经登录
        if (originalSession1 != null) {
            // 强制关掉会话，并删除会话上所有的绑定对象,会话被销毁后，执行监听器的销毁方法
            originalSession1.invalidate();
        }
        // 将用户的信息存储的session监听器中
        SessionListener.sessionMap.put(user.getMailbox(), session);
    }
}