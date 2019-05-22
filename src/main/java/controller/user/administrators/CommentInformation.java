package controller.user.administrators;

import entity.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import service.user.administrators.ActivityInformationService;
import service.user.administrators.CommentInformationService;
import service.user.consumer.AboutMusicService;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * 评论：
 * 查询：1、按id查找
 * 2、先选择类型（用户，音乐，MV，专辑），然后在输入id，查找到所有的评论
 * 显示的信息，评论以表格显示，评论id ，评论类型，音乐或MV或专辑的id，内容，时间，回复哪个评论，
 * 该评论被点赞的次数
 * 删除：按照id删除
 * @author 5月22日 张易兴创建
 */
@Controller
public class CommentInformation {
    private static final Logger logger = LoggerFactory.getLogger(ActivityInformationService.class);
    @Resource(name = "AboutMusicService")
    AboutMusicService aboutMusicService;
    @Resource(name = "CommentInformationService")
    CommentInformationService commentInformationService;

    /**
     * 显示评论信息
     *
     * @param type    选择的类型  1：音乐，2：MV，3：专辑，4：用户
     * @param id      对应类型的id
     * @param pageNum 表示当前第几页
     */
    @RequestMapping(value = "/showComment")
    public String showComment(Integer type, Integer id, Integer pageNum, Model model) throws ParseException {
        return commentInformationService.showComment(type, id, pageNum, model);
    }

    /**
     * 显示指定id的评论信息
     *
     * @param id 评论的id
     */
    @RequestMapping(value = "/selectComment")
    public String selectComment(Integer id, Model model) {
        return commentInformationService.selectComment(id, model);
    }

    /**
     * 删除指定评论，也要删除该评论的子评论
     */
    @RequestMapping(value = "/deleteComment")
    public State deleteComment(Integer id) throws DataBaseException {
        return aboutMusicService.deleteComment(id);
    }
}
