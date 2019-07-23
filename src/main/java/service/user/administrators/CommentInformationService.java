package service.user.administrators;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.Comment;
import mapper.CommentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.Arrays;
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

    /**
     * 显示评论信息
     *
     * @param condition 选择的类型  1：音乐，2：MV，3：专辑，4：用户 5：id 6：类型
     *                  对应类型的id
     * @param pageNum   表示当前第几页
     */
    public String showComment(String[] condition, Integer pageNum, Model model) {
        Comment comment = new Comment();
        if (condition != null) {
            System.out.println(Arrays.toString(condition));
            if((condition[0] != null) && !"".equals(condition[0])) {
                    comment.setType(Integer.valueOf(condition[0]));
            }
            if ((condition[2] != null) && !"".equals(condition[2])&& condition[1] != null && !"".equals(condition[1])) {
                // 1表示是id 2表示是接收方 3表示发送方
                switch (condition[2]) {
                    case "4":
                            comment.setType(1);
                            comment.setMusicId(Integer.parseInt(condition[1]));
                        break;
                    case "5":
                            comment.setType(2);
                            comment.setMusicId(Integer.parseInt(condition[1]));
                        break;
                    case "6":
                            comment.setType(3);
                            comment.setMusicId(Integer.parseInt(condition[1]));
                        break;
                    case "7":
                            comment.setUserId(Integer.parseInt(condition[1]));
                        break;
                    case "8":
                            comment.setId(Integer.parseInt(condition[1]));
                        break;
                    case "9":
                            if ("0".equals(condition[1])) {
                                condition[1] = "-1";
                            }
                            comment.setReply(Integer.parseInt(condition[1]));
                        break;
                    default:
                }
            }
            model.addAttribute("conditionZero", condition[0]);
            model.addAttribute("conditionOne", condition[1]);
            model.addAttribute("conditionTwo", condition[2]);
        } else {
            model.addAttribute("conditionZero", null);
            model.addAttribute("conditionOne", null);
            model.addAttribute("conditionTwo", null);
        }
        //在查询之前传入当前页，然后多少记录
        PageHelper.startPage(pageNum, 7);
        // 根据条件查找信息
        System.out.println(comment);
        List<Comment> list = commentMapper.selectListComment(comment);
        logger.debug("评论的信息" + list);
        // 传入页面信息
        model.addAttribute("pageInfo", new PageInfo<>(list));
        return "system/backgroundSystem";
    }

    /**
     * 查找指定id的评论信息
     */
    public Comment showIdComment(Integer id) {
        Comment comment = new Comment();
        comment.setId(id);
        List<Comment> list = commentMapper.selectListComment(comment);
        return list.get(0);
    }

}
