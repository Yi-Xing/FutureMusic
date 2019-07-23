package service.user.administrators;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.Focus;
import entity.State;
import entity.User;
import mapper.FocusMapper;
import mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import service.user.IdExistence;
import service.user.SpecialFunctions;
import service.user.ValidationInformation;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 管理员操作用户信息
 *
 * @author 5月20日 张易兴创建
 */

@Service(value = "UserInformationService")
public class UserInformationService {
    private static final Logger logger = LoggerFactory.getLogger(UserInformationService.class);
    @Resource(name = "UserMapper")
    UserMapper userMapper;
    @Resource(name = "ValidationInformation")
    ValidationInformation validationInformation;
    @Resource(name = "FocusMapper")
    FocusMapper focusMapper;
    @Resource(name = "IdExistence")
    IdExistence idExistence;
    @Resource(name = "SpecialFunctions")
    SpecialFunctions specialFunctions;

    /**
     * 显示和按条件查询用户信息
     * 条件顺序  类型（0用户id，1邮箱，2用户名）-各种id-举报次数-账号等级
     *
     * @param condition 条件可以有多个
     * @param pageNum   表示当前第几页
     */
    public String showUser(HttpSession session, String[] condition, Integer pageNum, Model model) {
        // 用来存储管理员输入的条件
        User user = new User();
        // 使用id字段存储当前用户等级来判断该用户是否有权限查找其他用户,先从会话上得到用户信息，然后得到该用户的等级
//        user.setId(specialFunctions.getUser(session).getLevel());
        if (condition != null) {
            logger.debug("查找到的用户名字" + condition[2]);
            if ((condition[0] != null) && !"".equals(condition[0]) && (condition[1] != null) && !"".equals(condition[1])) {
                // 1用户id 2邮箱 3用户名
                switch (condition[0]) {
                    case "1":
                        PageHelper.startPage(pageNum, 7);
                        // 查找指定id的用户
                        User selectUser = idExistence.isUserIdExist(condition[1]);
                        List<User> list = new ArrayList<>();
                        if (selectUser != null) {
                            list.add(selectUser);
                        }
                        PageInfo pageInfo = new PageInfo<>(list);
                        model.addAttribute("pageInfo", pageInfo);
                        // 用户找到返回信息
                        model.addAttribute("conditionZero", condition[0]);
                        model.addAttribute("conditionOne", condition[1]);
                        model.addAttribute("conditionTwo", condition[2]);
                        model.addAttribute("conditionThree", condition[3]);
                        return "system/backgroundSystem";
                    case "2":
                        user.setMailbox(condition[1]);
                        break;
                    case "3":
                        user.setName(condition[1]);
                        break;
                    default:
                }
            }
            if ((condition[2] != null) && !"".equals(condition[2])) {
                user.setReport(Integer.parseInt(condition[2]));
            }
            if ((condition[3] != null) && !"".equals(condition[3])) {
                user.setLevel(Integer.parseInt(condition[3]));
            }
            model.addAttribute("conditionZero", condition[0]);
            model.addAttribute("conditionOne", condition[1]);
            model.addAttribute("conditionTwo", condition[2]);
            model.addAttribute("conditionThree", condition[3]);
        } else {
            model.addAttribute("conditionZero", null);
            model.addAttribute("conditionOne", null);
            model.addAttribute("conditionTwo", null);
            model.addAttribute("conditionThree", null);
        }
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pageNum, 7);
        // 根据条件查找用户信息
        List<User> list = userMapper.selectUser(user);
        logger.debug("查找到的用户" + list);
        // 传入页面信息
        PageInfo pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo", pageInfo);
//        model.addAttribute("pages", new int[pageInfo.getPages()]);
        return "system/backgroundSystem";
    }

    /**
     * 修改用户信息，ajax * 可修改： 等级    余额  举报次数
     */
    public State modifyUser(String id, String level, String balance, String report) throws DataBaseException {
        // 状态码 1成功 0失败
        State state = new State();
        // 获取指定用户的信息
        User user = idExistence.isUserIdExist(id);
        // 等于null表示不存在
        if (user == null) {
            state.setInformation("id不存在");
            return state;
        }
        // 等级是否合法
        if (!level.matches("[0-4]")) {
            state.setInformation("用户等级不合法");
            return state;
        }
        user.setLevel(Integer.valueOf(level));
        // 判断余额是否合法
        if (!validationInformation.isPrice(balance)) {
            state.setInformation("余额不合法");
            return state;
        }
        user.setBalance(new BigDecimal(balance));
        // 举报次数是否合法
        if (!report.matches("([1-9][0-9]*|0)")) {
            state.setInformation("举报次数不合法");
            return state;
        }
        user.setReport(Integer.valueOf(report));
        if (userMapper.updateUser(user) < 1) {
            // 如果失败是数据库错误
            logger.error("邮箱：" + user.getMailbox() + "修改用户信息时，数据库出错");
            throw new DataBaseException("邮箱：" + user.getMailbox() + "修改用户信息时，数据库出错");
        }
        state.setState(1);
        return state;
    }

    /**
     * 修改用户的vip到期时间
     */
    public State modifyUserVipDate(String id, String vipDate) throws DataBaseException {
        // 状态码 1成功 0失败
        State state = new State();
        // 获取指定用户的信息
        User user = idExistence.isUserIdExist(id);
        // 等于null表示不存在
        if (user == null) {
            state.setInformation("id不存在");
            return state;
        }
        if (!vipDate.matches("^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$")) {
            state.setInformation("vip时间不合法");
            return state;
        }
        // 设置日期格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            user.setVipDate(simpleDateFormat.parse(vipDate));
        } catch (ParseException e) {
            state.setInformation("vip时间不合法");
            return state;
        }
        if (userMapper.updateUser(user) < 1) {
            // 如果失败是数据库错误
            logger.error("邮箱：" + user.getMailbox() + "修改用户信息时，数据库出错");
            throw new DataBaseException("邮箱：" + user.getMailbox() + "修改用户信息时，数据库出错");
        }
        state.setState(1);
        return state;
    }

    /**
     * 返回指定用户的所有信息+粉丝量
     *
     * @param id 条件可以有多个
     */
    public User showUserInformatics(Integer id) {
        Focus focus = new Focus();
        //返回指定用户的信息
        User user = idExistence.isUserId(id);
        // 先判断指定用户是否存在
        if (user != null) {
            focus.setUserFocusId(id);
            // 1表示关注
            focus.setUserType(1);
            // 该用户的粉丝量
            int count = focusMapper.selectUserFocusCount(focus);
            // 将用户的id用来存储用户的粉丝量
            user.setId(count);
            logger.debug("id为" + id + "的用户粉丝量为" + count);
        } else {
            logger.debug("id为" + id + "的用户不存在");
        }
        // 用户不存在返回null
        System.out.println(user);
        return user;
    }
}
