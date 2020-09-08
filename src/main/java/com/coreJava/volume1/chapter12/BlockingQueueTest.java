package com.coreJava.volume1.chapter12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BlockingQueueTest {
    private static final int FILE_QUEUE_SIZE = 3;
    private static final int SEARCH_THREAD = 2;
    private static final Path DUMMY = Paths.get("e");
    private static final BlockingQueue<Path> queue = new ArrayBlockingQueue<>(FILE_QUEUE_SIZE);

    public static void main(String[] args){
        try(Scanner in = new Scanner(System.in)) {
            System.out.print("the base directory: ");
            String directory = in.nextLine();
            System.out.println("\nthe keyword: ");
            String keyword = in.nextLine();
            Runnable enumerator = () -> {
                try {
                    enumerator(Paths.get(directory));
                    queue.put(DUMMY);
                    System.out.println(queue.size() + Arrays.toString(queue.toArray()));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {

                }
            };
            new Thread(enumerator).start();
            for (int i = 0; i < SEARCH_THREAD; i++){
                Runnable search = () -> {
                    try {
                        boolean done = false;
                        while(!done){
                            Path file = queue.take();
                            if(file == DUMMY) {
                                queue.put(file);
                                done = true;
                            }
                            else
                                search(file, keyword);
                        }
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }catch (IOException e){

                    }
                };
                new Thread(search).start();
            }
        }
    }

    private static void search(Path file, String keyword) throws IOException{
        try(Scanner in = new Scanner(file, "utf-8")){
            int lineNumber = 0;
            while (in.hasNextLine()){
                lineNumber++;
                String line = in.nextLine();
                if(line.contains(keyword))
                    System.out.println(Thread.currentThread() + ": " + file + ": " + lineNumber + ": " + line);
            }
        }
    }

    private static void enumerator(Path directory) throws IOException, InterruptedException {
        try(Stream<Path> children = Files.list(directory)) {
            for(Path child : children.collect(Collectors.toList())){
                if(Files.isDirectory(child))
                    enumerator(child);
                else queue.put(child);
                System.out.println(queue.contains(DUMMY) + Arrays.toString(queue.toArray()));
            }
        }
    }
}
