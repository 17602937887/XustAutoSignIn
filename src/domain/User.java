package domain;

public class User {
    private String uid;
    private String gh;

    public User(String uid, String gh) {
        this.uid = uid;
        this.gh = gh;
    }

    public String getUid() {
        return uid;
    }

    public String getGh() {
        return gh;
    }
}
