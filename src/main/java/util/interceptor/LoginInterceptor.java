package util.interceptor;


import entity.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import service.user.LoginService;
import service.user.SpecialFunctions;
import util.CookieUtil;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 用于实现自动登录的拦截器
 *
 * @author 5月18日
 */
public class LoginInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
    @Resource(name = "LoginService")
    LoginService loginService;
    @Resource(name = "SpecialFunctions")
    SpecialFunctions specialFunctions;

    /**
     * 方法前执行的方法
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.debug("开始判断自动登录");
        HttpSession session = request.getSession();
        // 会话中没有用户时候执行
        if (specialFunctions.getUser(session) == null) {
            //获取所有的cookie
            Cookie[] cookies = request.getCookies();
            Cookie cookie = CookieUtil.obtainCookie(cookies, "userInformation");
            if (cookie != null) {
                // 得到存储用户账号密码的cookie
                String[] userCookie = cookie.getValue().split("#");
                State state = loginService.login(userCookie[0], userCookie[1], false, response, session);
                if (state.getState() == 1) {
                    logger.info("邮箱：" + userCookie[0] + "自动登录成功");
                } else {
                    logger.debug("邮箱：" + userCookie[0] + "自动登录失败，原因：" + state.getInformation());
                }
            }
        }
        return true;
    }
}
