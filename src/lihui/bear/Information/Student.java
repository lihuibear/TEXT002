package lihui.bear.Information;

import lihui.bear.utils.jdbcUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static lihui.bear.Information.logindemo.studentLogin;
import static lihui.bear.main.demo.menu;

public class Student {
    public static void studentmenu(String username, String password) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=========欢迎进入学生端系统=========");
        System.out.println("1.个人信息查询");
        System.out.println("2.个人信息修改");
        System.out.println("3.账号密码修改");
        System.out.println("4.选课情况");
        System.out.println("5.选课");
        System.out.println("6.退课");
        System.out.println("输入其他返回上一级菜单");
        while (true) {
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("个人信息查询");
                    find(username, password);
                    break;
                case "2":
                    System.out.println("个人信息修改");
                    revise(username, password);
                    break;
                case "3":
                    System.out.println("账号密码修改");
                    newpassword(username, password);
                    break;
                case "4":
                    System.out.println("选课情况");
                    findsubject(username, password);
                    studentmenu(username, password);
                    break;
                case "5":
                    System.out.println("选课");
                    choicesubject(username, password);
                    break;
                case "6":
                    System.out.println("退课");
                    deletesubject(username, password);
                    break;
                default:
                    menu();
            }
        }
    }

    //个人信息查询
    public static void find(String username, String password) {
        JdbcTemplate template = new JdbcTemplate(jdbcUtils.getDataSource());
        Scanner sc = new Scanner(System.in);
        String sql = "select username 账号,sid id,name 姓名,sex 性别,classname 班级 from student where username = ?";
        Map<String, Object> map = template.queryForMap(sql, username);
        System.out.println(map);
        studentmenu(username, password);
    }

    //个人信息修改
    public static void revise(String username, String password) {
        JdbcTemplate template = new JdbcTemplate(jdbcUtils.getDataSource());
        Scanner sc = new Scanner(System.in);
        System.out.println("1.修改姓名");
        System.out.println("2.修改性别");
        System.out.println("3.修改班级");
//        System.out.println("4.修改账号");
//
        System.out.println("输入其他返回上一级菜单");
        while (true) {
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    String sql1 = "update student set name = ? where username = ?";
                    System.out.println("输入新的姓名");
                    String new_name = sc.nextLine();
                    template.update(sql1, new_name, username);
                    System.out.println("修改成功，请继续选择修改的内容或者退出回到上级菜单");
                    break;
                case "2":
                    String sql2 = "update student set sex = ? where username = ?";
                    System.out.println("输入新的性别");
                    String new_sex = sc.nextLine();
                    template.update(sql2, new_sex, username);
                    System.out.println("修改成功，请继续选择修改的内容或者退出回到上级菜单");
                    break;
                case "3":
                    String sql3 = "update student set classname = ? where username = ?";
                    System.out.println("输入新的班级");
                    String new_classname = sc.nextLine();
                    template.update(sql3, new_classname, username);
                    System.out.println("修改成功，请继续选择修改的内容或者退出回到上级菜单");
                    break;
//                case "4":
//                    String sql4 = "update student set username = ? where username = ?";
//                    System.out.println("输入新的账户");
//                    String new_username = sc.nextLine();
//                    template.update(sql4, new_username, username);
//                    System.out.println("修改成功，请继续选择修改的内容或者退出回到上级菜单");
//                    break;
                default:
                    studentmenu(username, password);
            }
        }
    }

    //账号密码修改
    public static void newpassword(String username, String password) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入当前密码");
        String oldpassword = sc.nextLine();
        int flag = 0;
        JdbcTemplate template = new JdbcTemplate(jdbcUtils.getDataSource());

        while (!oldpassword.equals(password) && flag < 3) {
            flag++;
            System.out.println("密码错误，还剩" + (3 - flag) + "次尝试");
            if (flag < 3) {
                System.out.println("请重新输入学生密码：");
                oldpassword = sc.nextLine();
            }
        }

        if (oldpassword.equals(password)) {
            System.out.println("请输入新的密码");
            String new_password = sc.nextLine();
            System.out.println("请再次输入新的密码");
            String new_password2 = sc.nextLine();

            if (new_password.equals(new_password2)) {
                String sql = "update student set password = ? where username = ?";
                template.update(sql, new_password, username);
                System.out.println("修改成功，请返回上一界面重新登录");
                studentLogin();
            } else {
                System.out.println("您前后输入的密码不一致，不可修改");
                studentmenu(username, password);
            }
        } else {
            System.out.println("失败次数过多，返回上一界面");
            studentLogin();
        }
    }


    //选课情况
    public static void findsubject(String username, String password) {
        JdbcTemplate template = new JdbcTemplate(jdbcUtils.getDataSource());

        String sql = "SELECT student.name 学生姓名, subject.suid 课程id, subject.suname 课程, teacher.name 教师姓名 " +
                "FROM student " +
                "LEFT JOIN sandt ON student.sid = sandt.sid " +
                "LEFT JOIN subject ON `subject`.suid = sandt.suid " +
                "LEFT JOIN teacher ON subject.tid = teacher.tid " +
                "WHERE student.username = ?";

        List<Map<String, Object>> list = template.queryForList(sql, username);

        if (list.isEmpty()) {
            System.out.println("你还没有选课");
        } else {
            for (Map<String, Object> stringObjectMap : list) {
                System.out.println(stringObjectMap);
            }
        }

    }

    //选课
    public static void choicesubject(String username, String password) {
        JdbcTemplate template = new JdbcTemplate(jdbcUtils.getDataSource());
        Scanner sc = new Scanner(System.in);
        System.out.println("目前开设的课程有：");
        String sql1 = "SELECT subject.suid AS 课程id, subject.suname AS 课程名称, teacher.name AS 教学老师\n" +
                "FROM subject\n" +
                "JOIN teacher ON subject.tid = teacher.tid\n";
        List<Map<String, Object>> list = template.queryForList(sql1);
        for (Map<String, Object> stringObjectMap : list) {
            System.out.println(stringObjectMap);
        }

        String sql2 = "SELECT sid FROM student WHERE username = ?";
        Integer sid = template.queryForObject(sql2, Integer.class, username);
        System.out.println("输入课程id");
        int subjectid = sc.nextInt();

        // 检查学生是否已经选过该课程
        String sql4 = "SELECT COUNT(*) FROM sandt WHERE sid = ? AND suid = ?";
        int count = template.queryForObject(sql4, Integer.class, sid, subjectid);
        if (count > 0) {
            System.out.println("您已经选过该课程，不允许重复选择！");
            studentmenu(username, password);
            return;
        }

        String sql3 = "INSERT INTO sandt (sid, suid) VALUES (?, ?)";
        template.update(sql3, sid, subjectid);

        System.out.println("选课成功！");
        studentmenu(username, password);
    }

    //退课
    public static void deletesubject(String username, String password) {
        JdbcTemplate template = new JdbcTemplate(jdbcUtils.getDataSource());
        Scanner sc = new Scanner(System.in);
        System.out.println("目前您选择的有：");
        findsubject(username, password);

        String sql2 = "SELECT sid FROM student WHERE username = ?";
        Integer sid = template.queryForObject(sql2, Integer.class, username);

        System.out.println("输入您想要退的课程id");
        int suid = sc.nextInt();

        String sql = "DELETE " +
                "FROM sandt " +
                "WHERE sandt.suid = ? AND sandt.sid = ?";
        template.update(sql, suid, sid);
        System.out.println("退课成功！");
        studentmenu(username, password);
    }

}

