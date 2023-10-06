package dev.hilligans;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;

public abstract class Task implements Runnable {

    public ParkerUnparker parker;
    public boolean running = true;

    public Task(ParkerUnparker parker) {
        this.parker = parker;
    }

    abstract void tick();

    @Override
    public void run() {
        while (running) {
            parker.park();
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
