package test.jjy;

import controller.music.exhibition.MusicIndex;
import entity.Music;
import entity.MusicVideoExt;
import mapper.MusicMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.music.MusicVideoService;
import service.music.SearchService;
import util.music.FileUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @author 蒋靓峣
 */
public class Test {
    public static void main(String[] args)throws Exception {
        String s = FileUtil.readInfoStream("");
        System.out.println(s);
    }

}
