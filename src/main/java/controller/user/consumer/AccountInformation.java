package controller.user.consumer;

import entity.State;
import exception.DataBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.SpecialFunctions;
import service.user.consumer.AccountInformationService;
import service.user.consumer.SecretProtectionService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 修改用户名，修改头像，修改密码，设置密保，找回密保，修改密保（原密保修改，邮箱修改），开通关闭个人空间
 *
 * @author 5月17日 张易兴创建
 */
public class AccountInformation {
    @Resource(name = "SecretProtectionService")
    SecretProtectionService secretProtectionService;
    @Resource(name = "AccountInformationService")
    AccountInformationService accountInformationService;
    @Resource(name = "SpecialFunctions")
    SpecialFunctions specialFunctions;
    private static final Logger logger = LoggerFactory.getLogger(AccountInformation.class);

    /**
     * 安全中心
     * 修改密码，密保都需要先进入安全中心
     * 该方法用于跳转到安全中心
     */
    @RequestMapping(value = "/safetyCenter")
    public String safetyCenter(HttpSession session) {
        return null;
    }

    /**
     * 点击修改用户名执行此方法，ajax
     *
     * @param userName 修改后的用户名
     */
    @RequestMapping(value = "/changeUserName")
    @ResponseBody
    public State changeUserName(String userName, HttpSession session) throws DataBaseException {
        logger.trace("changeUserName方法开始执行");
        return accountInformationService.changeUserName(userName, session);
    }

    /**
     * 设置或更改用户头像
     *
     * @param path    获取图片的路径
     * @param session 当前会话
     */
    @RequestMapping(value = "/setUpHeadPortrait")
    @ResponseBody
    public State setUpHeadPortrait(String path, HttpSession session) {
        logger.trace("setUpHeadPortrait方法开始执行");
        return null;
    }

    /**
     * 点击修改密码执行此方法，ajax
     *
     * @param originalPassword 获取用户输入的原密码
     * @param password         接收输入的新密码
     * @param passwordAgain    接收再次输入的新密码
     * @param session          获取当前会话的对象
     */
    @RequestMapping(value = "/changePassword")
    @ResponseBody
    public State changePassword(String originalPassword, String password, String passwordAgain, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws DataBaseException {
        logger.trace("changePassword方法开始执行");
        return accountInformationService.changePassword(originalPassword, password, passwordAgain, session, request, response);
    }

    /**
     * 给指定邮箱发送验证码，发送验证码
     * 用于找回密保
     *
     * @param mailbox 给该邮箱号发送验证码
     * @param session 获取当前会话的对象
     */
    @RequestMapping(value = "/secretProtectionVerificationCode")
    @ResponseBody
    public void secretProtectionVerificationCode(String mailbox, HttpSession session) {
        logger.trace("secretProtectionVerificationCode方法开始执行");
        specialFunctions.sendVerificationCode(mailbox, session);
    }

    /**
     * 点击设置密保或修改密保执行此方法，ajax
     * 修改密保的邮箱修改
     *
     * @param verificationCode 用户输入的验证码
     * @param gender           密保——性别
     * @param age              密保——年龄
     * @param birthday         密保——出生日期
     * @param address          密保——住址
     * @param session          当前会话
     */
    @RequestMapping(value = "/addSecretProtection")
    @ResponseBody
    public State addSecretProtection(String verificationCode, String gender, String age, String birthday, String address, HttpSession session) throws DataBaseException {
        logger.trace("addSecretProtection方法开始执行");
        return secretProtectionService.addSecretProtection(verificationCode, gender, age, birthday, address, session);
    }


    /**
     * 找回密码（通过密保找回，忘记密保通过邮箱验证找回）
     * 第一步，先进行账号验证，验证账号是否存在
     *
     * @param mailbox 获取要找回密码的账号
     */
    @RequestMapping(value = "/verificationAccount")
    @ResponseBody
    public State verificationAccount(String mailbox) {
        logger.trace("verificationAccount方法开始执行");
        return secretProtectionService.verificationAccount(mailbox);
    }

    /**
     * 找回密码
     * 第二步，验证账号的密保是否正确
     *
     * @param mailbox  需要验证的邮箱
     * @param gender   密保——性别
     * @param age      密保——年龄
     * @param birthday 密保——出生日期
     * @param address  密保——住址
     */
    @RequestMapping(value = "/verificationSecretProtection")
    @ResponseBody
    public State verificationSecretProtection(String mailbox, String gender, String age, String birthday, String address) {
        logger.trace("verificationSecretProtection方法开始执行");
        return secretProtectionService.verificationSecretProtection(mailbox, gender, age, birthday, address);
    }

    /**
     * 点击开通或关闭个人空间执行此方法，ajax
     *
     * @param session 获取当前会话
     */
    @RequestMapping(value = "/privacy")
    @ResponseBody
    public State privacy(HttpSession session) throws DataBaseException {
        logger.trace("privacy方法开始执行");
        // 修改用户空间状态，失败抛异常
        return accountInformationService.privacy(session);
    }
}
