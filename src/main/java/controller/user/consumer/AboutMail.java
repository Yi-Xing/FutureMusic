package controller.user.consumer;

import entity.Mail;
import entity.State;
import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.SpecialFunctions;
import service.user.administrators.MailInformationService;
import service.user.consumer.AboutMailService;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户之间发邮件，给客服发邮件（申请音乐人等），更改邮件状态的，查看收到的邮件，查看发送过的邮件
 *
 * @author 5月14日 张易兴创建
 */
@Controller
public class AboutMail {
    @Resource(name = "SpecialFunctions")
    SpecialFunctions specialFunctions;
    @Resource(name = "AboutMailService")
    AboutMailService aboutMailService;
    @Resource(name = "MailInformationService")
    MailInformationService mailInformationService;
    private static final Logger logger = LoggerFactory.getLogger(AboutUser.class);
    /**
     * 用户之间发送邮件执行次方法,ajax
     *
     * @param mailbox 获取接收邮件的用户邮箱
     * @param content 邮件发送的内容
     * @param session 获取当前会话
     */
    @RequestMapping(value = "/sendMailUser")
    @ResponseBody
    public State sendMailUser(String mailbox, String content, HttpSession session) throws DataBaseException {
        logger.trace("sendMailUser方法开始执行");
        return aboutMailService.sendMailUser(mailbox, content, session);
    }

    /**
     * 给客服发邮件执行次方法（可用于申请音乐人）,ajax
     *
     * @param content 发送的内容的内容
     */
    @RequestMapping(value = "/feedback")
    @ResponseBody
    public State feedback(String content, HttpSession session) throws DataBaseException {
        logger.trace("feedback方法开始执行");
        return aboutMailService.feedback( specialFunctions.getUser(session).getId(),content);
    }

    /**
     * 查看给那些用户发送过邮件
     */
    @RequestMapping(value = "/showSendMailUser")
    @ResponseBody
    public List<User> showSendMailUser( HttpSession session)  {
        logger.trace("showSendMailUser方法开始执行");
        return aboutMailService.showSendMailUser(session);
    }

    /**
     * 查看给指定用户发过那些邮件
     * @param id 接收者的id
     */
    @RequestMapping(value = "/showSendMail")
    @ResponseBody
    public List<Mail> showSendMail(Integer id,HttpSession session) {
        logger.trace("showSendMail方法开始执行");
        return aboutMailService.showSendMail(id,session);
    }

    /**
     * 查看收到过那些用户发送过邮件
     */
    @RequestMapping(value = "/showReceiveMailUser")
    @ResponseBody
    public List<User> showReceiveMailUser(HttpSession session)  {
        logger.trace("showReceiveMailUser方法开始执行");
        return aboutMailService.showReceiveMailUser(session);
    }

    /**
     * 查看收到过指定用户的那些邮件
     * @param id 指定 发送者的id
     */
    @RequestMapping(value = "/showReceiveMail")
    @ResponseBody
    public List<Mail> showReceiveMail(Integer id, HttpSession session)  {
        logger.trace("showReceiveMail方法开始执行");
        return aboutMailService.showReceiveMail(id,session);
    }


    /**
     * 修改邮箱信息,只修改状态，ajax
     */
    @RequestMapping(value = "/modifyMailState")
    @ResponseBody
    public State modifyMailState(@RequestBody Mail mail) throws DataBaseException {
        return mailInformationService.modifyMail(mail);
    }
}
