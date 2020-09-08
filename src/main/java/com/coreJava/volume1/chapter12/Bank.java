package com.coreJava.volume1.chapter12;

import java.util.Arrays;

public class Bank {
    private final double[] accounts;

    public Bank(int n, double initAccounts){
        accounts = new double[n];
        Arrays.fill(accounts, initAccounts);
    }

    public synchronized void transfer(int from, int to, double amount){
            if(accounts[from] < amount) return;
            System.out.print(Thread.currentThread());
            accounts[from] -= amount;
            System.out.printf("transfer %10.2f from %d to %d ", amount, from, to);
            accounts[to] += amount;
            System.out.printf("totalAmount: %10.2f ", talAccounts());
            printAccount();
    }

    private synchronized double talAccounts(){
            double sum = 0.0;
            for(double a : accounts)
                sum += a;
            return sum;
    }

    public int size(){
        return accounts.length;
    }

    private synchronized void printAccount(){
        System.out.println(Arrays.toString(accounts));
    }

    private static final int DELAY = 10;
    private static final int STEPS = 100;
    private static final double MAX_AMOUNT = 1000;
    public static void main(String... args){
        Bank bank = new Bank(4, 100000);
        Runnable task1 = () -> {
            try {
                for (int i = 0; i < STEPS; i++) {
                    double amount = MAX_AMOUNT * Math.random();
                    bank.transfer(0, 1, amount);
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
                    bank.transfer(2, 3, amount2);
                    Thread.sleep((int) (DELAY * Math.random()));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        new Thread(task1).start();
        new Thread(task2).start();
    }
}
