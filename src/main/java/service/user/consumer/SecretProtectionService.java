package service.user.consumer;

import entity.State;
import entity.User;
import exception.DataBaseException;
import mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.user.SpecialFunctions;
import service.user.ValidationInformation;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 关于密保的所有操作
 * @author 5月18日 张易兴创建
 */
public class SecretProtectionService {


    @Resource(name = "UserMapper")
    UserMapper userMapper;
    @Resource(name = "ValidationInformation")
    ValidationInformation validationInformation;
    @Resource(name = "SpecialFunctions")
    SpecialFunctions specialFunctions;
    private static final Logger logger = LoggerFactory.getLogger(SecretProtectionService.class);

    /**
     * 确定安全中心应该进入的界面
     */
    public String  safetyCenter(HttpSession session){
        if(specialFunctions.getUserMailbox(session)!=null){
            // 设置需要跳转的页面
            return null;
        }
        return null;
    }
    /**
     * 用于判断用户是否设置密保
     *
     * @param mailbox 需要验证的邮箱
     * @return boolean 返回用户是否设置密保
     */
    private boolean isSetUpSecretProtection(String mailbox) {
        // 从数据库中查找指定邮箱的用户
        User user = userMapper.selectUserMailbox(mailbox);
        // 判断有没有密保
        return user.getGender() != null;
    }

    /**
     * 用于添加和修改密保
     *
     * @param gender   密保——性别
     * @param age      密保——年龄
     * @param birthday 密保——出生日期
     * @param address  密保——住址
     * @param session  当前会话
     * @return User 返回成功用户信息，失败返回null
     */
    private User changeSecretProtection(String gender, String age, String birthday, String address, HttpSession session) {
        User user = (User) session.getAttribute("userInformation");
        user.setGender(specialFunctions.encryptionMD5(gender));
        user.setAge(specialFunctions.encryptionMD5(age));
        user.setBirthday(specialFunctions.encryptionMD5(birthday));
        user.setAddress(specialFunctions.encryptionMD5(address));
        if (userMapper.insertUser(user) > 0) {
            return user;
        }
        return null;
    }

    /**
     * 用于判断用户密保是否正确
     *
     * @param mailbox  需要验证密保的邮箱
     * @param gender   密保——性别
     * @param age      密保——年龄
     * @param birthday 密保——出生日期
     * @param address  密保——住址
     * @return boolean 返回用户密保是否正确
     */
    private boolean isSecretProtection(String mailbox, String gender, String age, String birthday, String address) {
        // 从数据库中查找指定邮箱的用户
        User user = userMapper.selectUserMailbox(mailbox);
        // 判断密保是否正确
        return user.getGender().equals(specialFunctions.encryptionMD5(gender)) &&
                user.getAge().equals(specialFunctions.encryptionMD5(age)) &&
                user.getBirthday().equals(specialFunctions.encryptionMD5(birthday)) &&
                user.getAddress().equals(specialFunctions.encryptionMD5(address));
    }

    /**
     * 设置密保和通过验证码更改密保
     *
     * @param verificationCode 用户输入的验证码
     * @param gender           密保——性别
     * @param age              密保——年龄
     * @param birthday         密保——出生日期
     * @param address          密保——住址
     * @param session          当前会话
     */
    public State addSecretProtection(String verificationCode, String gender, String age, String birthday, String address, HttpSession session) throws DataBaseException {
        // 获得设置或修改密保的邮箱
        String mailbox = ((User) session.getAttribute("userInformation")).getMailbox();
        State state = new State();
        // 验证邮箱验证码是否超时
        if (validationInformation.isMailboxVerificationCodeTime(session)) {
            // 验证邮箱的验证码是否正确
            if (validationInformation.isMailboxVerificationCode(session, verificationCode)) {
                User user = changeSecretProtection(gender, age, birthday, address, session);
                //成功用户信息，失败返回null
                if (user != null) {
                    // 修改会话上的用户信息
                    session.setAttribute("userInformation", user);
                    logger.info("邮箱：" + mailbox + "的密保设置或修改成功");
                    state.setState(1);
                } else {
                    // 如果失败是数据库错误
                    logger.error("邮箱：" + mailbox + "设置密保时，数据库出错");
                    throw new DataBaseException("邮箱：" + mailbox + "设置密保时，数据库出错");
                }
            } else {
                logger.debug("邮箱：" + mailbox + "验证码错误");
                state.setInformation("验证码错误");
            }
        } else {
            logger.debug("邮箱：" + mailbox + "的验证码超时");
            state.setInformation("邮箱的验证码超时，请重新获得");
        }
        return state;
    }

    /**
     * 找回密码（通过密保找回，忘记密保通过邮箱验证找回）
     * 第一步，先进行账号验证，验证账号是否存在
     *
     * @param mailbox 获取要找回密码的账号
     */
    public State verificationAccount(String mailbox) {
        State state = new State();
        if (validationInformation.isMailboxExistence(mailbox)) {
            if(isSetUpSecretProtection(mailbox)){
                logger.info("邮箱：" + mailbox + "开始找回密码");
                state.setState(1);
            }else{
                logger.debug("邮箱：" + mailbox + "邮箱没有设置密保");
                state.setInformation(mailbox + "邮箱没有设置密保");
            }
        } else {
            logger.debug("邮箱：" + mailbox + "邮箱不存在");
            state.setInformation(mailbox + "邮箱不存在");
        }
        return state;
    }

    /**
     * 找回密码
     * 第二步，验证账号的密保是否正确
     * 密保正确，填出输入密码框（未实现）
     *
     * @param mailbox  需要验证的邮箱
     * @param gender   密保——性别
     * @param age      密保——年龄
     * @param birthday 密保——出生日期
     * @param address  密保——住址
     */
    public State verificationSecretProtection(String mailbox, String gender, String age, String birthday, String address) {
        State state = new State();
        // 验证密保是否正确
        if (isSecretProtection(mailbox, gender, age, birthday, address)) {
            logger.info("邮箱：" + mailbox + "找回密码成功，开始设置新密码");
            state.setState(1);
        } else {
            logger.debug("邮箱：" + mailbox + "的密保验证有误");
            state.setInformation("密保有误");
        }
        return state;
    }

}
