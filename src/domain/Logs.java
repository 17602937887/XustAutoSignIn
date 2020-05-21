package domain;

import java.util.Date;

public class Logs {
    private String gh;
    private String msg;
    private Date time;

    public Logs() {
    }

    public Logs(String gh, String msg, Date time) {
        this.gh = gh;
        this.msg = msg;
        this.time = time;
    }

    public String getGh() {
        return gh;
    }

    public void setGh(String gh) {
        this.gh = gh;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Logs{" +
                "gh='" + gh + '\'' +
                ", msg='" + msg + '\'' +
                ", time=" + time +
                '}';
    }
}
