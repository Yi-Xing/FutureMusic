package controller.user.consumer;

import entity.State;
import entity.User;
import util.exception.DataBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.consumer.AboutUserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 关注用户，访问其他用户，举报用户
 *
 * @author 5月14日 张易兴创建
 */
@Controller
@RequestMapping(value = "/user")
public class AboutUser {
    @Resource(name = "AboutUserService")
    private AboutUserService aboutUserService;
    private static final Logger logger = LoggerFactory.getLogger(AboutUser.class);


    /**
     * 查找指定用户关注的所有用户，或被关注所有用户，被访问的记录
     * @param type    获取类型 1表示关注的用户，2表示被关注用户，3表示被访问的记录
     * @param session 获取当前会话
     */
    @RequestMapping(value = "/showFollowUser")
    @ResponseBody// 需要更改
    public List<User> showFollowUser(Integer type, HttpSession session)  {
        logger.trace("showFollowUser方法开始执行");
        return aboutUserService.showFollowUser(type,session);
    }

    /**
     * 点击关注指定用户或访问指定用户空间时执行该方法,ajax
     *
     * @param id      获取被关注者的id
     * @param type    获取类型 1表示关注，2表示访问
     * @param session 获取当前会话
     */
    @RequestMapping(value = "/followUser")
    @ResponseBody
    public State followUser(Integer id, Integer type, HttpSession session) throws DataBaseException {
        logger.trace("followUser方法开始执行");
        // 关注其他用户，失败抛异常
        return aboutUserService.followUser(id, type, session);
    }


    /**
     * 点击取消关注指定用户时候执行该方法,ajax
     *
     * @param id      获取被取消关注者的id
     * @param session 获取当前会话
     */
    @RequestMapping(value = "/cancelFollowUser")
    @ResponseBody
    public State cancelFollowUser(Integer id, HttpSession session) throws DataBaseException {
        logger.trace("State方法开始执行");
        // 取消  关注其他用户，失败抛异常
        return aboutUserService.cancelFollowUser(id, session);
    }



    /**
     * 举报指定用户执行次方法,ajax
     * 添加指定用户的被举报次数，并给客服和管理员发邮件
     *
     * @param mailbox 获取被举报用户的邮箱
     * @param content 举报的内容
     * @param session 获取当前会话
     */
    @RequestMapping(value = "/reportUser")
    @ResponseBody
    public State reportUser(String mailbox, String content, HttpSession session) throws DataBaseException {
        logger.trace("reportUser方法开始执行");
        return aboutUserService.reportUser(mailbox, content, session);
    }

}

