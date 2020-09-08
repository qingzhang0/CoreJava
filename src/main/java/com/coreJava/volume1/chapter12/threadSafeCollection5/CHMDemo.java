package com.coreJava.volume1.chapter12.threadSafeCollection5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class CHMDemo {
    public static ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();
    public static void process(Path file) {
        try(Scanner in = new Scanner(file)) {
            while (in.hasNext()) {
                String word = in.next();
                map.merge(word, 1L, Long::sum);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static Set<Path> descendants(Path rootFile) throws IOException{
        return Files.walk(rootFile).collect(Collectors.toSet());
    }
    public static void main(String[] args) throws InterruptedException, IOException{
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println(processors);
        ExecutorService executor = Executors.newFixedThreadPool(processors);
        Path pathToRoot = Paths.get(".");
        for (Path p : descendants(pathToRoot)){
            if(p.getFileName().toString().endsWith(".java"))
                executor.execute(() -> process(p));
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);
        map.forEach((k, v) -> {
            if(v > 10) System.out.println(k + " occurs " + v + " times");
        });
    }
}
