package service.music;

import entity.*;
import mapper.*;
import org.apache.ibatis.cache.CacheKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 点击显示详细信息的功能
 *  音乐、歌单、专辑、歌手
 * @author 蒋靓峣 5.11创建
 * */
@Service(value = "DetailsService")
public class DetailsService {
    private static final Logger logger = LoggerFactory.getLogger(PlayService.class);
    @Resource(name="MusicMapper")
    MusicMapper musicMapper;
    @Resource(name="ClassificationMapper")
    ClassificationMapper classificationMapper;
    @Resource(name = "UserMapper")
    UserMapper userMapper;
    @Resource(name = "SongListMapper")
    SongListMapper songListMapper;
    @Resource(name = "MusicVideoMapper")
    MusicVideoMapper musicVideoMapper;
    @Resource(name = "ShowCommentService")
    ShowCommentService showCommentService;
    @Resource(name = "MusicSongListMapper")
    MusicSongListMapper musicSongListMapper;

}
