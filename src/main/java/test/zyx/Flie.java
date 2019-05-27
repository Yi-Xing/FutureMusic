package test.zyx;

import entity.User;
import mapper.UserMapper;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

@Service(value = "Flie")
public class Flie {
    @Resource(name = "UserMapper")
    UserMapper userMapper;
    @RequestMapping(value = "/a")
    public String aa(){
        return zyx();
    }
    public String zyx() {
        System.out.println("开始");
        a();
        System.out.println("异常准备开始");
        int a=2/0;
        b();
//        System.out.println("我执行完了");
        return "index";
    }
    private void a(){
        User user=new User();
        user.setId(1);
        user.setName("我是你");
        userMapper.updateUser(user);
    }
    private void b(){
        User user=new User();
        user.setId(1);
        user.setName("aaaa");
        userMapper.updateUser(user);
    }




//    @RequestMapping(value = "/aaa")
//    public String aaa(HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {
//        //创建一个通用的多部分解析器
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//        //判断 request 是否有文件上传,即多部分请求
//        if(multipartResolver.isMultipart(request)){
//            //转换成多部分request
//            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
//            //取得request中的所有文件名
//            Iterator<String> iter = multiRequest.getFileNames();
//            while(iter.hasNext()){
//                //记录上传过程起始时的时间，用来计算上传时间
//                int pre = (int) System.currentTimeMillis();
//                //取得上传文件
//                MultipartFile file = multiRequest.getFile(iter.next());
//                if(file != null){
//                    //取得当前上传文件的文件名称
//                    String myFileName = file.getOriginalFilename();
//                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在
//                    if(myFileName.trim() !=""){
//                        System.out.println(myFileName);
//                        //重命名上传后的文件名
//                        String fileName = "demoUpload" + file.getOriginalFilename();
//                        //定义上传路径
//                        String path = "F:/" + fileName;
//                        File localFile = new File(path);
//                        file.transferTo(localFile);
//                    }
//                }
//                //记录上传该文件后的时间
//                int finaltime = (int) System.currentTimeMillis();
//                System.out.println(finaltime - pre);
//            }
//
//        }
//
//        return "index";
//    }
}
