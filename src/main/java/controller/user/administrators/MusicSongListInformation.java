package controller.user.administrators;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import service.user.administrators.MusicSongListInformationService;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * 歌单或专辑的存放的歌曲
 * 查询：专辑的id
 */
@Controller
public class MusicSongListInformation {

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
