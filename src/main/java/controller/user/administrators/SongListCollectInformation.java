package controller.user.administrators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import service.user.administrators.ActivityInformationService;
import service.user.administrators.SongListCollectInformationService;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * 查询：指定专辑或歌单被收藏的次数
 * @author 5月22日 张易兴创建
 */
@Controller
public class SongListCollectInformation {
    private static final Logger logger = LoggerFactory.getLogger(ActivityInformationService.class);
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
