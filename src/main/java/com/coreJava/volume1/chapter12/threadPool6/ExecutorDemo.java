package com.coreJava.volume1.chapter12.threadPool6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExecutorDemo {
    public static long occurrences(String word, Path path){
        long count = 0L;
        try(Scanner in = new Scanner(path)){
            while(in.hasNext())
                if(in.next().equals(word)) count++;
            return count;
        }catch (IOException e){
            return 0L;
        }
    }

    public static Set<Path> descendants(Path root) throws IOException{
        try(Stream<Path> paths = Files.walk(root)){
            return paths.filter(Files::isRegularFile).collect(Collectors.toSet());
        }
    }

    public static Callable<Path> searchForTask(String word, Path path){
        return () -> {
            try(Scanner in = new Scanner(path)) {
                while (in.hasNext()){
                    if(in.next().equals(word)) return path;
                    if(Thread.currentThread().isInterrupted()){
                        System.out.println("search in " + path + " cancelled");
                        return null;
                    }
                }
                throw new NoSuchElementException();
            }
        };
    }

    public static void main(String[] args) throws ExecutionException, IOException, InterruptedException{
        try(Scanner in = new Scanner(System.in)){
            System.out.println("the direction : ");
            String direction = in.nextLine();
            System.out.println("the keyword : ");
            String word = in.nextLine();
            Set<Path> files = descendants(Paths.get(direction));
            List<Callable<Long>> tasks = new ArrayList<>();
            for(Path path : files){
                Callable<Long> task = () -> occurrences(word,path);
                tasks.add(task);
            }
            ExecutorService executor = Executors.newCachedThreadPool();
//            ExecutorService executor = Executors.newSingleThreadExecutor();
            Instant startTime = Instant.now();
            List<Future<Long>> results = executor.invokeAll(tasks);
            Long total = 0L;
            for(Future<Long> result : results)
                total += result.get();
            Instant endTime = Instant.now();
            System.out.println("Occurrences of " + word + ": " + total);
            System.out.println("Time elapsed: " + Duration.between(startTime, endTime).toMillis() + " ms");
            List<Callable<Path>> searchTasks = new ArrayList<>();
            for(Path path : files)
                searchTasks.add(searchForTask(word, path));
            Path found = executor.invokeAny(searchTasks);
            System.out.println(word + " found in " + found);
            if(executor instanceof ThreadPoolExecutor)
                System.out.println("Largest size : " + ((ThreadPoolExecutor) executor).getMaximumPoolSize());
            executor.shutdown();
        }
    }
}
