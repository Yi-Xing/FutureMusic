package service.user.consumer;

import entity.Focus;
import entity.State;
import entity.User;
import org.springframework.ui.Model;
import service.user.SpecialFunctions;
import util.exception.DataBaseException;
import mapper.FocusMapper;
import mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import service.user.ValidationInformation;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 开关个人空间，关注用户，访问其他用户，举报用户
 *
 * @author 5月14日 张易兴创建
 */
@Service(value = "AboutUserService")
public class AboutUserService {
    private static final Logger logger = LoggerFactory.getLogger(AboutUserService.class);
    @Resource(name = "ValidationInformation")
    ValidationInformation validationInformation;
    @Resource(name = "UserMapper")
    UserMapper userMapper;
    @Resource(name = "FocusMapper")
    FocusMapper focusMapper;
    @Resource(name = "AboutMailService")
    AboutMailService aboutMailService;
    @Resource(name = "SpecialFunctions")
    SpecialFunctions specialFunctions;
    @Resource(name = "Existence")
    ExistenceService existenceService;

    /**
     * 查找指定用户关注的所有用户，或被关注所有用户，被访问的记录
     *
     * @param type    获取类型 1表示关注的用户，2表示被关注用户，3表示被访问的记录
     * @param session 获取当前会话
     */
    public String showFollowUser(Integer type, Model model, HttpSession session) {
        User user = specialFunctions.getUser(session);
        Focus focus = new Focus();
        List<Integer> idList=new ArrayList<>();
        if (type == 1) {
            // 查找指定用户关注的所有用户
            model.addAttribute("page","follow");
            focus.setUserType(1);
            focus.setUserId(user.getId());
            List<Focus> list = focusMapper.selectListFocus(focus);
            for(Focus f:list){
                idList.add(f.getUserFocusId());
            }
            logger.debug("用户的关注"+list);
        } else if (type == 2) {
            model.addAttribute("page","fans");
            focus.setUserType(1);
            focus.setUserFocusId(user.getId());
            List<Focus> list = focusMapper.selectListFocus(focus);
            for(Focus f:list){
                idList.add(f.getUserId());
            }
            logger.debug("用户的粉丝"+list);
        } else {
            focus.setUserType(2);
            focus.setUserFocusId(user.getId());
            List<Focus> list = focusMapper.selectListFocus(focus);
            for(Focus f:list){
                idList.add(f.getUserFocusId());
            }
            logger.debug("用户的访客"+list);
        }
        if(idList.size()==0){
            idList.add(0);
        }
        // 得到用户的关注粉丝量及用户信息
        specialFunctions.getUserInformation(user, model);
        model.addAttribute("users",userMapper.listIdSelectListUser(idList));
        System.out.println(userMapper.listIdSelectListUser(idList));
        return "userInformation/personal";
    }

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
            Focus newFocus = existenceService.isUserFollow(user.getId(), id, type);
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
     * 举报指定用户
     * 添加指定用户的被举报次数，并给客服和管理员发邮件
     *
     * @param mailbox 获取被举报用户的邮箱
     * @param content 举报的内容
     * @param session 获取当前会话
     */
    public State reportUser(String mailbox, String content, HttpSession session) throws DataBaseException {
        // 判断内容是否合法
        State state = validationInformation.isContent(content);
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
            aboutMailService.feedback(user.getId(), content);
        }
        return state;
    }
}
