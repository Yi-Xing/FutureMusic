package controller.user.administrators;

import entity.Mail;
import entity.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.administrators.ActivityInformationService;
import service.user.administrators.MailInformationService;
import util.SameUrlData;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 邮箱：
 * 可添加：填写所有信息，用于回复用户或客服
 * 查看  客服：可查看用户给发送的2级邮件
 * 管理员：可查看用户给发送的2级邮件和客服发送的3级邮件
 * 查询：1、按id查询
 * 2、按接收方id查询
 * 3、按发送方id查询
 * 4、按指定状态
 * 可修改：设置邮件的状态
 * 可删除：按id删除
 *
 * @author 5月22日 张易兴创建
 */
@Controller
@RequestMapping(value = "/administrators")
public class MailInformation {
    private static final Logger logger = LoggerFactory.getLogger(ActivityInformationService.class);
    @Resource(name = "MailInformationService")
    MailInformationService mailInformationService;

    /**
     * 显示邮箱信息
     *
     * @param condition 条件可以有多个 1、按id查询 2、按接收方id查询 3、按发送方id查询  4、按指定状态
     * @param pageNum   表示当前第几页
     * @param session   用于判断等级
     */
    @RequestMapping(value = "/showMail")
    public String  showMail(String[] condition, @RequestParam(defaultValue="1")Integer pageNum, HttpSession session, Model model) {
        return mailInformationService.showMail(condition,pageNum,session,model);
    }

    /**
     * 显示指定id的邮箱信息
     */
    @RequestMapping(value = "/showIdMail")
    @ResponseBody
    public Mail showIdMail(Integer id){
        return mailInformationService.showIdMail(id);
    }

    /**
     * 删除指定邮箱
     */
    @RequestMapping(value = "/deleteMail")
    @ResponseBody
    public State  deleteMail(Integer id) throws DataBaseException {
        return mailInformationService.deleteMail(id);
    }
}
