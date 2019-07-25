package service.user;

import entity.State;
import entity.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import util.exception.DataBaseException;
import mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import util.CookieUtil;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 绑定邮箱，修改密码，找回密码，安全中心的页面跳转的保护机制
 *
 * @author 5月17日 张易兴创建
 */
@Service(value = "SafetyCenterService")
public class SafetyCenterService {
    private static final Logger logger = LoggerFactory.getLogger(SafetyCenterService.class);
    @Resource(name = "SpecialFunctions")
    SpecialFunctions specialFunctions;
    @Resource(name = "ValidationInformation")
    ValidationInformation validationInformation;
    @Resource(name = "LoginService")
    LoginService loginService;
    @Resource(name = "UserMapper")
    UserMapper userMapper;

    /**
     * 如果会话上绑定过用户邮箱则跳转到安全中心首页，否则跳转到绑定邮箱页面
     */
    public String safetyCenterPage(HttpSession session, Model model) {
        //得到会话上的用户信息
        User user = specialFunctions.getUser(session);
        if (user != null) {
            // 如果会话上绑定有用户信息则不需要绑定
            session.setAttribute("userMailbox", user.getMailbox());
        }
        String userMailbox = specialFunctions.getUserMailbox(session);
        if (userMailbox != null) {
            model.addAttribute("page", "passwordVerification");
            return "security/safetyCenter";
        }
        model.addAttribute("page", "bindingAccount");
        return "security/safetyCenter";
    }


    /**
     * 绑定邮箱，如果成功进行页面跳转否则还是本页
     */
    public State bindingMailbox(String mailbox, HttpSession session) {
        State state = new State();
        if (validationInformation.isMailbox(mailbox)) {
            if (validationInformation.isMailboxExistence(mailbox)) {
                session.setAttribute("userMailbox", mailbox);
                state.setState(1);
                logger.info("邮箱：" + mailbox + "绑定成功");
            } else {
                logger.debug("邮箱：" + mailbox + "邮箱不存在");
                state.setInformation("邮箱不存在");
            }
        } else {
            logger.debug("邮箱：" + mailbox + "邮箱格式有误");
            state.setInformation("邮箱格式有误");
        }
        return state;
    }


    /**
     * 点击忘记密码跳转到邮箱验证页面
     */
    public String mailboxVerification(HttpSession session, Model model) {
        // 先判断用户是否绑定了邮箱
        String userMailbox = specialFunctions.getUserMailbox(session);
        if (userMailbox != null) {
            model.addAttribute("page", "mailboxVerification");
        } else {
            model.addAttribute("page", "bindingAccount");
        }
        return "security/safetyCenter";
    }


    /**
     * 取消邮箱绑定
     *
     * @return State 状态码为1时跳转到安全中心首页
     */
    public String cancelBinding(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
        //得到会话上的用户信息
        User user = specialFunctions.getUser(session);
        if (user != null) {
            // 如果会话上绑定有用户信息则需要先删除会话上的用户信息
            loginService.signOutLogin(request, response, session);
        }
        // 删除绑定的邮箱
        session.removeAttribute("userMailbox");
        logger.info("邮箱解绑成功");
        model.addAttribute("page", "bindingAccount");
        return "security/safetyCenter";
    }

    /**
     * 用于验证原密码是否正确
     */
    public State verifyOriginalPassword(String originalPassword, HttpSession session) {
        State state = new State();
        // 对密码进行加密然后去数据查找
        User user = loginService.isMailboxAndPassword(specialFunctions.getUserMailbox(session), specialFunctions.encryptionMD5(originalPassword));
        if (user != null) {
            session.setAttribute("originalPassword", originalPassword);
            System.out.println("第一次提交的密码" + originalPassword);
            state.setState(1);
        } else {
            state.setInformation("密码错误");
        }
        return state;
    }

    /**
     * 邮箱验证提交验证码
     */
    public State mailboxVerificationCode(String verificationCode, HttpSession session) {
        State state = new State();
        // 验证邮箱验证码是否超时
        if (validationInformation.isMailboxVerificationCodeTime(session)) {
            // 验证邮箱的验证码是否正确
            if (validationInformation.isMailboxVerificationCode(session, verificationCode)) {
                state.setState(1);
                session.setAttribute("mailboxVerificationCode", verificationCode);
            } else {
                state.setInformation("邮箱验证码出错");
            }
        } else {
            state.setInformation("验证码超时");
        }
        return state;
    }

