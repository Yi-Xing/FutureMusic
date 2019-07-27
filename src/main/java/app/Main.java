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
    public String backSystem() {
        return "back_system/back_system";
    }


    @RequestMapping(value = "/back_music")
    public String backMusic() {
        return "back_system/back_music";
    }

    @RequestMapping(value = "/back_MV")
    public String backMv() {
        return "back_system/back_MV";
    }

    @RequestMapping(value = "/Activities")
    public String activities() {
        return "back_system/Activities";
    }


    @RequestMapping(value = "/classification")
    public String classification() {
        return "back_system/classification";
    }

    @RequestMapping(value = "/personal")
    public String personal() {
        return "personal";
    }

    @RequestMapping(value = "/talking")
    public String talking() {
        return "back_system/talking";
    }

//    @RequestMapping(value = "/Email")
//    public String email() {
//        return "back_system/Email";
//    }

    @RequestMapping(value = "/musicList")
    public String musicList() {
        return "musicList";
    }


    @RequestMapping(value = "/aaa")
    public String aaa() {
        return "sendEmail";
    }


//
//    @RequestMapping(value = "/notice")
//    public String notice() {
//        return "notice";
//    }
//
//    @RequestMapping(value = "/comment")
//    public String comment() {
//        return "comment";
//    }


    @RequestMapping(value = "/order")
    public String order() {
        return "back_system/order";
    }

    @RequestMapping(value = "/artists")
    public String artists() {
        return "artists";
    }

    @RequestMapping(value = "/song_list")
    public String songList() {
        return "back_system/song_list";
    }


    @RequestMapping(value = "/musicLists")
    public String musicLists() {
        return "musicLists";
    }

    @RequestMapping(value = "/saveCenter")
    public String saveCenter() {
        return "saveCenter";
    }
    @RequestMapping(value = "/active")
    public String active() {
        return "back_system/Activities.html";
    }

    @RequestMapping(value = "/zyx")
    public String zyx() {
        return "zyx";
    }


    @RequestMapping(value = "/security1")
    public String musicLists1() {
        return "security/bindingAccount";
    }

    @RequestMapping(value = "/security2")
    public String saveCenter1() {
        return "security/passwordVerification";
    }
    @RequestMapping(value = "/security3")
    public String active1() {
        return "security/mailboxVerification";
    }

    @RequestMapping(value = "/security4")
    public String zyx1() {
        return "security/newPassword";
    }

    @RequestMapping(value = "/jjy")
    public String jjytest() {
        return "jjy";
    }


}





