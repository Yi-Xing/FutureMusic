package service.user.consumer;

import entity.*;
import mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import service.Emergency;
import service.user.IdExistence;
import service.user.SpecialFunctions;
import service.user.ValidationInformation;
import util.FileUpload;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service(value = "AboutsSupplementService")
public class AboutsSupplementService {
    private static final Logger logger = LoggerFactory.getLogger(AboutSongListService.class);
    @Resource(name = "SongListCollectMapper")
    SongListCollectMapper songListCollectMapper;
    @Resource(name = "SpecialFunctions")
    SpecialFunctions specialFunctions;
    @Resource(name = "Existence")
    ExistenceService existenceService;
    @Resource(name = "SongListMapper")
    SongListMapper songListMapper;
    @Resource(name = "ValidationInformation")
    ValidationInformation validationInformation;
    @Resource(name = "IdExistence")
    IdExistence idExistence;
    @Resource(name = "FileUpload")
    FileUpload fileUpload;
    @Resource(name = "MusicSongListMapper")
    MusicSongListMapper musicSongListMapper;
    @Resource(name = "TransactionService")
    TransactionService transactionService;
    @Resource(name = "MusicMapper")
    MusicMapper musicMapper;
    @Resource(name = "UserMapper")
    UserMapper userMapper;
    @Resource(name = "PlayMapper")
    PlayMapper playMapper;
    @Resource(name = "AboutMusicService")
    AboutMusicService aboutMusicService;
    @Resource(name = "Emergency")
    Emergency emergency;

    /**
     * 将指定音乐添加到专辑或歌单中
     *
     * @param musicSongList 获取需要添加到指定专辑或歌单中的音乐
     *                      所需参数：
     *                      belongId 专辑或歌单的id
     *                      type 1是歌单2是专辑
     *                      musicId 音乐的id
     */
    public State addMusicSongList(MusicSongList musicSongList, HttpSession session) throws DataBaseException {
        State state = new State();
        if (musicSongListMapper.selectListMusicSongList(musicSongList).size() == 0) {
            User user = specialFunctions.getUser(session);
            // 得到音乐的id
            int musicId = musicSongList.getMusicId();
            // 得到音乐信息
            Music music = idExistence.isMusicId(musicId);
            musicSongList.setHave(1);
            // 为歌单时候，再判断
            if (musicSongList.getType() == 1) {
                // 判断用户有没有购买
                if (transactionService.isPurchaseMusic(musicId, 1, user) == null) {
                    musicSongList.setHave(0);
                }
            }
            // 音乐的歌手的id
            musicSongList.setSingerId(music.getSingerId());
            // 音乐的分类的id
            musicSongList.setClassificationId(music.getClassificationId());
            if (musicSongListMapper.insertMusicSongList(musicSongList) < 1) {
                // 如果失败是数据库错误
                logger.error("歌单或专辑：" + musicSongList + "添加歌单或专辑信息时，数据库出错");
                throw new DataBaseException("歌单或专辑：" + musicSongList + "添加歌单或专辑信息时，数据库出错");
            }
            state.setState(1);
        }
        return state;
    }

    /**
     * 将指定音乐从歌单/专辑中删除
     * 所需参数：
     * belongId 专辑或歌单的id
     * type 1是歌单2是专辑
     * musicId 音乐的id
     */
    public State deleteMusicSongList(MusicSongList musicSongList) throws DataBaseException {
        System.out.println("======"+musicSongList);
        if (musicSongListMapper.deleteMusicSongList(musicSongList) < 1) {
            // 如果失败是数据库错误
            logger.error("歌单或专辑：" + musicSongList + "删除歌单或专辑音乐时，数据库出错");
            throw new DataBaseException("歌单或专辑：" + musicSongList + "删除歌单或专辑音乐时，数据库出错");
        }
        return new State(1);
    }

    /**
     * 得到指定用户创建的所有歌单
     */
    public List<SongList> getSongList(HttpSession session) {
        User user = specialFunctions.getUser(session);
        SongList songList = new SongList();
        songList.setType(1);
        songList.setUserId(user.getId());
        return songListMapper.selectListSongList(songList);
    }
}
