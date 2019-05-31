package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用于错误页面跳转
 * @author HP
 */
@Controller
public class View {
    /**
     * 用于跳转所有的404页面
     */
    @RequestMapping(value = "/noFind")
    public String noFind() {
        return "noFind";
    }

    /**
     * 500的页面的跳转
     */
    @RequestMapping(value = "/collapse")
    public String collapse() {
        return "collapse";
    }
}
