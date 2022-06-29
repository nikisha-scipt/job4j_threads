package ru.job4j.concurrent.nba.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {

    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void startCountIncrement(int start) {
        count.set(start);
    }

    public void increment() {
        if (count.get() == null) {
            throw new UnsupportedOperationException("Count is not impl.");
        }
        int current;
        int ref;
        do {
            current = count.get();
            ref = current + 1;
        } while (!count.compareAndSet(current, ref));
    }

    public int get() {
        if (count.get() == null) {
            throw new UnsupportedOperationException("Count is not impl.");
        }
        return count.get();
    }

}
