package domain;

public class User {
    private String uid;
    private String gh;
    private String name;

    public User() {
    }

    public User(String uid, String gh) {
        this.uid = uid;
        this.gh = gh;
    }

    public User(String uid, String gh, String name) {
        this.uid = uid;
        this.gh = gh;
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGh() {
        return gh;
    }

    public void setGh(String gh) {
        this.gh = gh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", gh='" + gh + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
