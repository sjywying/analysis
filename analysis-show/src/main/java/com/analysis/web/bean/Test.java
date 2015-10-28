package com.analysis.web.bean;

import java.util.Random;

/**
 * Created by crazyy on 15/7/13.
 */
public class Test {
    public static void main(String[] args) {

        Random random = new Random();
        float s = (float) ((random.nextInt(10)*0.1 + 5) * 0.01);

        System.out.println(s);
    }
}
