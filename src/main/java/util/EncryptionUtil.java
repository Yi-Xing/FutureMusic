package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 用于对密码加密
 *
 * @author 5月12号 张易兴创建
 */
public class EncryptionUtil {

    private static final Logger logger = LoggerFactory.getLogger(EncryptionUtil.class);

    /**
     * 用于对指定字符串进行MD5加密
     *
     * @param encryption 需要加密的字符串
     * @return String 返回加密后的字符串
     */
    public static String encryptionMD5(String encryption) {
        // 加密后的字符串
        String afterEncryption = null;
        try {
            //确定计算方法
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            BASE64Encoder base64Encoder = new BASE64Encoder();
            //加密后的字符串
            afterEncryption = base64Encoder.encode(messageDigest.digest(encryption.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            logger.debug(encryption + "加密失败");
            e.printStackTrace();
        }
        return afterEncryption;
    }
}
