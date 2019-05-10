package mapper;

import entity.Activity;

import java.util.List;

/**
 * 对活动表操作的Mapper
 *
 * @author 5月9日 张易兴创建
 */
public interface ActivityMapper {

    /**
     * 查找符合指定规则的活动信息
     *
     * @param activity 按照指定规则查找指定活动
     *                 封装信息：
     *                 无查找所有活动
     *                 id查找指定活动
     *                 name按名字模糊查询活动
     *                 name和type按名字模糊查询指定类别的活动
     *                 endDate查找还未结束的活动
     *                 endDate和type查找还未结束的指定类别的活动
     * @return List<Activity>  返回查找到的活动
     */
    public List<Activity> selectListActivity(Activity activity);

    /**
     * 添加指定活动的信息
     *
     * @param activity 活动的对象
     *                 封装信息：除id以外的所有信息
     * @return int 返回添加的条数
     */
    public int insertActivity(Activity activity);

    /**
     * 更新指定活动的信息
     *
     * @param activity 活动对象 封装所有信息
     * @return int 返回更新的条数
     */
    public int updateActivity(Activity activity);

    /**
     * 删除指定活动的信息
     *
     * @param id 活动的id
     * @return int 返回删除的条数
     */
    public int deleteActivity(int id);
}
