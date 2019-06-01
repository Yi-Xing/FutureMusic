package controller.user.administrators;

import com.github.pagehelper.PageInfo;
import entity.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.administrators.ActivityInformationService;
import service.user.administrators.CommentInformationService;
import service.user.consumer.AboutMusicService;
import util.exception.DataBaseException;

import javax.annotation.Resource;

/**
 * 评论：
 * 查询：1、按id查找
 * 2、先选择类型（用户，音乐，MV，专辑），然后在输入id，查找到所有的评论
 * 显示的信息，评论以表格显示，评论id ，评论类型，音乐或MV或专辑的id，内容，时间，回复哪个评论，
 * 该评论被点赞的次数
 * 删除：按照id删除
 *
 * @author 5月22日 张易兴创建
 */
@Controller
@RequestMapping(value = "/administrators")
public class CommentInformation {
    private static final Logger logger = LoggerFactory.getLogger(ActivityInformationService.class);
    @Resource(name = "CommentInformationService")
    CommentInformationService commentInformationService;
    @Resource(name = "AboutMusicService")
    AboutMusicService aboutMusicService;

    /**
     * 显示评论信息
     *
     * @param condition 选择的类型  1：音乐，2：MV，3：专辑，4：用户 5：id
     *                  对应类型的id
     * @param pageNum   表示当前第几页
     */
    @RequestMapping(value = "/showComment")
    @ResponseBody
    public PageInfo showComment(String[] condition, Integer pageNum, Model model) {
        return commentInformationService.showComment(condition, pageNum, model);
    }


    /**
     * 删除指定评论，也要删除该评论的子评论
     */
    @RequestMapping(value = "/deleteComment")
    @ResponseBody
    public State deleteComment(Integer id) throws DataBaseException {
        return aboutMusicService.deleteComment(id);
    }
}
