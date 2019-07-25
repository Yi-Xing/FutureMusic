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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
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
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
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
    public State addMusic(String name, String singerId, String albumId, String classificationId, String level, String price, String activity, String available, String musicVideoId, HttpServletRequest request) throws DataBaseException, IOException {
        // 先判断一部分信息是否合法
        State state = isModifyEdit(name, singerId, albumId, classificationId, level, price, activity, available);
        // 一部分信息验证完毕
        logger.debug("一部分信息验证的结果" + state);
        if (state.getState() == 1) {
            Music music = new Music();
            // 3个文件都必须上传所以必须都为true
            boolean[] fileCheckbox = new boolean[3];
            fileCheckbox[0] = true;
            fileCheckbox[1] = true;
            fileCheckbox[2] = true;
            state = isModifyMore(musicVideoId, fileCheckbox, request, music);
            // 更多信息验证完毕
            logger.debug("更多信息验证的结果" + state);
            if (state.getInformation() == null) {
                logger.debug("开始添加音乐");
                Music musicCopy = new Music(0, name, Integer.valueOf(level), new BigDecimal(price), Integer.valueOf(singerId), Integer.valueOf(albumId), Integer.valueOf(classificationId), new Date(), music.getPath(), music.getLyricPath(), Integer.valueOf(musicVideoId), Integer.valueOf(activity), Integer.valueOf(available), 0, music.getPicture());
                if (musicMapper.insertMusic(musicCopy) < 1) {
                    // 如果失败是数据库错误
                    logger.error(musicCopy + "添加音乐信息，数据库出错");
                    // 信息添加失败，需要对将才添加的文件进行删除
                    fileUpload.deleteFile(music.getPicture());
                    fileUpload.deleteFile(music.getPath());
                    fileUpload.deleteFile(music.getLyricPath());
                    throw new DataBaseException(musicCopy + "添加音乐信息，数据库出错");
                }
                state.setState(1);
            }
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
                if (validationInformation.isInt(condition[0])) {
                    music.setLevel(Integer.parseInt(condition[0]));
                } else {
                    music.setLevel(-1);
                }
            }
            if ((condition[1] != null) && !"".equals(condition[1])) {
                if (validationInformation.isInt(condition[1])) {
                    music.setAvailable(Integer.parseInt(condition[1]));
                } else {
                    music.setAvailable(-1);
                }
            }
            if ((condition[2] != null) && !"".equals(condition[2]) && (condition[3] != null) && !"".equals(condition[3])) {
                // 1-ID，2-名字 3-歌手 4-专辑 5-分类 6-活动
                if (!validationInformation.isInt(condition[3])) {
                    condition[3]="-1";
                }

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
        // 根据条件查找音乐信息
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
        State state = new State();
        // 先判断音乐的id是否合法
        if (validationInformation.isInt(id) && idExistence.isMusicId(Integer.valueOf(id)) != null) {
            state = isModifyEdit(name, singerId, albumId, classificationId, level, price, activity, available);
            if (state.getState() == 1) {
                if ("0".equals(available)) {
                    available = "-1";
                }
                if("0".equals(activity)){
                    activity = "-1";
                }
                Music music = new Music(Integer.valueOf(id), name, Integer.valueOf(level), new BigDecimal(price), Integer.valueOf(singerId), Integer.valueOf(albumId), Integer.valueOf(classificationId), Integer.valueOf(activity), Integer.valueOf(available));
                if (musicMapper.updateMusic(music) < 1) {
                    // 如果失败是数据库错误
                    logger.error(music + "修改音乐信息，数据库出错");
                    throw new DataBaseException(music + "修改音乐信息，数据库出错");
                }
            }
        } else {
            state.setInformation("音乐的id不存在");
        }
        return state;
    }

    /**
     * 修改更多音乐信息，ajax
     */
    public State modifyMoreMusic(String id, String musicVideoId, boolean[] checkbox, HttpServletRequest request) throws DataBaseException {
        Music music = new Music();
        State state = new State();
        // 先判断音乐id是否合法
        if (validationInformation.isInt(id) && idExistence.isMusicId(Integer.valueOf(id)) != null) {
            state = isModifyMore(musicVideoId, checkbox, request, music);
            if (state.getInformation() == null) {
                music.setId(Integer.valueOf(id));
                music.setMusicVideoId(Integer.valueOf(musicVideoId));
                if (musicMapper.updateMusic(music) < 1) {
                    // 如果失败是数据库错误
                    logger.error(music + "修改音乐信息，数据库出错");
                    // 信息修改失败，需要对将才添加的文件进行删除
                    if (checkbox[0]) {
                        fileUpload.deleteFile(music.getPicture());
                    }
                    if (checkbox[1]) {
                        fileUpload.deleteFile(music.getPath());
                    }
                    if (checkbox[2]) {
                        fileUpload.deleteFile(music.getLyricPath());
                    }
                    throw new DataBaseException(music + "修改音乐信息，数据库出错");
                }
                logger.debug("音乐信息为" + music);
                state.setState(1);
            }
        } else {
            state.setInformation("音乐的id不存在");
        }
        return state;
    }


    /**
     * 用于判断音乐的编辑信息是否合法
     */
    private State isModifyEdit(String name, String singerId, String albumId, String classificationId, String level, String price, String activity, String available) {
        State state = new State();
        if (validationInformation.isName(name)) {
            // 判断歌手是否存在
            User user;
            if (validationInformation.isInt(singerId) && (user = idExistence.isUserId(Integer.valueOf(singerId))) != null) {
                // 判断得到的用户是不是音乐人
                if (user.getLevel() == 2) {
                    // 判断专辑是否存在
                    if (validationInformation.isInt(albumId) && idExistence.isSongListId(Integer.valueOf(albumId)) != null) {
                        // 判断分类是否存在
                        if (validationInformation.isInt(classificationId) && idExistence.isClassificationId(Integer.valueOf(classificationId)) != null) {
                            // 判断等级是否存在
                            if (level.matches("([1-3])")) {
                                // 判断价格是否符合要求
                                if (validationInformation.isPrice(String.valueOf(price))) {
                                    // 判断活动id是否符合要求
                                    if ("0".equals(activity)||(activity.matches("([1-9][0-9]*)") && idExistence.isActivityId(Integer.valueOf(activity)) != null)) {
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
                    state.setInformation("指定用户不是歌手");
                }
            } else {
                state.setInformation("音乐的歌手不存在");
            }
        } else {
            state.setInformation("音乐的名字不符合要求");
        }

        return state;
    }

    /**
     * 用于判断音乐的更多信息是否合法,如果都合法则将路径封装在music中
     */
    private State isModifyMore(String musicVideoId, boolean[] checkbox, HttpServletRequest request, Music music) {
        State state = new State();
        // 判断MV的id
        if ("0".equals(musicVideoId)||"-1".equals(musicVideoId)||(musicVideoId.matches("([1-9][0-9]*)") && idExistence.isMusicVideoId(Integer.valueOf(musicVideoId)) != null)) {
            // 先得到3个文件input的name名称 判断接收到的3个文件是否合法
            if (checkbox != null && checkbox.length == 3) {
                // 音乐图片路径
                String musicPicture = null;
                //音乐文件路径
                String musicFile = null;
                if (checkbox[0]) {
                    // 音乐图片
                    musicPicture = fileUpload.musicPicture(fileUpload.getMultipartFile(request, "pictureFile"));
                    if (musicPicture == null) {
                        state.setInformation("音乐图片不符合要求");
                        return state;
                    }
                    // 得到原来的文件地址，并将其删除.
                    fileUpload.deleteFile(music.getPicture());
                    music.setPicture(musicPicture);
                    logger.debug("音乐图片的路径" + musicPicture);
                }
                if (checkbox[1]) {
                    //音乐文件
                    musicFile = fileUpload.music(fileUpload.getMultipartFile(request, "filePath"));
                    if (musicFile == null) {
                        state.setInformation("音乐文件不符合要求");
                        // 如果音乐文件不符合要求则需要将将才添加的音乐图片删除
                        if (checkbox[0]) {
                            fileUpload.deleteFile(musicPicture);
                        }
                        return state;
                    }
                    // 得到原来的文件地址，并将其删除.
                    fileUpload.deleteFile(music.getPath());
                    music.setPath(musicFile);
                    logger.debug("音乐文件的路径" + music);
                }
                if (checkbox[2]) {
                    // 歌词文件
                    String musicLyric = fileUpload.musicLyric(fileUpload.getMultipartFile(request, "lyricFilePath"));
                    if (musicLyric == null) {
                        state.setInformation("歌词文件不符合要求");
                        // 如果音乐文件不符合要求则需要将将才添加的音乐图片和音乐文件都删除删除
                        if (checkbox[0]) {
                            fileUpload.deleteFile(musicPicture);
                        }
                        if (checkbox[1]) {
                            fileUpload.deleteFile(musicFile);
                        }
                        return state;
                    }
                    // 得到原来的文件地址，并将其删除.
                    fileUpload.deleteFile(music.getLyricPath());
                    music.setLyricPath(musicLyric);
                    logger.debug("歌词文件的路径" + musicLyric);
                }
            } else {
                state.setInformation("接收到的文件参数不合法");
            }
        } else {
            state.setInformation("MV的id不存在");
        }
        return state;
    }

}
