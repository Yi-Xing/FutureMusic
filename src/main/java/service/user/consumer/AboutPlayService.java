package service.user.consumer;

import entity.MusicCollect;
import entity.MusicVideo;
import entity.User;
import mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import service.user.IdExistence;
import service.user.SpecialFunctions;
import service.user.ValidationInformation;
import util.FileUpload;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 跳转播放页面
 *
 * @author 7月27日 张易兴创建
 */
@Service(value = "AboutPlayService")
public class AboutPlayService {
    private static final Logger logger = LoggerFactory.getLogger(AboutPlayService.class);
    @Resource(name = "SpecialFunctions")
    SpecialFunctions specialFunctions;
    @Resource(name = "MusicCollectMapper")
    MusicCollectMapper musicCollectMapper;
    @Resource(name = "MusicVideoMapper")
    MusicVideoMapper musicVideoMapper;
    /**
     * 显示用户喜欢MV的播放页面
     */
    public String playMusicVideo(HttpSession session, Model model){
        System.out.println(1);
        User user=specialFunctions.getUser(session);
        System.out.println(user);
        MusicCollect musicCollect=new MusicCollect();
        musicCollect.setUserId(user.getId());
        musicCollect.setType(2);
        // 查找到用户收藏的MV
        List<MusicCollect> musicCollectList= musicCollectMapper.selectListMusicCollect(musicCollect);
        List<Integer> musicId=new ArrayList<>();
        for (MusicCollect musicCollect1:musicCollectList){
            musicId.add(musicCollect1.getMusicId());
        }
        List<MusicVideo> musicVideoList=musicVideoMapper.listIdSelectListMusicVideo(musicId);
        model.addAttribute("musicVideoList",musicVideoList);
        System.out.println(musicVideoList);
        System.out.println("===============================");
        return "userMusic/musicVideoPlayer";
    }

}
