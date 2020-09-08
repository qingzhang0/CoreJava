package com.coreJava.volume1.chapter3.array10;

import java.util.Arrays;

public class ArrayTest {
    int[] arr = {};
    public void printLengthOfArray(int[] arr){
        System.out.println("the length of this array : " + arr.length);
    }

    public static void main(String[] args){
        ArrayTest arrayTest = new ArrayTest();
        arrayTest.printLengthOfArray(arrayTest.arr);

        System.out.println(Arrays.toString(args));
    }
}
