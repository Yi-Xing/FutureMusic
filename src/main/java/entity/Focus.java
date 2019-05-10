package entity;

import java.util.Date;

/**
 * 关注访客表 用于存储用户关注的其他用户和被那些用户关注
 * @author 5月10日 张易兴创建
 */
public class Focus {
    /**
     * 主键
     */
    private int id=0;
    /**
     * 关注者或访客者的id
     */
    private int userId=0;
    /**
     * 被关注者或被访客的id
     */
    private int userFocusId=0;
    /**
     * 1表示是关注 2表示的访客
     */
    private int userType=0;
    /**
     * 年月日时分秒访客和关注的时间
     */
    private Date date=null;

    @Override
    public String toString() {
        return "Focus{" +
                "id=" + id +
                ", userId=" + userId +
                ", userFocusId=" + userFocusId +
                ", userType=" + userType +
                ", date=" + date +
                '}';
    }

    public Focus() {
    }

    public Focus(int id, int userId, int userFocusId, int userType, Date date) {
        this.id = id;
        this.userId = userId;
        this.userFocusId = userFocusId;
        this.userType = userType;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserFocusId() {
        return userFocusId;
    }

    public void setUserFocusId(int userFocusId) {
        this.userFocusId = userFocusId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
