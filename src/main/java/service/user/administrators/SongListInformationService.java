package service.user.administrators;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.Order;
import entity.SongList;
import entity.State;
import mapper.SongListMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    /**
     * 指定歌手的所有音乐被播放的次数
     * 指定专辑中的所有音乐被播放的次数
     *
     * @param id   歌单或专辑的id
     * @param type 1表示歌单或专辑的id，2表示活动的id 3、分类的id  4、用户的id
     */
    public String showSongList(Integer id, Integer type, Integer pageNum, Model model) {
        SongList songList = new SongList();
        if (type == 1) {
            songList.setId(id);
        } else if (type == 2) {
            songList.setActivity(id);
        } else if (type == 3) {
            songList.setClassificationId(id);
        } else if (type == 4) {
            songList.setUserId(id);
        }
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pageNum, 8);
        // 根据条件查找专辑或歌单信息
        List<SongList> list = songListMapper.selectListSongList(songList);
        PageInfo pageInfo = new PageInfo<>(list);
        // 传入页面信息
        model.addAttribute("pageInfo", pageInfo);
        return null;
    }

    /**
     * 修改专辑的活动，ajax
     */
    public State modifySongList(@RequestBody SongList songList) throws DataBaseException {
        State state = new State();
        // 判断活动是否存在
        if (idExistence.isActivityId(songList.getActivity())!=null) {
            if (songListMapper.updateSongList(songList) < 1) {
                // 如果失败是数据库错误
                logger.error("专辑或歌单：" + songList + "更新时，数据库出错");
                throw new DataBaseException("专辑或歌单：" + songList + "更新时，数据库出错");
            } else {
                state.setState(1);
            }
        }else {
            state.setInformation("活动id不存在");
        }
        return state;
    }

    /**
     * 删除指定id专辑或歌单
     */
    public String deleteSongList(Integer id) throws DataBaseException {
        if (songListMapper.deleteSongList(id) < 1) {
            // 如果失败是数据库错误
            logger.error("专辑或歌单：" + id + "删除时，数据库出错");
            throw new DataBaseException("专辑或歌单：" + id + "删除时，数据库出错");
        }
        return null;
    }

}
