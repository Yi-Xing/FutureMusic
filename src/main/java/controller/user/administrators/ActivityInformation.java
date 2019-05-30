package controller.user.administrators;

import com.github.pagehelper.PageInfo;
import entity.Activity;
import entity.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.administrators.ActivityInformationService;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;

/**
 * 活动：
 * 可添加：添加需要填入所有信息
 * 显示：活动的id 标题，折扣百分比，针 对对象（1时候折扣是针对音乐，2是MV，3是针对专辑，4的时候是针对购买vip的 ），
 * 开始时间，结束时间
 * 更多  活动图片 ，活动详细，活动跳转的网页
 * 查询：1、根据id查询
 * 2、根据标题查询
 * 3、到指定日期未结束的活动
 * 4、根据针对象查询
 * 关联查询，参加活动的（音乐或MV或专辑或）
 * 可修改：活动标题，折扣百分比，针对对象，开始时间，结束时间，活动图片 ，活动详细，活动跳转的网页
 * 可删除：根据id删除
 *
 * @author 5月20日 张易兴创建
 */
@Controller
@RequestMapping(value = "/administrators")
public class ActivityInformation {
    private static final Logger logger = LoggerFactory.getLogger(ActivityInformation.class);
    @Resource(name = "ActivityInformationService")
    ActivityInformationService activityInformationService;

    /**
     * 添加活动信息，ajax
     */
    @RequestMapping(value = "/addActivity")
    @ResponseBody
    public State addActivity(@RequestBody Activity activity, HttpServletRequest request) throws DataBaseException, IOException {
        logger.trace("addActivity方法开始执行");
        return activityInformationService.addActivity(activity, request);
    }

    /**
     * 显示活动信息
     *
     * @param condition 条件可以有多个
     *                  1、根据id查询
     *                  2、根据标题查询
     *                  3、到指定日期未结束的活动
     *                  4、根据针对象查询
     * @param pageNum   表示当前第几页
     */
    @RequestMapping(value = "/showActivity")
    @ResponseBody
    public PageInfo showActivity(String[] condition, Integer pageNum) throws ParseException {
        logger.trace("showActivity方法开始执行");
        return activityInformationService.showActivity(condition, pageNum);
    }

    /**
     * 修改活动信息，ajax
     */
    @RequestMapping(value = "/modifyActivity")
    @ResponseBody
    public State modifyActivity(@RequestBody Activity activity, HttpServletRequest request) throws DataBaseException, IOException {
        logger.trace("modifyActivity方法开始执行");
        return activityInformationService.modifyActivity(activity,request);
    }

    /**
     * 删除活动信息
     */
    @RequestMapping(value = "/deleteActivity")
    @ResponseBody
    public State deleteActivity(Integer id) throws DataBaseException {
        logger.trace("deleteActivity方法开始执行");
        return activityInformationService.deleteActivity(id);
    }
}
