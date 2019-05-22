package service.user.administrators;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.Comment;
import entity.State;
import mapper.CommentMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * 评论
 */
@Service(value = "CommentInformationService")
public class CommentInformationService {
    @Resource(name = "CommentMapper")
    CommentMapper commentMapper;

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
     * 显示指定id的分类信息
     * @param id 分类的id
     */
    public String selectComment(Integer id,Model model){
        Comment comment = new Comment();
        comment.setId(id);
        List<Comment> list = commentMapper.selectListComment(comment);
        if (list.size() != 0) {
            model.addAttribute("classification", list.get(0));
        } else {
            model.addAttribute("state", "没有指定id的评论");
        }
        return null;
    }

}
