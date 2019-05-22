package controller.user.administrators;

import entity.Classification;
import entity.MusicVideo;
import entity.SongList;
import entity.State;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.administrators.SongListInformationService;
import util.exception.DataBaseException;

import javax.annotation.Resource;

/**
 * 专辑
 *  显示id，用户id，分类，创建的时间，活动id
 *      更多：标题，图片，介绍
 *      查询：1、id
 *            2、活动id
 *            3、分类的id
 *            4、用户的id
 *  修改：专辑用于修改活动
 *  删除：id
 *
 */
public class SongListInformation {

    @Resource(name = "SongListInformationService")
    SongListInformationService songListInformationService;
    /**
     * 指定歌手的所有音乐被播放的次数
     * 指定专辑中的所有音乐被播放的次数
     * @param id 歌单或专辑的id
     * @param type 1表示歌单或专辑的id，2表示活动的id 3、分类的id  4、用户的id
     */
    @RequestMapping(value = "/showSongList")
    public String showSongList(Integer id,Integer type,Integer pageNum, Model model)  {
        return songListInformationService.showSongList(id,type,pageNum,model);
    }

    /**
     * 修改专辑信息，ajax
     */
    @RequestMapping(value = "/modifySongList")
    @ResponseBody
    public State modifySongList(@RequestBody SongList songList) throws DataBaseException {
        return songListInformationService.modifySongList(songList);
    }

    /**
     * 删除指定id专辑或歌单
     */
    @RequestMapping(value = "/deleteSongList")
    public String deleteSongList(Integer id) throws DataBaseException {
        return songListInformationService.deleteSongList(id);
    }
}