    /**
     * 用于密码验证显示修改密码页面
     */
    public String passwordChangePasswordPage(HttpSession session, Model model) {
        // 得到回话上原密码
        String originalPassword = (String) session.getAttribute("originalPassword");
        if (originalPassword == null || "".equals(originalPassword)) {
            logger.debug("没有密码");
            model.addAttribute("page", "passwordVerification");
            return "security/safetyCenter";
        }
        // 对密码进行加密然后去数据查找
        User user = loginService.isMailboxAndPassword(specialFunctions.getUserMailbox(session), specialFunctions.encryptionMD5(originalPassword));
        logger.debug("会话上的密码" + originalPassword);
        if (user != null) {
            logger.debug("密码验证成功进行页面的跳转");
            session.removeAttribute("originalPassword");
            // 用于表示验证过了
            session.setAttribute("setPassword", specialFunctions.getUserMailbox(session));
            model.addAttribute("page", "newPassword");
        } else {
            model.addAttribute("page", "passwordVerification");
        }
        return "security/safetyCenter";
    }

    /**
     * 邮箱验证显示修改密码页面
     */
    public String mailboxChangePasswordPage(HttpSession session, Model model) {
        // 取到回话上的验证码
        String verificationCode = (String) session.getAttribute("verificationCode");
        //再次验证验证码是否正确
        if (validationInformation.isMailboxVerificationCode(session, verificationCode)) {
            logger.debug("邮箱验证成功进行页面的跳转");
            session.removeAttribute("verificationCode");
            model.addAttribute("page", "newPassword");
            // 用于表示验证过了
            session.setAttribute("setPassword", specialFunctions.getUserMailbox(session));
        } else {
            // 不正确返回
            logger.debug("没有验证码");
            model.addAttribute("page", "mailboxVerification");
        }
        return "security/safetyCenter";
    }

    /**
     * 修改密码
     *
     * @param password      接收输入的新密码
     * @param passwordAgain 接收再次输入的新密码
     * @param session       获取当前会话的对象
     */
    public State changePassword(String password, String passwordAgain, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws DataBaseException {
        System.out.println("我执行了");
        State state = new State();
        String  mailbox = specialFunctions.getUserMailbox(session);
        // 先判断用户有没 setPassword的秘钥
        if (specialFunctions.getUserMailbox(session).equals(session.getAttribute("setPassword"))) {
            session.removeAttribute("setPassword");
            // 判断修改后的密码格式是否正确
            if (validationInformation.isPassword(password)) {
                // 判断两次密码是否相同
                if (password.equals(passwordAgain)) {
                    // 判断用户密码是否正确，如果正确则修改数据库中的密码以及cookie的密码
                    if (isMailboxAndPassword(mailbox, password, request, response)) {
                        logger.info(mailbox, "的密码修改成功");
                        state.setState(1);
                    } else {
                        logger.debug(mailbox, "密码错误");
                        state.setInformation("密码错误");
                    }
                } else {
                    logger.debug(mailbox, "两次输入的密码不相同");
                    state.setInformation("两次输入的密码不相同");
                }
            } else {
                logger.debug(mailbox, "密码格式有误");
                state.setInformation("密码格式有误");
            }
        }else{
            logger.debug(mailbox, "请重新绑定邮箱");
            state.setInformation("请重新绑定邮箱");
        }
        return state;
    }

    /**
     * 修改数据库中的密码以及cookie的密码
     *
     * @param mailbox  接收再次输入的新密码
     * @param password 接收输入的新密码
     * @return boolean 返回是否修改成功
     */
    private boolean isMailboxAndPassword(String mailbox, String password, HttpServletRequest request, HttpServletResponse response) throws DataBaseException {
        // 从数据库查找指定邮箱的用户
        User user = userMapper.selectUserMailbox(mailbox);
        if (user != null) {
            password = specialFunctions.encryptionMD5(password);
            // 对密码进行加密
            // 密码验证成功，修改密码
            user.setPassword(password);
            // 修改数据库中的信息
            if (userMapper.updateUser(user) > 0) {
                // 修改cookie中的用户密码
                Cookie[] cookies = request.getCookies();
                // 得到用户原来的cookie
                Cookie originalCookie = CookieUtil.obtainCookie(cookies, "userInformation");
                // 有Cookie在执行
                if (originalCookie != null) {
                    //得到原cookie还剩的时间
                    int time = originalCookie.getMaxAge();
                    Cookie userCookie = new Cookie("userInformation", mailbox + "#" + password);
                    userCookie.setMaxAge(time);
                    userCookie.setComment("/*");
                    // 重新添加cookie
                    response.addCookie(userCookie);
                    logger.info("邮箱：" + user.getMailbox() + "cookie中的密码修改成功");
                }
                return true;
            } else {
                // 如果失败是数据库错误
                logger.error("邮箱：" + mailbox + "修改密码时，数据库出错");
                throw new DataBaseException("邮箱：" + mailbox + "修改密码时，数据库出错");
            }
        }
        return false;
    }
}
