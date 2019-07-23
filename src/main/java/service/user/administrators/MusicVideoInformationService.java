package service.user.administrators;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.*;
import mapper.MusicCollectMapper;
import mapper.MusicVideoMapper;
import mapper.PlayMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
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
import java.util.List;

/**
 * MV
 *
 * @author 5月22日 张易兴创建
 */
@Service(value = "MusicVideoInformationService")
public class MusicVideoInformationService {
    private static final Logger logger = LoggerFactory.getLogger(MusicVideoInformationService.class);
    @Resource(name = "MusicVideoMapper")
    MusicVideoMapper musicVideoMapper;
    @Resource(name = "ValidationInformation")
    ValidationInformation validationInformation;
    @Resource(name = "IdExistence")
    IdExistence idExistence;
    @Resource(name = "FileUpload")
    FileUpload fileUpload;
    @Resource(name = "MusicCollectMapper")
    MusicCollectMapper musicCollectMapper;
    @Resource(name = "PlayMapper")
    PlayMapper playMapper;

    /**
     * 添加MV
     */
    public State addMusicVideo(String name, String singerId, String musicId, String classificationId, String level, String price, String activity, String available, String introduction, HttpServletRequest request) throws DataBaseException, IOException {
        // 先判断一部分信息是否合法
        State state = isModifyEdit(name, singerId, musicId, classificationId, level, price, activity, available);
        // 一部分信息验证完毕
        logger.debug("一部分信息验证的结果" + state);
        if (state.getState() == 1) {
            MusicVideo musicVideo = new MusicVideo();
            // 3个文件都必须上传所以必须都为true
            boolean[] fileCheckbox = new boolean[2];
            fileCheckbox[0] = true;
            fileCheckbox[1] = true;
            state = isModifyMore(introduction, fileCheckbox, request, musicVideo);
            // 更多信息验证完毕
            logger.debug("更多信息验证的结果" + state);
            if (state.getInformation() == null) {
                logger.debug("开始添加MV");
                MusicVideo musicVideoCopy = new MusicVideo(0,  name, musicVideo.getPath(),  introduction,new Date(), Integer.valueOf(level) , new BigDecimal(price),  Integer.valueOf(musicId) ,  Integer.valueOf(singerId) ,  Integer.valueOf(classificationId) ,  Integer.valueOf(activity) ,  Integer.valueOf(available) ,  0, musicVideo.getPicture());
                if (musicVideoMapper.insertMusicVideo(musicVideoCopy) < 1) {
                    // 如果失败是数据库错误
                    logger.error(musicVideoCopy + "添加MV信息，数据库出错");
                    // 信息添加失败，需要对将才添加的文件进行删除
                    fileUpload.deleteFile(musicVideo.getPicture());
                    fileUpload.deleteFile(musicVideo.getPath());
                    throw new DataBaseException(musicVideoCopy + "修改MV信息，数据库出错");
                }
                state.setState(1);
            }
        }
        return state;
    }

