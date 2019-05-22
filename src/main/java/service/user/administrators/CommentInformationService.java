package service.user.administrators;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.Comment;
import entity.State;
import mapper.CommentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * 评论
 * @author 5月22日 张易兴创建
 */
@Service(value = "CommentInformationService")
public class CommentInformationService {
    private static final Logger logger = LoggerFactory.getLogger(ActivityInformationService.class);
    @Resource(name = "CommentMapper")
    CommentMapper commentMapper;
    @Resource(name = "IdExistence")
    IdExistence idExistence;
    /**
     * 显示评论信息
     *
     * @param type    选择的类型  1：音乐，2：MV，3：专辑，4：用户
     * @param id      对应类型的id
     * @param pageNum 表示当前第几页
     */
    public String showComment(Integer type, Integer id, Integer pageNum, Model model) throws ParseException {
        Comment comment = new Comment();
        if (type < 4) {
            comment.setType(type);
            comment.setMusicId(id);
        } else {
            comment.setUserId(id);
        }
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pageNum, 8);
        // 根据条件查找信息
        List<Comment> list = commentMapper.selectListComment(comment);
        PageInfo pageInfo = new PageInfo<>(list);
        // 传入页面信息
        model.addAttribute("pageInfo", pageInfo);
        return null;
    }

    /**
     * 显示指定id的评论信息
     *
     * @param id 评论的id
     */
    public String selectComment(Integer id,Model model){
        Comment comment=idExistence.isCommentId(id);
        if (comment != null) {
            model.addAttribute("Comment", comment);
        } else {
            model.addAttribute("state", "没有指定id的评论");
        }
        return null;
    }
}
