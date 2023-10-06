package dev.hilligans;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {



    public static void main(String[] args) {
        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        int count = 100000;
        ParkerUnparker parkerUnparker = new ParkerUnparker(count);

        ArrayList<Task> tasks = new ArrayList<>();

        for(int x = 0; x < count; x++) {
            Task task = new CalcTask(parkerUnparker);
            tasks.add(task);
            executorService.submit(task);
        }
        parkerUnparker.waitForThreads();

        int x = 0;
        while(x != 30) {
            long start = System.currentTimeMillis();
            parkerUnparker.unpark();
            parkerUnparker.waitForThreads();
            System.out.println("Time: " + (System.currentTimeMillis() - start));
            x++;
        }
        System.out.println(CalcTask.RESULT.get());


        parkerUnparker.waitForThreads();
        for(Task task : tasks) {
            task.stop();
        }
        parkerUnparker.unpark();
        executorService.close();
    }
}