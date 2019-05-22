package controller.user.administrators;

import entity.Order;
import entity.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import service.user.administrators.ActivityInformationService;
import service.user.administrators.OrderInformationService;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * 订单：
 * 显示：所有信息
 *      查询：1、用户id
 *            2、音乐或MVid
 *  删除按id
 * @author 5月22日 张易兴创建
 */
@Controller
public class OrderInformation {
    private static final Logger logger = LoggerFactory.getLogger(ActivityInformationService.class);
    @Resource(name = "OrderInformationService")
    OrderInformationService orderInformationService;
    /**
     * 返回指定指定用户的所有订单或关于指定音乐或MV的订单
     * @param id 音乐或MVid或用户的id
     * @param pageNum 表示当前第几页
     * @param type 1表示是音乐  2表示MV 3表示用户
     */
    @RequestMapping(value = "/showActivity")
    public String showOrder(Integer id,Integer type,Integer pageNum ,Model model) throws ParseException {
        return orderInformationService.showOrder(id,type,pageNum,model);
    }

    /**
     *  删除按id
     */
    @RequestMapping(value = "/deleteOrder")
    public State deleteOrder(Integer id) throws DataBaseException {
        return orderInformationService.deleteOrder(id);
    }
}
