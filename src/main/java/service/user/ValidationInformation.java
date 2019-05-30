package service.user;

import entity.State;
import entity.User;
import mapper.UserMapper;
import org.springframework.stereotype.Service;
import util.ConstantUtil;

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
        if (userName == null) {
            return false;
        }
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
        if (verificationCode == null) {
            return false;
        }
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
        if (mailbox == null) {
            return false;
        }
        return mailbox.matches("[a-zA-z_0-9]+@qq.com");
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
        if (password == null) {
            return false;
        }
        return password.length() >= 8 && password.length() <= 12;
    }

    /**
     * 验证或名字标题是否是1-15个字符
     * 用于活动标题，音乐MV歌单专辑的名字
     */
    public boolean isName(String name) {
        if (name == null) {
            return false;
        }
        return name.length() > 0 && name.length() <= 15;
    }

    /**
     * 用于判断发送邮箱的内容或MV或歌单专辑的介绍是否合法
     *
     * @param content 内容
     */
    public State isContent(String content) {
        State state = new State();
        if (content != null) {
            if (content.length() != 0) {
                if (content.length() <= ConstantUtil.Two_Hundred.getIntValue()) {
                    state.setState(1);
                } else {
                    state.setInformation("内容过长");
                }
            } else {
                state.setInformation("内容不能为空");
            }
        } else {
            state.setInformation("内容不能为空");
        }
        return state;
    }

    /**
     * 用于判断字符串是不是一个两位小数
     */
    public boolean isPrice(String string) {
        if (string == null) {
            return false;
        }
        return string.matches("(^[1-9]\\d*|^0)(\\.\\d{1,2}|)$");
    }
}
