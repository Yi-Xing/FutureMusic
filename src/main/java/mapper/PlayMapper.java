package mapper;


import entity.Play;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 对浏览记录表操作的Mapper
 *
 * @author 5月10日 张易兴创建
 */
@Repository(value = "PlayMapper")
public interface PlayMapper {
    /**
     * 查找符合指定规则的播放信息
     *
     * @param play 按照指定规则查找指定播放信息
     *              封装信息：
     *              userId和type查询指定用户播放过的音乐或MV
     *              userId和type和musicId查询指定用户播放过的指定音乐或MV
     *             （用于判断该用户是否播放过的该音乐或MV，如果播放过则只需要更改时间即可）
     * @return List<Play>  返回查找到的播放信息
     */
    public List<Play> selectListPlay(Play play);

    /**
     * 查找指定音乐MV 专辑歌手的音乐播放量
     * @param play 按照指定规则查找指定播放量
     *             type和musicId查询音乐或MV的播放量
     *             singerId歌手所有音乐的播放量
     *             userId用户音乐和VM的播放量
     *             albumId专辑的播放量
     * @return int  返回查找到的播放量
     */
    public int selectPlays(Play play);
    /**
     * 添加指定播放的信息
     *
     * @param play 播放的对象
     *              封装信息：除id以外的所有信息
     * @return int 返回添加的条数
     */

    public int insertPlay(Play play);
    /**
     * 更新指定播放的信息
     *
     * @param play 播放对象 封装所有信息
     * @return int 返回更新的条数
     */
    public int updatePlay(Play play);
    /**
     * 删除指定播放的信息
     *
     * @param id 播放的id
     * @return int 返回删除的条数
     */
    public int deletePlay(int id);
}
