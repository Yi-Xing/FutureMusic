package service.user.administrators;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.*;
import mapper.MusicMapper;
import mapper.PlayMapper;
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
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
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
    @Resource(name = "PlayMapper")
    PlayMapper playMapper;
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
                                music.setDate(new Date());
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
     * 条件 1存储等级 2存储是否有版权 3存储id类型 4存储值
     *
     * @param condition 条件可以有多个
     * @param pageNum   表示当前第几页
     */
    public String showMusic(String[] condition, Integer pageNum, Model model) {
        Music music = new Music();
        if (condition != null) {
            if ((condition[0] != null) && !"".equals(condition[0])) {
                music.setLevel(Integer.parseInt(condition[0]));
            }
            if ((condition[1] != null) && !"".equals(condition[1])) {
                music.setAvailable(Integer.parseInt(condition[1]));
            }
            if ((condition[2] != null) && !"".equals(condition[2]) && (condition[3] != null) && !"".equals(condition[3])) {
                // 1-ID，2-名字 3-歌手 4-专辑 5-分类 6-活动
                switch (condition[2]) {
                    case "1":
                        music.setId(Integer.parseInt(condition[3]));
                        break;
                    case "2":
                        music.setName(condition[3]);
                        break;
                    case "3":
                        music.setSingerId(Integer.parseInt(condition[3]));
                        break;
                    case "4":
                        music.setAlbumId(Integer.parseInt(condition[3]));
                        break;
                    case "5":
                        music.setClassificationId(Integer.parseInt(condition[3]));
                        break;
                    case "6":
                        music.setActivity(Integer.parseInt(condition[3]));
                        break;
                    default:
                }
            }
            model.addAttribute("conditionZero", condition[0]);
            model.addAttribute("conditionOne", condition[1]);
            model.addAttribute("conditionTwo", condition[2]);
            model.addAttribute("conditionThree", condition[3]);
        } else {
            model.addAttribute("conditionZero", null);
            model.addAttribute("conditionOne", null);
            model.addAttribute("conditionTwo", null);
            model.addAttribute("conditionThree", null);
        }
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pageNum, 7);
        // 根据条件查找用户信息
        List<Music> list = musicMapper.selectListMusic(music);
        PageInfo pageInfo = new PageInfo<>(list);
        // 传入页面信息
        logger.debug("查找到的音乐" + list);
        model.addAttribute("pageInfo", pageInfo);
        return "system/backgroundSystem";
    }

    /**
     * 查找指定id的音乐+音乐的播放次数
     */
    public Music showIdMusic(Integer id) {
        Music music = new Music();
        music.setId(id);
        List<Music> list = musicMapper.selectListMusic(music);
        music = list.get(0);
        if (music != null) {
            // 查找指定音乐的播放次数
            Play play = new Play();
            play.setMusicId(id);
            play.setType(1);
            music.setPlayCount(playMapper.selectPlays(play));
        }
        return music;
    }

    /**
     * 修改编辑音乐信息，ajax
     */
    public State modifyEditMusic(String id, String name, String singerId, String albumId, String classificationId, String level, String price, String activity, String available) throws DataBaseException {
        State state = isModifyEdit(id, name, singerId, albumId, classificationId, level, price, activity, available);
//                            // 先判断是否设置了MV
//                            if (music.getMusicVideoId() != 0) {
//                                // 判断MV是否存在
//                                if (idExistence.isMusicVideoId(music.getMusicVideoId()) == null) {
//                                    state.setInformation("音乐的MV不存在");
//                                    return state;
//                                }
//                            }

//                            String lyricPath = fileUpload.musicLyric(request);
//                            if (lyricPath != null && !"".equals(lyricPath)) {
//                                music.setLyricPath(lyricPath);
//                            }
//                            // 获取上传的文件路径
//                            String path = fileUpload.music(request);
//                            if (path != null && !"".equals(path)) {
//                                music.setPath(path);
//                            }
        if (state.getState() == 1) {
            Music music = new Music(Integer.valueOf(id), name, Integer.valueOf(level), new BigDecimal(price), Integer.valueOf(singerId), Integer.valueOf(albumId), Integer.valueOf(classificationId), Integer.valueOf(activity), Integer.valueOf(available));
            if (musicMapper.updateMusic(music) < 1) {
                // 如果失败是数据库错误
                logger.error(music + "修改音乐信息，数据库出错");
                throw new DataBaseException(music + "修改音乐信息，数据库出错");
            }
        }
        return state;
    }

    /**
     * 修改更多音乐信息，ajax
     */
    public State modifyMoreMusic(String id,String musicVideoId,HttpServletRequest request){
        return null;
    }



    /**
     * 用于判断音乐的编辑信息是否合法
     */
    private State isModifyEdit(String id, String name, String singerId, String albumId, String classificationId, String level, String price, String activity, String available) {
        State state = new State();
        if (validationInformation.isInt(id) && idExistence.isMusicId(Integer.valueOf(id)) != null) {
            if (validationInformation.isName(name)) {
                // 判断歌手是否存在
                if (validationInformation.isInt(singerId) && idExistence.isUserId(Integer.valueOf(singerId)) != null) {
                    // 判断专辑是否存在
                    if (validationInformation.isInt(albumId) && idExistence.isSongListId(Integer.valueOf(albumId)) != null) {
                        // 判断分类是否存在
                        if (validationInformation.isInt(classificationId) && idExistence.isClassificationId(Integer.valueOf(classificationId)) != null) {
                            // 判断等级是否存在
                            if (level.matches("([1-3])")) {
                                // 判断价格是否符合要求
                                if (validationInformation.isPrice(String.valueOf(price))) {
                                    System.out.println(activity);
                                    // 判断活动id是否符合要求
                                    if (activity.matches("([1-9][0-9]*|0)") && idExistence.isActivityId(Integer.valueOf(activity)) != null) {
                                        // 判断版权
                                        if (available.matches("([0-1])")) {
                                            state.setState(1);
                                        } else {
                                            state.setInformation("版权不符合要求");
                                        }
                                    } else {
                                        state.setInformation("活动不存在");
                                    }
                                } else {
                                    state.setInformation("音乐的价格不符合要求");
                                }
                            } else {
                                state.setInformation("音乐的等级不合法");
                            }
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
                state.setInformation("音乐的名字不符合要求");
            }
        } else {
            state.setInformation("音乐的id不存在");
        }
        return state;
    }

    /**
     * 用于判断音乐的更多信息是否合法
     */
    private State isModifyMore(String id,String musicVideoId,HttpServletRequest request) {
        State state=new State();
        // 判断音乐的id和MV的id
        if (validationInformation.isInt(id) && idExistence.isMusicId(Integer.valueOf(id)) != null) {
            if(validationInformation.isInt(musicVideoId) && idExistence.isMusicVideoId(Integer.valueOf(musicVideoId))!=null){
                // 判断接收到的3个文件是否合法

            } else {
                state.setInformation("MV的id不存在");
            }
        } else {
            state.setInformation("音乐的id不存在");
        }
        return state;
    }
}
