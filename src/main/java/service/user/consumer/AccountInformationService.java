package service.user.consumer;

import entity.Focus;
import entity.State;
import entity.User;
import mapper.FocusMapper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import service.user.SpecialFunctions;
import util.FileUpload;
import util.exception.DataBaseException;
import mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import service.user.ValidationInformation;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 用户信息的业务逻辑
 * 显示用户页面，修改头像，修改用户名，开关个人空间
 *
 * @author 5月11日 张易兴创建
 */
@Service(value = "AccountInformationService")
public class AccountInformationService {
    private static final Logger logger = LoggerFactory.getLogger(AccountInformationService.class);
    @Resource(name = "ValidationInformation")
    ValidationInformation validationInformation;
    @Resource(name = "UserMapper")
    UserMapper userMapper;
    @Resource(name = "SpecialFunctions")
    SpecialFunctions specialFunctions;
    @Resource(name = "FocusMapper")
    FocusMapper focusMapper;
    @Resource(name = "FileUpload")
    FileUpload fileUpload;

    /**
     * 显示用户页面
     * Model封装：
     * userFollowCount用户关注的个数
     * followUserCount关注用户的个数
     */
    public String userPage(HttpSession session, Model model) {
        User user = specialFunctions.getUser(session);
        // 用于查找用户关注的人数
        Focus userFollow = new Focus();
        userFollow.setUserId(user.getId());
        //查找关注
        userFollow.setUserType(1);
        // 去数据库查找用户关注的人数
        int userFollowCount = focusMapper.selectUserFocusCount(userFollow);
        model.addAttribute("userFollowCount", userFollowCount);
        // 用于查找关注用户的人数
        Focus followUser = new Focus();
        followUser.setUserFocusId(user.getId());
        //查找关注
        userFollow.setUserType(1);
        // 去数据库查找关注用户的人数
        int followUserCount = focusMapper.selectUserFocusCount(followUser);
        model.addAttribute("followCount", followUserCount);
        // 用于查找用户喜欢的歌曲数量，未写完差一个方法调用
        return null;
    }

    /**
     * 用于修改用户的用户名
     * 并修改会话上的用户名
     *
     * @param userName 修改后的用户名
     */
    public State changeUserName(String userName, HttpSession session) throws DataBaseException {
        User user = specialFunctions.getUser(session);
        user.setName(userName);
        State state = new State();
        // 判断用户名是否合法
        if (validationInformation.isUserName(userName)) {
            //修改数据库和会话上的用户信息，失败抛异常
            modifyUserInformation(user, session);
            state.setState(1);
        } else {
            logger.debug("用户名：" + userName + "用户名格式有误");
            state.setInformation("用户名格式有误");
        }
        return state;
    }

    /**
     * 设置或更改用户头像
     *
     * @param request 获取图片的路径
     * @param session 当前会话
     */
    public State setUpHeadPortrait(HttpServletRequest request, HttpSession session) throws IOException, DataBaseException {
        User user = specialFunctions.getUser(session);
        String path = fileUpload.userHeadPortrait(request);
        // 修改用户头像
        user.setHeadPortrait(path);
        //修改数据库和会话上的用户信息，失败抛异常
        modifyUserInformation(user, session);
        return new State(1);
    }

    /**
     * 开通个人空间或关闭个人空间
     *
     * @param session 获取当前会话
     */
    public State privacy(HttpSession session) throws DataBaseException {
        User user = specialFunctions.getUser(session);
        int secret = user.getSecret();
        if (secret == 0) {
            secret = 1;
        } else {
            secret = 0;
        }
        // 修改用户的空间状态
        user.setSecret(secret);
        //修改数据库和会话上的用户信息，失败抛异常
        modifyUserInformation(user, session);
        return new State(1);
    }

    /**
     * 修改数据库和会话上的用户信息，失败抛异常
     */
    private void modifyUserInformation(User user, HttpSession session) throws DataBaseException {
        if (userMapper.updateUser(user) > 0) {
            logger.info("邮箱：" + user.getMailbox() + "用户的空间状态修改成功");
            // 修改会话上的用户信息
            session.setAttribute("userInformation", user);
        } else {
            // 如果失败是数据库错误
            logger.error("邮箱：" + user.getMailbox() + "修改用户信息时，数据库出错");
            throw new DataBaseException("邮箱：" + user.getMailbox() + "修改用户信息时，数据库出错");
        }
    }
}
