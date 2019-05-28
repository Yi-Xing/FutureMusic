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

    @RequestMapping(value = "/musicComment")
    public String musicComment() {
        return "musicComment";
    }

    @RequestMapping(value = "/system")
    public String back_system() {
        return "back_system/back_system";
    }


    @RequestMapping(value = "/back_music")
    public String back_MV() {
        return "back_system/back_music";
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
}


