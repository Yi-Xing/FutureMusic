package controller.user.administrators;

import entity.MusicVideo;
import entity.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.administrators.ActivityInformationService;
import service.user.administrators.MusicVideoInformationService;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;

/**
 * MV：
 *  添加：输入全部信息
 *  显示：id，名字，等级，价格，上传日期，是否有版权，播放次数
 *          更多，歌手，分类，MV的路径，简介，参加的活动，对应的音乐
 *      查询：1、id
 *            2、名字
 *            3、等级
 *            4.日期，指定日期之后发布的
 *            5、版权
 *            6、歌手id
 *            7、音乐id
 *            8、活动id
 *            9、分类的id
 *  可更改
 * @author 5月22日 张易兴创建
 */
@Controller
public class MusicVideoInformation {
    private static final Logger logger = LoggerFactory.getLogger(ActivityInformationService.class);
    @Resource(name = "MusicVideoInformationService")
    MusicVideoInformationService musicVideoInformationService;
    /**
     * 添加MV
     */
    @RequestMapping(value = "/addMusicVideo")
    @ResponseBody
    public State addMusicVideo(@RequestBody MusicVideo musicVideo, HttpServletRequest request) throws DataBaseException , IOException {
        return musicVideoInformationService.addMusicVideo(musicVideo,request);
    }
    /**
     * 显示和按条件查询MV
     * @param condition 条件可以有多个
     * @param pageNum 表示当前第几页
     */
    @RequestMapping(value = "/showMusicVideo")
    public String showMusicVideo(String[] condition,Integer pageNum, Model model) throws ParseException {
        return musicVideoInformationService.showMusicVideo(condition,pageNum,model);
    }

    /**
     * 修改MV信息，ajax
     */
    @RequestMapping(value = "/modifyMusicVideo")
    @ResponseBody
    public State modifyMusicVideo(@RequestBody MusicVideo musicVideo, HttpServletRequest request) throws DataBaseException , IOException{
        return musicVideoInformationService.modifyMusicVideo(musicVideo,request);
    }
}
