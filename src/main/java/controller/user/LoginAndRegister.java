package controller.user;


import entity.State;
import util.exception.DataBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.LoginService;
import service.user.RegisterService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录界面的功能
 * 登录，注册，退出登录，发送邮件
 *
 * @author 5月17日 张易兴创建
 */
@Controller
public class LoginAndRegister {
    @Resource(name = "LoginService")
    private LoginService loginService;
    @Resource(name = "RegisterService")
    private RegisterService registerService;

    private static final Logger logger = LoggerFactory.getLogger(LoginAndRegister.class);

    /**
     * 点击注册按钮执行此方法（ajax）
     * 注册成功后跳转到登录界面
     *
     * @param userName         接收用户名
     * @param sendMail         接收邮箱号
     * @param password         接收输入的密码
     * @param passwordAgain    接收再次输入的密码
     * @param verificationCode 接收输入的邮箱验证码
     * @param agreement 判断协议是否同意
     * @param session          获取当前会话的对象
     * @return State 返回执行的结果
     * @throws DataBaseException 数据库异常
     */
    @RequestMapping(value = "/register")
    @ResponseBody
    public State register(String userName, String sendMail, String password, String passwordAgain, String verificationCode,
                          String agreement,HttpSession session) throws DataBaseException {
        logger.trace("register方法开始执行");
        return registerService.register(userName,sendMail,password,passwordAgain,verificationCode,agreement,session);
    }

    /**
     * 点击发送验证码执行此方法，ajax
     * 用于注册账号
     *
     * @param mailbox 给该邮箱号发送验证码
     * @param session 获取当前会话的对象
     */
    @RequestMapping(value = "/registerVerificationCode")
    @ResponseBody
    public State registerVerificationCode(String mailbox, HttpSession session) {
        logger.trace("registerVerificationCode方法开始执行");
        return registerService.registerVerificationCode(mailbox,session);
    }

    /**
     * 点击登录执行此方法，ajax
     *
     * @param mailbox   给该邮箱号发送验证码
     * @param password  获取当前会话的对象
     * @param automatic 获取用户是否选择了7天自动登陆的复选框
     * @param session   获取当前会话
     */
    @RequestMapping(value = "/loginAccount")
    @ResponseBody
    public State login(String mailbox, String password, boolean automatic, HttpServletResponse response, HttpSession session) {
        logger.trace("login方法开始执行");
        return loginService.login(mailbox,password,automatic,response,session);
    }

    /**
     * 点击退出登录执行此方法，ajax
     * 删除7天自动登录，删除session中的用户信息
     *
     * @param request  获取客户端对象
     * @param response 获取服务器端对象
     * @param session  获取当前会话的对象
     */
    @RequestMapping(value = "/signOutLogin")
    @ResponseBody
    public State signOutLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        logger.trace("signOutLogin方法开始执行");
        return loginService.signOutLogin(request,response,session);
    }
}
