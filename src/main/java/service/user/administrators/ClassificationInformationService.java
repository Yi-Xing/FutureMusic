package service.user.administrators;

import controller.user.administrators.ClassificationInformation;
import entity.Classification;
import entity.State;
import mapper.ClassificationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    /**
     * 显示现在所有的分类
     */
    public String showClassification(Model model) {
        // 用于存储语种
        Set<String> languages=new HashSet<>();
        // 用于存储地区
        Set<String> region=new HashSet<>();
        // 用于存储语种
        Set<String> gender=new HashSet<>();
        // 用于存储性别
        Set<String> type=new HashSet<>();
        // 查找所有类型
        List<Classification> list= classificationMapper.selectListClassification(new Classification());
        for (Classification classification : list) {
            languages.add(classification.getLanguages());
            region.add(classification.getRegion());
            gender.add(classification.getGender());
            type.add(classification.getType());
        }
        model.addAttribute("languages",languages);
        model.addAttribute("region",region);
        model.addAttribute("gender",gender);
        model.addAttribute("type",type);
        return null;
    }

    /**
     * 添加分类
     */
    public State addClassification(@RequestBody Classification classification) throws DataBaseException {
        return null;
    }
    /**
     * 删除分类，分类前先先判断音乐，MV，专辑是否选有指定分类，如果有删除失败
     */
    public State deleteClassification(@RequestBody Classification classification) throws DataBaseException {
        return null;
    }
}
