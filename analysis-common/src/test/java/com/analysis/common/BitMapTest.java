package com.analysis.common;

import com.sun.scenario.effect.Offset;
import sun.jvm.hotspot.utilities.BitMap;

import java.util.BitSet;

/**
 * Created by crazyy on 15/4/3.
 */
public class BitMapTest {

    public static void main(String[] args) {
//        BitMap bitMap = new BitMap(1000);
////        bitMap.
//        BitSet bitSet = new BitSet();
//        bitSet.set(1);
//        bitSet.set(2);
//        bitSet.set(1);
//        bitSet.set(4);
//        bitSet.set(1);
//
//        System.out.println(bitSet.length());
//        System.out.println(bitSet.size());
//
//        System.out.println(bitSet.get(0));

//        String aaa = "abc";
//        int a = aaa.offsetByCodePoints(1, 1);
//        System.out.println(a);
//        System.out.println(aaa.codePointAt(1));
        int a = 2000000000;
        System.out.println(Integer.toBinaryString(a));
        System.out.println(a >>> 16);
        System.out.println(Integer.toBinaryString(a>>>16));
    }


}
