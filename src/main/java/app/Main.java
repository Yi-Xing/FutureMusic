package app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Main {

    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/player")
    public String player() {
        return "player";
    }

    @RequestMapping(value = "/artist")
    public String artist() {
        return "artist";
    }

    @RequestMapping(value = "/musicVideo")
    public String musicVideo() {
        return "musicVideo";
    }

    @RequestMapping(value = "/mvPlayer")
    public String mvPlayer() {
        return "mvPlayer";
    }

    @RequestMapping(value = "/musics")
    public String musics() {
        return "musics";
    }

    @RequestMapping(value = "/system")
    public String back_system() {
        return "back_system/back_system";
    }


    @RequestMapping(value = "/back_music")
    public String back_music() {
        return "back_system/back_music";
    }

    @RequestMapping(value = "/back_MV")
    public String back_MV() {
        return "back_system/back_MV";
    }

    @RequestMapping(value = "/Activities")
    public String Activities() {
        return "back_system/Activities";
    }


    @RequestMapping(value = "/classification")
    public String classification() {
        return "back_system/classification";
    }

    @RequestMapping(value = "/noFind")
    public String noFind() {
        return "noFind";
    }

    @RequestMapping(value = "/personal")
    public String personal() {
        return "personal";
    }

    @RequestMapping(value = "/Email")
    public String email() {
        return "back_system/Email";
    }

    @RequestMapping(value = "/musicList")
    public String musicList() {
        return "musicList";
    }


    @RequestMapping(value = "/order")
    public String order() {
        return "back_system/order";
    }

    @RequestMapping(value = "/artists")
    public String artists() {
        return "artists";
    }

    @RequestMapping(value = "/song_list")
    public String song_list() {
        return "back_system/song_list";
    }

    @RequestMapping(value = "/collapse")
    public String collapse() {
        return "collapse";
    }

    @RequestMapping(value = "/musicLists")
    public String musicLists() {
        return "musicLists";
    }

    @RequestMapping(value = "/saveCenter")
    public String saveCenter() {
        return "saveCenter";
    }

}





