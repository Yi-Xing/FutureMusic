package controller.user.administrators;

import com.github.pagehelper.PageInfo;
import entity.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
@RequestMapping(value = "/administrators")
public class OrderInformation {
    private static final Logger logger = LoggerFactory.getLogger(ActivityInformationService.class);
    @Resource(name = "OrderInformationService")
    OrderInformationService orderInformationService;
    /**
     * 返回指定指定用户的所有订单或关于指定音乐或MV的订单
     * @param condition 音乐或MVid或用户的id
     *                  1表示是音乐  2表示MV 3表示用户
     * @param pageNum 表示当前第几页
     */
    @RequestMapping(value = "/showOrder")
    @ResponseBody
    public PageInfo showOrder(String[] condition, Integer pageNum , Model model)  {
        return orderInformationService.showOrder(condition,pageNum,model);
    }

    /**
     *  删除按id
     */
    @RequestMapping(value = "/deleteOrder")
    @ResponseBody
    public State deleteOrder(Integer id) throws DataBaseException {
        return orderInformationService.deleteOrder(id);
    }
}
