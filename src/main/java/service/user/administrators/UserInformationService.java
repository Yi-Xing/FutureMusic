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
import service.user.ValidationInformation;
import util.exception.DataBaseException;

import javax.annotation.Resource;
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

    /**
     * 显示和按条件查询用户信息
     *
     * @param condition 条件可以有多个
     * @param pageNum   表示当前第几页
     */
    public String showUser(String[] condition, Integer pageNum, Model model) {
        // 用来存储管理员输入的条件
        User user = new User();
        if (condition != null) {
            logger.debug("名字" + condition[2]);
            if ((condition[0] != null) && !"".equals(condition[0])) {
                user.setId(Integer.parseInt(condition[0]));
            }
            if ((condition[1] != null) && !"".equals(condition[1])) {
                user.setMailbox(condition[1]);
            }
            if ((condition[2] != null) && !"".equals(condition[2])) {
                user.setName(condition[2]);
            }
            if ((condition[3] != null) && !"".equals(condition[3])) {
                user.setReport(Integer.parseInt(condition[3]));
            }
            if ((condition[4] != null) && !"".equals(condition[4])) {
                user.setLevel(Integer.parseInt(condition[4]));
            }
            model.addAttribute("conditionZero", condition[0]);
            model.addAttribute("conditionOne", condition[1]);
            model.addAttribute("conditionTwo", condition[2]);
            model.addAttribute("conditionThree", condition[3]);
            model.addAttribute("conditionFour", condition[4]);
        } else {
            model.addAttribute("conditionZero", null);
            model.addAttribute("conditionOne", null);
            model.addAttribute("conditionTwo", null);
            model.addAttribute("conditionThree", null);
            model.addAttribute("conditionFour", null);
        }
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pageNum, 9);
        // 根据条件查找用户信息
        List<User> list = userMapper.selectUser(user);
        logger.debug("查找到的用户" + list);
        // 传入页面信息
        PageInfo pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("pages", new int[pageInfo.getPages()]);
        return "back_system/back_system";
    }

    /**
     * 修改用户信息，ajax * 可修改： 等级  VIP时间  余额  举报次数
     */
    public State modifyUser(User user) throws DataBaseException {
        State state = new State();
        // 判断余额是否合法
        if (validationInformation.isPrice(String.valueOf(user.getBalance()))) {
            if (userMapper.updateUser(user) < 1) {
                // 如果失败是数据库错误
                logger.error("邮箱：" + user.getMailbox() + "修改用户信息时，数据库出错");
                throw new DataBaseException("邮箱：" + user.getMailbox() + "修改用户信息时，数据库出错");
            }
            state.setState(1);
        } else {
            state.setInformation("余额不合法");
        }
        return state;
    }


    /**
     * 返回指定用户的粉丝量
     *
     * @param id 条件可以有多个
     */
    public Integer showFocus(Integer id) {
        Focus focus = new Focus();
        if (idExistence.isUserId(id) != null) {
            focus.setUserFocusId(id);
            // 1表示关注
            focus.setUserType(1);
            // 该用户的粉丝量
            int count = focusMapper.selectUserFocusCount(focus);
            logger.debug("id为" + id + "的用户粉丝量为" + count);
            return count;
        }
        logger.debug("id为" + id + "的用户不存在");
        // 用户不存在返回0
        return 0;
    }
}
