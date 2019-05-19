package mapper;


import entity.Music;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 对音乐表操作的Mapper
 *
 * @author 5月10日 张易兴创建
 */
@Repository(value = "MusicMapper")
public interface MusicMapper {

    /**
     * 查找符合指定规则的音乐信息
     *
     * @param music 按照指定规则查找指定音乐
     *              封装信息：
     *              无查询所有音乐
     *              id查询指定音乐
     *              name按照名字模糊查询（可以和别的结合）
     *              level查询指定等级的音乐
     *              singerId查询指定歌手的音乐
     *              albumId查询指定专辑的音乐
     *              classificationId查询指定分类的音乐
     *              activity查询参加指定活动的音乐
     *              available查询可听和不可听的音乐
     *              playCount播放次数，非0按次数查找排序升序
     * @return List<Music> 返回查找到的音乐
     */
    public List<Music> selectListMusic(Music music);
    /**
     * 查找符合指定规则的音乐信息
     *
     * @param id 查找指定id的音乐
     *
     * @return List<Music> 返回查找到的音乐
     */
    public List<Music> listIdSelectListMusic(List<Integer> id);
    /**
     * 查找符合指定规则的音乐信息
     *
     * @param classificationId 查找指定分类id的音乐
     *
     * @return List<Music> 返回查找到的音乐
     */
    public List<Music> listClassificationIdSelectListMusic(List<Integer> classificationId);

    /**
     * 添加音乐
     *
     * @param music 音乐的对象
     *              封装信息：除id以外的所有信息
     * @return int 返回添加的条数
     */
    public int insertMusic(Music music);// 添加音乐
    /**
     * 更新音乐信息
     *
     * @param music 音乐的对象 封装所有信息
     * @return int 返回更新的条数
     */
    public int updateMusic(Music music); //修改音乐信息

}
