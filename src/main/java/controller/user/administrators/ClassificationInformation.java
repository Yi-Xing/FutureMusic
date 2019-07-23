package controller.user.administrators;

import entity.Classification;
import entity.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.administrators.ClassificationInformationService;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 分类：
 * 可添加：添加的时候选择需要添加的分类类型（如：语种，地区等）然后输入分类的具体名称
 * 显示：显示方式分类类型，再显示分类的具体内容
 * 可删除：先选择需要删除的分类类型，然后选择名称（删除前要判断有没有音乐或MV或专辑或歌单使用该分类）
 *
 * @author 5月20日 张易兴创建
 */
@Controller
@RequestMapping(value = "/administrators")
public class ClassificationInformation {
    private static final Logger logger = LoggerFactory.getLogger(ClassificationInformation.class);
    @Resource(name = "ClassificationInformationService")
    ClassificationInformationService classificationInformationService;

    /**
     * 显示现在所有的分类
     */
    @RequestMapping(value = "/showClassification")
    public String showClassification( @RequestParam(defaultValue = "1") Integer pageNum, Model model) {
        return classificationInformationService.showClassification(pageNum,model);
    }

    /**
     * 显示指定id的分类信息
     * @param id 分类的id
     */
    @RequestMapping(value = "/showIdClassification")
    public String showIdClassification(Integer id,Model model){
        return classificationInformationService.showIdClassification(id,model);
    }


    /**
     *  查找指定分类的子分类
     */
    @RequestMapping(value = "/showClassificationValue")
    @ResponseBody
    public Set<String> showClassificationValue(String key){
        return classificationInformationService.showClassificationValue(key);
    }

    /**
     * 添加分类
     */
    @RequestMapping(value = "/addClassification")
    @ResponseBody
    public State addClassification(String key, String value)throws DataBaseException {
        return classificationInformationService.addClassification(key,value);
    }

    /**
     * 删除分类
     */
    @RequestMapping(value = "/deleteClassification")
    @ResponseBody
    public State deleteClassification(String key, String value) throws DataBaseException {
        return classificationInformationService.deleteClassification(key,value);
    }

}
