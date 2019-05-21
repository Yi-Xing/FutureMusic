package controller.user.administrators;

import entity.Activity;
import entity.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.administrators.ActivityInformationService;
import util.exception.DataBaseException;

import javax.annotation.Resource;

/**
 * 活动：
 * 可添加：添加需要填入所有信息
 * 显示：活动的id 标题，折扣百分比，针对对象（1时候折扣是针对音乐，2是MV，3是针对专辑，4的时候是针对购买vip的 ），
 * 开始时间，结束时间
 *          更多  活动图片 ，活动详细，活动跳转的网页
 *     查询：1、根据id查询
 *           2、根据标题查询
 *           3、到指定日期未结束的活动
 *           4、根据针对象查询
 *        关联查询，参加活动的（音乐或MV或专辑或）
 * 可修改：活动标题，折扣百分比，针对对象，开始时间，结束时间，活动图片 ，活动详细，活动跳转的网页
 * 可删除：根据id删除
 */
public class ActivityInformation {
    private static final Logger logger = LoggerFactory.getLogger(UserInformation.class);
    @Resource(name = "ActivityInformationService")
    ActivityInformationService activityInformationService;
    /**
     * 添加活动信息，ajax
     */
    @RequestMapping(value = "/addActivity")
    @ResponseBody
    public State addActivity(@RequestBody Activity activity) throws DataBaseException {
        return null;
    }
    /**
     * 显示活动信息
     * @param condition 条件可以有多个
     * @param pageNum 表示当前第几页
     */
    @RequestMapping(value = "/showActivity")
    public String showActivity(String[] condition,Integer pageNum, Model model) {
        return null;
    }
    /**
     * 修改活动信息，ajax
     */
    @RequestMapping(value = "/modifyActivity")
    @ResponseBody
    public State modifyActivity(@RequestBody Activity activity) throws DataBaseException {
        return null;
    }
    /**
     * 删除活动信息
     */
    @RequestMapping(value = "/deleteActivity")
    public String deleteActivity(Integer id) throws DataBaseException {
        return null;
    }
}
