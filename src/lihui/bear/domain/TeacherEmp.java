package lihui.bear.domain;

public class TeacherEmp {
    private int tid;
    private String name;
    private String tel;
    private String username;

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "TeacherEmp{" +
                "tid=" + tid +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

}
