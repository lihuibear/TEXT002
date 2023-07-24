package lihui.bear.domain;

public class StudentEmp {
    private int sid;
    private String name;
    private String sex;
    private String classname;
    private String username;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "StudentEmp{" +
                "sid=" + sid +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", classname='" + classname + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
