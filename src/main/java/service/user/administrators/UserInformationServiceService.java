package service.user.administrators;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.State;
import entity.User;
import mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import java.util.List;

/**
 * 管理员操作用户信息
 *
 * @author 5月20日 张易兴创建
 */

@Service(value = "UserInformationService")
public class UserInformationServiceService {
    private static final Logger logger = LoggerFactory.getLogger(UserInformationServiceService.class);
    @Resource(name = "UserMapper")
    UserMapper userMapper;
    /**
     * 显示用户信息
     */
    public String showUser(Integer pageNum,Model model){
        // 查找所有的用户信息
        List<User> listUser=userMapper.selectUser(new User());
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pageNum,8);
        List<User> emps = userMapper.selectUser(new User());
        PageInfo pageInfo = new PageInfo<>(emps);
        // 传入页面信息
        model.addAttribute("pageInfo",pageInfo);
        return null;
    }

    /**
     * 修改用户信息，ajax
     */
    public State modifyUser(User user) throws DataBaseException {
        if(userMapper.updateUser(user)<1){
            // 如果失败是数据库错误
            logger.error("邮箱：" + user.getMailbox() + "修改用户信息时，数据库出错");
            throw new DataBaseException("邮箱：" +  user.getMailbox() + "修改用户信息时，数据库出错");
        }
        State state=new State();
        state.setState(1);
        return state;
    }

}
