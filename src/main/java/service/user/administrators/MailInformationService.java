package service.user.administrators;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.Mail;
import entity.State;
import entity.User;
import mapper.MailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.IdExistence;
import service.user.SpecialFunctions;
import service.user.ValidationInformation;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 邮箱
 *
 * @author 5月22日 张易兴创建
 */
@Service(value = "MailInformationService")
public class MailInformationService {
    private static final Logger logger = LoggerFactory.getLogger(MailInformationService.class);
    @Resource(name = "SpecialFunctions")
    SpecialFunctions specialFunctions;
    @Resource(name = "MailMapper")
    MailMapper mailMapper;
    @Resource(name = "IdExistence")
    IdExistence idExistence;
    @Resource(name = "ValidationInformation")
    ValidationInformation validationInformation;

    /**
     * 显示邮件信息
     *
     * @param condition 条件可以有多个 1、按id查询 2、按接收方id查询 3、按发送方id查询  4、按指定状态
     * @param pageNum   表示当前第几页
     * @param session   用于判断等级
     */
    public String  showMail(String[] condition, Integer pageNum, HttpSession session,Model model) {
        User user = specialFunctions.getUser(session);
        Mail mail = new Mail();
        if (condition != null) {
            if ((condition[0] != null) && !"".equals(condition[0])) {
                mail.setId(Integer.parseInt(condition[0]));
            }
            if ((condition[1] != null) && !"".equals(condition[1])) {
                mail.setRecipientId(Integer.parseInt(condition[1]));
            }
            if ((condition[2] != null) && !"".equals(condition[2])) {
                mail.setSenderId(Integer.parseInt(condition[2]));
            }
            if ((condition[3] != null) && !"".equals(condition[3])) {
                mail.setState(Integer.parseInt(condition[3]));
            }
            model.addAttribute("conditionZero", condition[0]);
            model.addAttribute("conditionOne", condition[1]);
            model.addAttribute("conditionTwo", condition[2]);
            model.addAttribute("conditionThree", condition[3]);
        } else {
            model.addAttribute("conditionZero", null);
            model.addAttribute("conditionOne", null);
            model.addAttribute("conditionTwo", null);
            model.addAttribute("conditionThree", null);
        }
//        // 控制显示的邮箱等级
        if (user.getLevel() == 3) {
            mail.setReply(1);
        } else if (user.getLevel() >= 4) {
            mail.setReply(2);
        }
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pageNum, 9);
        // 根据条件查找用户信息
        List<Mail> list = mailMapper.selectListMail(mail);
        // 传入页面信息
        model.addAttribute("pageInfo",new PageInfo<>(list));
        return "back_system/Email";
    }
    /**
     * 显示指定id的邮箱信息
     */
    @RequestMapping(value = "/showIdMail")
    @ResponseBody
    public Mail showIdMail(Integer id){
        Mail mail = new Mail();
        mail.setId(id);
        List<Mail> list = mailMapper.selectListMail(mail);
        return list.get(0);
    }

    /**
     * 添加邮箱信息
     */
    public State addMail(Mail mail) throws DataBaseException {
        State state = new State();
        // 验证发送方的id是否存在
        if (idExistence.isUserId(mail.getSenderId()) != null) {
            // 验证接收方的id是否存在
            if (idExistence.isUserId(mail.getRecipientId()) != null) {
                // 验证发送的信息是否合法
                state = validationInformation.isContent(mail.getContent());
                if (state.getState() == 1) {
                    // 添加的邮件未读
                    mail.setState(0);
                    if (mailMapper.insertMail(mail) < 1) {
                        // 如果失败是数据库错误
                        logger.error("添加邮箱时，数据库出错");
                        throw new DataBaseException("添加邮箱时，数据库出错");
                    }
                }
            } else {
                state.setInformation("发送方的id不存在" + mail.getSenderId());
            }
        } else {
            state.setInformation("接收方的id不存在" + mail.getSenderId());
        }
        return state;
    }

    /**
     * 修改邮箱信息,只修改状态，ajax
     */
    public State modifyMail(Mail mail) throws DataBaseException {
        State state = new State(1);
        int mailState = mail.getState();
        if (mailState >= 0 && mailState <= 2) {
            System.out.println(mail);
            if (mailMapper.updateMail(mail) < 1) {
                // 如果失败是数据库错误
                logger.error("修改邮箱信息时，数据库出错");
                throw new DataBaseException("修改邮箱信息时，数据库出错");
            }
            state.setState(1);
        } else {
            state.setInformation(mailState + "不存在");
        }
        return state;
    }

    /**
     * 删除指定邮箱
     */
    public String  deleteMail(Integer id,Model model,HttpSession session) throws DataBaseException {
        // 删除指定id的评论
        if (mailMapper.deleteMail(id) < 1) {
            // 如果失败是数据库错误
            logger.error("删除指定邮箱时，数据库出错");
            throw new DataBaseException("删除指定邮箱时，数据库出错");
        }
        return showMail(null,1,session,model);
    }
}
