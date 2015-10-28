package com.test;

import java.io.File;

/**
 * Created by crazyy on 15/5/8.
 */
public class TestPath {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));

        System.out.println(TestPath.class.getClassLoader().getResource(""));

        System.out.println(ClassLoader.getSystemResource(""));
        System.out.println(TestPath.class.getResource(""));
        System.out.println(TestPath.class.getResource("/")); //Class文件所在路径
        System.out.println(new File("/").getAbsolutePath());
        System.out.println(System.getProperty("user.dir"));
    }
}
