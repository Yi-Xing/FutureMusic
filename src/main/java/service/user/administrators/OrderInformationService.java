package service.user.administrators;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.Order;
import entity.State;
import mapper.OrderMapper;
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
 * 订单
 * @author 5月22日 张易兴创建
 */
@Service(value = "OrderInformationService")
public class OrderInformationService {
    private static final Logger logger = LoggerFactory.getLogger(OrderInformationService.class);
    @Resource(name = "OrderMapper")
    OrderMapper orderMapper;
    /**
     * 返回指定指定用户的所有订单或关于指定音乐或MV的订单
     * @param condition 音乐或MVid或用户的id
     *                  1表示是音乐  2表示MV 3表示用户
     */
    public PageInfo showOrder(String[] condition,Integer pageNum, Model model){
        Order order=new Order();
        if (condition != null) {
            if ((condition[0] != null) && !"".equals(condition[0])) {
                order.setType(1);
                order.setMusicId(Integer.parseInt(condition[0]));
            }
            if ((condition[1] != null) && !"".equals(condition[1])) {
                order.setType(2);
                order.setMusicId(Integer.parseInt(condition[1]));
            }
            if ((condition[2] != null) && !"".equals(condition[2])) {
                order.setUserId(Integer.parseInt(condition[2]));
            }
        }
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pageNum, 8);
        // 根据条件查找订单信息
        List<Order> list = orderMapper.selectListOrder(order);
        // 传入页面信息
        return new PageInfo<>(list);
    }

    /**
     *  删除按id
     */
    public State deleteOrder(Integer id) throws DataBaseException {
        if(orderMapper.deleteOrder(id)<1){
            // 如果失败是数据库错误
            logger.error("删除订单信息时，数据库出错");
            throw new DataBaseException("删除订单信息时，数据库出错");
        }
        return new State(1);
    }
}
