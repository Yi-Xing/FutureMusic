package service.user.consumer;

import entity.Focus;
import entity.Mail;
import entity.State;
import entity.User;
import service.user.SpecialFunctions;
import util.exception.DataBaseException;
import mapper.FocusMapper;
import mapper.MailMapper;
import mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import service.user.ValidationInformation;
import util.ConstantUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * 开关个人空间，关注用户，访问其他用户，用户之间发邮件，举报，发邮件给客服，更改邮件状态的业务逻辑
 *
 * @author 5月14日 张易兴创建
 */
@Service(value = "AboutUserService")
public class AboutUserService {
    private static final Logger logger = LoggerFactory.getLogger(AboutUserService.class);
    @Resource(name = "AboutUserService")
    private AboutUserService aboutUserService;
    @Resource(name = "ValidationInformation")
    ValidationInformation validationInformation;
    @Resource(name = "UserMapper")
    UserMapper userMapper;
    @Resource(name = "FocusMapper")
    FocusMapper focusMapper;
    @Resource(name = "MailMapper")
    MailMapper mailMapper;
    @Resource(name = "SpecialFunctions")
    SpecialFunctions specialFunctions;
    @Resource(name = "Existence")
    Existence existence;

    /**
     * 点击关注其他用户
     *
     * @param id      获取被关注者的id
     * @param type    获取类型 1表示关注，2表示访问
     * @param session 获取当前会话
     */
    public State followUser(int id, int type, HttpSession session) throws DataBaseException {
        State state = new State();
        state.setState(1);
        User user = specialFunctions.getUser(session);
        Focus focus = new Focus();
        focus.setUserId(user.getId());
        focus.setUserFocusId(id);
        // 1表示关注 2表示访客
        focus.setUserType(type);
        // 为访客时，需要进行是否存在判断
        if (focus.getUserType() == 2) {
            // 判断用户是否访问过该用户，如果访问过不需要添加只需要更新，没有访问则需要添加
            Focus newFocus=existence.isUserFollow(user.getId(),id,type);
            // 判断有没有访问
            if (newFocus != null) {
                //更新时间
                newFocus.setDate(new Date());
                //更新数据库
                if (focusMapper.updateFocus(newFocus) > 0) {
                    return state;
                } else {
                    // 如果失败是数据库错误
                    logger.debug("邮箱：" + user.getMailbox() + "更新关注用户信息时，数据库出错");
                    throw new DataBaseException("邮箱：" + user.getMailbox() + "更新关注用户信息时，数据库出错");
                }
            }
        }
        focus.setDate(new Date());
        if (focusMapper.insertFocus(focus) < 1) {
            // 如果失败是数据库错误
            logger.debug("邮箱：" + user.getMailbox() + "添加关注用户信息时，数据库出错");
            throw new DataBaseException("邮箱：" + user.getMailbox() + "添加关注用户信息时，数据库出错");
        }
        return state;
    }



    /**
     * 点击取消关注其他用户
     *
     * @param id      获取被取消关注者的id
     * @param session 获取当前会话
     */
    public State cancelFollowUser(Integer id, HttpSession session) throws DataBaseException {
        User user = specialFunctions.getUser(session);
        Focus focus = new Focus();
        focus.setUserId(user.getId());
        focus.setUserFocusId(id);
        // 1表示关注
        focus.setUserType(1);
        if (focusMapper.deleteFocus(focus) < 1) {
            // 如果失败是数据库错误
            logger.debug("邮箱：" + user.getMailbox() + "取消关注用户信息时，数据库出错");
            throw new DataBaseException("邮箱：" + user.getMailbox() + "取消关注用户信息时，数据库出错");
        }
        State state = new State();
        state.setState(1);
        return state;
    }

    /**
     * 用于判断发送的内容是否合法
     *
     * @param content 内容
     */
    private State isContent(String content) {
        State state = new State();
        if (content.length() != 0) {
            if (content.length() <= ConstantUtil.Two_Hundred.getIntValue()) {
                state.setState(1);
            } else {
                state.setInformation("内容过长");
            }
        } else {
            state.setInformation("内容不能为空");
        }
        return state;
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
            state = aboutUserService.isContent(content);
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
     * 举报指定用户
     * 添加指定用户的被举报次数，并给客服和管理员发邮件
     *
     * @param mailbox 获取被举报用户的邮箱
     * @param content 举报的内容
     * @param session 获取当前会话
     */
    public State reportUser(String mailbox, String content, HttpSession session) throws DataBaseException {
        // 判断内容是否合法
        State state = aboutUserService.isContent(content);
        if (state.getState() == 1) {
            User userReport = userMapper.selectUserMailbox(mailbox);
            // 增加该用户被举报的次数
            userReport.setReport(userReport.getReport() + 1);
            if (userMapper.updateUser(userReport) < 1) {
                // 如果失败是数据库错误
                logger.error("邮箱：" + userReport.getMailbox() + "修改信息时，数据库出错");
                throw new DataBaseException("邮箱：" + userReport.getMailbox() + "修改信息时，数据库出错");
            }
            // 获取举报者的用户信息
            User user = specialFunctions.getUser(session);
            // 将举报信息交给客服，进行核对
            feedback(user.getId(), content);
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
        State state = aboutUserService.isContent(content);
        if (state.getState() == 1) {
            Mail mail = new Mail();
            // 发送邮件的用户id
            mail.setSenderId(id);
            // 发送给客服
            mail.setReply(1);
            mail.setContent(content);
            mail.setDate(new Date());
            if (mailMapper.insertMail(mail) < 1) {
                // 如果失败是数据库错误
                logger.debug("用户id：" + id + "给客服发送邮件时，数据库出错");
                throw new DataBaseException("用户id：" + id + "给客服发送邮件时，数据库出错");
            }
        }
        return state;
    }
}
