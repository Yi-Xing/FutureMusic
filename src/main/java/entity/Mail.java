package entity;

import java.util.Date;

/**
 * 邮件表 存储用户之间的邮件信息
 * @author 5月9日 张易兴创建
 */
public class Mail {
    /**
     * 主键
     */
    private int id=0;
    /**
     * 发送方的id
     */
    private int senderId=0;
    /**
     * 接收方的id
     */
    private int recipientId=0;
    /**
     * 发送的信息
     */
    private String content=null;
    /**
     * 发送的时间年月日时分秒
     */
    private Date date=null;
    /**
     * 回复那个邮件的id 0表示开头
     */
    private int reply=0;
    /**
     * 0表示未读，1表示已读，2表示标记
     */
    private int state=0;

    @Override
    public String toString() {
        return "Mail{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", recipientId=" + recipientId +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", reply=" + reply +
                ", state=" + state +
                '}';
    }

    public Mail() {
    }

    public Mail(int id, int senderId, int recipientId, String content, Date date, int reply, int state) {
        this.id = id;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.content = content;
        this.date = date;
        this.reply = reply;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getReply() {
        return reply;
    }

    public void setReply(int reply) {
        this.reply = reply;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
