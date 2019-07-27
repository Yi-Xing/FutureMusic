package util;

/**
 * 用于表示程序中各种常量，代替魔法值
 *
 * @author 5月16日 张易兴创建
 */
public enum ConstantUtil {
    // 表示字符串 userInformation 用于表示session和cookie的键名
    User_Information("userInformation"),
    // 表示数字 200 用于表示内容的最大长度，以及举报最大限度
    Two_Hundred(200),
    // 表示数字 6 用于验证的个数
    Six(6);
    private String stringValue = null;
    private int intValue = 0;

    ConstantUtil(int intValue) {
        this.intValue = intValue;
    }

    ConstantUtil(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public int getIntValue() {
        return intValue;
    }

    ConstantUtil() {
    }
}
