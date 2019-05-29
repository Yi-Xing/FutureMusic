package util.interceptor;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.SameUrlData;

//import com.thinkgem.jeesite.common.mapper.JsonMapper;

/**
 * 一个用户 相同url 同时提交 相同数据 验证
 * 主要通过 session中保存到的url 和 请求参数。如果和上次相同，则是重复提交表单
 *
 * @author Administrator
 */
public class SameUrlDataInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SameUrlDataInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            SameUrlData annotation = method.getAnnotation(SameUrlData.class);
            if (annotation != null) {
                logger.debug("开始判断数据是否相同");
                //如果重复相同数据
                if (repeatDataValidator(request)) {
                    logger.debug("唉~数据相同，老子不让你过去");
                    return false;
                } else {
                    logger.debug("走你┏ (゜ω゜)=☞");
                    return true;
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    /**
     * 验证同一个url数据是否相同提交  ,相同返回true
     */
    private boolean repeatDataValidator(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        String params = new ObjectMapper().writeValueAsString(httpServletRequest.getParameterMap());
        String url = httpServletRequest.getRequestURI();
        Map<String, String> map = new HashMap<>(16);
        map.put(url, params);
        String nowUrlParams = map.toString();
        Object preUrlParams = httpServletRequest.getSession().getAttribute("repeatData");
        //如果上一个数据为null,表示还没有访问页面
        if (preUrlParams == null) {
            httpServletRequest.getSession().setAttribute("repeatData", nowUrlParams);
            return false;
            //否则，已经访问过页面
        } else {
            //如果上次url+数据和本次url+数据相同，则表示城府添加数据
            if (preUrlParams.toString().equals(nowUrlParams)) {
                return true;
            } else {
                //如果上次 url+数据 和本次url加数据不同，则不是重复提交
                httpServletRequest.getSession().setAttribute("repeatData", nowUrlParams);
                return false;
            }
        }
    }
}