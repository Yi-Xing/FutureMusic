package entity;

import java.io.Serializable;

/**
 * 分类表 用于存储音乐专辑mv的分类信息
 * @author 5月9日 张易兴创建
 */
public class Classification implements Serializable {
    /**
     * 分类的id
     */
    private int id=0;
    /**
     * 语种
     */
    private String languages=null;
    /**
     * 地区
     */
    private String region=null;
    /**
     * 性别
     */
    private String gender=null;
    /**
     * 类型
     */
    private String type=null;

    @Override
    public String toString() {
        return "Classification{" +
                "id=" + id +
                ", languages='" + languages + '\'' +
                ", region='" + region + '\'' +
                ", gender='" + gender + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public Classification() {
    }

    public Classification(int id, String languages, String region, String gender, String type) {
        this.id = id;
        this.languages = languages;
        this.region = region;
        this.gender = gender;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
