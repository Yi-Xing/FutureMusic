package mapper;

import entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 对评论表操作的Mapper
 *
 * @author 5月9日 张易兴创建
 */
@Repository(value = "CommentMapper")
public interface CommentMapper {
    /**
     * 查找符合指定规则的评论信息
     *
     * @param comment 按照指定规则查找指定评论
     *                封装信息：
     *                musicId和type查找指定音乐或MV或专辑的所有评论
     *                userId查找该用户的所有评论
     *                userId和type查找指定用户的音乐或MV或专辑的所有评论
     * @return List<Comment> 返回查找到的评论
     */
    public List<Comment> selectListComment(Comment comment);

    /**
     * 添加评论
     *
     * @param comment 评论的对象
     *                封装信息：除id以外的所有信息
     * @return int 返回添加的条数
     */
    public int insertComment(Comment comment);

    /**
     * 删除指定评论
     *
     * @param id 评论的id
     * @return int 返回删除的条数
     */
    public int deleteComment(int id);
}
