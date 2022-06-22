package ru.job4j.concurrent.shared;

public class Count {

    private int value;

    public synchronized void increment() {
        value++;
    }

    public synchronized int get() {
        return value;
    }

    public static void main(String[] args) throws InterruptedException {
        Count count = new Count();
        Thread first = new Thread(count::increment);
        Thread second = new Thread(count::increment);
        first.start();
        second.start();
        first.join();
        second.join();
        System.out.println(count.get());
    }

}
