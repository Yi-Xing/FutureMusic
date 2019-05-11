package mapper;


import entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 对订单表操作的Mapper
 *
 * @author 5月10日 张易兴创建
 */
@Repository(value = "OrderMapper")
public interface OrderMapper {
    /**
     * 查找符合指定规则的活动信息
     *
     * @param order 按照指定规则查找指定订单
     *              封装信息：
     *              userId和type查询指定用户已购买的音乐或MV
     *              userId和type和musicId查询指定用户已购买的指定音乐或MV（用于判断该用户是否购买了该音乐或MV）
     * @return List<Order>  返回查找到的订单
     */
    public List<Order> selectListOrder(Order order);

    /**
     * 添加指定订单的信息
     *
     * @param order 订单的对象
     *              封装信息：除id以外的所有信息
     * @return int 返回添加的条数
     */
    public int insertOrder(Order order);

    /**
     * 删除指定订单的信息
     *
     * @param id 订单的id
     * @return int 返回删除的条数
     */
    public int deleteOrder(int id);
}
