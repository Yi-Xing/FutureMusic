package util;


import javax.servlet.http.Cookie;

/**
 * Cookie的工具类
 *
 * @author 5月12号 张易兴创建
 *          5.20 蒋靓峣加了个方法
 */
public class CookieUtil {
    /**
     * 根据指定的name返回指定的Cookie没有返回空
     *
     * @param cookies 所有的cookie
     * @param name    需要得到的cookie的名字
     * @return Cookie 返回指定名字的cookie没有返回空
     */
    public static Cookie obtainCookie(Cookie[] cookies, String name) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }

}
