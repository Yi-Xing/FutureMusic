package service.user.administrators;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.Activity;
import entity.State;
import mapper.ActivityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import util.JudgeIsOverdueUtil;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * 活动
 */
@Service(value = "ActivityInformationService")
public class ActivityInformationService {
    private static final Logger logger = LoggerFactory.getLogger(ActivityInformationService.class);
    @Resource(name = "ActivityMapper")
    ActivityMapper activityMapper;
    /**
     * 添加活动信息，ajax
     */
    public State addActivity( Activity activity) throws DataBaseException {
        if(activityMapper.insertActivity(activity)<1){
            // 如果失败是数据库错误
            logger.error("活动：" + activity + "添加时，数据库出错");
            throw new DataBaseException("活动：" + activity + "添加时，数据库出错");
        }
        return new State(1);
    }
    /**
     * 显示活动信息
     * @param condition 条件可以有多个
     * @param pageNum 表示当前第几页
     */
    public String showActivity(String[] condition,Integer pageNum, Model model) throws ParseException {
        // 用来存储管理员输入的条件
        Activity activity = new Activity();
        if ((condition[0] != null) && !"".equals(condition[0])) {
            activity.setId(Integer.parseInt(condition[0]));
        }
        if ((condition[1] != null) && !"".equals(condition[1])) {
            activity.setName(condition[1]);
        }
        if ((condition[2] != null) && !"".equals(condition[2])) {
            // 将字符串转为日期类型
            activity.setEndDate(JudgeIsOverdueUtil.toDate(condition[2]));
        }
        if ((condition[3] != null) && !"".equals(condition[3])) {
            activity.setType(Integer.parseInt(condition[3]));
        }
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pageNum, 8);
        // 根据条件查找信息
        List<Activity> list = activityMapper.selectListActivity(activity);
        PageInfo pageInfo = new PageInfo<>(list);
        // 传入页面信息
        model.addAttribute("pageInfo", pageInfo);
        return null;
    }
    /**
     * 修改活动信息，ajax
     */
    public State modifyActivity( Activity activity) throws DataBaseException {
        if(activityMapper.updateActivity(activity)<1){
            // 如果失败是数据库错误
            logger.error("活动：" + activity + "更新时，数据库出错");
            throw new DataBaseException("活动：" + activity + "更新时，数据库出错");
        }
        return new State(1);
    }
    /**
     * 删除活动信息
     */
    public String deleteActivity(Integer id) throws DataBaseException {
        if(activityMapper.deleteActivity(id)<1){
            // 如果失败是数据库错误
            logger.error("活动：" + id + "删除时，数据库出错");
            throw new DataBaseException("活动：" + id + "删除时，数据库出错");
        }
        return null;
    }
}
