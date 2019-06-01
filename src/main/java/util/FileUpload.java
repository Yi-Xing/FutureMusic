package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * 用于实现各种文件上传
 *
 * @author 张易兴  5.24 创建
 */
@Service(value = "FileUpload")
public class FileUpload {
    private static final Logger logger = LoggerFactory.getLogger(FileUpload.class);

    /**
     * 用于上传用户头像
     *
     * @return 返回该用户头像的路径
     */
    public String userHeadPortrait(HttpServletRequest request) throws IOException {
        return "/static/file/userHeadPortrait/"+fileUpload(request, "/static/file/userHeadPortrait/");
    }

    /**
     * 用于上传音乐
     *
     * @return 返回该音乐的路径
     */
    public String music(HttpServletRequest request) throws IOException {
        return fileUpload(request, "/static/file/music/");
    }

    /**
     * 用于上传音乐的歌词
     *
     * @return 返回该音乐的歌词的路径
     */
    public String musicLyric(HttpServletRequest request) throws IOException {
        return fileUpload(request, "/static/file/musicLyric/");
    }

    /**
     * 用于上传MV
     *
     * @return 返回该MV的路径
     */
    public String musicVideo(HttpServletRequest request) throws IOException {
        return fileUpload(request, "/static/file/musicVideo/");
    }

    /**
     * 用于歌单和专辑的封面图片
     *
     * @return 返回该用户图片的路径
     */
    public String songList(HttpServletRequest request) throws IOException {
        return fileUpload(request, "/static/file/songList/");
    }

    /**
     * 用于活动的封面图片
     *
     * @return 返回该活动图片的路径
     */
    public String activityPicture(HttpServletRequest request) throws IOException {
        return fileUpload(request, "/static/file/activityPicture/");
    }

    public String fileUpload(HttpServletRequest request, String uploadPath) throws IllegalStateException, IOException {
        String fileName = null;
        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        logger.debug("multipartResolver"+multipartResolver.isMultipart(request));
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                //取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    //取得当前上传文件的文件名称
                    fileName = file.getOriginalFilename();
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    if (fileName != null) {
                        if (!"".equals(fileName.trim())) {
                            logger.trace("上传的文件名为：" + fileName);
                            // 先得到路径，判断文件是否存在
                            String path = "C:/first/FutureMusic/src/main/webapp" + uploadPath;
                            File filePath = new File(path);
                            // 判断文件夹是否存在 不存在这创建
                            if (!filePath.exists()) {
                                logger.trace("文件夹不存在，开始创建文件：" + filePath.mkdirs());
                            }
                            // 得到文件的后缀,然后重命名上传后的文件名
                            fileName = System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("\\") + 1);
                            //定义上传路径
                            path = path + fileName;
                            File localFile = new File(path);
                            file.transferTo(localFile);
                        }
                    }
                }
            }
        }
        return fileName;
    }

    /**
     * 删除指定路径的文件
     */
    public boolean deleteFile(String path) {
        path = "C:/first/FutureMusic/src/main/webapp" + path;
        return new File(path).delete();
    }
}
