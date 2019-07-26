package service.music;

import com.fasterxml.jackson.annotation.JsonFormat;
import entity.Comment;
import entity.CommentExt;
import entity.User;
import mapper.CommentMapper;
import mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 蒋靓峣 7.23 创建
 */
@Service("CommentService")
public class CommentService {

    @Resource(name = "CommentMapper")
    CommentMapper commentMapper;
    @Resource(name = "UserMapper")
    UserMapper userMapper;
    /**
     * 查找评论音乐、专辑、MV通用
     * type= 0
     */
    @RequestMapping("/searchCommentByMusicId")
    public List<CommentExt> searchCommentByMusicId(int musicId,int type){
        Comment comment = new Comment();
        comment.setMusicId(musicId);
        comment.setType(type);
        return getCommentExt(comment);
    }
    /**
     * 查找评论的内容
     */
    public List<CommentExt> getCommentExt(Comment comment){
        List<Comment> comments = commentMapper.selectListComment(comment);
        List<CommentExt> commentExts = new ArrayList<>();
        for(Comment c:comments){
            CommentExt commentExt = transform(c);
            commentExts.add(commentExt);
        }
        return commentExts;
    }
    /**
     * 主评论下的所有回复
     */
    public CommentExt transform(Comment c){
        CommentExt commentExt = new CommentExt();
        commentExt.setCommentId(c.getId());
        commentExt.setContent(c.getContent());
        commentExt.setDate(c.getDate());
        commentExt.setFabulous(c.getFabulous());
        commentExt.setHasReply(c.getReply());
        //获取评论的用户的id、头像和名字
        User user = new User();
        user.setId(c.getUserId());
        User u = userMapper.selectUser(user).get(0);
        commentExt.setCommentUserName(u.getName());
        commentExt.setCommentPhoto(u.getHeadPortrait());
        //获取回复者的 id 名字
//        if(c.getReply()!=0){
//            Comment com = new Comment();
//            com.setId(c.getReply());
//            Comment comment = commentMapper.selectListComment(com).get(0);
//            User user1 = new User();
//            user1.setId(comment.getUserId());
//            User user2 = userMapper.selectUser(user1).get(0);
//            commentExt.setReplyId(comment.getUserId());
//            commentExt.setReplyName(user2.getName());
//            commentExt.setReplyPhoto(user2.getHeadPortrait());
//        }
//        commentExt.setAllSubCommentExtList(getAllReply(c.getId(),new ArrayList<CommentExt>()));
        return commentExt;
    }
    /**
     * 获取评论的全部回复
     */
    public List<CommentExt> getAllReply(int commentId,List<CommentExt> commentExts){
        Comment comment = new Comment();
        comment.setReply(commentId);
        List<Comment> comments = commentMapper.selectListComment(comment);
        System.out.println(comments);
        if(comment==null||comments.size()==0) {
            return null;
        }
        for (Comment c : comments) {
            commentExts.add(transform(c));
            getAllReply(c.getId(),commentExts);
        }
        return commentExts;
    }

    /**
     * 传入所有子评论和后代评论，全部保存到一个集合中
     */
    public List<CommentExt> getCommentExtsList(List<CommentExt> c1,List<CommentExt> c2) {
        //先将这些评论全部取出来
        for(CommentExt ce:c1){
            if(ce.getAllSubCommentExtList()!=null){
                c2.add(ce);
                getCommentExtsList(ce.getAllSubCommentExtList(),c2);
            }
            c2.add(ce);
        }
        return c2;
    }
    /**
     * 传入评论，将评论按照日期排序
     */
    public List<CommentExt> sortComment(List<CommentExt> commentExts){
        listSortByTime(commentExts);
        return commentExts;
    }
    /**
     * 将评论集合按照时间的先后顺序排序，排序后放入原来的集合中
     * @param list 需要排序的集合
     */
    private static void listSortByTime(List<CommentExt> list) {
            //排序方法
            Collections.sort(list, new Comparator<CommentExt>() {
                @Override
                public int compare(CommentExt c1, CommentExt c2) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    // format.format(o1.getTime()) 表示 date转string类型 如果是string类型就不要转换了
                    Date dt1 = null;
                    try {
                        dt1 = format.parse(format.format(c1.getDate()));
                        Date dt2 = format.parse(format.format(c2.getDate()));
                        // 这是由向小向大排序
                        if (dt1.getTime() > dt2.getTime()) {
                            return 1;
                        } else if (dt1.getTime() < dt2.getTime()) {
                            return -1;
                        } else {
                            return 0;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return -1;
                    }
                }
            });
    }
}
