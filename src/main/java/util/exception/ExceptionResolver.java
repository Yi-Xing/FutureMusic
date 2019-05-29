package util.exception;

import com.alipay.api.AlipayApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 异常处理器
 *
 * @author 5月13日 张易兴创建
 */
public class ExceptionResolver implements HandlerExceptionResolver {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        if (e instanceof DataBaseException) {
            // DataBaseException 数据库异常
            logger.debug("数据库异常" + o);
            modelAndView.addObject("Exception", "出异常了");
        } else if (e instanceof IOException) {
            // IOException异常  文件上传下载
            logger.debug("文件上传下载异常" + o);
            modelAndView.addObject("Exception", "出异常了");
        } else if (e instanceof ParseException) {
            //ParseException  字符串转换日期异常
            logger.debug("字符串转换日期异常" + o);
            modelAndView.addObject("Exception", "出异常了");
        } else if (e instanceof AlipayApiException) {
            //AlipayApiException 阿里巴巴的支付异常
            logger.debug("阿里巴巴的支付异常异常" + o);
            modelAndView.addObject("Exception", "出异常了");
        } else {
            // 其他异常
            logger.debug("其他异常" + o);
            modelAndView.addObject("Exception", "出异常了");
        }
        modelAndView.setViewName("BackgroundManagementSystem");
        return modelAndView;
    }
}