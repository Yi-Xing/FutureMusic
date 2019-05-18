package util;

import java.io.File;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * @author 蒋靓峣 5.17创建
 * 上传文件的工具类
 */
public class UploadUtil {
    /**
     *
     * @param request
     * @param response 传入请求信息
     * @param fields 需要获取解析结果的所有属性的key
     * @param limit 限制文件的类型不用加.这个可以弄好几个限制
     * @param path  文件上传路径
     * @return int 成功1，失败0，系统异常-1
     * @throws IOException
     */
    public static int doUpload(HttpServletRequest request, HttpServletResponse response ,String[] fields,String limit,String path) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "text/html;charset=UTF-8");
        response.setContentType("text/html;charsetr=UTF-8");
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        DiskFileItemFactory factory = new DiskFileItemFactory();
        int state = 0;
        if (isMultipart) {
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setHeaderEncoding("UTF-8");
            try {
                final long MAX_SIZE = 102400 * 102400;
                factory.setSizeThreshold(1024 * 1024);
                upload.setSizeMax(MAX_SIZE);
                List<FileItem> items = upload.parseRequest(request);
                Iterator<FileItem> iter = items.iterator();
                while (iter.hasNext()) {
                    FileItem item = iter.next();
                    if (item.isFormField()) {
                        parseItem(item, fields);
                    } else {
                        state = upload(item, limit, path);
                    }
                }
                return state;
            } catch (FileUploadBase.SizeLimitExceededException e) {
                e.printStackTrace();
                return -1;
            } catch (FileUploadException e) {
                e.printStackTrace();
                return -1;
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }
        return state;
    }

    /**
     *
     * @param item  获取表单中要传输的内容
     * @param limit 要传输的格式限制一下
     * @param path 文件路径
     *
     * @return int 判断上传状态
     */
    public static int upload(FileItem item,String limit,String path) {
        String fileName = item.getName();
        String ext = fileName.substring(fileName.indexOf(".")+1);
        if(!ext.equals(limit)){
            System.out.println("文件类型上传错误，只能上传."+limit+"格式的文件");
            return 0;
        }else {
            File file = new File(path,fileName);
            try {
                item.write(file);
                return 1;
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }
    }
    /**
     * @param item
     * @return String[] 解析表单的内容得到的结果、一个字符串数组
     */
    public static String[] parseItem(FileItem item,String[] fileds){
        int judge = 0;
        String[] values = new String[fileds.length];
        for(int i = 0;i<fileds.length;i++) {
            if (judge >= fileds.length - 1) {
                break;
            }
            if (item.getFieldName().equals(fileds[i])) {
                values[judge] = item.getFieldName();
                judge += 1;
            }
        }
        return values;
    }
}
