package core.vo;

public class UserStatus {
    private int id;
    private int islogin;
    private String token;
    private int uid;

    public UserStatus() {
    }

    public UserStatus(int islogin, String token, int uid) {
        this.islogin = islogin;
        this.token = token;
        this.uid = uid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIslogin() {
        return islogin;
    }

    public void setIslogin(int islogin) {
        this.islogin = islogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "UserStatus{" +
                "id=" + id +
                ", islogin=" + islogin +
                ", token='" + token + '\'' +
                ", uid=" + uid +
                '}';
    }
}
