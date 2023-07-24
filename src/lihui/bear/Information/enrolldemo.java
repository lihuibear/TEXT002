package lihui.bear.Information;

import lihui.bear.utils.jdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class enrolldemo {

    public static void enroll() {
        System.out.println("1学生注册");
        System.out.println("2教师注册");
        Scanner sc = new Scanner(System.in);
        String choice = sc.nextLine();

        if (choice.equals("1")) {
            // 学生注册
            studentRegister();
        } else if (choice.equals("2")) {
            // 教师注册
            teacherRegister();
        } else {
            System.out.println("输入错误，请重新选择");
            enroll();
        }
    }

    public static void studentRegister() {
        // 获取学生注册信息，如用户名、密码等
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username = sc.nextLine();
        System.out.println("请输入密码：");
        String password = sc.nextLine();
        System.out.println("姓名");
        String name = sc.nextLine();
        System.out.println("性别");
        String sex = sc.nextLine();
        System.out.println("班级");
        String classname = sc.nextLine();
        // 获取其他学生信息...

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

        String sql = "INSERT INTO student (sid,name,sex,classname,username, password) VALUES (null,?,?,?,?,?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, name);
            stmt.setString(2, sex);
            stmt.setString(3, classname);
            stmt.setString(4, username);
            stmt.setString(5, password);

            // 设置其他学生信息...
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("学生注册成功");
            } else {
                System.out.println("学生注册失败");
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void teacherRegister() {
        // 获取学生注册信息，如用户名、密码等
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username = sc.nextLine();
        System.out.println("请输入密码：");
        String password = sc.nextLine();
        System.out.println("姓名");
        String name = sc.nextLine();
        System.out.println("联系方式");
        long tel = sc.nextLong();
        // 获取其他学生信息...

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

        String sql = "INSERT INTO teacher (tid,name,tel,username,password) VALUES (null,?,?,?,?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setLong(2, tel);
            stmt.setString(3, username);
            stmt.setString(4, password);
            // 设置其他学生信息...
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("教师注册成功");
            } else {
                System.out.println("教师注册失败");
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

