package service.user.consumer;

import entity.Mail;
import entity.State;
import entity.User;
import mapper.MailMapper;
import mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import service.user.SpecialFunctions;
import service.user.ValidationInformation;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户之间发邮件，给客服发邮件（申请音乐人等），更改邮件状态的，查看收到的邮件，查看发送过的邮件
 *
 * @author 5月14日 张易兴创建
 */
@Service(value = "AboutMailService")
public class AboutMailService {
    private static final Logger logger = LoggerFactory.getLogger(AboutMailService.class);
    @Resource(name = "ValidationInformation")
    ValidationInformation validationInformation;
    @Resource(name = "UserMapper")
    UserMapper userMapper;
    @Resource(name = "SpecialFunctions")
    SpecialFunctions specialFunctions;
    @Resource(name = "MailMapper")
    MailMapper mailMapper;

    /**
     * 用户之间发送邮件
     *
     * @param mailbox 获取接收邮件的用户邮箱
     * @param content 邮件发送的内容
     * @param session 获取当前会话
     */
    public State sendMailUser(String mailbox, String content, HttpSession session) throws DataBaseException {
        State state = new State();
        // 判断接收者是否存在
        if (validationInformation.isMailboxExistence(mailbox)) {
            // 判断内容是否合法
            state = validationInformation.isContent(content);
            if (state.getState() == 1) {
                Mail mail = new Mail();
                // 发送邮件的用户信息
                User sendUser = specialFunctions.getUser(session);
                mail.setSenderId(sendUser.getId());
                // 接收邮件的用户信息
                User receiveUser = userMapper.selectUserMailbox(mailbox);
                mail.setRecipientId(receiveUser.getId());
                mail.setContent(content);
                mail.setDate(new Date());
                // 添加的邮件未读
                mail.setState(0);
                if (mailMapper.insertMail(mail) < 1) {
                    // 如果失败是数据库错误
                    logger.error("邮箱：" + sendUser.getMailbox() + "发送邮件时，数据库出错");
                    throw new DataBaseException("邮箱：" + sendUser.getMailbox() + "发送邮件时，数据库出错");
                }
            }
        } else {
            logger.debug("邮箱：" + mailbox + "邮箱不存在");
            state.setInformation("邮箱不存在");
        }
        return state;
    }

    /**
     * 给客服发信息
     * 向客服反馈，举报用户
     *
     * @param id      发送者的id
     * @param content 发送的内容的内容
     */
    public State feedback(Integer id, String content) throws DataBaseException {
        // 判断内容是否合法
        State state = validationInformation.isContent(content);
        if (state.getState() == 1) {
            Mail mail = new Mail();
            // 发送邮件的用户id
            mail.setSenderId(id);
            // 发送给客服
            mail.setReply(1);
            mail.setContent(content);
            mail.setDate(new Date());
            // 添加的邮件未读
            mail.setState(0);
            if (mailMapper.insertMail(mail) < 1) {
                // 如果失败是数据库错误
                logger.debug("用户id：" + id + "给客服发送邮件时，数据库出错");
                throw new DataBaseException("用户id：" + id + "给客服发送邮件时，数据库出错");
            }
        }
        return state;
    }

    /**
     * 查看给那些用户发送过邮件
     */
    public List<User> showSendMailUser(HttpSession session) {
        // 用户信息
        User sendUser = specialFunctions.getUser(session);
        Mail mail = new Mail();
        mail.setSenderId(sendUser.getId());
        // 查找到该用户发送过的所有邮箱
        List<Mail> list = mailMapper.selectListMail(mail);
        // 用于存储接收者的用户信息
        List<User> userList = new ArrayList<>();
        User user = new User();
        for (Mail m : list) {
            // 得到接收者的id
            user.setId(m.getRecipientId());
            // 将查找到接收者的用户添加到集合中
            userList.add(userMapper.selectUser(user).get(0));
        }
        return userList;
    }

    /**
     * 查看给指定用户发过那些邮件
     *
     * @param id 接收者的id
     */
    public List<Mail> showSendMail(Integer id, HttpSession session){
        // 用户信息
        User user = specialFunctions.getUser(session);
        Mail mail = new Mail();
        mail.setSenderId(user.getId());
        mail.setRecipientId(id);
        // 查找到该用户给指定用户发送过的所有邮箱
        return mailMapper.selectListMail(mail);
    }

    /**
     * 查看收到过那些用户发送过邮件
     */
    public List<User> showReceiveMailUser(HttpSession session) {
        // 用户信息
        User sendUser = specialFunctions.getUser(session);
        Mail mail = new Mail();
        mail.setRecipientId(sendUser.getId());
        // 查找到该用户接收过的所有邮箱
        List<Mail> list = mailMapper.selectListMail(mail);
        // 用于存储发送者的用户信息
        List<User> userList = new ArrayList<>();
        User user = new User();
        for (Mail m : list) {
            // 得到发送者的id
            user.setId(m.getSenderId());
            // 将查找到发送者的用户添加到集合中
            userList.add(userMapper.selectUser(user).get(0));
        }
        return userList;
    }

    /**
     * 查看收到过指定用户的那些邮件
     *
     * @param id 指定 发送者的id
     */
    public List<Mail> showReceiveMail(Integer id, HttpSession session) {
        // 用户信息
        User user = specialFunctions.getUser(session);
        Mail mail = new Mail();
        mail.setSenderId(id);
        mail.setRecipientId(user.getId());
        // 查找到指定用户给该用户发送过的所有邮箱
        return mailMapper.selectListMail(mail);
    }
}
