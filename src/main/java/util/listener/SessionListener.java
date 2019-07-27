package util.listener;

import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;

/**
 * session监听器
 * 控制一个账号只能在一个地方登录
 *
 * @author 5月12日 张易兴创建
 */
public class SessionListener extends HttpServlet implements HttpSessionListener {
    private static final Logger logger = LoggerFactory.getLogger(SessionListener.class);
    /**
     * 该HashMap以用户名-HttpSession对象存储一个账号只能被一个人登陆的信息。
     */
    public static HashMap<String, HttpSession> sessionMap = new HashMap<>();
    public static HashMap<HttpSession, String> mailboxMap = new HashMap<>();


    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        String sessionId = httpSessionEvent.getSession().getId();
        logger.info("创建session sessionId= " + sessionId);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        //得到被销毁的会话
        HttpSession session = httpSessionEvent.getSession();
        delSession(session);
    }

    /**
     * 删除监听器中存储的指定用户的会话
     *
     * @param session 获得当前会话
     */
    public static synchronized void delSession(HttpSession session) {
        System.out.println(sessionMap);
        if (session != null) {
            System.out.println("执行了" + session.getId());
            System.out.println("执行了" + session);
//            // 删除单一登录中记录的变量
//            Object object = null;
//            try {
//                object = session.getAttribute("userInformation");
//            } catch (Exception e) {
//                logger.debug("object出异常了" + object + "异常信息" + e);
//            }
//            logger.debug("会话userInformation的值" + object);
//            if (object != null) {
//                if (object instanceof User) {
//                    User user = (User) object;
//                    logger.debug(session + "会话" + session.getId() + "中的用户" + user);
//                    // 删除集合中的存储的该用户的会话
//                    // 退出登录将信息从域对象中删除
//                    logger.debug("将" + user.getMailbox() + "用户从会话中删除");
//                }
//            }
            logger.debug("开始删除sessionMap");
            SessionListener.sessionMap.remove(SessionListener.mailboxMap.get(session));
            logger.debug("开始删除mailboxMap");
            SessionListener.mailboxMap.remove(session);
            session.removeAttribute("userInformation");
            System.out.println("删除完毕");
        }
    }

    public static synchronized void forceUserLogout(String userMail) {
        logger.debug("判断" + userMail + "用户是否已存在在会话中");
        HttpSession session = sessionMap.get(userMail);
        if (session != null) {
            System.out.println("删除会话" + session.getId());
            System.out.println("删除会话" + session);
            delSession(session);
//            session.invalidate()
        }
    }

}