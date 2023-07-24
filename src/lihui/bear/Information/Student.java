package lihui.bear.Information;

import lihui.bear.utils.jdbcUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;
import java.util.Scanner;

import static lihui.bear.Information.logindemo.login;
import static lihui.bear.Information.logindemo.studentLogin;
import static lihui.bear.main.demo.menu;

public class Student {
    public static void studentmenu(String username, String password) {
        Scanner sc = new Scanner(System.in);
        System.out.println("1.个人信息查询");
        System.out.println("2.个人信息修改");
        System.out.println("3.账号密码修改");
        System.out.println("4.课程管理");
        System.out.println("5.选课");
        System.out.println("6.退课");
        System.out.println("输入其他返回上一级菜单");
        while (true) {
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("ceshi1");
                    find(username);
                    break;
                case "2":
                    System.out.println("ceshi2");
                    revise(username, password);
                    break;
                case "3":
                    System.out.println("ceshi3");
                    newpassword(username, password);
                    break;
                case "4":
                    System.out.println("ceshi4");
                    findsubject();
                    break;
                case "5":
                    System.out.println("ceshi5");
                    break;
                case "6":
                    System.out.println("ceshi6");
                    break;
                default:
                    menu();
            }
        }
    }

    //个人信息查询
    public static void find(String username) {
        JdbcTemplate template = new JdbcTemplate(jdbcUtils.getDataSource());
        String sql = "select username 账号,sid id,name 姓名,sex 性别,classname 班级 from student where username = ?";
//        String sql = "select * from student where username = ?";
//        String username = ;
        Map<String, Object> map = template.queryForMap(sql, username);
        System.out.println(map);
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
        if (oldpassword.equals(password)) {
            String sql = "update student set password = ? where username = ?";
            System.out.println("输入新的密码");
            String new_password = sc.nextLine();
            System.out.println("请再次输入新的密码");
            String new_password2 = sc.nextLine();
            if (new_password2.equals(new_password)) {
                template.update(sql, new_password, username);
                System.out.println("修改成功，请重新登录");
                studentLogin();
            } else {
                System.out.println("您前后输入的密码不一致，重新输入");
                newpassword(username, password);
            }

        } else {
            flag++;
            System.out.println("密码错误，还剩" + (3 - flag) + "次尝试");
            if (flag < 3) {
                System.out.println("请重新输入学生密码：");
                oldpassword = sc.nextLine();
                String sql = "update student set password = ? where username = ?";
                System.out.println("输入新的密码");
                String new_password = sc.nextLine();
                template.update(sql, new_password, username);
                System.out.println("修改成功，请重新登录");
                studentLogin();
            } else {
                System.out.println("失败次数过多，返回上一界面");
                studentmenu(username, password);
            }
        }
    }

    public static void findsubject(){

    }
}
