package mapper;


import entity.MusicVideo;

import java.util.List;

/**
 * 对MV表操作的Mapper
 *
 * @author 5月10日 张易兴创建
 */
public interface MusicVideoMapper {
    /**
     * 查找符合指定规则的音乐信息
     *
     * @param musicVideo 按照指定规则查找指定MV
     *                   封装信息：
     *                   无查询所有MV
     *                   id查询指定MV
     *                   name按照名字模糊查询（可以和别的结合）
     *                   level查询指定等级的MV
     *                   singerId查询指定歌手的MV
     *                   classificationId查询指定分类的MV
     *                   activity查询参加指定活动的MV
     *                   available查询可听和不可听的MV
     * @return List<MusicVideo> 返回查找到的音乐
     */
    public List<MusicVideo> selectListMusicVideo(MusicVideo musicVideo);
    /**
     * 查找符合指定规则的MV信息
     *
     * @param id 查找指定id的MV
     *
     * @return List<MusicVideo> 返回查找到的MV
     */
    public List<MusicVideo> listIdSelectListMusicVideo(List<Integer> id);


    /**
     * 添加MV
     *
     * @param musicVideo MV的对象
     *                   封装信息：除id以外的所有信息
     * @return int 返回添加的条数
     */
    public int insertMusicVideo(MusicVideo musicVideo);
    /**
     * 更新MV信息
     *
     * @param musicVideo MV的对象 封装所有信息
     * @return int 返回更新的条数
     */
    public int updateMusicVideo(MusicVideo musicVideo);
}
