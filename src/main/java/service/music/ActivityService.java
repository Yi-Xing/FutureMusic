package service.music;

import entity.Activity;
import mapper.ActivityMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
@Service(value = "ActivityService")
public class ActivityService {

@Resource(name = "ActivityMapper")
private ActivityMapper activityMapper;
    /**
     * 在首页中展示活动
     * @return List<Activity>  返回查找到的活动
     */
    public List<Activity> selectActivity() {
        Activity activity = new Activity();
        activity.setEndDate(new Date());
        List<Activity> activityList = activityMapper.selectListActivity(activity);
        return activityList;
    }
}
