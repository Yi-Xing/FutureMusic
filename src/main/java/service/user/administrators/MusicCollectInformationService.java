package service.user.administrators;

import entity.MusicCollect;
import mapper.MusicCollectMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 音乐或MV的收藏
 * 查询：指定音乐或MV被收藏的次数
 *
 */
import javax.annotation.Resource;
import java.text.ParseException;

@Service(value = "MusicCollectInformationService")
public class MusicCollectInformationService {
    @Resource(name = "MusicCollectMapper")
    MusicCollectMapper musicCollectMapper;
    /**
     * 返回指定音乐或MV被收藏的次数
     * @param id 音乐或MV的id
     * @param type 1表示是音乐收藏  2表示是MV的收藏
     */
    public String showMusicCollect(Integer id, Integer type, Model model) throws ParseException {
        MusicCollect musicCollect=new MusicCollect();
        musicCollect.setMusicId(id);
        musicCollect.setType(type);
        int  musicCollectCount=musicCollectMapper.selectUserMusicCollectCount(musicCollect);
        model.addAttribute("musicCollectCount",musicCollectCount);
        return null;
    }
}
