package lihui.bear.Information;

import java.util.Scanner;

import static lihui.bear.main.demo.menu;

public class Teacher {
    public static void teachermenu() {
        Scanner sc = new Scanner(System.in);
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
                    System.out.println("ceshi1");
                    break;
                case "2":
                    System.out.println("ceshi2");
                    break;
                case "3":
                    System.out.println("ceshi3");
                    break;
                case "4":
                    System.out.println("ceshi4");
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
}
