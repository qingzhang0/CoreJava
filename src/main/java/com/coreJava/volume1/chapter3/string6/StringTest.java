package com.coreJava.volume1.chapter3.string6;

public class StringTest {
    private static String greeting = "Hello";

    public static void codePointTest(){
        int cpCount = greeting.codePointCount(0, greeting.length());
        System.out.println("The count of code points in greeting: " + cpCount);
    }

    public static void printAllCodePoints(){
        for(int i = 0; i < greeting.length(); i++)
            getCodePointWithSite(i);
        StringTest.printLine();
    }

    public static void getCodePointWithSite(int codePointOffset){
        int index = greeting.offsetByCodePoints(0,codePointOffset);
        int cp = greeting.codePointAt(index);
//        int cp = greeting.codePointAt(codePointOffset);
        System.out.println("the " + codePointOffset + "th" + " code point is " + cp);
    }

    public static void printLine(){
        System.out.println("--------------------------------------------------");
    }

    public static int[] printCodePointStream(){
        int[] cps = greeting.codePoints().toArray();
        for(int i = 0; i < cps.length; i++)
            System.out.println("code point stream : " + cps[i]);
        return cps;
    }

    public static void convertCodePointArray2String(int[] cps, int offset, int count){
        String str = new String(cps, offset, count);
        System.out.println("new String :" + str);
    }
}
