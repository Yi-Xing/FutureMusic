//package util.interceptor;
//
//import java.lang.reflect.Method;
//import java.util.UUID;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//import util.FormToken;
//
///**
// * 用于处理表单重复提交
// * 对标识了@FormToken的方法进行拦截，并且按照相应的规则进行处理
// * @author 张易兴
// */
//public class TokenInterceptor extends HandlerInterceptorAdapter {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (handler instanceof HandlerMethod) {
//            HandlerMethod handlerMethod = (HandlerMethod) handler;
//            Method method = handlerMethod.getMethod();
//            FormToken annotation = method.getAnnotation(FormToken.class);
//            if (annotation != null) {
//                boolean needSaveSession = annotation.save();
//                if (needSaveSession) {
//                    request.getSession(false).setAttribute("formToken", UUID.randomUUID().toString());
//                }
//                boolean needRemoveSession = annotation.remove();
//                if (needRemoveSession) {
//                    if (isRepeatSubmit(request)) {
//                        return false;
//                    }
//                    request.getSession(false).removeAttribute("formToken");
//                }
//            }
//            return true;
//        }
//        return true;
//    }
//
//    private boolean isRepeatSubmit(HttpServletRequest request) {
//        String sessionToken = (String) request.getSession().getAttribute("formToken");
//        if (sessionToken == null) {
//            return true;
//        }
//        String requestToken = request.getParameter("formToken");
//        if (requestToken == null) {
//            return true;
//        }
//        return !sessionToken.equals(requestToken);
//    }
//}