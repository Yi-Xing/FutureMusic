package mapper;

import entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 对评论表操作的Mapper
 *
 * @author 5月9日 张易兴创建
 * 5月15日 张易兴 修改selectListComment方法，添加按id查找评论
 *                添加updateComment方法，用于更新评论的点赞次数
 *                修改selectListComment方法，按reply查找指定评论的子评论
 */
@Repository(value = "CommentMapper")
public interface CommentMapper {
    /**
     * 查找符合指定规则的评论信息
     *
     * @param comment 按照指定规则查找指定评论
     *                封装信息：
     *                id按照id查找指定评论
     *                musicId和type查找指定音乐或MV或专辑的所有评论
     *                userId查找该用户的所有评论
     *                userId和type查找指定用户的音乐或MV或专辑的所有评论
     *                reply查找指定评论的子评论
     *               date 查找最新评论
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
     * 更新评论，用于更新评论的点赞次数
     *
     * @param comment 评论的对象
     *                封装信息：id和fabulous
     * @return int 返回添加的条数
     */
    public int updateComment(Comment comment);

    /**
     * 删除指定评论
     *
     * @param id 评论的id
     * @return int 返回删除的条数
     */
    public int deleteComment(int id);
}
