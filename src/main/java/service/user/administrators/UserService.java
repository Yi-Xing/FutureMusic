package service.user.administrators;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.User;
import mapper.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.print.Doc;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员操作用户信息
 *
 * @author 5月20日 张易兴创建
 */

@Service(value = "UserService")
public class UserService {
    @Resource(name = "UserMapper")
    UserMapper userMapper;
    /**
     * 显示用户信息
     */
    public String showUser(Model model){
        List<User> listUser=userMapper.selectUser(new User());
        //引入分页查询，使用PageHelper分页功能
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(3,5);
        //startPage后紧跟的这个查询就是分页查询
        List<User> emps = userMapper.selectUser(new User());
        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以
        PageInfo pageInfo = new PageInfo<>(emps,5);
        //pageINfo封装了分页的详细信息，也可以指定连续显示的页数
        model.addAttribute("pageInfo",pageInfo);
        return null;
    }
}
