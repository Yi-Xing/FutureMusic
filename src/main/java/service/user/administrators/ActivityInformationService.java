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
import service.user.IdExistence;
import service.user.ValidationInformation;
import util.FileUpload;
import util.JudgeIsOverdueUtil;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 活动
 *
 * @author 5月22日 张易兴创建
 */
@Service(value = "ActivityInformationService")
public class ActivityInformationService {
    private static final Logger logger = LoggerFactory.getLogger(ActivityInformationService.class);
    @Resource(name = "ActivityMapper")
    ActivityMapper activityMapper;
    @Resource(name = "ValidationInformation")
    ValidationInformation validationInformation;
    @Resource(name = "FileUpload")
    FileUpload fileUpload;
    @Resource(name = "IdExistence")
    IdExistence idExistence;

    /**
     * 添加活动信息，ajax
     */
    public State addActivity(String name, String type, String discount, String website, String startDate, String endDate, String content, HttpServletRequest request) throws DataBaseException, IOException {
        // 先判断一部分信息是否合法
        State state = isModifyEdit(name, type, discount, website, startDate, endDate);
        // 一部分信息验证完毕
        logger.debug("一部分信息验证的结果" + state);
        if (state.getState() == 1) {
            Activity activity = new Activity();
            // 3个文件都必须上传所以必须都为true
            state = isModifyMore(content, true, request, activity);
            // 更多信息验证完毕
            logger.debug("更多信息验证的结果" + state);
            if (state.getInformation() == null) {
                logger.debug("开始添加活动");
                // 设置日期格式
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Activity activityCopy;
                try {
                    activityCopy = new Activity(0, name, activity.getPicture(), Float.valueOf(discount), Integer.valueOf(type), content, website, simpleDateFormat.parse(startDate), simpleDateFormat.parse(endDate));
                } catch (ParseException e) {
                    state.setInformation("开始/结束时间不合法");
                    return state;
                }
                if (activityMapper.insertActivity(activityCopy) < 1) {
                    // 如果失败是数据库错误
                    logger.error(activityCopy + "添加活动信息，数据库出错");
                    // 信息添加失败，需要对将才添加的文件进行删除
                    fileUpload.deleteFile(activity.getPicture());
                    throw new DataBaseException(activityCopy + "添加活动信息，数据库出错");
                }
                state.setState(1);
            }
        }
        return state;
    }


