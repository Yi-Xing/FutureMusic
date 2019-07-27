package util.interceptor;

import entity.User;
import org.springframework.web.servlet.HandlerInterceptor;
import service.user.SpecialFunctions;
import util.exception.ExceptionJump;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author HP
 */
public class UserInterceptor implements HandlerInterceptor {
    @Resource(name = "SpecialFunctions")
    SpecialFunctions specialFunctions;
    @Resource(name = "ExceptionJump")
    ExceptionJump exceptionJump;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = specialFunctions.getUser(session);
        //
        if (user != null) {
            return true;
        } else {
            // 会话中没有用户时候执行
            request.setAttribute("exception", "请先登录");
        }
        // 跳转页面，表示没有登录
        exceptionJump.pageJump(request, response);
        return false;
    }
}
