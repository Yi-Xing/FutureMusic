package controller.user.administrators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import service.user.administrators.ActivityInformationService;
import service.user.administrators.MusicSongListInformationService;

import javax.annotation.Resource;

/**
 * 歌单或专辑的存放的歌曲
 * 查询：专辑的id
 * @author 5月22日 张易兴创建
 */
@Controller
public class MusicSongListInformation {
    private static final Logger logger = LoggerFactory.getLogger(ActivityInformationService.class);
    @Resource(name = "MusicSongListInformationService")
    MusicSongListInformationService musicSongListInformationService;

    /**
     * 查找指定专辑或歌单中的所有音乐
     * @param id 专辑或歌单的id
     * @param type 1是歌单2是专辑
     */
    @RequestMapping(value = "/showMusicSongList")
    public String showMusicSongList(Integer id,Integer type,Model model) {
        return musicSongListInformationService.showMusicSongList(id,type,model);
    }
}
