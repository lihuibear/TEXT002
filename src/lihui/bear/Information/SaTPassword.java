package lihui.bear.Information;

import lihui.bear.utils.jdbcUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Scanner;

import static lihui.bear.Information.Student.studentmenu;
import static lihui.bear.Information.Teacher.teachermenu;
import static lihui.bear.Information.logindemo.studentLogin;
import static lihui.bear.Information.logindemo.teacherLogin;

public class SaTPassword {
    private static final JdbcTemplate template = new JdbcTemplate(jdbcUtils.getDataSource());

    public static void newpassword(String username, String password, String profession) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入当前密码");
        String oldpassword = sc.nextLine();
        int flag = 0;

        while (!oldpassword.equals(password) && flag < 3) {
            flag++;
            System.out.println("密码错误，还剩" + (3 - flag) + "次尝试");
            if (flag < 3) {
                System.out.println("请重新输入密码：");
                oldpassword = sc.nextLine();
            }
        }

        if (oldpassword.equals(password)) {
            System.out.println("请输入新的密码");
            String new_password = sc.nextLine();
            System.out.println("请再次输入新的密码");
            String new_password2 = sc.nextLine();

            if (new_password.equals(new_password2)) {
                String sql = "update " + profession + " set password = ? where username = ?";
                template.update(sql, new_password, username);
                System.out.println("修改成功，请返回上一界面重新登录");

                if (profession.equals("student")) {
                    studentLogin();
                } else if (profession.equals("teacher")) {
                    teacherLogin();
                }
            } else {
                System.out.println("您前后输入的密码不一致，不可修改");
                if (profession.equals("student")) {
                    studentmenu(username, password);
                } else if (profession.equals("teacher")) {
                    teachermenu(username, password);
                }
            }
        } else {
            System.out.println("失败次数过多，返回上一界面");
            if (profession.equals("student")) {
                studentLogin();
            } else if (profession.equals("teacher")) {
                teacherLogin();
            }
        }
    }
}
