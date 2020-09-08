package com.coreJava.volume1.chapter3.string6;

import org.junit.Test;
import static org.junit.Assert.*;

public class StringTestTest {

    @Test
    public void codePointTest() {
        StringTest.codePointTest();
    }

    @Test
    public void printAllCodePoints() {
        StringTest.printAllCodePoints();
    }

    @Test
    public void getCodePointWithSite() {
        StringTest.getCodePointWithSite(4);
    }

    @Test
    public void printLine() {
        StringTest.printLine();
    }

    @Test
    public void printCodePointStream() {
        StringTest.printCodePointStream();
    }

    @Test
    public void convertCodePointArray2String() {
        int[] cps = {121,1,2,34,4,5,6,7,8,49,110};
        StringTest.convertCodePointArray2String(cps, 0, cps.length);
    }
}