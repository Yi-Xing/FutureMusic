package util;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 一个用户 相同url 同时提交 相同数据 验证
 *
 * @author Administrator
 */
// 表示该注解可用于方法上
@Target(ElementType.METHOD)
//他们能在运行时被JVM或其他使用反射机制的代码所读取和使用
@Retention(RetentionPolicy.RUNTIME)
public @interface SameUrlData {


}
