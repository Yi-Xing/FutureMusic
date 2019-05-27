package test.jjy;

import controller.music.exhibition.Exhibition;
import service.music.ExhibitionService;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        Map<Integer,Integer> integerIntegerMap = new HashMap<>(16);
        integerIntegerMap.put(1,1);
        integerIntegerMap.put(2,9);
        integerIntegerMap.put(3,7);
        integerIntegerMap.put(4,3);
        integerIntegerMap.put(5,6);
        integerIntegerMap.put(6,7);
        ExhibitionService exhibitionService = new ExhibitionService();
        Map<Integer,Integer> integerIntegerMap1 = exhibitionService.sortByValueDescending(integerIntegerMap);
        System.out.println(integerIntegerMap1);
    }
}
