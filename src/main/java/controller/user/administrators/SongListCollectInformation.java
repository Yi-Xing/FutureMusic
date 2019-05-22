package controller.user.administrators;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import service.user.administrators.SongListCollectInformationService;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * 查询：指定专辑或歌单被收藏的次数
 */
@Controller
public class SongListCollectInformation {
    @Resource(name = "SongListCollectInformationService")
    SongListCollectInformationService songListCollectInformationService;

    /**
     * 指定歌手的所有音乐被播放的次数
     * 指定专辑中的所有音乐被播放的次数
     * @param id 歌单或专辑的id
     * @param type 1表示是歌单 2表示是专辑
     */
    @RequestMapping(value = "/showSongListCollect")
    public String showSongListCollect(Integer id,Integer type, Model model)  {
        return songListCollectInformationService.showSongListCollect(id,type,model);
    }
}
