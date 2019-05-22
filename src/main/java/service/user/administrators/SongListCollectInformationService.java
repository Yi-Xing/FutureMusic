package service.user.administrators;

import entity.SongListCollect;
import mapper.SongListCollectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 *歌单或专辑的收藏
 * @author 5月22日 张易兴创建
 */
@Service(value = "SongListCollectInformationService")
public class SongListCollectInformationService {
    private static final Logger logger = LoggerFactory.getLogger(ActivityInformationService.class);
    @Resource(name = "SongListCollectMapper")
    SongListCollectMapper songListCollectMapper;

    /**
     * 指定歌手的所有音乐被播放的次数
     * 指定专辑中的所有音乐被播放的次数
     * @param id 歌单或专辑的id
     * @param type 1表示是歌单 2表示是专辑
     */
    @RequestMapping(value = "/showSongListCollect")
    public String showSongListCollect(Integer id,Integer type, Model model)  {
        SongListCollect songListCollect=new  SongListCollect();
        songListCollect.setMusicId(id);
        songListCollect.setType(type);
        List<SongListCollect> list= songListCollectMapper.selectListSongListCollect(songListCollect);
        model.addAttribute("SongListCollectCount",list.size());
        return null;
    }
}
