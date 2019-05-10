package controller;

import entity.Activity;
import entity.Classification;
import entity.User;
import mapper.ActivityMapper;
import mapper.ClassificationMapper;
import mapper.UserMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Demo {
    @RequestMapping(value = "/demo")
    public void lookup() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-mvc.xml");
        ClassificationMapper classificationMapper = applicationContext.getBean(ClassificationMapper.class);
        System.out.println("执行了");
        Classification classification=new Classification();
        System.out.println(classificationMapper.selectListClassification(classification));
        System.out.println(122);
    }
}
