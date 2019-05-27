package service.user.administrators;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.MusicCollect;
import entity.MusicVideo;
import entity.Play;
import entity.State;
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
import java.text.ParseException;
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
    public State addMusicVideo(MusicVideo musicVideo, HttpServletRequest request) throws DataBaseException, IOException {
        State state = new State();
        if (validationInformation.isName(musicVideo.getName())) {
            // 判断价格是否符合要求
            if (validationInformation.isPrice(String.valueOf(musicVideo.getPrice()))) {
                // 判断歌手是否存在
                if (idExistence.isUserId(musicVideo.getSingerId()) != null) {
                    // 判断音乐是否存在
                    if (idExistence.isMusicId(musicVideo.getMusicId()) != null) {
                        // 判断分类是否存在
                        if (idExistence.isClassificationId(musicVideo.getClassificationId()) != null) {
                            // 先判断是否设置了活动
                            if (musicVideo.getActivity() != 0) {
                                // 判断活动id是否存在
                                if (idExistence.isActivityId(musicVideo.getActivity()) == null) {
                                    state.setInformation("音乐的活动不存在");
                                    return state;
                                }
                            }
                            // 判断MV的介绍是否合法
                            state = validationInformation.isContent(musicVideo.getIntroduction());
                            if (state.getState() == 1) {
                                // 获取上传的文件路径
                                String path = fileUpload.musicVideo(request);
                                if (path != null && !"".equals(path)) {
                                    musicVideo.setPath(path);
                                }
                                // 设置上传日期
                                musicVideo.setDate(new Date());
                                if (musicVideoMapper.insertMusicVideo(musicVideo) < 1) {
                                    // 如果失败是数据库错误
                                    logger.error(musicVideo + "添加MV信息时，数据库出错");
                                    throw new DataBaseException(musicVideo + "添加MV信息时，数据库出错");
                                }
                            } else {
                                return state;
                            }
                        } else {
                            state.setInformation("MV的分类不存在");
                        }
                    } else {
                        state.setInformation("MV的音乐不存在");
                    }
                } else {
                    state.setInformation("MV的歌手不存在");
                }
            } else {
                state.setInformation("MV的价格不符合要求");
            }
        } else {
            state.setInformation("MV的名字不符合要求");
        }
        return state;
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
        System.out.println(list);
        PageInfo pageInfo = new PageInfo<>(list);
        // 传入页面信息
        model.addAttribute("pageInfo", pageInfo);
        return null;
    }

    /**
     * 修改MV信息，ajax
     */
    public State modifyMusicVideo(MusicVideo musicVideo, HttpServletRequest request) throws DataBaseException, IOException {
        State state = new State();
        if (validationInformation.isName(musicVideo.getName())) {
            // 判断价格是否符合要求
            if (validationInformation.isPrice(String.valueOf(musicVideo.getPrice()))) {
                // 判断歌手是否存在
                if (idExistence.isUserId(musicVideo.getSingerId()) != null) {
                    // 判断音乐是否存在
                    if (idExistence.isMusicId(musicVideo.getMusicId()) != null) {
                        // 判断分类是否存在
                        if (idExistence.isClassificationId(musicVideo.getClassificationId()) != null) {
                            // 先判断是否设置了活动
                            if (musicVideo.getActivity() != 0) {
                                // 判断活动id是否存在
                                if (idExistence.isActivityId(musicVideo.getActivity()) == null) {
                                    state.setInformation("音乐的活动不存在");
                                    return state;
                                }
                            }
                            // 判断MV的介绍是否合法
                            state = validationInformation.isContent(musicVideo.getIntroduction());
                            if (state.getState() == 1) {
                                // 获取上传的文件路径
                                String path = fileUpload.musicVideo(request);
                                if (path != null && !"".equals(path)) {
                                    musicVideo.setPath(path);
                                }
                                if (musicVideoMapper.updateMusicVideo(musicVideo) < 1) {
                                    // 如果失败是数据库错误
                                    logger.error(musicVideo + "修改MV信息，数据库出错");
                                    throw new DataBaseException(musicVideo + "修改MV信息，数据库出错");
                                }
                            } else {
                                return state;
                            }
                        } else {
                            state.setInformation("MV的分类不存在");
                        }
                    } else {
                        state.setInformation("MV的音乐不存在");
                    }
                } else {
                    state.setInformation("MV的歌手不存在");
                }
            } else {
                state.setInformation("MV的价格不符合要求");
            }
        } else {
            state.setInformation("MV的名字不符合要求");
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
}
