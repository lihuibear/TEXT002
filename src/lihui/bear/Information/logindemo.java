package lihui.bear.Information;

import lihui.bear.utils.jdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static lihui.bear.Information.Student.studentmenu;
import static lihui.bear.Information.Teacher.teachermenu;
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
        } else if (choice.equals("3")){
            menu();
        }else {
            System.out.println("输入错误，请重新选择");
            login(username, password);
        }
    }

    public static void studentLogin() {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入学生用户名：");
        String username = sc.nextLine();
        System.out.println("请输入学生密码：");
        String password = sc.nextLine();

        // 连接数据库
        Connection conn = null;
        try {
            conn = jdbcUtils.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (conn == null) {
            System.out.println("数据库连接失败");
            return;
        }

        String sql = "SELECT * FROM student WHERE username = ? AND password = ?";
        int flag = 0; // 登录尝试次数
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            while (flag < 3) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    // 学生登录成功
                    String sid = rs.getString("sid");
                    System.out.println("学生登录成功，学号：" + sid);
                    // 进行学生相关操作
                    studentmenu(username,password);

                    stmt.close();
                    conn.close();
                    return;
                } else {
                    flag++;
                    System.out.println("学生登录失败，用户名或密码错误，还剩" + (3 - flag) + "次尝试");
                    if (flag < 3) {
                        System.out.println("请重新输入学生用户名：");
                        username = sc.nextLine();
                        System.out.println("请重新输入学生密码：");
                        password = sc.nextLine();
                        stmt.setString(1, username);
                        stmt.setString(2, password);
                    }
                }
            }

            System.out.println("登录失败次数过多，返回登录选择界面");
            menu();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void teacherLogin() {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入教师用户名：");
        String username = sc.nextLine();
        System.out.println("请输入教师密码：");
        String password = sc.nextLine();

        // 连接数据库
        Connection conn = null;
        try {
            conn = jdbcUtils.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (conn == null) {
            System.out.println("数据库连接失败");
            return;
        }

        String sql = "SELECT * FROM teacher WHERE username = ? AND password = ?";
        int flag = 0; // 登录尝试次数
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            while (flag < 3) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    // 学生登录成功
                    String tid = rs.getString("tid");
                    System.out.println("教师登录成功，工号：" + tid);
                    // 进行学生相关操作
                    // ...
                    teachermenu();
                    stmt.close();
                    conn.close();
                    return;
                } else {
                    flag++;
                    System.out.println("教师登录失败，用户名或密码错误，还剩" + (3 - flag) + "次尝试");
                    if (flag < 3) {
                        System.out.println("请重新输入教师用户名：");
                        username = sc.nextLine();
                        System.out.println("请重新输入教师密码：");
                        password = sc.nextLine();
                        stmt.setString(1, username);
                        stmt.setString(2, password);
                    }
                }
            }

            System.out.println("登录失败次数过多，返回登录选择界面");
            menu();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}