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


    /**
     * 歌单或专辑的信息
     *
     * @param condition 1表示歌单或专辑的id，2表示活动的id 3、分类的id  4、歌手的id  5、type
     */
    public String showSongList(String[] condition, Integer pageNum, Model model) {
        SongList songList = new SongList();
        if (condition != null) {
            if ((condition[0] != null) && !"".equals(condition[0])) {
                songList.setId(Integer.parseInt(condition[0]));
            }
            if ((condition[1] != null) && !"".equals(condition[1])) {
                songList.setActivity(Integer.parseInt(condition[1]));
            }
            if ((condition[2] != null) && !"".equals(condition[2])) {
                songList.setClassificationId(Integer.parseInt(condition[2]));
            }
            if ((condition[3] != null) && !"".equals(condition[3])) {
                songList.setUserId(Integer.parseInt(condition[3]));
            }
            if ((condition[4] != null) && !"".equals(condition[4])) {
                songList.setType(Integer.parseInt(condition[4]));
            }
            model.addAttribute("conditionZero", condition[0]);
            model.addAttribute("conditionOne", condition[1]);
            model.addAttribute("conditionTwo", condition[2]);
            model.addAttribute("conditionThree", condition[3]);
            model.addAttribute("conditionFour", condition[4]);
        } else {
            model.addAttribute("conditionZero", null);
            model.addAttribute("conditionOne", null);
            model.addAttribute("conditionTwo", null);
            model.addAttribute("conditionThree", null);
            model.addAttribute("conditionFour", null);
        }
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pageNum, 9);
        // 根据条件查找专辑或歌单信息
        List<SongList> list = songListMapper.selectListSongList(songList);
        // 传入页面信息
        model.addAttribute("pageInfo", new PageInfo<>(list));
        return "back_system/song_list";
    }

    /**
     * 显示指定id的歌单或专辑信息
     */
    public SongList showIdSongList(Integer id) {
        SongList songList = new SongList();
        songList.setId(id);
        List<SongList> list = songListMapper.selectListSongList(songList);
        return list.get(0);
    }

    /**
     * 修改专辑的活动，ajax
     */
    public State modifySongList(@RequestBody SongList songList) throws DataBaseException {
        State state = new State();
        if (songList.getType() == 2) {
            // 判断活动是否存在
            if (idExistence.isActivityId(songList.getActivity()) != null) {
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
            state.setInformation("只有活动可以设置活动");

        }
        return state;
    }

    /**
     * 删除指定id专辑或歌单
     */
    public String deleteSongList(Integer id, Model model) throws DataBaseException {
        if (songListMapper.deleteSongList(id) < 1) {
            // 如果失败是数据库错误
            logger.error("专辑或歌单：" + id + "删除时，数据库出错");
            throw new DataBaseException("专辑或歌单：" + id + "删除时，数据库出错");
        }
        return showSongList(null,1,model);
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
