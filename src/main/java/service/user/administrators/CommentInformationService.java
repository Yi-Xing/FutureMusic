package service.user.administrators;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.Comment;
import mapper.CommentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import service.user.IdExistence;

import javax.annotation.Resource;
import java.util.List;

/**
 * 评论
 *
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
     * @param type    选择的类型  1：音乐，2：MV，3：专辑，4：用户 5：id
     * @param id      对应类型的id
     * @param pageNum 表示当前第几页
     */
    public String showComment(Integer type, Integer id, Integer pageNum, Model model) {
        Comment comment = new Comment();
        if (type != null) {
            if (type > 0 && type < 4) {
                comment.setType(type);
                comment.setMusicId(id);
            } else if (type == 4) {
                comment.setUserId(id);
            } else if (type == 5) {
                comment.setId(id);
            }
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

}
