package service.user.administrators;

import entity.Play;
import mapper.PlayMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

@Service(value = "PlayInformationService")
public class PlayInformationService {

    @Resource(name ="PlayMapper")
    PlayMapper playMapper;

    /**
     * 指定歌手的所有音乐被播放的次数
     * 指定专辑中的所有音乐被播放的次数
     * @param id 音乐或MV或专辑的id
     * @param type 1、音乐  2、MV  3、专辑
     */
    public String showPlay(Integer id,Integer type, Model model) throws ParseException {
        Play play=new Play();
        if(type<3){
            play.setMusicId(id);
            play.setType(type);
        }else if(type ==3){
            play.setAlbumId(id);
        }
        List<Play> list= playMapper.selectListPlay(play);
        model.addAttribute("PlayCount",list.size());
        return null;
    }
}
