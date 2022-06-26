package ru.job4j.concurrent.wnn;

import lombok.Data;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
@Data
public class SimpleBlockingQueue<T> {

    private final int count;

    public SimpleBlockingQueue(int count) {
        this.count = count;
    }

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() >= count) {
            wait();
        }
        queue.offer(value);
        notify();
    }

    public synchronized T poll() {
        if (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        T rsl = queue.poll();
        notify();
        return rsl;
    }

    public int getSize() {
        return queue.size();
    }

}
