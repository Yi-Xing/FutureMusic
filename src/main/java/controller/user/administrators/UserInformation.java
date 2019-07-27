package controller.user.administrators;

import entity.State;
import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.administrators.UserInformationService;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import javax.mail.Session;
import javax.servlet.http.HttpSession;

/**
 * 用户：
 *  显示  id  邮箱  名字  等级 VIP到期时间 余额 创号时间  被举报次数
 *         查询：1、id
 *               2、邮箱
 *               3、名字
 *               4、等级
 *               5、被举报次数（查询到是大于输入的举报次数）
 *         关联查询，用户的粉丝量，创建的专辑，上传过的音乐，购买过的东西，评论过的信息
 * 可修改：  等级  VIP时间  余额 ，举报次数
 * @author 5月20日 张易兴创建
 */
@Controller
@RequestMapping(value = "/administrators")
public class UserInformation {
    private static final Logger logger = LoggerFactory.getLogger(UserInformation.class);
    @Resource(name = "UserInformationService")
    UserInformationService userInformationService;


    /**
     * 显示和按条件查询用户信息
     * @param condition 条件可以有多个
     * @param pageNum 表示当前第几页
     * @param session 获取会话用于得到当前用户的账号等级
     */
    @RequestMapping(value = "/showUser")
    public String showUser(HttpSession session ,String[] condition, @RequestParam(defaultValue="1")Integer pageNum, Model model) {
        return userInformationService.showUser(session,condition,pageNum,model);
    }

    /**
     * 修改用户信息，ajax * 可修改：  等级  VIP时间  余额  举报次数
     */
    @RequestMapping(value = "/modifyUser")
    @ResponseBody
    public State modifyUser(String id ,String level,String balance,String report ) throws DataBaseException {
        return userInformationService.modifyUser(id,level,balance,report);
    }
    /**
     * 修改用户的vip到期时间
     */
    @RequestMapping(value = "/modifyUserVipDate")
    @ResponseBody
    public State modifyUserVipDate(String id ,String vipDate) throws DataBaseException {
        return userInformationService.modifyUserVipDate(id,vipDate);
    }



    /**
     * 返回指定用户的所有信息+粉丝量
     * @param id 用户的id
     */
    @RequestMapping(value = "/showUserInformatics")
    @ResponseBody
    public User showUserInformatics(Integer id) {
        return userInformationService.showUserInformatics(id);
    }
}
