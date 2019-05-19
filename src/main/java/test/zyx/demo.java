package test.zyx;

import entity.Activity;
import entity.User;
import mapper.ActivityMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import util.ConstantUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Controller
public class demo {


    @RequestMapping(value = "/demo")
    public String test() {
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-mvc.xml");
//        ActivityMapper activityMapper = applicationContext.getBean(ActivityMapper.class);
//        Activity activity=new Activity();
//        activity.setId(1);
//        System.out.println(activityMapper.selectListActivity(activity));
        System.out.println("执行完了");
//        ((User)null).getMailbox();
        return "index.jsp";
    }

    @RequestMapping(value = "/aaaazyx")
    public void aaaazyx() {
        System.out.println(222);
    }

    public static void main(String[] args) {
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        System.out.println(sdFormat.format(calendar.getTime()));
        calendar.add(Calendar.MINUTE, 30);
        System.out.println(sdFormat.format(calendar.getTime()));
        System.out.println("2860482971@qq.com".matches("[a-zA-z_0-9]+@[a-zA-z_0-9]{2,6}(\\.[a-zA-z_0-9]{2,3})+"));
        // 对密码进行加密
        System.out.println(ConstantUtil.Two_Hundred.getIntValue());
//        System.out.println( EncryptionUtil.encryptionMD5("12346578"));
//        System.out.println( EncryptionUtil.encryptionMD5("12346578"));

    }
}
