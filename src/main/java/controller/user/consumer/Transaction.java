package controller.user.consumer;

import entity.State;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

/**
 * 充值余额，充值VIP，购买音乐，购买MV，购买专辑
 *
 * @author 5月14日 张易兴创建
 */
@Controller
public class Transaction {
    /**
     * 充值按钮执行此方法
     *
     * @param money   给该邮箱号发送验证码
     * @param session   获取当前会话
     */
    public State repaidBalance(HttpSession session,String money){

        return null;
    }
}
