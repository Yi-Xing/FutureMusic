package service.music;

import entity.Music;
import mapper.MusicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author 蒋靓峣  5.18日创建
 * 测试一下自己写得代码能用不能
 * Service层
 */
@Service("TestService")
public class TestService {
    @RequestMapping("/main")
    public static void main(String[] args) {
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-mvc.xml");
    MusicMapper musicMapper = applicationContext.getBean(MusicMapper.class);
        List<Music> musicList =  musicMapper.listIdSelectListMusic(null);
        for (Music music : musicList) {
            System.out.print(music.getId());
        }
    }
}