    /**
     * 显示和按条件查询MV
     * 条件 1存储等级 2存储是否有版权 3存储id类型 4存储值
     * @param condition 条件可以有多个
     * @param pageNum   表示当前第几页
     */
    public String showMusicVideo(String[] condition, Integer pageNum, Model model) {
        // 用来存储管理员输入的条件
        MusicVideo musicVideo = new MusicVideo();
        if (condition != null) {
            if ((condition[0] != null) && !"".equals(condition[0])) {
                musicVideo.setLevel(Integer.parseInt(condition[0]));
            }
            if ((condition[1] != null) && !"".equals(condition[1])) {
                musicVideo.setAvailable(Integer.parseInt(condition[1]));
            }
            if ((condition[2] != null) && !"".equals(condition[2]) && (condition[3] != null) && !"".equals(condition[3])) {
                // 1-ID，2-名字 3-歌手 4-对应音乐 5-分类 6-活动
                switch (condition[2]) {
                    case "1":
                        musicVideo.setId(Integer.parseInt(condition[3]));
                        break;
                    case "2":
                        musicVideo.setName(condition[3]);
                        break;
                    case "3":
                        musicVideo.setSingerId(Integer.parseInt(condition[3]));
                        break;
                    case "4":
                        musicVideo.setMusicId(Integer.parseInt(condition[3]));
                        break;
                    case "5":
                        musicVideo.setClassificationId(Integer.parseInt(condition[3]));
                        break;
                    case "6":
                        musicVideo.setActivity(Integer.parseInt(condition[3]));
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
        // 根据条件查找MV信息
        List<MusicVideo> list = musicVideoMapper.selectListMusicVideo(musicVideo);
        PageInfo pageInfo = new PageInfo<>(list);
        // 传入页面信息
        logger.debug("查找到的MV" + list);
        model.addAttribute("pageInfo", pageInfo);
        return "system/backgroundSystem";
    }



    /**
     * 修改编辑MV信息，ajax
     */
    public State modifyEditMusicVideo(String id, String name, String singerId, String musicId, String classificationId, String level, String price, String activity, String available) throws DataBaseException, IOException {
        State state = new State();
        // 先判断MV的id是否合法
        if (validationInformation.isInt(id) && idExistence.isMusicVideoId(Integer.valueOf(id)) != null) {
            state = isModifyEdit(name, singerId, musicId, classificationId, level, price, activity, available);
            if (state.getState() == 1) {
                MusicVideo musicVideo = new MusicVideo(Integer.valueOf(id), name, Integer.valueOf(level), new BigDecimal(price), Integer.valueOf(musicId), Integer.valueOf(singerId), Integer.valueOf(classificationId), Integer.valueOf(activity), Integer.valueOf(available));
                if (musicVideoMapper.updateMusicVideo(musicVideo) < 1) {
                    // 如果失败是数据库错误
                    logger.error(musicVideo + "修改MV信息，数据库出错");
                    throw new DataBaseException(musicVideo + "修改MV信息，数据库出错");
                }
            }
        } else {
            state.setInformation("MV的id不存在");
        }
        return state;
    }

    /**
     * 修改更多MV信息，ajax
     */
    public State modifyMoreMusicVideo(String id, String introduction, boolean[] fileCheckbox, HttpServletRequest request) throws DataBaseException {
        MusicVideo musicVideo = new MusicVideo();
        State state = new State();
        // 先判断音乐id是否合法
        if (validationInformation.isInt(id) && idExistence.isMusicId(Integer.valueOf(id)) != null) {
            state = isModifyMore(introduction, fileCheckbox, request, musicVideo);
            if (state.getInformation() == null) {
                musicVideo.setId(Integer.valueOf(id));
                musicVideo.setIntroduction(introduction);
                if (musicVideoMapper.updateMusicVideo(musicVideo) < 1) {
                    // 如果失败是数据库错误
                    logger.error(musicVideo + "修改MV信息，数据库出错");
                    // 信息修改失败，需要对将才添加的文件进行删除
                    if (fileCheckbox[0]) {
                        fileUpload.deleteFile(musicVideo.getPicture());
                    }
                    if (fileCheckbox[1]) {
                        fileUpload.deleteFile(musicVideo.getPath());
                    }
                    throw new DataBaseException(musicVideo + "修改MV信息，数据库出错");
                }
                logger.debug("MV信息为" + musicVideo);
                state.setState(1);
            }
        } else {
            state.setInformation("MV的id不存在");
        }
        return state;
    }


    /**
     * 返回指定音乐或MV被收藏的次数
     *
     * @param id   音乐或MV的id
     * @param type 1表示是音乐收藏  2表示是MV的收藏
     */
    public int showMusicCollect(Integer id, Integer type) {
        MusicCollect musicCollect = new MusicCollect();
        musicCollect.setMusicId(id);
        musicCollect.setType(type);
        return musicCollectMapper.selectUserMusicCollectCount(musicCollect);
    }

    /**
     * 指定歌手的所有音乐被播放的次数
     * 指定专辑中的所有音乐被播放的次数
     *
     * @param id   音乐或MV或专辑的id
     * @param type 1、音乐  2、MV  3、专辑
     */
    public int showPlay(Integer id, Integer type) {
        Play play = new Play();
        if (type < 3) {
            play.setMusicId(id);
            play.setType(type);
        } else if (type == 3) {
            play.setAlbumId(id);
        }
        List<Play> list = playMapper.selectListPlay(play);
        return list.size();
    }

    /**
     * 查找指定id的MV+MV的播放量
     */
    public MusicVideo showIdMusicVideo(Integer id) {
        MusicVideo music = new MusicVideo();
        music.setId(id);
        List<MusicVideo> list = musicVideoMapper.selectListMusicVideo(music);
        music = list.get(0);
        if (music != null) {
            // 查找指定MV的播放次数
            Play play = new Play();
            play.setMusicId(id);
            play.setType(2);
            music.setPlayCount(playMapper.selectPlays(play));
        }
        return music;
    }

    /**
     * 用于判断MV的编辑信息是否合法
     */
    private State isModifyEdit(String name, String singerId, String musicId, String classificationId, String level, String price, String activity, String available) {
        State state = new State();
        if (validationInformation.isName(name)) {
            // 判断歌手是否存在
            User user;
            if (validationInformation.isInt(singerId) && (user = idExistence.isUserId(Integer.valueOf(singerId))) != null) {
                // 判断得到的用户是不是音乐人
                if (user.getLevel() == 2) {
                    // 判断音乐是否存在
                    if (validationInformation.isInt(musicId) && idExistence.isMusicId(Integer.valueOf(musicId)) != null) {
                        // 判断分类是否存在
                        if (validationInformation.isInt(classificationId) && idExistence.isClassificationId(Integer.valueOf(classificationId)) != null) {
                            // 判断等级是否存在
                            if (level.matches("([1-3])")) {
                                // 判断价格是否符合要求
                                if (validationInformation.isPrice(String.valueOf(price))) {
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
                                    state.setInformation("MV的价格不符合要求");
                                }
                            } else {
                                state.setInformation("MV的等级不合法");
                            }
                        } else {
                            state.setInformation("MV的分类不存在");
                        }
                    } else {
                        state.setInformation("音乐Id不存在");
                    }
                } else {
                    state.setInformation("指定用户不是歌手");
                }
            } else {
                state.setInformation("MV的歌手不存在");
            }
        } else {
            state.setInformation("MV的名字不符合要求");
        }
        return state;
    }

    /**
     * 用于判断音乐的更多信息是否合法,如果都合法则将路径封装在music中
     */
    private State isModifyMore(String introduction, boolean[] checkbox, HttpServletRequest request, MusicVideo musicVideo) {
        State state = new State();
        // 判断MV的内容
        if(introduction!=null && !"".equals(introduction) && introduction.length()<=300){
            // 先得到2个文件input的name名称 判断接收到的3个文件是否合法
            if (checkbox != null && checkbox.length == 2) {
                // MV图片路径
                String musicVideoPicture = null;
                if (checkbox[0]) {
                    // MV图片
                    musicVideoPicture = fileUpload.musicVideoPicure(fileUpload.getMultipartFile(request, "pictureFile"));
                    if (musicVideoPicture == null) {
                        state.setInformation("MV图片不符合要求");
                        return state;
                    }
                    // 得到原来的文件地址，并将其删除.
                    fileUpload.deleteFile(musicVideo.getPicture());
                    // 将新文件写入
                    musicVideo.setPicture(musicVideoPicture);
                    logger.debug("MV图片的路径" + musicVideoPicture);
                }
                if (checkbox[1]) {
                    //MV视频
                    String musicFile = fileUpload.musicVideo(fileUpload.getMultipartFile(request, "filePath"));
                    if (musicFile == null) {
                        state.setInformation("MV视频不符合要求");
                        // 如果音乐文件不符合要求则需要将将才添加的音乐图片删除
                        if (checkbox[0]) {
                            fileUpload.deleteFile(musicVideoPicture);
                        }
                        return state;
                    }
                    // 得到原来的文件地址，并将其删除.
                    fileUpload.deleteFile(musicVideo.getPath());
                    musicVideo.setPath(musicFile);
                    logger.debug("MV视频的路径" + musicVideo);
                }
            }else{
                state.setInformation("接收到的文件参数不合法");
            }
        } else {
            state.setInformation("MV的内容不合法");
        }
        return state;
    }
}
