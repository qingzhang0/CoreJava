package com.coreJava.volume1.chapter4;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class CalendarTest {
    public void printThisMonth(){
        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        int today = date.getDayOfMonth();
        date = date.minusDays(today -1);
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        int week = dayOfWeek.getValue();
        System.out.println("Mon Tue Wed Thu Fri Sat Sun");
        for(int i = 1; i < week; i++)
            System.out.print(" \t");
        while(date.getMonthValue() == month){
            System.out.print(date.getDayOfMonth());
            if(date.getDayOfMonth() == today)
                System.out.print("*\t");
            else
                System.out.print(" \t");
            date = date.plusDays(1);
            if(date.getDayOfWeek().getValue() == 1)
                System.out.println();
        }
    }

}
