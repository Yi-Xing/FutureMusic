package controller.user.administrators;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/administrators")
public class HomePage {
    /**
     * 显示首页
     *
     */
    @RequestMapping(value = "/showHomePage")
    public String showHomePage(Model model) {
        model.addAttribute("page","homePage");
        return "system/backgroundSystem";
    }
}
