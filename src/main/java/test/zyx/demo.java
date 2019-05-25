package test.zyx;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.Activity;
import entity.Music;
import entity.User;
import mapper.ActivityMapper;
import mapper.MusicMapper;
import mapper.UserMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import util.ConstantUtil;
import util.FileUpload;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class demo {

    @Resource(name = "UserMapper")
    UserMapper userMapper;
    @Resource(name = "FileUpload")
    FileUpload fileUpload;


    @RequestMapping(value = "/demo")
    public String test(String text,HttpServletRequest request) throws IOException {
        System.out.println(text);
        fileUpload.userHeadPortrait(request);
        return null;
    }

    @RequestMapping(value = "/aaaazyx")
    public void aaaazyx() {
        System.out.println(222);
    }

    public static void main(String[] args) {
//        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        System.out.println(sdFormat.format(calendar.getTime()));
//        calendar.add(Calendar.MINUTE, 30);
//        System.out.println(sdFormat.format(calendar.getTime()));
//        System.out.println("2860482971@qq.com".matches("[a-zA-z_0-9]+@[a-zA-z_0-9]{2,6}(\\.[a-zA-z_0-9]{2,3})+"));
//        // 对密码进行加密
//        System.out.println(ConstantUtil.Two_Hundred.getIntValue());
//        System.out.println( EncryptionUtil.encryptionMD5("12346578"));
//        System.out.println( EncryptionUtil.encryptionMD5("12346578"));
        System.out.println("2860482971.0".matches("(^[1-9]\\d*|^0)(\\.\\d{1,2}|)$"));
    }
}
//        List<User> listUser=userMapper.selectUser(new User());
//        //引入分页查询，使用PageHelper分页功能
//        //在查询之前传入当前页，然后多少记录。参数1：第几页，参数2：每页几个
//        PageHelper.startPage(2,3);
//        //startPage后紧跟的这个查询就是分页查询
//        List<User> emps = userMapper.selectUser(new User());
//        //使用PageInfo包装查询结果，只需要将pageInfo交给页面就可以
//        PageInfo pageInfo = new PageInfo<>(emps);
//        System.out.println(pageInfo.getList());
//        return "index";
