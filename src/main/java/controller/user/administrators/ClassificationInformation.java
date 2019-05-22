package controller.user.administrators;

import entity.Classification;
import entity.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.administrators.ClassificationInformationService;
import util.exception.DataBaseException;

import javax.annotation.Resource;

/**
 * 分类：
 * 可添加：添加的时候选择需要添加的分类类型（如：语种，地区等）然后输入分类的具体名称
 * 显示：显示方式分类类型，再显示分类的具体内容
 * 可删除：先选择需要删除的分类类型，然后选择名称（删除前要判断有没有音乐或MV或专辑或歌单使用该分类）
 *
 * @author 5月20日 张易兴创建
 */
@Controller
public class ClassificationInformation {
    private static final Logger logger = LoggerFactory.getLogger(ClassificationInformation.class);
    @Resource(name = "ClassificationInformationService")
    ClassificationInformationService classificationInformationService;

    /**
     * 显示现在所有的分类
     */
    @RequestMapping(value = "/showClassification")
    public String showClassification(Model model) {
        return classificationInformationService.showClassification(model);
    }

    /**
     * 显示指定id的分类信息
     * @param id 分类的id
     */
    @RequestMapping(value = "/selectClassification")
    public String selectClassification(Integer id,Model model){
        return classificationInformationService.selectClassification(id,model);
    }
    /**
     * 添加分类
     */
    @RequestMapping(value = "/addClassification")
    @ResponseBody
    public State addClassification(@RequestBody Classification classification) throws DataBaseException {
        return classificationInformationService.addClassification(classification);
    }

    /**
     * 删除分类
     */
    @RequestMapping(value = "/deleteClassification")
    public String deleteClassification(@RequestBody Classification classification,Model model) throws DataBaseException {
        return classificationInformationService.deleteClassification(classification,model);
    }
}
