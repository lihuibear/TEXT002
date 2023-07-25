package lihui.bear.Information;

import lihui.bear.utils.jdbcUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static lihui.bear.Information.logindemo.studentLogin;
import static lihui.bear.Information.logindemo.teacherLogin;
import static lihui.bear.main.demo.menu;

public class Teacher {
    public static void teachermenu(String username, String password) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=========欢迎进入教师端系统=========");
        System.out.println("1.个人信息查询");
        System.out.println("2.个人信息修改");
        System.out.println("3.账号密码修改");
        System.out.println("4.查看我的课程");
        System.out.println("5.查看选课学生");
        System.out.println("6.修改课程信息");
        System.out.println("输入其他数字返回上一级菜单");
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
                    System.out.println("查看我的课程");
                    findsubject(username, password);
                    System.out.println("继续选择其他功能或输入其他返回上一级菜单");
                    break;
                case "5":
                    System.out.println("查看选课学生");
                    findstudent(username, password);
                    break;
                case "6":
                    System.out.println("修改课程信息");
                    revisesubject(username, password);
                    break;
                default:
                    menu();
            }
        }
    }

    public static void find(String username, String password) {
        JdbcTemplate template = new JdbcTemplate(jdbcUtils.getDataSource());
        String sql = "select username 账号,tid id,name 姓名,tel 手机号码 from teacher where username = ?";

        Map<String, Object> map = template.queryForMap(sql, username);
        System.out.println(map);

        teachermenu(username, password);
    }

    //个人信息修改
    public static void revise(String username, String password) {
        JdbcTemplate template = new JdbcTemplate(jdbcUtils.getDataSource());
        Scanner sc = new Scanner(System.in);
        System.out.println("1.修改姓名");
        System.out.println("2.修改电话号码");

        System.out.println("输入其他返回上一级菜单");
        while (true) {
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    String sql1 = "update teacher set name = ? where username = ?";
                    System.out.println("输入新的姓名");
                    String new_name = sc.nextLine();
                    template.update(sql1, new_name, username);
                    System.out.println("修改成功，请继续选择修改的内容或者退出回到上级菜单");
                    break;
                case "2":
                    String sql2 = "update teacher set tel = ? where username = ?";
                    System.out.println("输入新的11位电话号码");

                    long new_tel = 0;
                    String new_tel_str;

                    while (true) {
                        try {
                            new_tel = sc.nextLong();
                        } catch (InputMismatchException e) {
                            System.out.println("你输入的不是完全的数字，请重新输入：");
                            sc.nextLine(); // 清除非数字输入，以免进入死循环
                            continue; // 继续下一次循环
                        }

                        new_tel_str = String.valueOf(new_tel);
                        if (new_tel_str.length() != 11) {
                            System.out.println("电话号码必须为11位长度，请重新输入：");
                        } else {
                            break; // 电话号码输入正确，跳出循环
                        }
                    }

                    template.update(sql2, new_tel_str, username);
                    System.out.println("修改成功，请继续选择修改的内容或者退出回到上级菜单");
                default:
                    teachermenu(username, password);
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
                String sql = "update teacher set password = ? where username = ?";
                template.update(sql, new_password, username);
                System.out.println("修改成功，请返回上一界面重新登录");
                teacherLogin();
            } else {
                System.out.println("您前后输入的密码不一致，不可修改");
                teachermenu(username, password);
            }
        } else {
            System.out.println("失败次数过多，返回上一界面");
            teacherLogin();
        }
    }

    //查看我的课程
    public static void findsubject(String username, String password) {
        JdbcTemplate template = new JdbcTemplate(jdbcUtils.getDataSource());

        String sql = "SELECT subject.suid AS 课程id, subject.suname AS 课程 " +
                "FROM teacher " +
                "JOIN subject ON teacher.tid = subject.tid " +
                "WHERE teacher.username = ?";

        List<Map<String, Object>> subjectList = template.queryForList(sql, username);

        if (!subjectList.isEmpty()) {
            System.out.println("您所教授的课程：");
            for (Map<String, Object> subject : subjectList) {
                System.out.println("课程ID：" + subject.get("课程id") + "，课程名：" + subject.get("课程"));
            }
        } else {
            System.out.println("您目前没有任教的课程。");
        }
    }

    //查看选课学生
    public static void findstudent(String username, String password) {
        JdbcTemplate template = new JdbcTemplate(jdbcUtils.getDataSource());
        String sql = "SELECT subject.suid AS 课程id, subject.suname AS 课程, student.classname AS 学生班级, student.name AS 学生姓名 " +
                "FROM teacher " +
                "JOIN subject ON teacher.tid = subject.tid " +
                "JOIN sandt ON subject.suid = sandt.suid " +
                "JOIN student ON sandt.sid = student.sid " +
                "WHERE teacher.username = ?";

        List<Map<String, Object>> list = template.queryForList(sql, username);

        // 在这里对查询结果进行处理，输出学生信息等
        for (Map<String, Object> studentInfo : list) {
            int courseId = (int) studentInfo.get("课程id");
            String courseName = (String) studentInfo.get("课程");
            String className = (String) studentInfo.get("学生班级");
            String studentName = (String) studentInfo.get("学生姓名");

            System.out.println("学生姓名：" + studentName + "，学生班级：" + className +
                    "，所选课程ID：" + courseId + "，所选课程：" + courseName);
        }
        teachermenu(username, password);
    }

    //修改课程信息
    public static void revisesubject(String username, String password) {
        JdbcTemplate template = new JdbcTemplate(jdbcUtils.getDataSource());
        Scanner sc = new Scanner(System.in);
        System.out.println("您当前有的课有：");
        findsubject(username, password);
        String sql2 = "SELECT tid FROM teacher WHERE username = ?";
        Integer tid = template.queryForObject(sql2, Integer.class, username);

        System.out.println("输入要修改的课程id");
        int suid = sc.nextInt();
        sc.nextLine();
        System.out.println("输入课程新名称");
        String new_name = sc.nextLine();

        String sql = "UPDATE subject SET suname = ? WHERE tid = ? AND suid = ?";

        template.update(sql, new_name, tid, suid);
        teachermenu(username, password);
    }

}
