package controller.user.administrators;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import service.user.administrators.FocusInformationService;
import service.user.administrators.MusicCollectInformationService;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * 音乐或MV的收藏
 * 查询：指定音乐或MV被收藏的次数
 *
 */
public class MusicCollectInformation {

    @Resource(name = "MusicCollectInformationService")
    MusicCollectInformationService musicCollectInformationService;

    /**
     * 返回指定音乐或MV被收藏的次数
     * @param id 音乐或MV的id
     * @param type 1表示是音乐收藏  2表示是MV的收藏
     */
    @RequestMapping(value = "/showMusicCollect")
    public String showMusicCollect(Integer id, Integer type,Model model) throws ParseException {
        return null;
    }
}
