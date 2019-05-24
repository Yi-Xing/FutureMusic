package util.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理器
 * @author 5月13日 张易兴创建
 */
public class ExceptionResolver implements HandlerExceptionResolver {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        if (e instanceof DataBaseException) {
             logger.debug("数据库异常"+o);
            System.out.println("数据库异常"+o);
            System.out.println("异常信息"+e);
            // 设置跳转的路径
            modelAndView.setViewName("/js/www");
        } else {
            // 补充捕获 ParseException异常
            logger.debug("异常"+o);
            System.out.println("异常"+o);
            System.out.println("异常信息"+e);
            modelAndView.addObject("a", "出异常了");
            // 设置跳转的路径
            modelAndView.setViewName("BackgroundManagementSystem");
        }
        return modelAndView;
    }
}