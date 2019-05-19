package util.exception;

/**
 * 用于存放数据库的异常
 * @author 5月13日 张易兴创建
 */
public class DataBaseException extends Exception{
    public DataBaseException() {
    }
    public DataBaseException(String message) {
        super(message);
    }
}
