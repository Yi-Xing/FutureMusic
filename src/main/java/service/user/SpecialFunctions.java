package service.user;

import com.sun.mail.util.MailSSLSocketFactory;
import entity.State;
import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;
import util.ConstantUtil;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

/**
 * 返回会话上的用户信息
 * 发送邮箱，用于注册，找回密保
 * 加密字符串，用于密码，密保

 *
 * @author 5月17日 张易兴创建
 */
@Service(value = "SpecialFunctions")
public class SpecialFunctions {
    private static final Logger logger = LoggerFactory.getLogger(SpecialFunctions.class);

    /**
     * 返回会话上的用户信息
     */
    public User getUser(HttpSession session) {
        return (User) session.getAttribute("userInformation");
    }

    /**
     * 返回会话上邮箱号
     */
    public String getUserMailbox(HttpSession session) {
        return (String) session.getAttribute("userMailbox");
    }

    /**
     * 给指定邮箱发送验证码，发送验证码
     * 用于注册，找回密码
     *
     * @param mailbox 给该邮箱号发送验证码
     * @param session 获取当前会话的对象
     */
    public State sendVerificationCode(String mailbox, HttpSession session) {
        //调用发送邮箱的方法，将返回的验证码存到session中
        session.setAttribute("verificationCode", sendMail(mailbox));
        // 存储验证码发送的时间
        session.setAttribute("verificationCodeTime", new Date());
        // 邮箱发送成功
        logger.info("邮箱：" + mailbox + "验证码发送成功");
        return new State(1);
    }

    /**
     * 发送邮箱返回验证码
     *
     * @param mailbox 接收邮箱号
     * @return String 返回验证码
     */
    private String sendMail(String mailbox) {
        // 验证码中可能出现的字符
        String verificationCodeRandom = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        // 用于存储验证码
        StringBuilder verificationCode = new StringBuilder();
        // 用于生成验证码验证码6位
        for (int i = 0; i < ConstantUtil.Six.getIntValue(); i++) {
            // 获取随机位置的字符 生成验证码中的每个字符
            verificationCode.append(verificationCodeRandom.charAt(new Random().nextInt(verificationCodeRandom.length())));
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
        MailSSLSocketFactory mailSSLSocketFactory;
        try {
            mailSSLSocketFactory = new MailSSLSocketFactory();
            mailSSLSocketFactory.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", mailSSLSocketFactory);
            //authenticator参数，登录自己的邮箱帐号密码，
            Authenticator authenticator = new Authenticator() {
                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    // 发件人的邮箱账号和发件人的授权码
                    return new PasswordAuthentication("1160589090@qq.com", "anrzquybqwblicgf");
                }
            };
            //1、连接
            /*
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
            /*
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
                    "请勿泄露，谨防被骗。如非本人操作请忽略或咨询15*******55）";
            message.setContent(str, "text/html;charset=UTF-8");
            //3、发送
            Transport.send(message);
            logger.info("邮箱发送成功，给" + mailbox + "发送的验证码为：" + verificationCode);
        } catch (Exception e) {
            logger.debug(mailbox + "的邮箱发送失败");
            e.printStackTrace();
        }
        return verificationCode.toString();
    }


    /**
     * 用于对指定字符串进行MD5加密
     *
     * @param encryption 需要加密的字符串
     * @return String 返回加密后的字符串
     */
     String encryptionMD5(String encryption) {
        // 加密后的字符串
        String afterEncryption = null;
        try {
            //确定计算方法
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            BASE64Encoder base64Encoder = new BASE64Encoder();
            //加密后的字符串
            afterEncryption = base64Encoder.encode(messageDigest.digest(encryption.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            logger.error(encryption + "加密失败");
            e.printStackTrace();
        }
        return afterEncryption;
    }
}
