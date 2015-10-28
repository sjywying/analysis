package com.analysis.common.genlong;

import java.util.BitSet;

/**
 * Created by crazyy on 15/4/20.
 */
public class TestMem {

    public static void main(String[] args) {
        BitSet bitSet = new BitSet(100000000);
        bitSet.set(100000000);
        bitSet.set(9999999);

//        byte[] bytes = new byte[100000000];
//        bytes[9999999] = 1;


        System.gc();
        System.out.println("111");
    }

}
