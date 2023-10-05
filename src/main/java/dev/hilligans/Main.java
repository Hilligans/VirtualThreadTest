package dev.hilligans;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {



    public static void main(String[] args) {
        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        int count = 100000;
        SemaphorePair semaphorePair = new SemaphorePair(count);
        semaphorePair.acquire();
        ArrayList<Task> tasks = new ArrayList<>();

        for(int x = 0; x < count; x++) {
            Task task = new CalcTask(semaphorePair);
            tasks.add(task);
            executorService.submit(task);
        }

        int x = 0;
        while(x != 30) {
            long start = System.currentTimeMillis();
            semaphorePair.swap();
            System.out.println("Time: " + (System.currentTimeMillis() - start));
            x++;
        }
        System.out.println(CalcTask.RESULT.get());


        semaphorePair.waitFor();
        for(Task task : tasks) {
            task.stop();
        }
        semaphorePair.swap();
        executorService.close();
    }
}