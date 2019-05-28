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
import util.JudgeIsOverdueUtil;

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
     * @param condition 选择的类型  1：音乐，2：MV，3：专辑，4：用户 5：id
     *                  对应类型的id
     * @param pageNum   表示当前第几页
     */
    public PageInfo showComment(String[] condition, Integer pageNum, Model model) {
        Comment comment = new Comment();
        if (condition != null) {
            if ((condition[0] != null) && !"".equals(condition[0])) {
                comment.setType(1);
                comment.setMusicId(Integer.parseInt(condition[0]));
            }
            if ((condition[1] != null) && !"".equals(condition[1])) {
                comment.setType(2);
                comment.setMusicId(Integer.parseInt(condition[1]));
            }
            if ((condition[2] != null) && !"".equals(condition[2])) {
                comment.setType(3);
                comment.setMusicId(Integer.parseInt(condition[2]));
            }
            if ((condition[3] != null) && !"".equals(condition[3])) {
                comment.setUserId(Integer.parseInt(condition[3]));
            }
            if ((condition[4] != null) && !"".equals(condition[4])) {
                comment.setId(Integer.parseInt(condition[4]));
            }
        }
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pageNum, 8);
        // 根据条件查找信息
        List<Comment> list = commentMapper.selectListComment(comment);
        // 传入页面信息
        return  new PageInfo<>(list);
    }

}
