package service.user.administrators;

import entity.MusicCollect;
import mapper.MusicCollectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * 音乐或MV的收藏
 * 查询：指定音乐或MV被收藏的次数
 *
 * @author 5月22日 张易兴创建
 */
@Service(value = "MusicCollectInformationService")
public class MusicCollectInformationService {
    private static final Logger logger = LoggerFactory.getLogger(ActivityInformationService.class);
    @Resource(name = "MusicCollectMapper")
    MusicCollectMapper musicCollectMapper;

    /**
     * 返回指定音乐或MV被收藏的次数
     *
     * @param id   音乐或MV的id
     * @param type 1表示是音乐收藏  2表示是MV的收藏
     */
    public String showMusicCollect(Integer id, Integer type, Model model) throws ParseException {
        MusicCollect musicCollect = new MusicCollect();
        musicCollect.setMusicId(id);
        musicCollect.setType(type);
        int musicCollectCount = musicCollectMapper.selectUserMusicCollectCount(musicCollect);
        model.addAttribute("musicCollectCount", musicCollectCount);
        System.out.println(musicCollectCount);
        return null;
    }
}
