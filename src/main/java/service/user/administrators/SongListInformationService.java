package service.user.administrators;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.MusicSongList;
import entity.SongList;
import entity.SongListCollect;
import entity.State;
import mapper.MusicCollectMapper;
import mapper.MusicSongListMapper;
import mapper.SongListCollectMapper;
import mapper.SongListMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import service.user.IdExistence;
import service.user.ValidationInformation;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import java.util.List;

/**
 * 专辑或歌单的信息
 *
 * @author 5月22日 张易兴创建
 */
@Service(value = "SongListInformationService")
public class SongListInformationService {
    private static final Logger logger = LoggerFactory.getLogger(ActivityInformationService.class);
    @Resource(name = "SongListMapper")
    SongListMapper songListMapper;
    @Resource(name = "IdExistence")
    IdExistence idExistence;
    @Resource(name = "SongListCollectMapper")
    SongListCollectMapper songListCollectMapper;
    @Resource(name = "MusicSongListMapper")
    MusicSongListMapper musicSongListMapper;
    @Resource(name = "ValidationInformation")
    ValidationInformation validationInformation;

    /**
     * 歌单或专辑的信息
     *
     * @param condition 条件 1存储类别  2存储id类型 2存储值
     */
    public String showSongList(String[] condition, Integer pageNum, Model model) {
        SongList songList = new SongList();
        if (condition != null) {
            if ((condition[0] != null) && !"".equals(condition[0])) {
                songList.setType(Integer.parseInt(condition[0]));
            }
            if ((condition[1] != null) && !"".equals(condition[1]) && (condition[2] != null) && !"".equals(condition[2])) {
                // 1-ID，2-标题 3-用户  4-分类 5-活动
                switch (condition[1]) {
                    case "1":
                        songList.setId(Integer.parseInt(condition[2]));
                        break;
                    case "2":
                        songList.setName(condition[2]);
                        break;
                    case "3":
                        songList.setUserId(Integer.parseInt(condition[2]));
                        break;
                    case "4":
                        songList.setClassificationId(Integer.parseInt(condition[2]));
                        break;
                    case "5":
                        songList.setActivity(Integer.parseInt(condition[2]));
                        break;
                    default:
                }
            }
            model.addAttribute("conditionZero", condition[0]);
            model.addAttribute("conditionOne", condition[1]);
            model.addAttribute("conditionTwo", condition[2]);
        } else {
            model.addAttribute("conditionZero", null);
            model.addAttribute("conditionOne", null);
            model.addAttribute("conditionTwo", null);
        }
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pageNum, 7);
        // 根据条件查找专辑或歌单信息
        List<SongList> list = songListMapper.selectListSongList(songList);
        PageInfo pageInfo = new PageInfo<>(list);
        // 传入页面信息
        logger.debug("查找到的歌单/专辑信息" + list);
        model.addAttribute("pageInfo", pageInfo);
        return "system/backgroundSystem";
    }

    /**
     * 显示指定id的歌单或专辑信息+收藏量
     */
    public SongList showIdSongList(Integer id) {
        SongList songList = new SongList();
        songList.setId(id);
        // 从数据库中取出来歌单或专辑的信息
        List<SongList> list = songListMapper.selectListSongList(songList);
        System.out.println(list);
        if (list.size() != 0) {
            songList = list.get(0);
            SongListCollect songListCollect = new SongListCollect();
            // 去数据库查找指定歌单或专辑的收藏量
            songListCollect.setMusicId(id);
            List<SongListCollect> collections = songListCollectMapper.selectListSongListCollect(songListCollect);
            // 将收藏量存入一个字段中
            songList.setUserId(collections.size());
        }
        System.out.println(songList);
        return songList;
    }

    /**
     * 修改专辑的活动，ajax
     */
    public State modifySongList(String id, String activity) throws DataBaseException {
        State state = new State();
        // 验证id是否合法
        if (validationInformation.isInt(id)) {
            SongList songList;
            // 验证id是否存在
            if ((songList = idExistence.isSongListId(Integer.valueOf(id))) != null) {
                // 验证是不是专辑，歌单不能设置为活动
                if ( songList.getType() == 2) {
                    // 判断活动是否存在
                    if (validationInformation.isInt(activity) && idExistence.isActivityId(Integer.valueOf(activity)) != null) {
                        songList.setActivity(Integer.valueOf(activity));
                        if (songListMapper.updateSongList(songList) < 1) {
                            // 如果失败是数据库错误
                            logger.error("专辑或歌单：" + songList + "更新时，数据库出错");
                            throw new DataBaseException("专辑或歌单：" + songList + "更新时，数据库出错");
                        } else {
                            state.setState(1);
                        }
                    } else {
                        state.setInformation("活动id不存在");
                    }
                } else {
                    state.setInformation("只有专辑可以设置活动");
                }
            } else {
                state.setInformation("id不存在");
            }
        } else {
            state.setInformation("id不存在");
        }
        return state;
    }

    /**
     * 删除指定id专辑或歌单
     */
    public State deleteSongList(String id) throws DataBaseException {
        State state = new State();
        // 先判断id是否存在
        // 根据条件查找歌单专辑信息
        if (validationInformation.isInt(id)) {
            // 验证id是否存在
            if (idExistence.isSongListId(Integer.valueOf(id)) != null) {
                // 删除指定id的评论
                if (songListMapper.deleteSongList(Integer.valueOf(id)) < 1) {
                    // 如果失败是数据库错误
                    logger.error("专辑或歌单：" + id + "删除时，数据库出错");
                    throw new DataBaseException("专辑或歌单：" + id + "删除时，数据库出错");
                }
                state.setState(1);
            } else {
                state.setInformation("id不存在");
            }
        } else {
            state.setInformation("id不存在");
        }
        return state;
    }

    /**
     * 指定歌单或专辑被收藏的次数
     *
     * @param id   歌单或专辑的id
     * @param type 1表示是歌单 2表示是专辑
     */
    public int showSongListCollect(Integer id, Integer type) {
        SongListCollect songListCollect = new SongListCollect();
        songListCollect.setMusicId(id);
        songListCollect.setType(type);
        List<SongListCollect> list = songListCollectMapper.selectListSongListCollect(songListCollect);
        return list.size();
    }

    /**
     * 查找指定专辑或歌单中的所有音乐
     *
     * @param id   专辑或歌单的id
     * @param type 1是歌单2是专辑
     */
    public PageInfo showMusicSongList(Integer id, Integer type, Integer pageNum) {
        MusicSongList musicSongList = new MusicSongList();
        musicSongList.setBelongId(id);
        musicSongList.setType(type);
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pageNum, 8);
        // 根据条件查找专辑或歌单信息
        List<MusicSongList> list = musicSongListMapper.selectListMusicSongList(musicSongList);
        // 传入页面信息
        return new PageInfo<>(list);
    }
}
