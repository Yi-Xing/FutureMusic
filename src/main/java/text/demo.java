package text;

import entity.Activity;
import mapper.ActivityMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class demo {

    @RequestMapping(value = "/demo")
    public String test(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-mvc.xml");
        ActivityMapper activityMapper = applicationContext.getBean(ActivityMapper.class);
        Activity activity=new Activity();
        activity.setId(1);
        System.out.println(activityMapper.selectListActivity(activity));
        System.out.println("执行完了");
        return "222";
    }
}
