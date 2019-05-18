package test.jjy;

public class ContainsTest {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String a = "l love feng ye";
        String b = "lo";
        boolean c = a.contains(b);
        //这里就是判断集合b是否包含字符或者字符串a
        System.out.println(a.contains(b));
        //assertEquals(a.contains(b), "true");
        assert (c) = true;
        //加断言
    }
}
