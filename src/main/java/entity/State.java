package entity;

import java.io.Serializable;

/**
 * 存储各种状态
 * @author 5月11日 张易兴创建
 */
public class State implements Serializable {
    /**
     * 执行结果的状态 1表示成功，0表示失败
     */
    private int state=0;
    /**
     * 执行结果的提示信息
     */
    private String information=null;

    @Override
    public String toString() {
        return "State{" +
                "state=" + state +
                ", information='" + information + '\'' +
                '}';
    }

    public State() {
    }

    public State(int state, String information) {
        this.state = state;
        this.information = information;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
