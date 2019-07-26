package controller.user.consumer;

import entity.Mail;
import entity.State;
import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping(value = "/user")
public class AboutMail {
    @Resource(name = "SpecialFunctions")
    SpecialFunctions specialFunctions;
    @Resource(name = "AboutMailService")
    AboutMailService aboutMailService;
    @Resource(name = "MailInformationService")
    MailInformationService mailInformationService;
    private static final Logger logger = LoggerFactory.getLogger(AboutUser.class);

    /**
     * 显示发送页面
     */
    @RequestMapping(value = "/sendEmail")
    public String sendEmail(HttpSession session, Model model) {
        return aboutMailService.sendEmail(session, model);
    }

    /**
     * 显示接收页面
     */
    @RequestMapping(value = "/receiveEmail")
    public String receiveEmail(HttpSession session, Model model) {
        return aboutMailService.receiveEmail(session, model);
    }

    /**
     * 显示通知页面
     */
    @RequestMapping(value = "/noticeEmail")
    public String noticeEmail(Model model) {
        return aboutMailService.noticeEmail( model);
    }


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
        return aboutMailService.feedback(specialFunctions.getUser(session).getId(), content);
    }

    /**
     * 客服给全体成员发消息,ajax
     *
     * @param content 发送的内容的内容
     */
    @RequestMapping(value = "/sendWhole")
    @ResponseBody
    public State sendWhole(String content, HttpSession session) throws DataBaseException {
        logger.trace("sendWhole方法开始执行");
        return aboutMailService.sendWhole(session, content);
    }

    // 以下代码无效

    /**
     * 修改邮箱信息,只修改状态，ajax
     */
    @RequestMapping(value = "/modifyMailState")
    @ResponseBody
    public State modifyMailState(@RequestBody Mail mail) throws DataBaseException {
        return mailInformationService.modifyMail(mail);
    }
}