    /**
     * 显示活动信息
     * 条件 1打折对象  2是否结束  3存储id类型 4存储值
     *
     * @param condition 条件可以有多个
     * @param pageNum   表示当前第几页
     */
    public String showActivity(String[] condition, Integer pageNum, Model model) {
        // 用来存储管理员输入的条件
        Activity activity = new Activity();
        if (condition != null) {
            if ((condition[0] != null) && !"".equals(condition[0])) {
                activity.setType(Integer.parseInt(condition[0]));
            }
            if ((condition[1] != null) && !"".equals(condition[1])) {
                activity.setStartDate(new Date());
            }
            if ((condition[2] != null) && !"".equals(condition[2]) && (condition[3] != null) && !"".equals(condition[3])) {
                // 1-ID，2-标题
                switch (condition[2]) {
                    case "1":
                        activity.setId(Integer.parseInt(condition[3]));
                        break;
                    case "2":
                        activity.setName(condition[3]);
                        break;
                    default:
                }
            }
            model.addAttribute("conditionZero", condition[0]);
            model.addAttribute("conditionOne", condition[1]);
            model.addAttribute("conditionTwo", condition[2]);
            model.addAttribute("conditionThree", condition[3]);
        } else {
            model.addAttribute("conditionZero", null);
            model.addAttribute("conditionOne", null);
            model.addAttribute("conditionTwo", null);
            model.addAttribute("conditionThree", null);
        }
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pageNum, 7);
        // 根据条件查找音乐信息
        List<Activity> list = activityMapper.selectListActivity(activity);
        PageInfo pageInfo = new PageInfo<>(list);
        // 传入页面信息
        logger.debug("查找到的活动" + list);
        model.addAttribute("pageInfo", pageInfo);
        return "system/backgroundSystem";
    }

    /**
     * 查找指定id的活动信息
     */
    public Activity showIdActivity(Integer id) {
        Activity activity = new Activity();
        activity.setId(id);
        List<Activity> list = activityMapper.selectListActivity(activity);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 删除活动信息
     */
    public State deleteActivity(Integer id) throws DataBaseException {
        if (activityMapper.deleteActivity(id) < 1) {
            // 如果失败是数据库错误
            logger.error("活动：" + id + "删除时，数据库出错");
            throw new DataBaseException("活动：" + id + "删除时，数据库出错");
        }
        return new State(1);
    }


    /**
     * 修改编辑活动信息，ajax
     */
    public State modifyEditActivity(String id, String name, String type, String discount, String website, String startDate, String endDate) throws DataBaseException {
        State state = new State();
        // 先判断活动的id是否合法
        if (validationInformation.isInt(id) && idExistence.isActivityId(Integer.valueOf(id)) != null) {
            state = isModifyEdit(name, type, discount, website, startDate, endDate);
            if (state.getState() == 1) {
                Activity activity = new Activity(Integer.valueOf(id), name, Float.valueOf(discount), Integer.valueOf(type), website);
                // 设置日期格式
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    activity.setStartDate(simpleDateFormat.parse(startDate));
                    activity.setEndDate(simpleDateFormat.parse(endDate));
                } catch (ParseException e) {
                    state.setInformation("开始/结束时间不合法");
                    return state;
                }
                if (activityMapper.updateActivity(activity) < 1) {
                    // 如果失败是数据库错误
                    logger.error(activity + "修改活动信息，数据库出错");
                    throw new DataBaseException(activity + "修改活动信息，数据库出错");
                }
            }
        } else {
            state.setInformation("活动的id不存在");
        }
        return state;
    }

    /**
     * 修改更多活动信息，ajax
     */
    public State modifyMoreActivity(String id, String content, boolean checkbox, HttpServletRequest request) throws DataBaseException {
        Activity activity = new Activity();
        State state = new State();
        // 先判断活动id是否合法
        if (validationInformation.isInt(id) && idExistence.isActivityId(Integer.valueOf(id)) != null) {
            state = isModifyMore(content, checkbox, request, activity);
            if (state.getInformation() == null) {
                activity.setId(Integer.valueOf(id));
                activity.setContent(content);
                if (activityMapper.updateActivity(activity) < 1) {
                    // 如果失败是数据库错误
                    logger.error(activity + "修改音乐信息，数据库出错");
                    // 信息修改失败，需要对将才添加的文件进行删除
                    if (checkbox) {
                        fileUpload.deleteFile(activity.getPicture());
                    }
                    throw new DataBaseException(activity + "修改音乐信息，数据库出错");
                }
                logger.debug("活动信息为" + activity);
                state.setState(1);
            }
        } else {
            state.setInformation("音乐的id不存在");
        }
        return state;
    }


    /**
     * 用于判断活动的编辑信息是否合法
     */
    private State isModifyEdit(String name, String type, String discount, String website, String startDate, String endDate) {
        State state = new State();
        // 先判断标题是否合法
        if (validationInformation.isName(name)) {
            // 判断类型是否合法
            if (type.matches("[1-3]")) {
                // 判断折扣是否合法
                if (discount.matches("^0\\.\\d{1,2}$")) {
                    // 判断网站是否合法
                    if (website.length() > 0 && website.length() <= 250) {
                        // 判断开始时间是否合法
                        if (startDate.matches("^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$")) {
                            if (endDate.matches("^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$")) {
                                state.setState(1);
                            } else {
                                state.setInformation("活动的结束时间不合法");
                            }
                        } else {
                            state.setInformation("活动的开始时间不合法");
                        }
                    } else {
                        state.setInformation("活动的网站不合法");
                    }
                } else {
                    state.setInformation("活动的折扣不合法");
                }
            } else {
                state.setInformation("活动的对象不合法");
            }
        } else {
            state.setInformation("活动的标题不合法");
        }
        return state;
    }

    /**
     * 用于判断活动的更多信息是否合法,如果都合法则将路径封装在Activity中
     */
    private State isModifyMore(String content, boolean checkbox, HttpServletRequest request, Activity activity) {
        State state = new State();
        // 判断活动内容是否合法
        System.out.println(content);
        if (content.length() > 0 && content.length() <= 300) {
            // 如果选择这更改图片
            if (checkbox) {
                // 活动图片
                String activityPicture = fileUpload.musicPicture(fileUpload.getMultipartFile(request, "pictureFile"));
                if (activityPicture == null) {
                    state.setInformation("活动图片不符合要求");
                    return state;
                }
                // 得到原来的文件地址，并将其删除.
                fileUpload.deleteFile(activity.getPicture());
                activity.setPicture(activityPicture);
                logger.debug("音乐图片的路径" + activityPicture);
            }
        } else {
            state.setInformation("活动的介绍不合法");
        }
        return state;
    }

}
