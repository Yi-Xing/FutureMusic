package service.user.administrators;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.Music;
import entity.State;
import entity.User;
import mapper.MusicMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.IdExistence;
import service.user.ValidationInformation;
import util.FileUpload;
import util.JudgeIsOverdueUtil;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * 音乐
 *
 * @author 5月22日 张易兴创建
 */
@Service(value = "MusicInformationService")
public class MusicInformationService {
    private static final Logger logger = LoggerFactory.getLogger(MusicInformationService.class);
    @Resource(name = "MusicMapper")
    MusicMapper musicMapper;
    @Resource(name = "ValidationInformation")
    ValidationInformation validationInformation;
    @Resource(name = "IdExistence")
    IdExistence idExistence;
    @Resource(name = "FileUpload")
    FileUpload fileUpload;
    /**
     * 添加音乐
     */
    public State addMusic(Music music, HttpServletRequest request) throws DataBaseException, IOException {
        State state = new State();
        if (validationInformation.isName(music.getName())) {
            // 判断价格是否符合要求
            if (validationInformation.isPrice(String.valueOf(music.getPrice()))) {
                // 判断歌手是否存在
                User user = idExistence.isUserId(music.getSingerId());
                if (user != null) {
                    if (user.getLevel() == 2) {
                        // 判断专辑是否存在
                        if (idExistence.isSongListId(music.getAlbumId()) != null) {
                            // 判断分类是否存在
                            if (idExistence.isClassificationId(music.getClassificationId()) != null) {
                                // 先判断是否设置了MV
                                if (music.getMusicVideoId() != 0) {
                                    // 判断MV是否存在
                                    if (idExistence.isMusicVideoId(music.getMusicVideoId()) == null) {
                                        state.setInformation("音乐的MV不存在");
                                        return state;
                                    }
                                }
                                // 先判断是否设置了活动
                                if (music.getActivity() != 0) {
                                    // 判断活动id是否存在
                                    if (idExistence.isActivityId(music.getActivity()) == null) {
                                        state.setInformation("音乐的活动不存在");
                                        return state;
                                    }
                                }
                                // 获取上传的文件路径
                                String path = fileUpload.music(request);
                                if (path != null && !"".equals(path)) {
                                    music.setPath(path);
                                }
                                String lyricPath = fileUpload.musicLyric(request);
                                if (lyricPath != null && !"".equals(lyricPath)) {
                                    music.setLyricPath(lyricPath);
                                }
                                if (musicMapper.insertMusic(music) < 1) {
                                    // 如果失败是数据库错误
                                    logger.error(music + "添加音乐信息时，数据库出错");
                                    throw new DataBaseException(music + "添加音乐信息时，数据库出错");
                                }
                                state.setState(1);
                            } else {
                                state.setInformation("音乐的分类不存在");
                            }
                        } else {
                            state.setInformation("音乐的专辑不存在");
                        }
                    } else {
                        state.setInformation("音乐的歌手不存在");
                    }
                } else {
                    state.setInformation("音乐的歌手不存在");
                }
            } else {
                state.setInformation("音乐的价格不符合要求");
            }
        } else {
            state.setInformation("音乐的名字不符合要求");
        }
        return state;
    }

    /**
     * 显示和按条件查询音乐
     *
     * @param condition 条件可以有多个
     * @param pageNum   表示当前第几页
     */
    public String showMusic(String[] condition, Integer pageNum, Model model) throws ParseException {
        // 用来存储管理员输入的条件
        Music music = new Music();
        if ((condition[0] != null) && !"".equals(condition[0])) {
            music.setId(Integer.parseInt(condition[0]));
        }
        if ((condition[1] != null) && !"".equals(condition[1])) {
            music.setName(condition[1]);
        }
        if ((condition[2] != null) && !"".equals(condition[2])) {
            music.setLevel(Integer.parseInt(condition[2]));
        }
        if ((condition[3] != null) && !"".equals(condition[3])) {
            music.setDate(JudgeIsOverdueUtil.toDate(condition[3]));
        }
        if ((condition[4] != null) && !"".equals(condition[4])) {
            music.setAvailable(Integer.parseInt(condition[4]));
        }
        if ((condition[5] != null) && !"".equals(condition[5])) {
            music.setSingerId(Integer.parseInt(condition[5]));
        }
        if ((condition[6] != null) && !"".equals(condition[6])) {
            music.setAlbumId(Integer.parseInt(condition[6]));
        }
        if ((condition[7] != null) && !"".equals(condition[7])) {
            music.setActivity(Integer.parseInt(condition[7]));
        }
        if ((condition[8] != null) && !"".equals(condition[8])) {
            music.setClassificationId(Integer.parseInt(condition[8]));
        }
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pageNum, 8);
        // 根据条件查找用户信息
        List<Music> list = musicMapper.selectListMusic(music);
        PageInfo pageInfo = new PageInfo<>(list);
        // 传入页面信息
        model.addAttribute("pageInfo", pageInfo);
        return null;
    }

    /**
     * 修改音乐信息，ajax
     */
    public State modifyMusic(Music music, HttpServletRequest request) throws DataBaseException, IOException {
        State state = new State();
        if (validationInformation.isName(music.getName())) {
            // 判断价格是否符合要求
            if (validationInformation.isPrice(String.valueOf(music.getPrice()))) {
                // 判断歌手是否存在
                if (idExistence.isUserId(music.getSingerId()) != null) {
                    // 判断专辑是否存在
                    if (idExistence.isSongListId(music.getAlbumId()) != null) {
                        // 判断分类是否存在
                        if (idExistence.isClassificationId(music.getClassificationId()) != null) {
                            // 先判断是否设置了MV
                            if (music.getMusicVideoId() != 0) {
                                // 判断MV是否存在
                                if (idExistence.isMusicVideoId(music.getMusicVideoId()) == null) {
                                    state.setInformation("音乐的MV不存在");
                                    return state;
                                }
                            }
                            // 先判断是否设置了活动
                            if (music.getActivity() != 0) {
                                // 判断活动id是否存在
                                if (idExistence.isActivityId(music.getActivity()) == null) {
                                    state.setInformation("音乐的活动不存在");
                                    return state;
                                }
                            }
                            // 获取上传的文件路径
                            String path = fileUpload.music(request);
                            if (path != null && !"".equals(path)) {
                                music.setPath(path);
                            }
                            String lyricPath = fileUpload.musicLyric(request);
                            if (lyricPath != null && !"".equals(lyricPath)) {
                                music.setLyricPath(lyricPath);
                            }
                            if (musicMapper.updateMusic(music) < 1) {
                                // 如果失败是数据库错误
                                logger.error(music + "修改音乐信息，数据库出错");
                                throw new DataBaseException(music + "修改音乐信息，数据库出错");
                            }
                            state.setState(1);
                        } else {
                            state.setInformation("音乐的分类不存在");
                        }
                    } else {
                        state.setInformation("音乐的专辑不存在");
                    }
                } else {
                    state.setInformation("音乐的歌手不存在");
                }
            } else {
                state.setInformation("音乐的价格不符合要求");
            }
        } else {
            state.setInformation("音乐的名字不符合要求");
        }
        return state;
    }
}
