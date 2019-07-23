package test.jjy;

import entity.Music;
import mapper.MusicMapper;
import service.music.SearchService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 蒋靓峣
 */
public class Test {
    @Resource(name = "MusicMapper")
    static MusicMapper musicMapper;
    public static void main(String[] args) {
    Music music = new Music();
////    music.setSingerId(1);
//        music.setId(1);
    List<Music> musicList =
            (new SearchService()).selectListMusicByName("1");
    System.out.println(musicList);
    }
}
