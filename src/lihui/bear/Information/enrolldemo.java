package lihui.bear.Information;

import lihui.bear.utils.jdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static lihui.bear.main.demo.menu;

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

        // 检查用户名是否重复
        String checkDuplicateSql = "SELECT COUNT(*) FROM student WHERE username = ?";
        try {
            PreparedStatement checkStmt = conn.prepareStatement(checkDuplicateSql);
            checkStmt.setString(1, username);
            ResultSet resultSet = checkStmt.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            checkStmt.close();

            if (count > 0) {
                System.out.println("该用户名已被注册，请重新选择用户名！");
                studentRegister(); // 重新调用学生注册方法
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 如果用户名未重复，则继续注册
        System.out.println("请输入密码：");
        String password = sc.nextLine();
        System.out.println("姓名");
        String name = sc.nextLine();
        System.out.println("性别");
        String sex = sc.nextLine();
        System.out.println("班级");
        String classname = sc.nextLine();

        String sql = "INSERT INTO student (sid,name,sex,classname,username,password) VALUES (null,?,?,?,?,?)";
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
                System.out.println("学生注册成功，请登录");
                menu();
            } else {
                System.out.println("学生注册失败");
            }
            jdbcUtils.close(stmt, conn);
//            stmt.close();
//            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void teacherRegister() {
        // 获取教师注册信息，如用户名、密码等
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username = sc.nextLine();

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

        // 检查用户名是否重复
        String checkDuplicateSql = "SELECT COUNT(*) FROM teacher WHERE username = ?";
        try {
            PreparedStatement checkStmt = conn.prepareStatement(checkDuplicateSql);
            checkStmt.setString(1, username);
            ResultSet resultSet = checkStmt.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            checkStmt.close();

            if (count > 0) {
                System.out.println("该用户名已被注册，请重新选择用户名！");
                teacherRegister(); // 重新调用教师注册方法
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 如果用户名未重复，则继续注册
        System.out.println("请输入密码：");
        String password = sc.nextLine();
        System.out.println("姓名");
        String name = sc.nextLine();
//        System.out.println("联系方式");
//        long tel = sc.nextLong();

        System.out.println("输入新的11位电话号码");

        long tel = 0;
        String tel_str;

        while (true) {

            try {
                tel = sc.nextLong();
            } catch (InputMismatchException e) {
                System.out.println("你输入的不是完全的数字，请重新输入：");
                sc.nextLine(); // 清除非数字输入，以免进入死循环
                continue; // 继续下一次循环
            }
            tel_str = String.valueOf(tel);
            if (tel_str.length() != 11) {
                System.out.println("电话号码必须为11位长度，请重新输入：");
            } else {
                break; // 电话号码输入正确，跳出循环
            }
        }
        String sql = "INSERT INTO teacher (tid, name, tel, username, password) VALUES (null, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setLong(2, tel);
            stmt.setString(3, username);
            stmt.setString(4, password);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("教师注册成功，请登录");
                menu();
            } else {
                System.out.println("教师注册失败");
            }
            jdbcUtils.close(stmt, conn);
//            stmt.close();
//            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

