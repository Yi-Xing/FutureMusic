package controller.user.consumer;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import entity.State;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import test.zyx.AlipayConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * 充值余额，充值VIP，购买音乐，购买MV，购买专辑，判断指定用户没有有购买指定音乐或MV
 *
 * @author 5月14日 张易兴创建
 */
@Controller
public class Transaction {
    /**
     * 充值按钮执行此方法，输入充值的金额
     *
     * @param money   需要充值的金额
     * @param orderName   订单的名称
     * @param commodityDescription   订单的描述
     * @param session   获取当前会话
     */
    @RequestMapping(value = "/rechargeBalance")
    public void rechargeBalance(HttpServletRequest request, HttpServletResponse response, String money,String orderName,String commodityDescription, HttpSession session) throws IOException, AlipayApiException {

    }
    /**
     * 购买音乐或MV或专辑
     */
    @RequestMapping(value = "/purchase")
    public  State purchase(HttpSession session){
        return null;
    }

    /**
     * 充值VIP
     */
    @RequestMapping(value = "/rechargeVIP")
    public void rechargeVIP( HttpSession session) throws IOException, AlipayApiException {

    }

}
