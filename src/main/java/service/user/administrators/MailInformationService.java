package service.user.administrators;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import controller.user.administrators.UserInformation;
import entity.Mail;
import entity.State;
import entity.User;
import mapper.MailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import service.user.SpecialFunctions;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

@Service(value = "MailInformationService")
public class MailInformationService {
    private static final Logger logger = LoggerFactory.getLogger(MailInformationService.class);
    @Resource(name = "SpecialFunctions")
    SpecialFunctions specialFunctions;
    @Resource(name = "MailMapper")
    MailMapper mailMapper;
    /**
     * 显示邮件信息
     *
     * @param condition 条件可以有多个 1、按id查询 2、按接收方id查询 3、按发送方id查询  4、按指定状态
     * @param pageNum   表示当前第几页
     * @param session   用于判断等级
     */
    public String showMail(String[] condition, Integer pageNum, Model model, HttpSession session) throws ParseException {
        User user = specialFunctions.getUser(session);
        Mail mail = new Mail();
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
        // 控制显示的邮箱等级
        if (user.getLevel() == 3) {
            mail.setReply(1);
        } else if (user.getLevel() >= 4 ) {
            mail.setReply(2);
        }
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pageNum, 8);
        // 根据条件查找用户信息
        List<Mail> list = mailMapper.selectListMail(mail);
        PageInfo pageInfo = new PageInfo<>(list);
        // 传入页面信息
        model.addAttribute("pageInfo", pageInfo);
        return null;
    }


    /**
     * 添加邮箱信息
     */
    public State addMail( Mail mail) throws DataBaseException {
        if(mailMapper.insertMail(mail)< 1) {
            // 如果失败是数据库错误
            logger.error("添加邮箱时，数据库出错");
            throw new DataBaseException("添加邮箱时，数据库出错");
        }
        return new State(1);
    }

    /**
     * 修改邮箱信息，ajax
     */
    public State modifyMail( Mail mail) throws DataBaseException {
        if(mailMapper.updateMail(mail)< 1) {
            // 如果失败是数据库错误
            logger.error("修改邮箱信息时，数据库出错");
            throw new DataBaseException("修改邮箱信息时，数据库出错");
        }
        return new State(1);
    }

    /**
     * 删除指定邮箱
     */
    public State deleteMail(Integer id) throws DataBaseException {
        // 删除指定id的评论
        if(mailMapper.deleteMail(id)< 1) {
            // 如果失败是数据库错误
            logger.error("删除指定邮箱时，数据库出错");
            throw new DataBaseException("删除指定邮箱时，数据库出错");
        }
        return null;
    }
}
