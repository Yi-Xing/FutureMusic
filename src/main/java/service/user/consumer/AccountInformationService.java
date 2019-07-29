package service.user.consumer;

import entity.*;
import mapper.FocusMapper;
import org.springframework.ui.Model;
import service.user.IdExistence;
import service.user.SpecialFunctions;
import service.user.administrators.UserInformationService;
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
import java.util.List;

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
    @Resource(name = "IdExistence")
    IdExistence idExistence;
    @Resource(name = "UserInformationService")
    UserInformationService userInformationService;
    /**
     * 显示用户页面
     * Model封装：
     * userFollowCount用户关注的个数
     * followUserCount关注用户的个数
     */
    public String userPage(HttpSession session, Model model) {
        // 判断用户是否去访问
        User user = (User) session.getAttribute("otherUser");
        model.addAttribute("page", "show");
        if (user == null) {
            user = specialFunctions.getUser(session);
            // 从数据库中获取信息
            user = idExistence.isUserId(user.getId());
            model.addAttribute("page", "personal");
            model.addAttribute("show", "personal");
            if(user.getLevel()>2){
            System.out.println("我这里");
                //如果用户等级够的话跳转到管理员页面
                model.addAttribute("page","homePage");
                return "system/backgroundSystem";
            }
        } else {
            session.removeAttribute("otherUser");
            if (user.getSecret() == 1) {
                model.addAttribute("user", user);
                model.addAttribute("close", 1);
                return "userPage/userPage";
            }
        }
        // 得到用户的关注粉丝量及用户信息
        specialFunctions.getUserInformation(user, model);
        return "userPage/userPage";
    }


    /**
     * 用于访问其他用户的页面
     */
    public String otherUserPage(String id, HttpSession session, Model model) {
        User user = idExistence.isUserIdExist(id);
        if (user != null) {
            session.setAttribute("otherUser", user);
            // 得到关注者的ID
            int userId = specialFunctions.getUser(session).getId();
            Focus focus=new Focus();
            // 关注
            focus.setUserType(1);
            focus.setUserId(userId);
            focus.setUserFocusId(user.getId());
            List<Focus> list= focusMapper.selectListFocus(focus);
            if(list.size()==1){
                model.addAttribute("focus", "focus");
            }else{
                model.addAttribute("focus", "follow");
            }
        }
        return userPage(session, model);
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
        State state = new State();
        User user = specialFunctions.getUser(session);
        String path = fileUpload.userHeadPortrait(fileUpload.getMultipartFile(request, "file"));
        logger.debug("头像路径" + path);
        logger.debug("用户信息" + user);
        if (path != null) {
            String originalPath = user.getHeadPortrait();
            logger.debug("我执行了，开始修改用户头像");
            // 修改用户头像
            user.setHeadPortrait(path);
            //修改数据库和会话上的用户信息，失败抛异常
            modifyUserInformation(user, session);
            state.setState(1);
            // 修改成功删除原文件
            fileUpload.deleteFile(originalPath);
        } else {
            logger.debug("我也执行了");
            state.setInformation("所选图片不合法");
        }
        return state;
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
            logger.info("邮箱：" + user.getMailbox() + "用户的信息修改成功");
            // 修改会话上的用户信息
            session.setAttribute("userInformation", user);
        } else {
            // 如果失败是数据库错误
            logger.error("邮箱：" + user.getMailbox() + "修改用户信息时，数据库出错");
            throw new DataBaseException("邮箱：" + user.getMailbox() + "修改用户信息时，数据库出错");
        }
    }

    /**
     * 点击充值余额页面
     */
    public String balancePage(HttpSession session, Model model) {
        model.addAttribute("page", "balance");
        // 从数据库中获取信息
        User user = idExistence.isUserId(specialFunctions.getUser(session).getId());
        model.addAttribute("user", user);
        return "/vip/purchasePage";
    }

    /**
     * 点击充值vip
     */
    public String vipPage(HttpSession session, Model model) {
        // 从数据库中获取信息
        User user = idExistence.isUserId(specialFunctions.getUser(session).getId());
        model.addAttribute("user", user);
        model.addAttribute("page", "vip");
        return "/vip/purchasePage";
    }

    /**
     * 购买音乐或MV页面
     */
    public String musicPage(HttpSession session, String id, String type, Model model) {
        // 从数据库中获取信息
        User user = idExistence.isUserId(specialFunctions.getUser(session).getId());
        model.addAttribute("user", user);
        model.addAttribute("page", "music");
        model.addAttribute("id", id);
        model.addAttribute("type", type);
        // 先判断id和type是否合法
        if (validationInformation.isInt(id) && validationInformation.isInt(type)) {
            int musicId = Integer.valueOf(id);
            int musicType = Integer.valueOf(type);
            if (musicType == 1) {
                // 音乐
                Music music = idExistence.isMusicId(musicId);
                if (music != null) {
                    model.addAttribute("music", music);
                    return "/vip/purchasePage";
                }
            } else if (musicType == 2) {
                //MV
                MusicVideo musicVideo = idExistence.isMusicVideoId(musicId);
                if (musicVideo != null) {
                    model.addAttribute("music", musicVideo);
                    return "/vip/purchasePage";
                }
            }
        }
        model.addAttribute("select", "请先选择音乐/MV");
        return "/vip/purchasePage";
    }
}
