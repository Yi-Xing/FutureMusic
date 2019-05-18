package util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;

/**
 * @params request  response fileName  fileSourcePath
 *
 * @return
 */
    public class DownLoadUtil {
    /**
     *
     * @param request
     * @param response
     * @param fileName  传入文件的名字
     * @param fileSourcePath 文件资源的地址目录
     */
    public static void download(HttpServletRequest request, HttpServletResponse response,String fileName,String fileSourcePath){
        InputStream in = null;
        ServletOutputStream out = null;
        try {
            request.setCharacterEncoding("utf-8");
            //下载文件需要设置响应头 下载文件的类型，
            response.addHeader("content-Type", "application/octet-stream");
            //二进制下载文件类型
            //获取客户端的user-agent处理
            System.out.println(fileName);
            //如果有,不等于-1
            response.addHeader("content-Disposition", "attachement;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            //包含后缀
            //servlet通过文件地址，将文件转为输入流，读到servlet中
            in = new FileInputStream((new File(fileSourcePath)));
            //通过输出流将刚才已转为的输入流的文件 输出给用户
            out = response.getOutputStream();
            //循环
            byte[] bytes = new byte[1024];
            int len = -1;
            //读完是-1
            while ((len = in.read(bytes)) != -1) {
                out.write(bytes, 0, len);
            }
            out.close();
            in.close();
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try{
                out.close();
                in.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
