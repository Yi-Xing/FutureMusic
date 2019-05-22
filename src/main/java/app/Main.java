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


    @RequestMapping(value = "/player")
    public String a() {
        return "player";
    }

}

