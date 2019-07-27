package controller.user.administrators;

import entity.Music;
import entity.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import service.user.administrators.ActivityInformationService;
import service.user.administrators.MusicInformationService;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * 音乐：
 * 添加：输入全部信息
 * 显示：id，名字，等级，价格，上传日期，是否有版权，播放次数
 * 更多，歌手，专辑，分类，音乐的路径，歌词的路径，参加的活动，对应的所有的MV
 * 查询：1、id
 * 2、名字
 * 3、等级
 * 4.日期，指定日期之后发布的
 * 5、版权
 * 6、歌手id
 * 7、专辑id
 * 8、活动id
 * 9、分类的id
 * 可更改
 *
 * @author 5月22日 张易兴创建
 */
@Controller
@RequestMapping(value = "/administrators")
public class MusicInformation {
    private static final Logger logger = LoggerFactory.getLogger(ActivityInformationService.class);
    @Resource(name = "MusicInformationService")
    MusicInformationService musicInformationService;

    /**
     * 添加音乐
     */
    @RequestMapping(value = "/addMusic")
    @ResponseBody
    public State addMusic( String name, String singerId, String albumId, String classificationId, String level, String price, String activity, String available, String musicVideoId, HttpServletRequest request) throws DataBaseException, IOException {
        return musicInformationService.addMusic( name, singerId, albumId, classificationId, level, price, activity, available, musicVideoId, request);
    }

    /**
     * 显示和按条件查询音乐
     *
     * @param condition 条件可以有多个
     * @param pageNum   表示当前第几页
     */
    @RequestMapping(value = "/showMusic")
    public String showMusic(String[] condition, @RequestParam(defaultValue = "1") Integer pageNum, Model model) {
        return musicInformationService.showMusic(condition, pageNum, model);
    }

    /**
     * 修改编辑音乐信息，ajax
     */
    @RequestMapping(value = "/modifyEditMusic")
    @ResponseBody
    public State modifyEditMusic(String id, String name, String singerId, String albumId, String classificationId, String level, String price, String activity, String available) throws DataBaseException {
        return musicInformationService.modifyEditMusic(id, name, singerId, albumId, classificationId, level, price, activity, available);
    }

    /**
     * 修改更多音乐信息，ajax
     */
    @RequestMapping(value = "/modifyMoreMusic")
    @ResponseBody
    public State modifyMoreMusic(String id, String musicVideoId, boolean[] fileCheckbox, HttpServletRequest request) throws DataBaseException {
        return musicInformationService.modifyMoreMusic(id, musicVideoId, fileCheckbox, request);
    }

    /**
     * 查找指定id的音乐+音乐的播放次数
     */
    @RequestMapping(value = "/showIdMusic")
    @ResponseBody
    public Music showIdMusic(Integer id) {
        return musicInformationService.showIdMusic(id);
    }
}
