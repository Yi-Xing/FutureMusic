package mapper;


import entity.SongListCollect;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 对收藏歌单或专辑表操作的Mapper
 *
 * @author 5月9日 张易兴创建
 */
@Repository(value = "SongListCollectMapper")
public interface SongListCollectMapper {
    /**
     * 查找符合指定规则的收藏的歌单或专辑信息
     *
     * @param musicSongList 按照指定规则查找指定收藏的歌单或专辑
     *                      封装信息：
     *                      userId和type查询指定用户收藏的所有专辑或歌单
     *                      userId和musicId和type查询指定用户收藏的指定专辑或歌单（用于判断是否收藏某歌单或专辑）
     * @return List<SongListCollect>  返回查找到的指定歌单或专辑的信息
     */
    public List<SongListCollect> selectListSongListCollect(SongListCollect musicSongList);
    /**
     * 添加定收藏的歌单或专辑的信息
     *
     * @param musicSongList 收藏的歌单或专辑对象
     *                      封装信息：除id以外的所有信息
     * @return int 返回添加的条数
     */
    public int insertSongListCollect(SongListCollect musicSongList);
    /**
     * 删除指定收藏的歌单或专辑的信息
     *
     * @param id 收藏的歌单或专辑的id
     * @return int 返回删除的条数
     */
    public int deleteSongListCollect(int id);
}
