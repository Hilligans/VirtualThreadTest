package dev.hilligans;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class CalcTask extends Task {

    public static final AtomicLong RESULT = new AtomicLong();
    Random random = new Random();

    public CalcTask(SemaphorePair semaphore1) {
        super(semaphore1);
    }

    @Override
    void tick() {
        //just some random work idk
        long rand = 0;
        for(int x = 0; x < 5000; x++) {
            long bound = RESULT.get();
            bound = Math.abs(bound);
            if(bound == 0) {
                bound = 10;
            }
            rand += random.nextLong(bound);
        }
        RESULT.addAndGet(rand);
    }
}
