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
        // 会话中没有用户时候执行
        if (user != null) {
            // 判断用户等级,为用户才可以查看
            if (user.getLevel() < 3) {
                return true;
            } else {
                request.setAttribute("exception", "用户没有查看的权限");
            }
        } else {
            request.setAttribute("exception", "请先登录");
        }
        exceptionJump.pageJump(request, response);

        // 跳转页面，表示没有登录
        return false;
    }
}
