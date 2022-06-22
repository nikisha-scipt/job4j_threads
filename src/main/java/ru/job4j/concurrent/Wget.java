package ru.job4j.concurrent;

public class Wget {

    public static void main(String[] args) {
        Thread thread = new Thread();
        thread.start();
        while (thread.getState() != Thread.State.TERMINATED) {
            for (int i = 0; i <= 100; i++) {
                try {
                    System.out.print("\rLoading : " + i  + "%");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.printf("%n%s ended", Thread.currentThread().getName());
    }

}
