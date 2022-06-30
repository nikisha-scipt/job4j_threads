package ru.job4j.concurrent.pools;

import ru.job4j.concurrent.wnn.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {

    private final List<Thread> threads;
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(Runtime.getRuntime().availableProcessors());

    public ThreadPool() {
        this.threads = new LinkedList<>();
    }

    public void work(Runnable job) throws InterruptedException {
        while (!Thread.currentThread().isInterrupted()) {
            threads.add((Thread) job);
        }
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

}
