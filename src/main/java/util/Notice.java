package util;

import controller.user.LoginAndRegister;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service(value = "Notice")
public class Notice {
    private static final Logger logger = LoggerFactory.getLogger(Notice.class);
    // 环绕通知
    public Object surround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String name=proceedingJoinPoint.getSignature().getName();
        // 方法前
        logger.trace("方法"+name+"开始执行");
        Object object=proceedingJoinPoint.proceed();
        // 方法后
        logger.trace("方法"+name+"执行完毕");
        return object;
    }
}
