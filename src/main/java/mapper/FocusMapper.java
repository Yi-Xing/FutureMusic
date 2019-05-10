package mapper;


import entity.Focus;

import java.util.List;

/**
 * 对访客和关注表操作的Mapper
 *
 * @author 5月9日 蒋靓峣创建
 */
public interface FocusMapper {

    /**
     * 查找符合指定规则的访客信息
     *
     * @param focus 按照指定规则查找指定访客
     *                 封装信息：
     *                 userId和type查找该用户关注或访问了那些人
     *                 userFocusId和type查找该用户被谁关注或被谁访问了
     * @return List<Focus>  返回查找到的用户
     */
    public List<Focus> selectListFocus(Focus focus);
    /**
     * 添加指定访客的信息
     *
     * @param focus 访客的对象
     *                 封装信息：除id以外的所有信息
     * @return int 返回添加的条数
     */
    public int insertFocus(Focus focus);
    /**
     * 删除指定访客的信息
     *
     * @param id 访客或关注的id
     * @return int 返回删除的条数
     */
    public int deleteFocus(String id);

}
