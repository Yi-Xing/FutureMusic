package listener;

import entity.User;

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
public class SessionListener implements HttpSessionListener {
    /**
     * 该HashMap以用户名-HttpSession对象存储一个账号只能被一个人登陆的信息。
     */
    public static HashMap<String, HttpSession> sessionMap = new HashMap<>();

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
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
        if (session != null) {
            // 删除单一登录中记录的变量
            String userInformation = "userInformation";
            User user = (User) session.getAttribute(userInformation);
            if (user != null) {
                // 删除集合中的存储的该用户的会话
                SessionListener.sessionMap.remove(user.getMailbox());
            }
        }
    }
}