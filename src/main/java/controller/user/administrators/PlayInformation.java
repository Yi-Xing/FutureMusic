package controller.user.administrators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import service.user.administrators.ActivityInformationService;
import service.user.administrators.PlayInformationService;

import javax.annotation.Resource;

/**
 * 查询：指定音乐或MV被播放的次数
 *       指定专辑中的所有音乐被播放的次数
 * @author 5月22日 张易兴创建
 */
@Controller
public class PlayInformation {
    private static final Logger logger = LoggerFactory.getLogger(ActivityInformationService.class);
    @Resource(name ="PlayInformationService")
    PlayInformationService playInformationService;

    /**
     * 指定歌手的所有音乐被播放的次数
     * 指定专辑中的所有音乐被播放的次数
     * @param id 音乐或MV或专辑的id
     * @param type 1、音乐  2、MV  3、专辑
     */
    @RequestMapping(value = "/showPlay")
    public String showPlay(Integer id,Integer type, Model model) {
        return playInformationService.showPlay(id,type,model);
    }
}
