package exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用于存放数据库的异常
 * @author 5月13日 张易兴创建
 */
public class DataBaseException extends Exception{
    public DataBaseException() {
    }
    public DataBaseException(String message) {
        super(message);
    }
}
