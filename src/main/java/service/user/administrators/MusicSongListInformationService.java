package service.user.administrators;

import entity.MusicSongList;
import mapper.MusicSongListMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * 歌单或专辑中的音乐
 * @author 5月22日 张易兴创建
 */
@Service(value = "MusicSongListInformationService")
public class MusicSongListInformationService {
    private static final Logger logger = LoggerFactory.getLogger(ActivityInformationService.class);
    @Resource(name = "MusicSongListMapper")
    MusicSongListMapper musicSongListMapper;
    /**
     * 查找指定专辑或歌单中的所有音乐
     * @param id 专辑或歌单的id
     * @param type 1是歌单2是专辑
     */
    public String showMusicSongList(Integer id,Integer type,Model model) {
        MusicSongList musicSongList=new MusicSongList();
        musicSongList.setBelongId(id);
        musicSongList.setType(type);
        // 查找指定歌单或专辑的所有音乐
        List<MusicSongList> list=musicSongListMapper.selectListMusicSongList(musicSongList);
        model.addAttribute("MusicSongList",list);
        return null;
    }
}
