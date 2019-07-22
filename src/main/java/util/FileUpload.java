package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
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
     * 用于上传音乐的图片
     */
    public String musicPicture(MultipartFile file) {
        if(file==null){
            return null;
        }
        // 得到文件名
        String fileName = file.getOriginalFilename();
        // 没有名称表示不存在
        if (fileName != null) {
            if (fileName.contains(".")) {
                // 将后缀变为大写
                String suffix = fileName.split("\\.")[1].toUpperCase();
                // 先判断文件的后缀
                if ("JPG".equals(suffix) || "PNG".equals(suffix) || "JPEG".equals(suffix)) {
                    // 在判断文件大小
                    if (file.getSize() <= 102000000) {
                        try {
                            BufferedImage image = ImageIO.read(file.getInputStream());
                            //如果image=null 表示上传的不是图片格式
                            if (image != null) {
                                // 再判断图片的高度和宽度是否符合要求（单位为px）
                                if (image.getWidth() == 1600 && image.getHeight() == 750) {
                                    // 然后存入音乐路径中，返回数据库路径
                                    return fileUpload(file, "/static/file/musicPicture/", suffix);
                                } else {
                                    logger.debug(fileName + "图片长宽不符合要求");
                                }
                            } else {
                                logger.debug(fileName + "的文件不是图片");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            logger.debug(fileName + "的文件不是图片");
                        }
                    } else {
                        logger.debug(fileName + "的文件大小不符合要求");
                    }
                } else {
                    logger.debug(fileName + "的文件后缀不符合要求");
                }
            }
        } else {
            logger.debug("文件不存在");
        }
        return null;
    }

    /**
     * 用于上传音乐
     *
     * @return 返回该音乐的路径
     */
    public String music(MultipartFile file) {
        if(file==null){
            return null;
        }
        // 得到文件名
        String fileName = file.getOriginalFilename();
        // 没有名称表示不存在
        if (fileName != null) {
            if (fileName.contains(".")) {
                // 将后缀变为大写
                String suffix = fileName.split("\\.")[1].toUpperCase();
                // 先判断文件的后缀
                if ("MP3".equals(suffix) || "MP4".equals(suffix) || "MP5".equals(suffix)) {
                    // 在判断文件大小
                    if (file.getSize() <= 100002000) {
                        try {
                            return fileUpload(file, "/static/file/music/", suffix);
                        } catch (IOException e) {
                            e.printStackTrace();
                            logger.debug(fileName + "的文件上传失败");
                        }
                    } else {
                        logger.debug(fileName + "的文件大小不符合要求");
                    }
                } else {
                    logger.debug(fileName + "的文件后缀不符合要求");
                }
            }
        } else {
            logger.debug("文件不存在");
        }
        return null;
    }

    /**
     * 上传音乐歌词
     */
    public String musicLyric(MultipartFile file) {
        if(file==null){
            return null;
        }
        // 得到文件名
        String fileName = file.getOriginalFilename();
        // 没有名称表示不存在
        if (fileName != null) {
            if (fileName.contains(".")) {
                // 将后缀变为大写
                String suffix = fileName.split("\\.")[1].toUpperCase();
                // 先判断文件的后缀
                if ("MP31".equals(suffix) || "MP43".equals(suffix) || "MP25".equals(suffix)) {
                    // 在判断文件大小
                    if (file.getSize() <= 100030000) {
                        try {
                            return fileUpload(file, "/static/file/musicLyric/", suffix);
                        } catch (IOException e) {
                            e.printStackTrace();
                            logger.debug(fileName + "的文件上传失败");
                        }
                    } else {
                        logger.debug(fileName + "的文件大小不符合要求");
                    }
                } else {
                    logger.debug(fileName + "的文件后缀不符合要求");
                }
            }
        } else {
            logger.debug("文件不存在");
        }
        return null;
    }

    /**
     * 用于上传用户头像
     *
     * @return 返回该用户头像的路径
     */
    public String userHeadPortrait(MultipartFile file) {
        if(file==null){
            return null;
        }
        // 得到文件名
        String fileName = file.getOriginalFilename();
        // 没有名称表示不存在
        if (fileName != null) {
            if (fileName.contains(".")) {
                // 将后缀变为大写
                String suffix = fileName.split("\\.")[1].toUpperCase();
                // 先判断文件的后缀
                if ("JPG".equals(suffix) || "PNG".equals(suffix) || "JPEG".equals(suffix)) {
                    // 在判断文件大小
                    if (file.getSize() <= 10000000) {
                        try {
                            BufferedImage image = ImageIO.read(file.getInputStream());
                            //如果image=null 表示上传的不是图片格式
                            if (image != null) {
                                // 再判断图片的高度和宽度是否符合要求（单位为px）
                                if (image.getWidth() == 1600 && image.getHeight() == 750) {
                                    // 然后存入音乐路径中，返回数据库路径
                                    return fileUpload(file, "/static/file/userHeadPortrait/", suffix);
                                } else {
                                    logger.debug(fileName + "图片长宽不符合要求");
                                }
                            } else {
                                logger.debug(fileName + "的文件不是图片");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            logger.debug(fileName + "的文件不是图片");
                        }
                    } else {
                        logger.debug(fileName + "的文件大小不符合要求");
                    }
                } else {
                    logger.debug(fileName + "的文件后缀不符合要求");
                }
            }
        } else {
            logger.debug("文件不存在");
        }
        return null;
    }

    /**
     * 用于上传MV
     *
     * @return 返回该MV的路径
     */
    public String musicVideo(MultipartFile file) {
        if(file==null){
            return null;
        }
        // 得到文件名
        String fileName = file.getOriginalFilename();
        // 没有名称表示不存在
        if (fileName != null) {
            if (fileName.contains(".")) {
                // 将后缀变为大写
                String suffix = fileName.split("\\.")[1].toUpperCase();
                // 先判断文件的后缀
                if ("MP31".equals(suffix) || "MP43".equals(suffix) || "MP25".equals(suffix)) {
                    // 在判断文件大小
                    if (file.getSize() <= 100030000) {
                        try {
                            return fileUpload(file, "/static/file/musicVideo/", suffix);
                        } catch (IOException e) {
                            e.printStackTrace();
                            logger.debug(fileName + "的文件上传失败");
                        }
                    } else {
                        logger.debug(fileName + "的文件大小不符合要求");
                    }
                } else {
                    logger.debug(fileName + "的文件后缀不符合要求");
                }
            }
        } else {
            logger.debug("文件不存在");
        }
        return null;
    }

    /**
     * 用于上传MV的图片
     */
    public String musicVideoPicure(MultipartFile file) {
        if(file==null){
            return null;
        }
        // 得到文件名
        String fileName = file.getOriginalFilename();
        // 没有名称表示不存在
        if (fileName != null) {
            if (fileName.contains(".")) {
                // 将后缀变为大写
                String suffix = fileName.split("\\.")[1].toUpperCase();
                // 先判断文件的后缀
                if ("JPG".equals(suffix) || "PNG".equals(suffix) || "JPEG".equals(suffix)) {
                    // 在判断文件大小
                    if (file.getSize() <= 10000000) {
                        try {
                            BufferedImage image = ImageIO.read(file.getInputStream());
                            //如果image=null 表示上传的不是图片格式
                            if (image != null) {
                                // 再判断图片的高度和宽度是否符合要求（单位为px）
                                if (image.getWidth() == 1600 && image.getHeight() == 750) {
                                    // 然后存入音乐路径中，返回数据库路径
                                    return fileUpload(file, "/static/file/musicVideoPicture/", suffix);
                                } else {
                                    logger.debug(fileName + "图片长宽不符合要求");
                                }
                            } else {
                                logger.debug(fileName + "的文件不是图片");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            logger.debug(fileName + "的文件不是图片");
                        }
                    } else {
                        logger.debug(fileName + "的文件大小不符合要求");
                    }
                } else {
                    logger.debug(fileName + "的文件后缀不符合要求");
                }
            }
        } else {
            logger.debug("文件不存在");
        }
        return null;
    }

    /**
     * 用于歌单和专辑的封面图片
     *
     * @return 返回该用户图片的路径
     */
    public String songList(MultipartFile file) {
        if(file==null){
            return null;
        }
        // 得到文件名
        String fileName = file.getOriginalFilename();
        // 没有名称表示不存在
        if (fileName != null) {
            if (fileName.contains(".")) {
                // 将后缀变为大写
                String suffix = fileName.split("\\.")[1].toUpperCase();
                // 先判断文件的后缀
                if ("JPG".equals(suffix) || "PNG".equals(suffix) || "JPEG".equals(suffix)) {
                    // 在判断文件大小
                    if (file.getSize() <= 10000000) {
                        try {
                            BufferedImage image = ImageIO.read(file.getInputStream());
                            //如果image=null 表示上传的不是图片格式
                            if (image != null) {
                                // 再判断图片的高度和宽度是否符合要求（单位为px）
                                if (image.getWidth() == 1600 && image.getHeight() == 750) {
                                    // 然后存入音乐路径中，返回数据库路径
                                    return fileUpload(file, "/static/file/songList/", suffix);
                                } else {
                                    logger.debug(fileName + "图片长宽不符合要求");
                                }
                            } else {
                                logger.debug(fileName + "的文件不是图片");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            logger.debug(fileName + "的文件不是图片");
                        }
                    } else {
                        logger.debug(fileName + "的文件大小不符合要求");
                    }
                } else {
                    logger.debug(fileName + "的文件后缀不符合要求");
                }
            }
        } else {
            logger.debug("文件不存在");
        }
        return null;
    }

    /**
     * 用于活动的封面图片
     *
     * @return 返回该活动图片的路径
     */
    public String activityPicture(MultipartFile file) {
        if(file==null){
            return null;
        }
        // 得到文件名
        String fileName = file.getOriginalFilename();
        // 没有名称表示不存在
        if (fileName != null) {
            if (fileName.contains(".")) {
                // 将后缀变为大写
                String suffix = fileName.split("\\.")[1].toUpperCase();
                // 先判断文件的后缀
                if ("JPG".equals(suffix) || "PNG".equals(suffix) || "JPEG".equals(suffix)) {
                    // 在判断文件大小
                    if (file.getSize() <= 10000000) {
                        try {
                            BufferedImage image = ImageIO.read(file.getInputStream());
                            //如果image=null 表示上传的不是图片格式
                            if (image != null) {
                                // 再判断图片的高度和宽度是否符合要求（单位为px）
                                if (image.getWidth() == 1600 && image.getHeight() == 750) {
                                    // 然后存入音乐路径中，返回数据库路径
                                    return fileUpload(file, "/static/file/activityPicture/", suffix);
                                } else {
                                    logger.debug(fileName + "图片长宽不符合要求");
                                }
                            } else {
                                logger.debug(fileName + "的文件不是图片");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            logger.debug(fileName + "的文件不是图片");
                        }
                    } else {
                        logger.debug(fileName + "的文件大小不符合要求");
                    }
                } else {
                    logger.debug(fileName + "的文件后缀不符合要求");
                }
            }
        } else {
            logger.debug("文件不存在");
        }
        return null;
    }


    /**
     * 将给定request对象，返回request中,指定名称的MultipartFile对象
     */
    public MultipartFile getMultipartFile(HttpServletRequest request, String name) {
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //获取multiRequest 中所有的文件名
            return multiRequest.getFile(name);
        }
        return null;
    }


    /**
     * 给定 MultipartFile对象和文件将要保存的路径
     * 返回在数据库存储的路径名称
     *
     * @param file       存储文件的类
     * @param uploadPath file文件的存放路径
     * @param suffix     文件的后缀
     */
    private String fileUpload(MultipartFile file, String uploadPath, String suffix) throws IOException {
        // 先得到路径，判断文件是否存在
        File filePath = new File(uploadPath);
        // 判断文件夹是否存在 不存在这创建
        if (!filePath.exists()) {
            logger.trace("文件夹不存在，开始创建文件：" + filePath.mkdirs());
        }
        // 路径加上动态生成的文件名和文件的后缀
        uploadPath = uploadPath + System.currentTimeMillis() + "." + suffix;
        // 生成存放在硬盘的路径
        File localFile = new File("C:/first/FutureMusic/src/main/webapp" + uploadPath);
        // 将文件内容写到封装好的File文件中
        file.transferTo(localFile);
        return uploadPath;
    }


    /**
     * 删除指定路径的文件
     */
    public void deleteFile(String path) {
        // 得到原来的文件地址，并将其删除.
        if (path != null && !"".equals(path)) {
            // 存在则删除
            if (new File("C:/first/FutureMusic/src/main/webapp" + path).delete()) {
                logger.debug(path + "文件删除成功");
            } else {
                logger.debug(path + "文件删除失败");
            }
        }
    }


}
