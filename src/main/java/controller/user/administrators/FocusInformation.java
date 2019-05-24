package controller.user.administrators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import service.user.administrators.ActivityInformationService;
import service.user.administrators.FocusInformationService;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * 访客或关注：
 *
 * 查询：根据用户id查找，可查找该用户的关注量和粉丝量
 *
 * @author 5月22日 张易兴创建
 */
@Controller
public class FocusInformation {
    private static final Logger logger = LoggerFactory.getLogger(ActivityInformationService.class);
    @Resource(name = "FocusInformationService")
    FocusInformationService focusInformationService;

    /**
     * 返回指定用户的粉丝量
     * @param id 用户的id
     */
    @RequestMapping(value = "/showFocus")
    public String showFocus(Integer id, Model model) {
        return focusInformationService.showFocus(id,model);
    }
}
