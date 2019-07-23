package controller.music.search;

import org.springframework.stereotype.Controller;
import service.music.CommentService;

import javax.annotation.Resource;

@Controller
public class SearchComment {
    @Resource(name = "CommentService")
    CommentService commentService;
}
