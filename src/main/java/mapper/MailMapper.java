package mapper;


import entity.Mail;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 对邮件表操作的Mapper
 *
 * @author 5月9日 张易兴创建
 */
@Repository(value = "MailMapper")
public interface MailMapper {
    /**
     * 查找符合指定规则的邮件信息
     *
     * @param mail 按照指定规则查找指定邮件
     *             封装信息：
     *              senderId查询发送方的所有邮件（用户的id）
     *              senderId和state查询发送方的指定状态的邮件
     *              recipientId查询接收方的所有邮件（用户的id）
     *              recipientId和state查询接收方的指定状态的邮件
     * @return List<Mail> 返回查找到的邮件
     */
    public List<Mail> selectListMail(Mail mail);
    /**
     * 添加邮箱
     *
     * @param mail 邮箱的对象
     *             封装信息：除id以外的所有信息
     * @return int 返回添加的条数
     */
    public int insertMail(Mail mail);
    /**
     * 删除指定邮箱
     *
     * @param id 邮箱的id
     * @return int 返回删除的条数
     */
    public int deleteMail(int id);
}
