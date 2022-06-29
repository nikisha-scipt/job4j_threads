package ru.job4j.concurrent.threads;

public class ConsoleProgress implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }

    @Override
    public void run() {
        String[] symbols = {"/", "\\", "|"};
        while (!Thread.currentThread().isInterrupted()) {
            try {
                for (String symbol : symbols) {
                    System.out.print("\r load: " + symbol);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.print("\rload: end");
        }
    }
}
