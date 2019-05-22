package service.user.administrators;

import entity.*;
import mapper.ClassificationMapper;
import mapper.MusicMapper;
import mapper.MusicVideoMapper;
import mapper.SongListMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 管理员对分类信息的操作
 *
 * @author 5月20日 张易兴创建
 */
@Service(value = "ClassificationInformationService")
public class ClassificationInformationService {
    private static final Logger logger = LoggerFactory.getLogger(ClassificationInformationService.class);
    @Resource(name = "ClassificationMapper")
    ClassificationMapper classificationMapper;
    @Resource(name = "MusicMapper")
    MusicMapper musicMapper;
    @Resource(name = "MusicVideoMapper")
    MusicVideoMapper musicVideoMapper;
    @Resource(name = "SongListMapper")
    SongListMapper songListMapper;
    /**
     * 用于存储语种
     */
    private Set<String> setLanguages = new HashSet<>();
    /**
     * 用于存储地区
     */
    private Set<String> setRegion = new HashSet<>();
    /**
     * 用于存储语种
     */
    private Set<String> setGender = new HashSet<>();
    /**
     * 用于存储性别
     */
    private Set<String> setType = new HashSet<>();

    /**
     * 显示现在所有的分类
     */
    public String showClassification(Model model) {
        // 查找所有类型
        List<Classification> list = classificationMapper.selectListClassification(new Classification());
        for (Classification classification : list) {
            setLanguages.add(classification.getLanguages());
            setRegion.add(classification.getRegion());
            setGender.add(classification.getGender());
            setType.add(classification.getType());
        }
        model.addAttribute("languages", setLanguages);
        model.addAttribute("region", setRegion);
        model.addAttribute("gender", setGender);
        model.addAttribute("type", setType);
        return null;
    }

    /**
     * 显示指定id的分类信息
     *
     * @param id 分类的id
     */
    public String selectClassification(Integer id, Model model) {
        Classification classification = new Classification();
        classification.setId(id);
        List<Classification> list = classificationMapper.selectListClassification(classification);
        if (list.size() != 0) {
            model.addAttribute("classification", list.get(0));
        } else {
            model.addAttribute("state", "没有指定id的分类");
        }
        return null;
    }

