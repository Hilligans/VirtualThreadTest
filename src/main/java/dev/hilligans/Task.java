package dev.hilligans;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;

public abstract class Task implements Runnable {

    public SemaphorePair semaphorePair;
    public boolean running = true;

    public Task(SemaphorePair semaphore1) {
        this.semaphorePair = semaphore1;
    }

    abstract void tick();

    @Override
    public void run() {
        while (running) {
            semaphorePair.block();
            try {
                tick();
            } catch (Exception e) {
                e.printStackTrace();;
                stop();
            }
        }
    }

    public void stop() {
        this.running = false;
    }
}
