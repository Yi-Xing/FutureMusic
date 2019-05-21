package service.user.administrators;

import entity.Activity;
import entity.State;
import mapper.ActivityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import util.exception.DataBaseException;

import javax.annotation.Resource;

/**
 * 活动
 */
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
    public String showActivity(String[] condition,Integer pageNum, Model model) {
        return null;
    }
    /**
     * 修改活动信息，ajax
     */
    public State modifyActivity( Activity activity) throws DataBaseException {
        return null;
    }
    /**
     * 删除活动信息
     */
    public String deleteActivity(Integer id) throws DataBaseException {
        return null;
    }
}
