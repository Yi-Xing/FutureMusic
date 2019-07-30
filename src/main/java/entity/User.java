package entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * 用户表用于存储用户的个人信息
 * @author 5月10日 张易兴创建
 *         5月14日 张易兴修改 将name，gender，age，birthday，address全部改成字符串类型
 *                              当成密保需要加密
 *        5月18日 张易兴修改 将余额类型改为BigDecimal
 */
public class User implements Serializable {
    /**
     * 用户id(主键)
     */
    private int id=0;
    /**
     * 邮箱，发邮件进行验证，可用来找回密码
     */
    private String mailbox=null;
    /**
     * 密码需要加密
     */
    private String password=null;
    /**
     * 名字 2-6个字符
     */
    private String name=null;
    /**
     * 性别 需要加密
     */
    private String gender=null;
    /**
     * 年龄 需要加密
     */
    private String age=null;
    /**
     * 生日 年月日 需要加密
     */
    private String birthday=null;
    /**
     * 地址 需要加密
     */
    private String address=null;
    /**
     * 等级，0为普通用户、1为vip用户、2为音乐人、3为客服、4为管理员、5为总管理员只有一个
     */
    private int level=0;
    /**
     * 余额
     */
    private BigDecimal balance=new BigDecimal("0");
    /**
     * 创号的时间 年月日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date=null;
    /**
     * 头像图片路径
     */
    private String headPortrait="/static/file/userHeadPortrait/Personal.PNG";
    /**
     * vip的到期时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date vipDate=null;
    /**
     * 个人空间的信息是否公开，0为公开，1为封闭
     */
    private int secret=0;
    /**
     * 用户为那些评论点过赞的评论id用#分隔
     */
    private String fabulous=null;
    /**
     * 被举报的次数
     */
    private int report=0;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", mailbox='" + mailbox + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", birthday=" + birthday +
                ", address='" + address + '\'' +
                ", level=" + level +
                ", balance=" + balance +
                ", date=" + date +
                ", headPortrait='" + headPortrait + '\'' +
                ", vipDate=" + vipDate +
                ", secret=" + secret +
                ", fabulous='" + fabulous + '\'' +
                ", report=" + report +
                '}';
    }

    public User() {
    }

    public User(int id, String mailbox, String password, String name, String gender, String age, String birthday, String address, int level, BigDecimal balance, Date date, String headPortrait, Date vipDate, int secret, String fabulous, int report) {
        this.id = id;
        this.mailbox = mailbox;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.birthday = birthday;
        this.address = address;
        this.level = level;
        this.balance = balance;
        this.date = date;
        this.headPortrait = headPortrait;
        this.vipDate = vipDate;
        this.secret = secret;
        this.fabulous = fabulous;
        this.report = report;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public Date getVipDate() {
        return vipDate;
    }

    public void setVipDate(Date vipDate) {
        this.vipDate = vipDate;
    }

    public int getSecret() {
        return secret;
    }

    public void setSecret(int secret) {
        this.secret = secret;
    }

    public String getFabulous() {
        return fabulous;
    }

    public void setFabulous(String fabulous) {
        this.fabulous = fabulous;
    }

    public int getReport() {
        return report;
    }

    public void setReport(int report) {
        this.report = report;
    }
}
