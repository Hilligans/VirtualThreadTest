package dev.hilligans;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class SemaphorePair {

    public volatile Semaphore semaphore1;
    public volatile Semaphore semaphore2;

    public int waitCount;
    public AtomicInteger waiting = new AtomicInteger();

    public SemaphorePair(int count) {
        semaphore1 = new Semaphore(100);
        semaphore2 = new Semaphore(100);
        this.waitCount = count;
    }

    public void acquire() {
        try {
            semaphore1.acquire(100);
            waiting.set(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void waitFor() {
        while(waiting.get() != waitCount) {}
    }

    public void swap() {
        try {
            waitFor();

            Semaphore semaphore = semaphore1;
            semaphore1 = semaphore2;
            semaphore2 = semaphore;

            semaphore1.acquire(100);
            waiting.set(0);
            semaphore2.release(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void block() {
        try {
            waiting.incrementAndGet();
            Semaphore semaphore = semaphore1;
            semaphore.acquire();
            semaphore.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
