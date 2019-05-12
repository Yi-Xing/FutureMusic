package Listener;

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
     * @param session 获得当前会话
     */
    private synchronized void delSession(HttpSession session) {
        if (session != null) {
            // 删除单一登录中记录的变量
            String userInformation = "userInformation";
            if (session.getAttribute(userInformation) != null) {
                User user = (User) session.getAttribute(userInformation);
                // 删除集合中的存储的该用户的会话
                SessionListener.sessionMap.remove(user.getMailbox());
            }
        }
    }

    /**
     * 关掉指定会话
     *
     * @param mailbox 需要删除会话的键
     */
    public static void forceUserLogout(String mailbox) {
        // 根据用户的邮箱号获得该邮箱号所在的会话
        HttpSession session = SessionListener.sessionMap.get(mailbox);
        // 再次验证session中是否有该用户
        if (session != null) {
            // 强制关掉会话，并删除会话上所有的绑定对象,会话被销毁后，执行监听器的销毁方法
            session.invalidate();
        }
    }
}