package lihui.bear.main;

import java.util.Scanner;

import static lihui.bear.Information.enrolldemo.*;
import static lihui.bear.Information.logindemo.*;

public class demo {
    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("==========");
        System.out.println("选课管理系统");
        System.out.println("输入1 登录");
        System.out.println("输入2 注册");
        System.out.println("输入0 退出系统");
        String s = sc.nextLine();
        String usename = null;
        String pawssord = null;

        while (!s.equals("1") && !s.equals("2")) {
            System.out.println("输入错误，请重新输入");
            s = sc.nextLine();
        }

        if (s.equals("1")) {
            login(usename, pawssord);
        }
        if (s.equals("2")) {
            enroll();
        }if(s.equals("0")){
            System.exit(0);
        }
    }

}


