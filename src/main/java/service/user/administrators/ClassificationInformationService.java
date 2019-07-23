package service.user.administrators;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import service.user.IdExistence;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import java.util.*;

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
    @Resource(name = "IdExistence")
    IdExistence idExistence;
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
    public String showClassification(Integer pageNum, Model model) {
        // 查找所有类型
        List<Classification> list = classificationMapper.selectListClassification(new Classification());
        for (Classification classification : list) {
            setLanguages.add(classification.getLanguages());
            setRegion.add(classification.getRegion());
            setGender.add(classification.getGender());
            setType.add(classification.getType());
        }
        List<Classification> classificationList = new ArrayList<>();
        Iterator languagesIterator = setLanguages.iterator();
        Iterator regionIterator = setRegion.iterator();
        Iterator genderIterator = setGender.iterator();
        Iterator typeIterator = setType.iterator();
        while (languagesIterator.hasNext() || regionIterator.hasNext() || genderIterator.hasNext() || typeIterator.hasNext()) {
            String languages = "";
            String region = "";
            String gender = "";
            String type = "";
            if (languagesIterator.hasNext()) {
                languages = (String) languagesIterator.next();
            }
            if (regionIterator.hasNext()) {
                region = (String) regionIterator.next();
            }
            if (genderIterator.hasNext()) {
                gender = (String) genderIterator.next();
            }
            if (typeIterator.hasNext()) {
                type = (String) typeIterator.next();
            }
            classificationList.add(new Classification(languages, region, gender, type));
        }
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pageNum, 7);
        PageInfo pageInfo = new PageInfo<>(classificationList);
        // 传入页面信息
        logger.debug("查找到的分类" + classificationList);
        model.addAttribute("pageInfo", pageInfo);
        return "system/backgroundSystem";
    }

    /**
     * 显示指定id的分类信息
     *
     * @param id 分类的id
     */
    public String showIdClassification(Integer id, Model model) {
        Classification classification = idExistence.isClassificationId(id);
        List<Classification> list = new ArrayList<>();
        list.add(classification);
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(1, 7);
        PageInfo pageInfo = new PageInfo<>(list);
        // 传入页面信息
        logger.debug("查找到的分类" + classification);
        model.addAttribute("pageInfo", pageInfo);
        System.out.println(pageInfo);
        model.addAttribute("classificationId", id);
        return "system/backgroundSystem";
    }

    /**
     * 查找指定分类的子分类
     */
    public Set<String> showClassificationValue(String key) {
        Classification classification = new Classification();
        switch (key) {
            case "1":
                classification.setLanguages(key);
                break;
            case "2":
                classification.setRegion(key);
                break;
            case "3":
                classification.setGender(key);
                break;
            case "4":
                classification.setType(key);
                break;
            default:
                return null;
        }
        return classificationMapper.selectClassificationValue(classification);
    }

    /**
     * 添加分类
     */
    public State addClassification(String key, String value) throws DataBaseException {
        State state = new State();
        // 先判断值是否合法
        if (isClassificationLength(value)) {
            // 再判断键是否合法
            Classification classification = new Classification();
            List<Classification> list;
            if(key==null){
                state.setInformation("请选择正确的分类");
                return state;
            }
            switch (key) {
                case "1":
                    classification.setLanguages(value);
                    if (classificationMapper.selectListClassification(classification).size() == 0) {
                        list = classificationMapper.selectListClassification(new Classification());
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
                        state.setState(1);
                    } else {
                        state.setInformation("语种中已经有" + value);
                    }
                    break;
                case "2":
                    classification.setRegion(value);
                    if (classificationMapper.selectListClassification(classification).size() == 0) {
                        list = classificationMapper.selectListClassification(new Classification());
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
                        state.setState(1);
                    } else {
                        state.setInformation("地区中已经有" + value);
                    }
                    break;
                case "3":
                    classification.setGender(value);
                    if (classificationMapper.selectListClassification(classification).size() == 0) {
                        list = classificationMapper.selectListClassification(new Classification());
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
                        state.setState(1);
                    } else {
                        state.setInformation("歌手中已经有" + value);
                    }
                    break;
                case "4":
                    classification.setType(value);
                    if (classificationMapper.selectListClassification(classification).size() == 0) {
                        list = classificationMapper.selectListClassification(new Classification());
                        for (Classification c : list) {
                            setLanguages.add(c.getLanguages());
                            setRegion.add(c.getRegion());
                            setGender.add(c.getGender());
                        }
                        for (String languagesValue : setLanguages) {
                            for (String regionValue : setRegion) {
                                for (String genderValue : setGender) {
                                    classification.setLanguages(languagesValue);
                                    classification.setRegion(regionValue);
                                    classification.setGender(genderValue);
                                    if (classificationMapper.insertClassification(classification) < 1) {
                                        // 如果失败是数据库错误
                                        logger.error("分类：" + classification + "添加时，数据库出错");
                                        throw new DataBaseException("分类：" + classification + "添加时，数据库出错");
                                    }
                                }
                            }
                        }
                        state.setState(1);
                    } else {
                        state.setInformation("类型中已经有" + value);
                    }
                    break;
                default:
                    state.setInformation("请选择正确的分类");
            }
        } else {
            state.setInformation(value + "长度不合法");
        }
        return state;
    }

    /**
     * 删除分类，分类前先先判断音乐，MV，专辑是否选有指定分类，如果有删除失败
     */
    public State deleteClassification(String key, String value) throws DataBaseException {
        State state = new State();
        // 先判断值是否合法
        if (isClassificationLength(value)) {
            // 再判断键是否合法
            Classification classification = new Classification();
            if(key==null){
                state.setInformation("请选择正确的分类");
                return state;
            }
            switch (key) {
                case "1":
                    classification.setLanguages(value);
                    break;
                case "2":
                    classification.setRegion(value);
                    break;
                case "3":
                    classification.setGender(value);
                    break;
                case "4":
                    classification.setType(value);
                    break;
                default:
                    state.setInformation("请选择正确的分类");
                    return state;
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
                    state.setInformation("删除失败，有分类被使用");
                    return state;
                }
            }
            // 不存在则开始删除
            if ((classificationMapper.deleteClassification(classification)) < 1) {
                // 如果失败是数据库错误
                logger.error("分类：" + classification + "删除时，数据库出错");
                throw new DataBaseException("分类：" + classification + "删除时，数据库出错");
            }
            state.setState(1);
        }
        return state;
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

    /**
     * 用于判断输入的分类的长度是否合法
     */
    private boolean isClassificationLength(String string) {
        return string.length() > 0 && string.length() <= 6;
    }
}
