package app;

import entity.SongList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class Main {
    @RequestMapping(value = "/main")
    public String searchListSongLista() {
        return "index";
    }

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

}

