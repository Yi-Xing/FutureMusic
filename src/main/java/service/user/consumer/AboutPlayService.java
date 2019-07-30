package service.user.consumer;

import entity.*;
import mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import service.Emergency;
import service.user.SpecialFunctions;

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
     * 显示用户喜欢MV的播放页面,即将播放MV的Id
     */
    public String playCollectMusicVideo(HttpSession session, int musicId, Model model) {
        // 查找到用户收藏的MV
        List<MusicCollect> musicCollectList = aboutMusicService.showUserCollectionMusic(2, session);
        List<Integer> musicIds = new ArrayList<>();
        for (MusicCollect musicCollect1 : musicCollectList) {
            musicIds.add(musicCollect1.getMusicId());
        }
        List<MusicVideo> musicVideoList = musicVideoMapper.listIdSelectListMusicVideo(musicIds);
        // 将用户是否喜欢指定MV存入分类中
        model.addAttribute("musicVideoList", emergency.getMusicVideoCollect(specialFunctions.getUser(session), musicVideoList));
        logger.debug("用户喜欢的MV" + musicVideoList);
        if (musicId == 0) {
            if (musicVideoList.size() > 0) {
                musicId = musicVideoList.get(0).getId();
            }
        }
        model.addAttribute("musicVideoId", musicId);
        return "userMusic/musicVideoPlayer";
    }

    /**
     * 显示用户喜欢音乐的播放页面
     *
     */
    public String playCollectMusic(HttpSession session, Model model) {
        User user = specialFunctions.getUser(session);
        List<MusicCollect> list = aboutMusicService.showUserCollectionMusic(1, session);
        List<Integer> musicId = new ArrayList<>();
        for (MusicCollect musicCollect1 : list) {
            musicId.add(musicCollect1.getMusicId());
        }
        List<Music> musicList = musicMapper.listIdSelectListMusic(musicId);
        SongList songList = new SongList();
        songList.setPicture(user.getHeadPortrait());
        songList.setName(user.getName());
        // 当前歌单数据,其实是用户数据
        model.addAttribute("songList", songList);
        // 音乐列表数据
        // 将用户是否喜欢音乐传入分类中
        model.addAttribute("musicList", emergency.getMusicCollect(user, musicList));
        return "userMusic/musicPlayer";
    }

    /**
     * 播放指定MV
     */
    public String playMusicVideoId(HttpSession session, Integer id, Model model) {
        return playCollectMusicVideo(session, id, model);
    }


}
