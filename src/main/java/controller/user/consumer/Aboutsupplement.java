package controller.user.consumer;

import entity.Music;
import entity.MusicSongList;
import entity.SongList;
import entity.State;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.user.consumer.AboutsSupplementService;
import util.exception.DataBaseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class Aboutsupplement {
    @Resource(name = "AboutsSupplementService")
    AboutsSupplementService aboutsSupplementService;
    /**
     * 将指定音乐添加到专辑或歌单中
     *
     * @param musicSongList 获取需要添加到指定专辑或歌单中的音乐
     *                      所需参数：
     *                      belongId 专辑或歌单的id
     *                      type 1是歌单2是专辑
     *                      musicId 音乐的id
     */
    @RequestMapping(value = "/SongListAddMusic")
    @ResponseBody
    public State addMusicSongList(@RequestBody MusicSongList musicSongList, HttpSession session) throws DataBaseException {
        return aboutsSupplementService.addMusicSongList(musicSongList, session);
    }

    /**
     * 将指定音乐虫歌单中删除
     *                      所需参数：
     *                      belongId 专辑或歌单的id
     *                      type 1是歌单2是专辑
     *                      musicId 音乐的id
     */
    @RequestMapping(value = "/SongListDeleteMusic")
    @ResponseBody
    public State deleteMusicSongList(@RequestBody MusicSongList musicSongList) throws DataBaseException {
        return aboutsSupplementService.deleteMusicSongList(musicSongList);
    }


    /**
     * 得到用户的所有歌单
     */
    @RequestMapping(value = "/getSongList")
    @ResponseBody
    public List<SongList> getSongList(HttpSession session) {
        return aboutsSupplementService.getSongList(session);
    }


}
