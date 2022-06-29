package ru.job4j.concurrent.wnn;

import lombok.Data;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
@Data
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int count;

    public SimpleBlockingQueue(int count) {
        this.count = count;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() >= count) {
            wait();
        }
        queue.offer(value);
        notify();
    }

    public synchronized T poll() throws InterruptedException {
        if (queue.isEmpty()) {
            wait();
        }

        T rsl = queue.poll();
        notify();
        return rsl;
    }

    public int getSize() {
        return queue.size();
    }

}
