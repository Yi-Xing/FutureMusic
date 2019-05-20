package controller.music;

import entity.Music;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.TestService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 测试层
 * 用来测试写的
 */
@Controller
public class Test {
    public static void main(String[] args)throws Exception {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newdate = simpleDateFormat.format(date);
        System.out.println(newdate);
    }

}
/**
 *      5.20
 *      1.遇到了bug遍历时只能遍历两条数据
 */