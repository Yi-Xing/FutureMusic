package service.user;

import entity.State;
import entity.User;
import util.exception.DataBaseException;
import mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import util.listener.SessionListener;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 注册账号的业务逻辑
 *
 * @author 5月17日 张易兴创建
 */
@Service(value = "RegisterService")
public class RegisterService {
    @Resource(name = "UserMapper")
    UserMapper userMapper;
    @Resource(name = "ValidationInformation")
    ValidationInformation validationInformation;
    @Resource(name = "SpecialFunctions")
    SpecialFunctions specialFunctions;
    private static final Logger logger = LoggerFactory.getLogger(RegisterService.class);

    /**
     * 注册账号
     *
     * @param userName         接收用户名
     * @param mailbox          接收邮箱号
     * @param password         接收输入的密码
     * @param passwordAgain    接收再次输入的密码
     * @param verificationCode 接收输入的邮箱验证码
     * @param session          获取当前会话的对象
     * @return State 返回执行的结果
     * @throws DataBaseException 数据库异常
     */
    public State register(String userName, String mailbox, String password, String passwordAgain, String verificationCode,
                          String agreement, HttpSession session) throws DataBaseException {
        System.out.println(userName);
        System.out.println(agreement);
        logger.debug("日志输出");
        State state = new State();
        // 验证用户名是否合法
        if (validationInformation.isUserName(userName)) {
            //判断邮箱是否合法
            if (validationInformation.isMailbox(mailbox)) {
                // 验证密码是否合法
                if (validationInformation.isPassword(password)) {
                    // 判断两次密码是否相同
                    if (password.equals(passwordAgain)) {
                        // 对比邮箱是否相同，判断用户发完邮箱验证后是否又更改了邮箱
                        if (mailbox.equals(session.getAttribute("mailbox"))) {
                            // 验证邮箱验证码是否超时
                            if (validationInformation.isMailboxVerificationCodeTime(session)) {
                                // 验证邮箱的验证码是否正确
                                if (validationInformation.isMailboxVerificationCode(session, verificationCode)) {
                                    if ("true".equals(agreement)) {
                                        // 将信息存入数据库
                                        insertUser(userName, mailbox, password,session);
                                        state.setState(1);
                                        logger.info("邮箱：" + mailbox + "注册成功");

                                    } else {
                                        logger.debug("邮箱：" + mailbox + "协议未选中");
                                        state.setInformation("协议未选中");
                                    }
                                } else {
                                    logger.debug("邮箱：" + mailbox + "邮箱验证码出错");
                                    state.setInformation("邮箱验证码出错");
                                }
                            } else {
                                logger.debug("邮箱：" + mailbox + "的验证码超时");
                                state.setInformation("邮箱的验证码超时，请重新获得");
                            }
                        } else {
                            logger.debug("邮箱：" + mailbox + "请先获取验证码");
                            state.setInformation("请先获取验证码");
                        }
                    } else {
                        logger.debug("邮箱：" + mailbox + "两次密码不相同");
                        state.setInformation("两次密码不相同");
                    }
                } else {
                    logger.debug("邮箱：" + mailbox + "输入的密码不合法");
                    state.setInformation("输入的密码不合法");
                }
            } else {
                logger.debug("邮箱：" + mailbox + "邮箱格式有误");
                state.setInformation("邮箱格式有误");
            }
        } else {
            logger.debug("用户名：" + userName + "用户名格式有误");
            state.setInformation("用户名格式有误");
        }
        return state;
    }

    /**
     * 发送验证码
     * 注册，找回密保
     *
     * @param mailbox 给该邮箱号发送验证码
     * @param session 获取当前会话的对象
     */
    public State registerVerificationCode(String mailbox, HttpSession session) {
        State state = new State();
        //发邮箱前先判断邮箱是否合法
        if (validationInformation.isMailbox(mailbox)) {
            //判断邮箱是否已存在
            if (validationInformation.isMailboxExistence(mailbox)) {
                logger.debug("邮箱：" + mailbox + "邮箱已存在");
                state.setInformation("邮箱已存在");
            } else {
                // 发送邮箱
                state = specialFunctions.sendVerificationCode(mailbox, session);
                // 发送成功吗，将邮箱存入会话中
                session.setAttribute("mailbox", mailbox);
            }
        } else {
            logger.debug("邮箱：" + mailbox + "邮箱格式有误");
            state.setInformation("邮箱格式有误");
        }
        return state;
    }

    /**
     * 注册成功将账号密码用户名存入数据库
     *
     * @param userName 注册的邮箱
     * @param mailbox  注册的邮箱
     * @param password 注册的密码
     */
    private void insertUser(String userName, String mailbox, String password, HttpSession session) throws DataBaseException {
        User user = new User();
        user.setName(userName);
        user.setMailbox(mailbox);
        user.setDate(new Date());
        // 对密码进行加密
        logger.debug(user.getName());
        logger.debug(user.getMailbox());
        logger.debug(user.getDate()+"");
        user.setPassword(specialFunctions.encryptionMD5(password));
        logger.debug(user.getPassword());
        if (userMapper.insertUser(user) < 1) {
            // 如果失败是数据库错误
            logger.error("邮箱：" + mailbox + "注册时，数据库出错");
            throw new DataBaseException("邮箱：" + mailbox + "注册时，数据库出错");
        }
        // 注册成功，直接登录信息存到会话中
        session.setAttribute("userInformation", user);
        // 实现唯一登录
        // 将用户的信息存储的session监听器中
        SessionListener.sessionMap.put(user.getMailbox(), session);
    }
}
