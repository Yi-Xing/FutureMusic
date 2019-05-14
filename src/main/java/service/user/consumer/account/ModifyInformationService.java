package service.user.consumer.account;

import entity.User;
import exception.DataBaseException;
import mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import util.CookieUtil;
import util.EncryptionUtil;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 修改用户信息的业务逻辑
 * 修改密码，修改用户名，判断是否设置密保，设置修改密保，判断密保是否正确
 *
 * @author 5月11日 张易兴创建
 */
@Service(value = "ModifyInformationService")
public class ModifyInformationService {
    private static final Logger logger = LoggerFactory.getLogger(ModifyInformationService.class);

    @Resource(name = "LoginService")
    private LoginService loginService;
    @Resource(name = "UserMapper")
    UserMapper userMapper;

    /**
     * 判断用户密码是否正确，如果正确则修改数据库中的密码和会话上的密码以及cookie的密码
     *
     * @param mailbox          接收再次输入的新密码
     * @param originalPassword 获取用户输入的原密码
     * @param password         接收输入的新密码
     * @return boolean 返回是否修改成功
     */
    public boolean isMailboxAndPassword(String mailbox, String originalPassword, String password, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws DataBaseException {
        // 对密码进行加密然后去数据查找
        User user = loginService.isMailboxAndPassword(mailbox, EncryptionUtil.encryptionMD5(originalPassword));
        if (user != null) {
            // 对密码进行加密
            password = EncryptionUtil.encryptionMD5(password);
            // 密码验证成功，修改密码
            user.setPassword(password);
            // 修改数据库中的信息
            if (userMapper.updateUser(user) > 0) {
                // 修改会话上的用户信息（密码）
                session.setAttribute("userInformation", user);
                // 修改cookie中的用户密码
                Cookie[] cookies = request.getCookies();
                // 得到用户原来的cookie
                Cookie originalCookie = CookieUtil.obtainCookie(cookies, "userInformation");
                //得到原cookie还剩的时间
                int time = originalCookie.getMaxAge();
                Cookie userCookie = new Cookie("userInformation", mailbox + "#" + password);
                userCookie.setMaxAge(time);
                userCookie.setComment("/*");
                // 重新添加cookie
                response.addCookie(userCookie);
                return true;
            } else {
                // 如果失败是数据库错误
                logger.debug("邮箱：" + mailbox + "修改密码时，数据库出错");
                throw new DataBaseException("邮箱：" + mailbox + "修改密码时，数据库出错");
            }
        }
        return false;
    }

    /**
     * 用于修改用户的用户名
     * 并修改会话上的用户名
     *
     * @param userName 修改后的用户名
     */
    public void changeUserName(String userName, HttpSession session) throws DataBaseException {
        User user = (User) session.getAttribute("userInformation");
        user.setName(userName);
        // 修改数据库中的信息
        if (userMapper.updateUser(user) > 0) {
            // 修改会话上的用户信息
            session.setAttribute("userInformation", user);
        } else {
            // 如果失败是数据库错误
            logger.debug("邮箱：" + user.getMailbox() + "修改用户名时，数据库出错");
            throw new DataBaseException("邮箱：" + user.getMailbox() + "修改用户名时，数据库出错");
        }
    }

    /**
     * 用于判断用户是否设置密保
     *
     * @param mailbox 需要验证的邮箱
     * @return boolean 返回用户是否设置密保
     */
    public boolean isSetUpSecretProtection(String mailbox) {
        User user = new User();
        user.setMailbox(mailbox);
        // 从数据库中查找指定用户
        List<User> list = userMapper.selectUser(user);
        // 因为邮箱号是模糊查询，先判断该用户存不存在
        for (User listUser : list) {
            if (listUser.getMailbox().equals(mailbox)) {
                // 判断有没有密保
                if (listUser.getGender() != null) {
                    return true;
                }
            }
        }
        return false;
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
    public User changeSecretProtection(String gender, String age, String birthday, String address, HttpSession session) {
        User user = (User) session.getAttribute("userInformation");
        user.setGender(EncryptionUtil.encryptionMD5(gender));
        user.setAge(EncryptionUtil.encryptionMD5(age));
        user.setBirthday(EncryptionUtil.encryptionMD5(birthday));
        user.setAddress(EncryptionUtil.encryptionMD5(address));
        if (userMapper.insertUser(user) > 0) {
            // 修改会话上的用户信息
            session.setAttribute("userInformation", user);
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
    public boolean isSecretProtection(String mailbox, String gender, String age, String birthday, String address) {
        User user = new User();
        user.setMailbox(mailbox);
        // 从数据库中查找指定用户
        List<User> list = userMapper.selectUser(user);
        // 因为邮箱号是模糊查询，先判断该用户存不存在
        for (User listUser : list) {
            if (listUser.getMailbox().equals(mailbox)) {
                // 判断密保是否正确
                if (listUser.getGender().equals(EncryptionUtil.encryptionMD5(gender)) &&
                        listUser.getAge().equals(EncryptionUtil.encryptionMD5(age)) &&
                        listUser.getBirthday().equals(EncryptionUtil.encryptionMD5(birthday)) &&
                        listUser.getAddress().equals(EncryptionUtil.encryptionMD5(address))) {
                    return true;
                }
            }
        }
        return false;
    }
}
