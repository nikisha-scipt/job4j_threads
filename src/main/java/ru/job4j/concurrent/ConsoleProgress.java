package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();

    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            for (int i = 0; i <= 100; i++) {
                try {
                    if (i % 2 == 0) {
                        System.out.print("\rload: /");
                    } else {
                        System.out.print("\rload: \\");
                    }
                    Thread.sleep(500);
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.print("\rload: end");
        }
    }
}
