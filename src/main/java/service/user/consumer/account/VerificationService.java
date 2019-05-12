package service.user.consumer.account;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * 账号的各种验证信息
 *
 * @author 5月11日 张易兴创建
 */
@Service(value = "VerificationService")
public class VerificationService {
    /**
     * 用于验证邮箱验证码是否正确
     *
     * @param httpSession      获取当前会话
     * @param verificationCode 接收邮箱的验证码
     * @return boolean 返回验证码是否正确
     */
    public boolean isMailboxVerificationCode(HttpSession httpSession, String verificationCode) {
        return httpSession.getAttribute("verificationCode").equals(verificationCode);
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

    /**
     * 用于验证邮箱密码是否正确,用于登录
     *
     * @param mailbox  需要验证的密码
     * @param password 需要验证的密码
     * @return boolean 返回是否登录成功
     */
    public boolean isLogin(String mailbox, String password) {
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

}
