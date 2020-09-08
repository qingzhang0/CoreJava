package com.coreJava.volume1.chapter12.threadPool6;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.*;
import java.util.function.DoublePredicate;

public class ForkJoinTest {
    public static void main(String[] args) throws InterruptedException{
        final int SIZE = 10000000;
        double[] values = new double[SIZE];
        Instant firstStart = Instant.now();
//        normalRandom(values);
        parallelRandom(values);
        Instant firstEnd = Instant.now();
        System.out.println("the normal random : " + Duration.between(firstStart, firstEnd).toMillis() + " ms");
        Counter counter = new Counter(values, 0, values.length, x -> x > 0.5);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(counter);
        System.out.println(counter.join());
    }

    private static void normalRandom(double[] values){
        for(int i = 0; i < values.length; i++) values[i] = Math.random();
    }

    private static void parallelRandom(double[] values) throws InterruptedException{
        int runSum = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(runSum);
//        ExecutorService executor = Executors.newCachedThreadPool();
//        ExecutorService executor = Executors.newSingleThreadExecutor();
        Instant secondStart = Instant.now();
        executor.execute( () ->{
            for(int i = 0; i < values.length; i++) values[i] = Math.random();
        } );
        Instant secondEnd = Instant.now();
        System.out.println("the parallel random : " + Duration.between(secondStart, secondEnd).toMillis() + " ms");
        Instant p1 = Instant.now();
        executor.shutdown();
        Instant p1e = Instant.now();
        System.out.println("the shutdown() time : " + Duration.between(p1, p1e).toMillis() + " ms");
        Instant p2 = Instant.now();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        Instant p2e = Instant.now();
        System.out.println("the awaitTermination() time : " + Duration.between(p2, p2e).toMillis() + " ms");
    }
}

class Counter extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 1000;
    private double[] values;
    private int from;
    private int to;
    private DoublePredicate filter;

    protected Counter(double[] values, int from, int to, DoublePredicate filter){
        this.values = values;
        this.from = from;
        this.to = to;
        this.filter = filter;
    }

    @Override
    protected Integer compute() {
        if(to - from < THRESHOLD){
            int count = 0;
            for(int i = from; i < to; i++) {
                if(filter.test(values[i])) count++;
            }
            return count;
        } else {
            int mid = (from + to) / 2 ;
            Counter first = new Counter(values, from, mid, filter);
            Counter second = new Counter(values, mid, to, filter);
            invokeAll(first,second);
            return first.join() + second.join();
        }
    }
}
