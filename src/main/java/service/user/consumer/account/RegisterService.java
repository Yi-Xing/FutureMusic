package service.user.consumer.account;

import com.sun.mail.util.MailSSLSocketFactory;
import entity.User;
import mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Properties;
import java.util.Random;

/**
 * 注册账号的业务逻辑
 *
 * @author 5月11日 张易兴创建
 */
@Service(value = "RegisterService")
public class RegisterService {

    private static final Logger logger = LoggerFactory.getLogger(RegisterService.class);
    @Resource(name = "UserMapper")
    UserMapper userMapper;

    /**
     * 发送邮箱返回验证码
     *
     * @param mailbox 接收邮箱号
     * @return String 返回验证码
     */
    public String sendMail(String mailbox) {
        // 验证码中可能出现的字符
        String verificationCodeRandom = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        // 用于存储验证码
        String verificationCode = "";
        // 用于生成验证码验证码6位
        for (int i = 0; i < 6; i++) {
            // 获取随机位置的字符 生成验证码中的每个字符
            verificationCode += verificationCodeRandom.charAt(new Random().nextInt(verificationCodeRandom.length()));
        }
        //props和authenticator参数
        Properties props = new Properties();
        // 设置邮件服务器主机名
        props.setProperty("mail.host", "smtp.qq.com");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        //开启debug调试
        props.setProperty("mail.debug", "true");
        //QQ邮箱的SSL加密。
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", sf);
            //authenticator参数，登录自己的邮箱帐号密码，
            Authenticator authenticator = new Authenticator() {
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    // 发件人的邮箱账号和发件人的授权码
                    return new PasswordAuthentication("1160589090@qq.com", "anrzquybqwblicgf");
                }
            };
            //1、连接
            /**
             * props
             *         连接配置信息，邮件服务器的地址，是否进行权限验证
             * authenticator
             *         权限验证，也就是帐号密码验证
             * 所以需要先配置这两个参数
             */
            Session session = Session.getDefaultInstance(props, authenticator);
            //2、发送的内容对象Message
            Message message = new MimeMessage(session);
            //2.1、发件人是谁
            message.setFrom(new InternetAddress("1160589090@qq.com"));
            // 2.2  , to:收件人 ; cc:抄送 ; bcc :暗送.
            /**
             * 收件人是谁？
             *         第一个参数：
             *             RecipientType.TO    代表收件人
             *             RecipientType.CC    抄送
             *             RecipientType.BCC    暗送
             *         比如A要给B发邮件，但是A觉得有必要给要让C也看看其内容，就在给B发邮件时，
             *         将邮件内容抄送给C，那么C也能看到其内容了，但是B也能知道A给C抄送过该封邮件
             *         而如果是暗送(密送)给C的话，那么B就不知道A给C发送过该封邮件。
             *     第二个参数
             *         收件人的地址，或者是一个Address[]，用来装抄送或者暗送人的名单。或者用来群发。
             */
            // 设置收件人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailbox));
            // 2.3 主题（标题）
            message.setSubject("未来音乐");
            // 2.4 正文
            String str = "【未来音乐】您的验证码：" + verificationCode + "（30分钟内有效）验证码提供给他人对账号安全有影响，" +
                    "请勿泄露，谨防被骗。如非本人操作请忽略或咨询150*****655）";
            message.setContent(str, "text/html;charset=UTF-8");
            //3、发送
            Transport.send(message);
            logger.debug("邮箱发送成功，给"+mailbox+"发送的验证码为："+ verificationCode);
        } catch (Exception e) {
            logger.debug(mailbox+"的邮箱发送失败");
            e.printStackTrace();
        }
        return verificationCode;
    }

    /**
     * 注册成功将账号密码用户名存入数据库
     *
     * @param userName 注册的邮箱
     * @param mailbox  注册的邮箱
     * @param password 注册的密码
     * @return String 返回是否成功
     */
    public boolean registerAccount(String userName, String mailbox, String password) {
        User user = new User();
        user.setName(userName);
        user.setMailbox(mailbox);
        user.setPassword(password);
        return userMapper.insertUser(user) > 0;
    }

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
        List<User> list = userMapper.selectUser(new User());
        for (User user : list) {
            // 验证邮箱是否存在
            if (user.getMailbox().equals(mailbox)) {
                return true;
            }
        }
        return false;
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
