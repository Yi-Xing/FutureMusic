package mapper;


import entity.MusicCollect;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 对音乐或MV的收藏表操作的Mapper
 *
 * @author 5月9日 张易兴创建
 *         5月18日 张易兴添加selectUserMusicCollectCount用于查找用户收藏的音乐或MV的个数
 */
@Repository(value = "MusicCollectMapper")
public interface MusicCollectMapper {
    /**
     * 查找符合指定规则的收藏信息
     *
     * @param musicCollect 按照指定规则查找指定收藏
     *                     封装信息：
     *                     userId和type查询指定用户收藏的音乐或MV
     *                     userId和type和musicId查询指定用户收藏的指定音乐或MV（用于判断用户是否收藏该音乐）
     * @return List<MusicCollect> 返回查找到的收藏
     */
    public List<MusicCollect> selectListMusicCollect(MusicCollect musicCollect);
    /**
     * 查找指定用户收藏的音乐或MV的个数
     *
     * @param musicCollect 按照指定规则查找指定收藏
     *                     封装信息：
     *                     userId和type查找指定用户收藏的音乐或MV的个数
     * @return int 返回查找到的个数
     */
    public int selectUserMusicCollectCount(MusicCollect musicCollect);
    /**
     * 添加收藏的音乐或MV
     *
     * @param musicCollect 收藏的对象
     *                     封装信息：除id以外的所有信息
     * @return int 返回添加的条数
     */
    public int insertMusicCollect(MusicCollect musicCollect);
    /**
     * 更新指定歌单或专辑中的音乐信息
     *
     * @param musicCollect 活动对象 封装所有信息
     * @return int 返回更新的条数
     */
    public int updateMusicCollect(MusicCollect musicCollect);
    /**
     * 删除指定评论
     *
     * @param id 收藏的id
     * @return int 返回删除的条数
     */
    public int deleteMusicCollect(int id);
}
