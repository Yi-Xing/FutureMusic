package test.zyx;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import util.ConstantUtil;
import util.FileUpload;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class Demo {

    Flie flie;
    @Resource(name = "UserMapper")
    UserMapper userMapper;



    @RequestMapping(value = "/test")
    public String test() {
        System.out.println(flie);
//        flie.zyx();
//        System.out.println(text);
//        fileUpload.userHeadPortrait(request);
        System.out.println("啊哈哈哈");
        return null;
    }

    //    @RequestMapping(value = "/a")
    public String aa() {
        return aaaazyx();
    }

    //    @Transactional
    public String aaaazyx() {
        System.out.println("开始");
        b();
        System.out.println("异常准备开始");
        int a = 2 / 0;
        a();
        System.out.println("我执行完了");
        return "index";
    }

    private void a() {
        User user = new User();
        user.setId(1);
        user.setName("我是你");
        userMapper.updateUser(user);
    }

    private void b() {
        User user = new User();
        user.setId(1);
        user.setName("aaaa");
        userMapper.updateUser(user);
    }

    public static void main(String[] args) {

//        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        System.out.println(new Date());
//        System.out.println(sdFormat.format(calendar.getTime()));
//        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
//        System.out.println(sdFormat.format(calendar.getTime()));
//        System.out.println("2860482971@qq.com".matches("[a-zA-z_0-9]+@[a-zA-z_0-9]{2,6}(\\.[a-zA-z_0-9]{2,3})+"));
//        // 对密码进行加密
//        System.out.println(ConstantUtil.Two_Hundred.getIntValue());
//        System.out.println( EncryptionUtil.encryptionMD5("12346578"));
//        System.out.println( EncryptionUtil.encryptionMD5("12346578"));
        System.out.println(calendar.getTime());
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
