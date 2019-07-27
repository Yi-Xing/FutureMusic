package mapper;


import entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 对用户表操作的Mapper
 *
 * @author 5月9日 蒋靓峣创建
 *         5月14日 张易兴修改 添加selectUserMailbox方法（方便按邮箱查询）
 *         5月26日 张易兴添加 listIdSelectListUser方法 查找list集合查找用户
 */
@Repository(value = "UserMapper")
public interface UserMapper {

    /**
     * 查找符合指定规则的用户信息
     *
     * @param user 按照指定规则查找指定用户
     *                 封装信息：
     *                 无查找所有用户
     *                 id查找指定用户
     *                 name按名字模糊查询用户
     *                 mailbox按邮箱模糊查询用户
     *                level查找指定等级的账号
     *                report查找已被封号的账号
     * @return List<User>  返回查找到的用户
     */
    public List<User> selectUser(User user);

    /**
     *
     *
     */
    public List<User> selectUsers(User user);

    /**
     * 查找符合指定规则的用户信息
     *
     * @param mailbox 按照mailbox查询用户
     *
     * @return User  返回查找到的用户
     */
    public User selectUserMailbox(String mailbox);
    /**
     * 查找符合指定规则的用户信息
     *
     * @param id 查找指定id的用户的集合
     *
     * @return List<User> 返回查找到的用户
     */
    public List<User> listIdSelectListUser(List<Integer> id);

    /**
     * 添加指定用户的信息
     *
     * @param user 用户的对象
     *                 封装信息：除id以外的所有信息
     * @return int 返回添加的条数
     */
    public int insertUser(User user);
    /**
     * 更新指定用户的信息
     *
     * @param user 用户对象 封装所有信息
     * @return int 返回更新的条数
     */
    public int updateUser(User user);
}
