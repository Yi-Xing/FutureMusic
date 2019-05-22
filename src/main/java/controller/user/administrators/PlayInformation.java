package controller.user.administrators;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import service.user.administrators.PlayInformationService;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * 查询：指定歌手的所有音乐被播放的次数
 *       指定专辑中的所有音乐被播放的次数
 *
 */
@Controller
public class PlayInformation {
    @Resource(name ="PlayInformationService")
    PlayInformationService playInformationService;

    /**
     * 指定歌手的所有音乐被播放的次数
     * 指定专辑中的所有音乐被播放的次数
     * @param id 用户的id
     */
    @RequestMapping(value = "/showActivity")
    public String showFocus(Integer id, Model model) throws ParseException {
        return focusInformationService.showFocus(id,model);
    }
}
