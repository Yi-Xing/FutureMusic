package service.user.administrators;

import entity.Focus;
import mapper.FocusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import service.user.IdExistence;

import javax.annotation.Resource;

/**
 * 关注和访客
 *
 * @author 5月22日 张易兴创建
 */
@Service(value = "FocusInformationService")
public class FocusInformationService {
    private static final Logger logger = LoggerFactory.getLogger(ActivityInformationService.class);
    @Resource(name = "FocusMapper")
    FocusMapper focusMapper;
    @Resource(name = "IdExistence")
    IdExistence idExistence;

    /**
     * 返回指定用户的粉丝量
     *
     * @param id 条件可以有多个
     */
    public String showFocus(Integer id, Model model) {
        Focus focus = new Focus();
        if (idExistence.isUserId(id) != null) {
            focus.setUserFocusId(id);
            // 1表示关注
            focus.setUserType(1);
            int focusCount = focusMapper.selectUserFocusCount(focus);
            // 该用户的粉丝量
            model.addAttribute("focusCount", focusCount);
        } else {
            model.addAttribute("state", "id：" + id + "的用户不存在");
        }
        return null;
    }
}
