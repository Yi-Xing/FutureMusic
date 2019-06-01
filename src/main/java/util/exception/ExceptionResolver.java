package util.exception;

import com.alipay.api.AlipayApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 异常处理器
 *
 * @author 5月13日 张易兴创建
 */
@Component
public class ExceptionResolver implements HandlerExceptionResolver {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        if (e instanceof DataBaseException) {
            // DataBaseException 数据库异常
            logger.debug("数据库异常" + o);
            logger.debug("数据库异常" + e);
            modelAndView.addObject("exception", "数据库异常");
        } else if (e instanceof IOException) {
            // IOException异常  文件上传下载
            logger.debug("文件上传下载异常" + o);
            logger.debug("文件上传下载异常" + e);
            modelAndView.addObject("exception", "可能是文件上传下载异常");
        } else if (e instanceof ParseException) {
            //ParseException  字符串转换日期异常
            logger.debug("字符串转换日期异常" + o);
            logger.debug("字符串转换日期异常" + e);
            modelAndView.addObject("exception", "可能是字符串转换日期异常");
        } else if (e instanceof AlipayApiException) {
            //AlipayApiException 阿里巴巴的支付异常
            logger.debug("阿里巴巴的支付异常异常" + o);
            logger.debug("阿里巴巴的支付异常异常" + e);
            modelAndView.addObject("exception", "阿里巴巴的支付异常异常");
        } else if (e instanceof ServletException) {
            // ServletException 页面跳转异常
            logger.debug("页面跳转异常" + o);
            logger.debug("页面跳转异常" + e);
            modelAndView.addObject("exception", "可能是页面跳转异常");
        } else {
            // 其他异常
            logger.debug("其他异常" + o);
            logger.debug("其他异常" + e);
            modelAndView.addObject("exception2", "其他异常");
//            httpServletResponse.setStatus(300)
        }
        modelAndView.setViewName("collapse");
        return modelAndView;
    }
}