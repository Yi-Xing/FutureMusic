package controller.user.administrators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import service.user.administrators.ActivityInformationService;
import service.user.administrators.FocusInformationService;
import service.user.administrators.MusicCollectInformationService;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * 音乐或MV的收藏
 * 查询：指定音乐或MV被收藏的次数
 * @author 5月22日 张易兴创建
 */
@Controller
public class MusicCollectInformation {
    private static final Logger logger = LoggerFactory.getLogger(ActivityInformationService.class);
    @Resource(name = "MusicCollectInformationService")
    MusicCollectInformationService musicCollectInformationService;

    /**
     * 返回指定音乐或MV被收藏的次数
     * @param id 音乐或MV的id
     * @param type 1表示是音乐收藏  2表示是MV的收藏
     */
    @RequestMapping(value = "/showMusicCollect")
    public String showMusicCollect(Integer id, Integer type,Model model) throws ParseException {
        return musicCollectInformationService.showMusicCollect(id,type,model);
    }
}
