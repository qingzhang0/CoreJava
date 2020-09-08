package com.coreJava.javaTest;

public class BitOperate {
    public static void main(String[] args){
        int n = (1 << 31) ;
        System.out.println(n);
        System.out.println(Integer.toBinaryString(n));
        System.out.println(Integer.toBinaryString(n-1));
        int n1 = -2;
        System.out.println(Integer.toBinaryString(n1));
    }
}
