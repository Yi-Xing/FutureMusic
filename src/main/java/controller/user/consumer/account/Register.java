package controller.user.consumer.account;

import entity.State;
import exception.DataBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.consumer.account.RegisterService;
import service.user.consumer.account.VerificationService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 注册账号
 *
 * @author 5月11日 张易兴创建
 */
@Controller
public class Register {

    private static final Logger logger = LoggerFactory.getLogger(Register.class);
    @Resource(name = "RegisterService")
    private RegisterService registerService;
    @Resource(name = "VerificationService")
    VerificationService verificationService;

    /**
     * 点击注册按钮执行此方法，ajax
     *
     * @param sendMail         接收邮箱号
     * @param password         接收输入的密码
     * @param passwordAgain    接收再次输入的密码
     * @param verificationCode 接收输入的邮箱验证码
     * @param httpSession      获取当前会话的对象
     */
    @RequestMapping(value = "register")
    @ResponseBody
    public State register(String sendMail, String password, String passwordAgain, String verificationCode,
                          HttpSession httpSession) throws DataBaseException {
        State state = new State();
        //发邮箱前先判断邮箱是否合法
        if (verificationService.isMailbox(sendMail)) {
            // 用于从Session中取出发邮件的邮箱号
            String mailbox = "mailbox";
            // 对比邮箱是否相同，判断用户发完邮箱验证后是否又更改了邮箱
            if (httpSession.getAttribute(mailbox).equals(sendMail)) {
                // 验证邮箱的验证码是否正确
                if (verificationService.isMailboxVerificationCode(httpSession, verificationCode)) {
                    // 验证密码是否合法
                    if (verificationService.isPassword(password)) {
                        // 判断两次密码是否相同
                        if (password.equals(passwordAgain)) {
                            // 判断是否注册成功
                            if (registerService.registerAccount(sendMail, password)) {
                                state.setState(1);
                                logger.info("邮箱："+sendMail+"注册成功");
                            }else{
                                // 如果失败是数据库错误
                                throw new DataBaseException("注册时，数据库出错");
                            }
                        } else {
                            state.setInformation("两次密码不相同");
                        }
                    } else {
                        state.setInformation("输入的密码不合法");
                    }
                } else {
                    state.setInformation("邮箱验证码出错");
                }
            } else {
                state.setInformation("请先获取验证码");
            }
        } else {
            state.setInformation("邮箱格式有误");
        }
        return state;
    }

    /**
     * 点击发送验证码执行此方法，ajax
     *
     * @param mailbox     给该邮箱号发送验证码
     * @param httpSession 获取当前会话的对象
     */
    @RequestMapping(value = "sendMail")
    @ResponseBody
    public State sendMail(String mailbox, HttpSession httpSession) {
        State state = new State();
        //发邮箱前先判断邮箱是否合法
        if (verificationService.isMailbox(mailbox)) {
            //判断邮箱是否已存在
            if (verificationService.isMailboxExistence(mailbox)) {
                //调用发送邮箱的方法，将返回的验证码存到session中
                httpSession.setAttribute("verificationCode", registerService.sendMail(mailbox));
                httpSession.setAttribute("mailbox", mailbox);
                // 邮箱发送成功
                state.setState(1);
            } else {
                state.setInformation("邮箱已存在");
            }
        } else {
            state.setInformation("邮箱格式有误");
        }
        return state;
    }
}
