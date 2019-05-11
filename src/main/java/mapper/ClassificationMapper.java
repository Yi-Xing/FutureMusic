package mapper;

import entity.Classification;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 对分类表操作的Mapper
 *
 * @author 5月9日 张易兴创建
 */
@Repository(value = "ClassificationMapper")
public interface ClassificationMapper {
    /**
     * 查找符合指定规则的类型信息
     *
     * @param classification 按照指定规则查找指定活动
     *                       封装信息：
     *                       id查找指定类型
     *                       languages或region或gender或type任意个数，按名字查找类型
     * @return List<Classification> 返回查找到的类型
     */
    public List<Classification> selectListClassification(Classification classification);

    /**
     * 添加指定活动的信息
     *
     * @param classification 分类的对象
     *                      封装信息：除id以外的所有信息
     * @return int 返回添加的条数
     */
    public int insertClassification(Classification classification);// 添加类型
    /**
     * 删除分类中含有该名称的类型
     *
     * @param classification 分类的对象
     *                       封装信息：
     *                       languages或region或gender或type任意一个(只要一个)
     * @return int 返回删除的条数
     */
    public int deleteClassification(Classification classification);
}
