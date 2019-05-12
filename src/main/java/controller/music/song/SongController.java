package controller.music.song;

import controller.user.consumer.account.Register;
import entity.State;
import exception.DataBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.song.SongService;
import service.user.consumer.account.RegisterService;
import service.user.consumer.account.VerificationService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 歌曲的controller层
 *
 * @author 5.12 蒋靓峣创建
 * */

@Controller
public class SongController {
    private static final Logger logger = LoggerFactory.getLogger(SongController.class);
    @Autowired
    private SongService songService;

/**
     * keyup执行此方法，ajax
     *
     * @param keyWord         接收搜索的关键字(歌名)
     */

}
