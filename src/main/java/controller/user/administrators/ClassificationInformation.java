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
 * 管理员对分类信息的操作
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
     * 添加分类
     */
    @RequestMapping(value = "/addClassification")
    @ResponseBody
    public State addClassification(@RequestBody Classification classification) throws DataBaseException {
        return null;
    }
    /**
     * 删除分类
     */
    @RequestMapping(value = "/deleteClassification")
    @ResponseBody
    public State deleteClassification(@RequestBody Classification classification) throws DataBaseException {
        return null;
    }
}
