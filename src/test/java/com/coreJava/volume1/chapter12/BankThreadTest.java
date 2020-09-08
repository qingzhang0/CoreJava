package com.coreJava.volume1.chapter12;

import org.junit.Test;

public class BankThreadTest {
    private static final int DELAY = 10;
    private static final int STEPS = 100;
    private static final double MAX_AMOUNT = 1000;
    @Test
    public void transfer(){
        Bank bank = new Bank(4, 100000);
        Runnable task1 = () -> {
            try {
                for (int i = 0; i < STEPS; i++) {
                    double amount = MAX_AMOUNT * Math.random();
                    bank.transfer(0, 1, amount);
                    System.out.println("1 i " + i);
                    Thread.sleep((int) (DELAY * Math.random()));
                }
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        };
        Runnable task2 = () -> {
            try {
                for (int i = 0; i < STEPS; i++) {
                    double amount2 = MAX_AMOUNT * Math.random();
                    System.out.println("2 before i " + i);
                    bank.transfer(2, 3, amount2);
                    System.out.println("2 after i " + i);
                    Thread.sleep((int) (DELAY * Math.random()));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        new Thread(task2).start();
        new Thread(task1).start();
        //bank.printAccount();
    }
}