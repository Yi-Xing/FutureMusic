package interceptor;


import entity.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import service.user.LoginService;
import util.CookieUtil;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用于实现自动登录的拦截器
 * @author 5月18肉
 */
@Service
public class LoginInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
    @Resource(name = "LoginService")
    LoginService loginService;
    /**
     * 方法前执行的方法
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取所有的cookie
        Cookie[] cookies = request.getCookies();
        Cookie cookie = CookieUtil.obtainCookie(cookies, "userInformation");
        if (cookie != null) {
            // 得到存储用户账号密码的cookie
            String[] userCookie=cookie.getValue().split("#");
            State state=loginService.login(userCookie[0],userCookie[1],false,response,request.getSession());
            if(state.getState()==1){
                logger.info("邮箱："+userCookie[0]+"自动登录成功");
            }else{
                logger.info("邮箱："+userCookie[0]+"自动登录失败，原因："+state.getInformation());
            }
        }
        return true;
    }
}
