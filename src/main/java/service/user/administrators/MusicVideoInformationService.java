package service.user.administrators;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.Music;
import entity.MusicVideo;
import entity.State;
import mapper.MusicMapper;
import mapper.MusicVideoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import util.JudgeIsOverdueUtil;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

@Service(value = "MusicVideoInformationService")
public class MusicVideoInformationService {
    private static final Logger logger = LoggerFactory.getLogger(MusicVideoInformationService.class);
    @Resource(name = "MusicVideoMapper")
    MusicVideoMapper musicVideoMapper;

    /**
     * 添加MV
     */
    public State addMusicVideo(MusicVideo musicVideo) throws DataBaseException {
        if (musicVideoMapper.insertMusicVideo(musicVideo) < 1) {
            // 如果失败是数据库错误
            logger.error(musicVideo + "添加MV信息时，数据库出错");
            throw new DataBaseException(musicVideo + "添加MV信息时，数据库出错");
        }
        return new State(1);
    }

    /**
     * 显示和按条件查询MV
     *
     * @param condition 条件可以有多个
     * @param pageNum   表示当前第几页
     */
    public String showMusicVideo(String[] condition, Integer pageNum, Model model) throws ParseException {
        // 用来存储管理员输入的条件
        MusicVideo musicVideo = new MusicVideo();
        if ((condition[0] != null) && !"".equals(condition[0])) {
            musicVideo.setId(Integer.parseInt(condition[0]));
        }
        if ((condition[1] != null) && !"".equals(condition[1])) {
            musicVideo.setName(condition[1]);
        }
        if ((condition[2] != null) && !"".equals(condition[2])) {
            musicVideo.setLevel(Integer.parseInt(condition[2]));
        }
        if ((condition[3] != null) && !"".equals(condition[3])) {
            musicVideo.setDate(JudgeIsOverdueUtil.toDate(condition[3]));
        }
        if ((condition[4] != null) && !"".equals(condition[4])) {
            musicVideo.setAvailable(Integer.parseInt(condition[4]));
        }
        if ((condition[5] != null) && !"".equals(condition[5])) {
            musicVideo.setSingerId(Integer.parseInt(condition[5]));
        }
        if ((condition[6] != null) && !"".equals(condition[6])) {
            musicVideo.setMusicId(Integer.parseInt(condition[6]));
        }
        if ((condition[7] != null) && !"".equals(condition[7])) {
            musicVideo.setActivity(Integer.parseInt(condition[7]));
        }
        if ((condition[8] != null) && !"".equals(condition[8])) {
            musicVideo.setClassificationId(Integer.parseInt(condition[8]));
        }
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pageNum, 8);
        // 根据条件查找用户信息
        List<MusicVideo> list = musicVideoMapper.selectListMusicVideo(musicVideo);
        PageInfo pageInfo = new PageInfo<>(list);
        // 传入页面信息
        model.addAttribute("pageInfo", pageInfo);
        return null;
    }

    /**
     * 修改MV信息，ajax
     */
    public State modifyMusicVideo( MusicVideo musicVideo) throws DataBaseException {
        if (musicVideoMapper.updateMusicVideo(musicVideo) < 1) {
            // 如果失败是数据库错误
            logger.error(musicVideo + "修改MV信息，数据库出错");
            throw new DataBaseException(musicVideo + "修改MV信息，数据库出错");
        }
        return new State(1);
    }
}