    /**
     * 添加分类
     */
    public State addClassification(@RequestBody Classification classification) throws DataBaseException {
        String gender = classification.getGender();
        String languages = classification.getLanguages();
        String region = classification.getRegion();
        String type = classification.getType();
        if (gender == null || "".equals(gender)) {
            if (languages == null || "".equals(languages)) {
                if (region == null || "".equals(region)) {
                    if (type != null && !"".equals(type)) {
                        classification.setType(type);
                        List<Classification> list = classificationMapper.selectListClassification(new Classification());
                        for (Classification c : list) {
                            setLanguages.add(c.getLanguages());
                            setRegion.add(c.getRegion());
                            setGender.add(c.getGender());
                        }
                        for (String languagesValue : setLanguages) {
                            for (String regionValue : setRegion) {
                                for (String genderValue : setGender) {
                                    classification.setLanguages(languagesValue);
                                    classification.setGender(regionValue);
                                    classification.setGender(genderValue);
                                    if (classificationMapper.insertClassification(classification) < 1) {
                                        // 如果失败是数据库错误
                                        logger.error("分类：" + classification + "添加时，数据库出错");
                                        throw new DataBaseException("分类：" + classification + "添加时，数据库出错");
                                    }
                                }
                            }
                        }
                    } else {
                        classification.setRegion(region);
                        List<Classification> list = classificationMapper.selectListClassification(new Classification());
                        for (Classification c : list) {
                            setLanguages.add(c.getLanguages());
                            setGender.add(c.getGender());
                            setType.add(c.getType());
                        }
                        for (String languagesValue : setLanguages) {
                            for (String typeValue : setType) {
                                for (String genderValue : setGender) {
                                    classification.setLanguages(languagesValue);
                                    classification.setType(typeValue);
                                    classification.setGender(genderValue);
                                    if (classificationMapper.insertClassification(classification) < 1) {
                                        // 如果失败是数据库错误
                                        logger.error("分类：" + classification + "添加时，数据库出错");
                                        throw new DataBaseException("分类：" + classification + "添加时，数据库出错");
                                    }
                                }
                            }
                        }
                    }
                } else {
                    classification.setLanguages(languages);
                    List<Classification> list = classificationMapper.selectListClassification(new Classification());
                    for (Classification c : list) {
                        setRegion.add(c.getRegion());
                        setGender.add(c.getGender());
                        setType.add(c.getType());
                    }
                    for (String regionValue : setRegion) {
                        for (String typeValue : setType) {
                            for (String genderValue : setGender) {
                                classification.setRegion(regionValue);
                                classification.setType(typeValue);
                                classification.setGender(genderValue);
                                if (classificationMapper.insertClassification(classification) < 1) {
                                    // 如果失败是数据库错误
                                    logger.error("分类：" + classification + "添加时，数据库出错");
                                    throw new DataBaseException("分类：" + classification + "添加时，数据库出错");
                                }
                            }
                        }
                    }
                }
            } else {
                classification.setGender(gender);
                List<Classification> list = classificationMapper.selectListClassification(new Classification());
                for (Classification c : list) {
                    setLanguages.add(c.getLanguages());
                    setRegion.add(c.getRegion());
                    setType.add(c.getType());
                }
                for (String regionValue : setRegion) {
                    for (String typeValue : setType) {
                        for (String languagesValue : setLanguages) {
                            classification.setRegion(regionValue);
                            classification.setType(typeValue);
                            classification.setLanguages(languagesValue);
                            if (classificationMapper.insertClassification(classification) < 1) {
                                // 如果失败是数据库错误
                                logger.error("分类：" + classification + "添加时，数据库出错");
                                throw new DataBaseException("分类：" + classification + "添加时，数据库出错");
                            }
                        }
                    }
                }
            }
        }
        return new State(1);
    }

    /**
     * 删除分类，分类前先先判断音乐，MV，专辑是否选有指定分类，如果有删除失败
     */
    public String deleteClassification(@RequestBody Classification classification, Model model) throws DataBaseException {
        String gender = classification.getGender();
        String languages = classification.getLanguages();
        String region = classification.getRegion();
        String type = classification.getType();
        if (gender == null || "".equals(gender)) {
            if (languages == null || "".equals(languages)) {
                if (region == null || "".equals(region)) {
                    if (type != null && !"".equals(type)) {
                        classification.setType(type);
                    }
                } else {
                    classification.setRegion(region);
                }
            } else {
                classification.setLanguages(languages);
            }
        } else {
            classification.setGender(gender);
        }
        // 查找指定规则的所有分类
        List<Classification> list = classificationMapper.selectListClassification(classification);
        // 用来存储分类的id有没有被使用
        boolean isId;
        for (Classification c : list) {
            // 对每一个id是否被使用进行判断
            isId = isClassification(c.getId());
            //如果存在则结束
            if (isId) {
                model.addAttribute("state", "删除失败，有分类被使用");
                return null;
            }
        }
        // 不存在则开始删除
        if (classificationMapper.deleteClassification(classification) < 1) {
            // 如果失败是数据库错误
            logger.error("分类：" + classification + "删除时，数据库出错");
            throw new DataBaseException("分类：" + classification + "删除时，数据库出错");
        }
        model.addAttribute("state", "删除成功");
        return null;
    }

    /**
     * 判断音乐，MV，专辑，歌单是否有指定分类
     */
    private boolean isClassification(int id) {
        Music music = new Music();
        music.setClassificationId(id);
        MusicVideo musicVideo = new MusicVideo();
        musicVideo.setClassificationId(id);
        SongList songList = new SongList();
        songList.setClassificationId(id);
        // 查找是否含有指定分类
        if (musicMapper.selectListMusic(music).size() != 0) {
            return true;
        }
        if (musicVideoMapper.selectListMusicVideo(musicVideo).size() != 0) {
            return true;
        }
        return songListMapper.selectListSongList(songList).size() != 0;
    }
}
