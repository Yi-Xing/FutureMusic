package service.user.administrators;

import entity.Focus;
import mapper.FocusMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.text.ParseException;

@Service(value = "FocusInformationService")
public class FocusInformationService {
    @Resource(name ="FocusMapper")
    FocusMapper focusMapper;
    /**
     * 返回指定用户的粉丝量
     * @param id 条件可以有多个
     */
    public String showFocus(Integer id, Model model) throws ParseException {
        Focus focus=new Focus();
        focus.setUserFocusId(id);
        // 1表示关注
        focus.setUserType(1);
        int focusCount=focusMapper.selectUserFocusCount(focus);
        // 该用户的粉丝量
        model.addAttribute("focusCount",focusCount);
        return null;
    }
}
