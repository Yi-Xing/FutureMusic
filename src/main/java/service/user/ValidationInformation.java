package service.user;

import entity.User;
import mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;

/**
 * 验证信息是否合法
 *
 * @author 5月17日 张易兴创建
 */
@Service(value = "ValidationInformation")
public class ValidationInformation {
    @Resource(name = "UserMapper")
    UserMapper userMapper;

    /**
     * 用于验证用户名是否合法
     *
     * @param userName 需要验证的用户名
     * @return boolean 返回用户名是否合法
     */
    public boolean isUserName(String userName) {
        return userName.length() >= 2 && userName.length() <= 6;
    }

    /**
     * 用于验证邮箱验证码是否正确
     *
     * @param session          获取当前会话
     * @param verificationCode 接收邮箱的验证码
     * @return boolean 返回验证码是否正确
     */
    public boolean isMailboxVerificationCode(HttpSession session, String verificationCode) {
        return verificationCode.equals(session.getAttribute("verificationCode"));
    }

    /**
     * 用于验证邮箱验证码是否超时
     *
     * @param session 获取当前会话
     * @return boolean 返回验证码是否超时
     */
    public boolean isMailboxVerificationCodeTime(HttpSession session) {
        Date verificationCodeTime = (Date) session.getAttribute("verificationCodeTime");
        Calendar calendar = Calendar.getInstance();
        // 将日期封装
        calendar.setTime(verificationCodeTime);
        // 向后推移30分钟
        calendar.add(Calendar.MINUTE, 30);
        // 得到推移后的时间
        verificationCodeTime = calendar.getTime();
        // 转换成毫秒进行比较
        Long verificationCodeTimeLong = verificationCodeTime.getTime();
        // 获取当前系统的时间毫秒
        Long dateLong = System.currentTimeMillis();
        return verificationCodeTimeLong >= dateLong;
    }


    /**
     * 用于验证邮箱是否合法
     *
     * @param mailbox 需要验证的邮箱
     * @return boolean 返回邮箱是否合法
     */
    boolean isMailbox(String mailbox) {
        return mailbox.matches("[a-zA-z_0-9]+@[a-zA-z_0-9]{2,6}(\\.[a-zA-z_0-9]{2,3})+");
    }

    /**
     * 用于验证邮箱是否存在
     *
     * @param mailbox 需要验证的邮箱
     * @return boolean 返回邮箱是否存在
     */
    public boolean isMailboxExistence(String mailbox) {
        // 从数据库中查找指定邮箱的用户
        User user = userMapper.selectUserMailbox(mailbox);
        return user != null;
    }

    /**
     * 用于验证密码是否合法
     *
     * @param password 需要验证的密码
     * @return boolean 返回密码是否合法
     */
    public boolean isPassword(String password) {
        return password.length() >= 8 && password.length() <= 12;
    }
}
