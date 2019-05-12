package service.user.consumer.account;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * 注册账号的业务逻辑
 *
 * @author 5月11日 张易兴创建
 */
@Service(value = "RegisterService")
public class RegisterService {
    /**
     * 发送邮箱返回验证码
     *
     * @param mailbox 接收邮箱号
     * @return String 返回验证码
     */
    public String sendMail(String mailbox) {
        return null;
    }

    /**
     * 注册成功将账号密码用户名存入数据库
     *
     * @param userName 注册的邮箱
     * @param mailbox  注册的邮箱
     * @param password 注册的密码
     * @return String 返回验证码
     */
    public boolean registerAccount(String userName, String mailbox, String password) {
        return false;
    }

    /**
     * 用于验证用户名是否合法
     *
     * @param userName 需要验证的用户名
     * @return boolean 返回用户名是否合法
     */
    public boolean isUserName(String userName) {
        return false;
    }

    /**
     * 用于验证邮箱验证码是否正确
     *
     * @param session          获取当前会话
     * @param verificationCode 接收邮箱的验证码
     * @return boolean 返回验证码是否正确
     */
    public boolean isMailboxVerificationCode(HttpSession session, String verificationCode) {
        return session.getAttribute("verificationCode").equals(verificationCode);
    }

    /**
     * 用于验证邮箱是否合法
     *
     * @param mailbox 需要验证的邮箱
     * @return boolean 返回邮箱是否合法
     */
    public boolean isMailbox(String mailbox) {
        return false;
    }

    /**
     * 用于验证邮箱是否存在
     *
     * @param mailbox 需要验证的邮箱
     * @return boolean 返回邮箱是否存在
     */
    public boolean isMailboxExistence(String mailbox) {
        return false;
    }

    /**
     * 用于验证密码是否合法
     *
     * @param password 需要验证的密码
     * @return boolean 返回密码是否合法
     */
    public boolean isPassword(String password) {
        return false;
    }


}
