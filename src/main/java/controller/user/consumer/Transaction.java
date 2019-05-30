package controller.user.consumer;

import com.alipay.api.AlipayApiException;
import entity.State;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import service.user.consumer.AlipayService;
import service.user.consumer.TransactionService;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 充值余额，充值VIP，购买音乐，购买MV，判断指定用户没有有购买指定音乐或MV
 *
 * @author 5月14日 张易兴创建
 */
@Controller
public class Transaction {
    @Resource(name = "AlipayService")
    AlipayService alipayService;
    @Resource(name = "TransactionService")
    TransactionService transactionService;
    /**
     * 充值按钮执行此方法，输入充值的金额
     *
     * @param money   需要充值的金额
     */
    @RequestMapping(value = "/rechargeBalance")
    public void rechargeBalance(HttpServletRequest request, HttpServletResponse response, String money) throws IOException, AlipayApiException {
        alipayService.ali(request,response,money);
    }

    /**
     * 充值完回调此方法
     *
     */
    @RequestMapping(value = "/rechargeCallback")
    public State rechargeCallback(HttpServletRequest request, HttpSession session) throws IOException, AlipayApiException, DataBaseException {
        return transactionService.rechargeBalance(request,session);
    }
    /**
     * 购买音乐或MV
     * @param id   得到购买的id
     * @param type 得到购买的类型 1表示音乐  2表示MV
     */
    @RequestMapping(value = "/purchase")
    public  State purchase(Integer id, Integer type,HttpSession session) throws DataBaseException {
        return transactionService.purchase(id,type,session);
    }

    /**
     * 充值VIP
     *
     * @param count 表示充值几个月
     */
    @RequestMapping(value = "/rechargeVIP")
    public State rechargeVIP( Integer count,HttpSession session) throws DataBaseException {
       return transactionService.rechargeVIP(count,session);
    }

}
