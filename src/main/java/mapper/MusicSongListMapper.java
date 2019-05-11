package mapper;


import entity.MusicSongList;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 对歌单或专辑的存放的音乐表操作的Mapper
 *
 * @author 5月10日 张易兴创建
 */
@Repository(value = "MusicSongListMapper")
public interface MusicSongListMapper {

    /**
     * 查找符合指定歌单或专辑中的音乐信息
     *
     * @param musicSongList 按照指定规则查找指定歌单或专辑的存放的音乐
     *                      封装信息：
     *                      belongId和type查找指定歌单或专辑中的所有音乐
     *                      belongId和type和musicId查找指定歌单或专辑中的指定音乐（用于判断该音乐是否存在）
     * @return List<MusicSongList>  返回查找到的歌单或专辑中的音乐信息
     */
    public List<MusicSongList> selectListMusicSongList(MusicSongList musicSongList);

    /**
     * 添加指定歌单或专辑中的音乐信息
     *
     * @param musicSongList 歌单或专辑的存放的音乐的对象
     *                      封装信息：除id以外的所有信息
     * @return int 返回添加的条数
     */
    public int insertMusicSongList(MusicSongList musicSongList);
    /**
     * 更新指定歌单或专辑中的音乐信息
     *
     * @param musicSongList 歌单或专辑的存放的音乐对象 封装所有信息
     * @return int 返回更新的条数
     */
    public int updateMusicSongList(MusicSongList musicSongList);
    /**
     * 删除指定歌单或专辑中的音乐信息
     *
     * @param musicSongList 歌单或专辑的存放的音乐的对象
     *                      封装信息：
     *                      id删除指定歌单或专辑中的音乐信息
     *                      belongId和type删除所属歌单或专辑的所有音乐
     * @return int 返回删除的条数
     */
    public int deleteMusicSongList(MusicSongList musicSongList);
}
