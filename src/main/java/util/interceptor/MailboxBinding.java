package util.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import service.user.SpecialFunctions;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 用于实现安全中心账号验证
 *
 * @author 5月18日
 */
public class MailboxBinding implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(MailboxBinding.class);
    @Resource(name = "SpecialFunctions")
    SpecialFunctions specialFunctions;

    /**
     * 方法前执行的方法
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        HttpSession session = request.getSession();
        // 会话中没有邮箱时候执行
        logger.debug("开始验证用户是否绑定邮箱");
        if (specialFunctions.getUserMailbox(session) == null) {
            logger.debug("用户没有绑定邮箱");
            response.sendRedirect("/safetyCenterPage");
            return false;
        }
        return true;
    }
}
