package service.user.consumer;

import com.mysql.cj.Session;
import entity.Mail;
import entity.State;
import entity.User;
import mapper.MailMapper;
import mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import service.user.IdExistence;
import service.user.SpecialFunctions;
import service.user.ValidationInformation;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

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
    @Resource(name = "IdExistence")
    IdExistence idExistence;

    /**
     * 显示发送页面
     */
    public String sendEmail(HttpSession session, Model model) {
        User user = specialFunctions.getUser(session);
        Mail mail = new Mail();
        mail.setSenderId(user.getId());
        List<Mail> list = mailMapper.selectListMail(mail);
        // 使用有存储顺序的map集合
        Map<Mail, User> mailMap = new LinkedHashMap<>();
        for (Mail m : list) {
            // 一个邮箱对应一个接收者
            mailMap.put(m, idExistence.isUserId(m.getRecipientId()));
        }
        if(user.getLevel()>2){
            model.addAttribute("level", "level");
        }
        model.addAttribute("mailMap", mailMap);
        model.addAttribute("page", "sendEmail");
        return "mail/mailPage";
    }

    /**
     * 显示接收页面
     */
    public String receiveEmail(HttpSession session, Model model) {
        User user = specialFunctions.getUser(session);
        Mail mail = new Mail();
        mail.setRecipientId(user.getId());
        List<Mail> list = mailMapper.selectListMail(mail);
        // 使用有存储顺序的map集合
        Map<Mail, User> mailMap = new LinkedHashMap<>();
        for (Mail m : list) {
            // 一个邮箱对应一个接收者
            if (m.getReply() == 1) {
                mailMap.put(m, idExistence.isUserId(1));
            } else {
                mailMap.put(m, idExistence.isUserId(m.getSenderId()));

            }
        }
        model.addAttribute("mailMap", mailMap);
        model.addAttribute("page", "receiveEmail");
        return "mail/mailPage";
    }

    /**
     * 显示用户通知页面
     */
    public String noticeEmail(Model model) {
        Mail mail = new Mail();
        mail.setReply(12);
        List<Mail> list = mailMapper.selectListMail(mail);
        logger.debug("所有的通知信息"+list);
        // 使用有存储顺序的map集合
        Map<Mail, User> mailMap = new LinkedHashMap<>();
        User user = idExistence.isUserId(1);
        for (Mail m : list) {
            // 一个邮箱对应一个接收者
            mailMap.put(m, user);
        }
        model.addAttribute("mailMap", mailMap);
        model.addAttribute("page", "noticeEmail");
        return "mail/mailPage";
    }

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
            // 收件人客服
            mail.setRecipientId(1);
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
     * 客服或管理员给所有成员发消息
     */
    public State sendWhole(HttpSession session,String content) throws DataBaseException {
        State state = new State();
        // 发送邮件的用户信息
        User sendUser = specialFunctions.getUser(session);
        // 判断有没有权限
        if(sendUser.getLevel()>2){
            // 判断内容是否合法
            state = validationInformation.isContent(content);
            if (state.getState() == 1) {
                Mail mail = new Mail(1,content,new Date(),2);
                // 添加的邮件未读
                mail.setState(0);
                if(mailMapper.insertMail(mail)<1){
                    // 如果失败是数据库错误
                    logger.error("邮箱：" + sendUser.getMailbox() + "发送邮件时，数据库出错");
                    throw new DataBaseException("邮箱：" + sendUser.getMailbox() + "发送邮件时，数据库出错");
                }
            }
        }else{
            state.setInformation("没有权限");
        }
        return state;
    }
}
