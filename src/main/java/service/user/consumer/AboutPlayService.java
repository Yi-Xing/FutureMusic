package service.user.consumer;

import entity.*;
import mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import service.Emergency;
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
    @Resource(name = "AboutMusicService")
    AboutMusicService aboutMusicService;
    @Resource(name = "MusicMapper")
    MusicMapper musicMapper;
    @Resource(name = "Emergency")
    Emergency emergency;

    /**
     * 显示用户喜欢MV的播放页面
     */
    public String playCollectMusicVideo(HttpSession session, Model model){
        // 查找到用户收藏的MV
        List<MusicCollect> musicCollectList=aboutMusicService.showUserCollectionMusic(2,session);
        List<Integer> musicId=new ArrayList<>();
        for (MusicCollect musicCollect1:musicCollectList){
            musicId.add(musicCollect1.getMusicId());
        }
        List<MusicVideo> musicVideoList=musicVideoMapper.listIdSelectListMusicVideo(musicId);
        // 将用户是否喜欢指定MV存入分类中
        model.addAttribute("musicVideoList",emergency.getMusicVideoCollect(specialFunctions.getUser(session),musicVideoList));
        logger.debug("用户喜欢的MV"+musicVideoList);
        if(musicVideoList.size()>0){
            model.addAttribute("musicVideoId",musicVideoList.get(0));
        }
        return "userMusic/musicVideoPlayer";
    }

    /**
     * 显示用户喜欢音乐的播放页面
     */
    public String playCollectMusic(HttpSession session, Model model){
        User user=specialFunctions.getUser(session);
        List <MusicCollect> list=aboutMusicService.showUserCollectionMusic(1,session);
        List<Integer> musicId=new ArrayList<>();
        for (MusicCollect musicCollect1:list){
            musicId.add(musicCollect1.getMusicId());
        }
        List<Music> musicList=musicMapper.listIdSelectListMusic(musicId);
        SongList songList=new SongList();
        songList.setPicture(user.getHeadPortrait());
        songList.setName(user.getName());
        // 当前歌单数据,其实是用户数据
        model.addAttribute("songList", songList);
        // 音乐列表数据
        // 将用户是否喜欢音乐传入分类中
        model.addAttribute("musicList", emergency.getMusicCollect(user,musicList));
        return "userMusic/musicPlayer";
    }

    /**
     * 播放指定MV
     */
    public String playMusicVideoId(Integer id, Model model){
        model.addAttribute("musicVideoId",id);
        return "userMusic/musicVideoPlayer";
    }


}
