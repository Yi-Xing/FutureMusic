package service.user.administrators;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.Order;
import entity.State;
import mapper.OrderMapper;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import service.user.ValidationInformation;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单
 *
 * @author 5月22日 张易兴创建
 */
@Service(value = "OrderInformationService")
public class OrderInformationService {
    private static final Logger logger = LoggerFactory.getLogger(OrderInformationService.class);
    @Resource(name = "OrderMapper")
    OrderMapper orderMapper;
    @Resource(name = "ValidationInformation")
    ValidationInformation validationInformation;

    /**
     * 返回指定指定用户的所有订单或关于指定音乐或MV的订单
     * 类型-音乐/MVid-用户id-id
     *
     * @param condition 音乐或MVid或用户的id
     *                  1表示是音乐  2表示MV 3表示用户
     */
    public String showOrder(String[] condition, Integer pageNum, Model model) {
        Order order = new Order();
        if (condition != null) {
            if ((condition[0] != null) && !"".equals(condition[0])) {
                // 1表示是音乐 2表示是MV 3表示充值 4表示用户 5表示订单
                switch (condition[0]) {
                    case "1":
                    case "2":
                        order.setType(Integer.parseInt(condition[0]));
                        if ((condition[1] != null) && !"".equals(condition[1])) {
                            if (validationInformation.isInt(condition[1])) {
                                order.setMusicId(Integer.parseInt(condition[1]));
                            } else {
                                order.setMusicId(-1);
                            }
                        }
                        break;
                    case "3":
                        order.setType(3);
                        break;
                    case "4":
                        condition[2] = condition[1];
                        condition[1] = null;
                        break;
                    case "5":
                        condition[3] = condition[1];
                        condition[1] = null;
                        break;
                    default:
                }
            }
            if ((condition[2] != null) && !"".equals(condition[2])) {
                if (validationInformation.isInt(condition[2])) {
                    order.setUserId(Integer.parseInt(condition[2]));
                } else {
                    order.setUserId(-1);
                }
            }
            if ((condition[3] != null) && !"".equals(condition[3])) {
                if (validationInformation.isInt(condition[3])) {
                    order.setId(Integer.parseInt(condition[3]));
                } else {
                    order.setId(-1);
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
        // 根据条件查找订单信息
        List<Order> list = orderMapper.selectListOrder(order);
        PageInfo pageInfo = new PageInfo<>(list);
        // 传入页面信息
        logger.debug("查找到的订单" + list);
        model.addAttribute("pageInfo", pageInfo);
        return "system/backgroundSystem";
    }

    /**
     * 删除按id
     */
    public State deleteOrder(Integer id) throws DataBaseException {
        State state = new State();
        // 先判断id是否存在
        // 根据条件查找订单信息
        Order order = new Order();
        order.setId(id);
        List<Order> list = orderMapper.selectListOrder(order);
        if (list.size() != 0) {
            if (orderMapper.deleteOrder(id) < 1) {
                // 如果失败是数据库错误
                logger.error("删除订单信息时，数据库出错");
                throw new DataBaseException("删除订单信息时，数据库出错");
            }
            state.setState(1);
        } else {
            state.setInformation("id不存在");
        }
        return state;
    }
}
