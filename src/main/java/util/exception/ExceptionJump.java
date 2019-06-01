package util.exception;

import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author HP
 */
@Service(value = "ExceptionJump")
public class ExceptionJump {

    /**
     * 用于显示错误页面
     */
    public void pageJump(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/collapse").forward(request,response);
    }
}
