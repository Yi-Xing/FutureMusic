//package controller.music;
//
//import entity.Music;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import service.music.TestService;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import java.util.List;
//
///**
// * 测试层
// * 用来测试写的
// */
//public class Test {
//    @Resource(name = "TestService")
//    private TestService testService;
//    /**
//     * @param request 展示指定的歌曲（巅峰榜）
//     *              分类：新歌、电音、华语、欧美、日韩
//     *                只显示3条数据
//     * @return List<Music> 返回符合条件的歌曲集合
//     * */
//    @RequestMapping(value = "/exhibitionTest")
//    @ResponseBody
//    public List<Music> exhibitionTest(HttpServletRequest request){
//        String classification = request.getParameter("classification");
//        List<Music> musicList = testService.exhibitionTest(classification);
//        return musicList;
//    }
//}
