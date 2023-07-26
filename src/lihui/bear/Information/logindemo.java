package lihui.bear.Information;

import lihui.bear.utils.jdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static lihui.bear.main.demo.menu;

public class logindemo {
    public static void login(String username, String password) {
        System.out.println("1学生登录");
        System.out.println("2教师登录");
        System.out.println("3.返回上级目录");
        Scanner sc = new Scanner(System.in);
        String choice = sc.nextLine();

        if (choice.equals("1")) {
            // 学生登录
            studentLogin();
        } else if (choice.equals("2")) {
            // 教师登录
            teacherLogin();
        } else if (choice.equals("3")) {
            menu();
        } else {
            System.out.println("输入错误，请重新选择");
            login(username, password);
        }
    }

    public static void login(String profession) {
        Scanner sc = new Scanner(System.in);
        String pchoice = profession.equals("student") ? "学生" : "教师";
        System.out.println("请输入" + pchoice + "用户名：");
        String username = sc.nextLine();
        System.out.println("请输入" + pchoice + "密码：");
        String password = sc.nextLine();

        String sql = "SELECT * FROM " + profession + " WHERE username = ? AND password = ?";
        int flag = 0; // 登录尝试次数
        try (Connection conn = jdbcUtils.getConnection()) {
            while (flag < 3) {
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, username);
                    stmt.setString(2, password);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        // 登录成功
                        System.out.println(pchoice + "登录成功");
                        // 进行相关操作
                        if (profession.equals("student")) {
                            Student.studentmenu(username, password);
                        } else if (profession.equals("teacher")) {
                            Teacher.teachermenu(username, password);
                        }
                        return;
                    } else {
                        flag++;
                        System.out.println(pchoice + "登录失败，用户名或密码错误，还剩" + (3 - flag) + "次尝试");
                        if (flag < 3) {
                            System.out.println("请重新输入" + pchoice + "用户名：");
                            username = sc.nextLine();
                            System.out.println("请重新输入" + pchoice + "密码：");
                            password = sc.nextLine();
                        }
                    }
                }
            }
            System.out.println("登录失败次数过多，返回登录选择界面");
            menu();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void studentLogin() {
        login("student");
    }

    public static void teacherLogin() {
        login("teacher");
    }
}

