package test.jjy;

import entity.Music;
import mapper.MusicMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static String test() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-mvc.xml");
        MusicMapper musicMapper = applicationContext.getBean(MusicMapper.class);
        List<Integer> integerList = new ArrayList<>();
//        integerList.add(2);
//        integerList.add(3);
        List<Music> musicList =  musicMapper.listIdSelectListMusic(integerList);
        for (Music music : musicList) {
            System.out.print(music.getPrice()+""+music.getClassificationId()+"\t"+music.getClassificationId()+"\t"+music.getAvailable());
        }
        return "jjy";
    }
    public static void main(String[] args) {
        System.out.println(test());
    }
}
