package controller.user.consumer;

import entity.State;
import org.springframework.ui.Model;
import service.user.SpecialFunctions;
import util.exception.DataBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.consumer.AccountInformationService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 个人账号信息
 * 修改用户名，修改头像，开通关闭个人空间
 *
 * @author 5月17日 张易兴创建
 */
@Controller
@RequestMapping(value = "/user")
public class AccountInformation {
    @Resource(name = "AccountInformationService")
    AccountInformationService accountInformationService;
    @Resource(name = "SpecialFunctions")
    SpecialFunctions specialFunctions;
    private static final Logger logger = LoggerFactory.getLogger(AccountInformation.class);

    /**
     * 显示用户页面
     * Model封装：
     * userFollowCount用户关注的个数
     * followUserCount关注用户的个数
     */
    @RequestMapping(value = "/userPage")
    public String userPage(HttpSession session, Model model){
        return accountInformationService.userPage(session,model);
    }

    /**
     * 用于访问其他用户的页面
     */
    @RequestMapping(value = "/otherUserPage")
    public String otherUserPage(String id,HttpSession session, Model model){
        return accountInformationService.otherUserPage(id,session,model);
    }
    /**
     * 点击修改用户名执行此方法，ajax
     *
     * @param userName 修改后的用户名
     */
    @RequestMapping(value = "/changeUserName")
    @ResponseBody
    public State changeUserName(String userName, HttpSession session) throws DataBaseException {
        logger.trace("changeUserName方法开始执行");
        return accountInformationService.changeUserName(userName, session);
    }

    /**
     * 设置或更改用户头像，修改图片路径还需删除原来的图片
     *
     * @param request    获取图片的路径
     * @param session 当前会话
     */
    @RequestMapping(value = "/setUpHeadPortrait")
    @ResponseBody
    public State setUpHeadPortrait(HttpServletRequest request, HttpSession session) throws IOException, DataBaseException {
        logger.trace("setUpHeadPortrait方法开始执行");
        return accountInformationService.setUpHeadPortrait(request,session);
    }

    /**
     * 显示用户订单页面
     */
    @RequestMapping(value = "/userOrderPage")
    public String userOrderPage(HttpSession session,Model model){
        return accountInformationService.userOrderPage(session,model);
    }


    /**
     *  点击充值余额页面
     */
    @RequestMapping(value = "/balancePage")
    public String balancePage(HttpSession session,Model model){
        return accountInformationService.balancePage(session,model);
    }

    /**
     * 点击充值vip
     */
    @RequestMapping(value = "/vipPage")
    public String vipPage(HttpSession session,Model model){
        return accountInformationService.vipPage(session,model);
    }

    /**
     * 购买音乐或MV页面
     * @param id 音乐/MV的id
     * @param type 1为音乐 2为MV
     */
    @RequestMapping(value = "/musicPage")
    public String musicPage(HttpSession session,String id,String type,Model model){
        return accountInformationService.musicPage(session,id,type,model);
    }

    /**
     * 点击开通或关闭个人空间执行此方法，ajax
     *
     * @param session 获取当前会话
     */
    @RequestMapping(value = "/privacy")
    @ResponseBody
    public State privacy(HttpSession session) throws DataBaseException {
        logger.trace("privacy方法开始执行");
        // 修改用户空间状态，失败抛异常
        return accountInformationService.privacy(session);
    }
}
