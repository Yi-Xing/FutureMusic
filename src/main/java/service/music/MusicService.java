package service.music;

import mapper.*;

import javax.annotation.Resource;

public class MusicService {
    @Resource(name = "MusicMapper")
    MusicMapper musicMapper;
    @Resource(name = "ClassificationMapper")
    ClassificationMapper classificationMapper;
    @Resource(name = "UserMapper")
    UserMapper userMapper;
    @Resource(name = "ActivityMapper")
    ActivityMapper activityMapper;
    @Resource(name = "SongListMapper")
    SongListMapper songListMapper;
    @Resource(name = "MusicVideoMapper")
    MusicVideoMapper musicVideoMapper;
    @Resource(name = "PlayMapper")
    PlayMapper playMapper;
    @Resource(name = "FocusMapper")
    FocusMapper focusMapper;

    /**
     * 音乐排行榜，只显示歌曲
     */
    /**
     * 搜索音乐
     */

}
