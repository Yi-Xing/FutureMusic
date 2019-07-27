package util.music;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileUpload;

import java.io.*;

public class FileUtil {
    private static final String DEFAULT_ENCODING = "GBK";
    private static final int PROTECTED_LENGTH = 51200;
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
    private static String path = "C:\\first\\FutureMusic\\src\\main\\webapp\\static\\file\\musicLyric\\";
//private static String path = "C:\\Users\\Lenovo\\Desktop\\";
    public static String readInfoStream(String fileName) throws Exception {
        String realPath = path + fileName;
        File f = new File(realPath);
        InputStream out = new FileInputStream(f);
        if (out == null) {
            logger.error("输入流为null");
        }
        //字节数组
        byte[] bcache = new byte[2048];
        //每次读取的字节长度
        int readSize = 0;
        //总字节长度
        int totalSize = 0;
        ByteArrayOutputStream infoStream = new ByteArrayOutputStream();
        try {
            //一次性读取2048字节
            while ((readSize = out.read(bcache)) > 0) {
                totalSize += readSize;
                if (totalSize > PROTECTED_LENGTH) {
                    logger.error("输入流超出50K大小限制");
                }
                //将bcache中读取的input数据写入infoStream
                infoStream.write(bcache, 0, readSize);
            }
        } catch (IOException e1) {
            logger.error("输入流读取异常");
        } finally {
            try {
                //输入流关闭
                out.close();
            } catch (IOException e) {
                logger.error("输入流关闭异常");
            }
        }
        try {
            return infoStream.toString(DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            logger.error("输出异常");
        }
        return "读取异常";
    }
}
