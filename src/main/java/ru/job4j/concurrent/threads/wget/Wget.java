package ru.job4j.concurrent.threads.wget;

import java.io.*;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Wget implements Runnable {

    private final String url;
    private final String file;
    private final int speed;

    public Wget(String url, int speed, String file) {
        this.url = url;
        this.speed = speed;
        this.file = file;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            byte[] downloadBuffer = new byte[1024];
            int byteCount;
            int downloadData = 0;
            long timeApplication = System.currentTimeMillis();
            while ((byteCount = in.read(downloadBuffer, 0, 1024)) != -1) {
                out.write(downloadBuffer, 0, byteCount);
                downloadData += byteCount;
                if (downloadData >= speed) {
                    long interval = System.currentTimeMillis() - timeApplication;
                    if (interval < 1000) {
                        Thread.sleep(1000 - interval);
                    }
                    timeApplication = System.currentTimeMillis();
                    downloadData = 0;
                }
            }
            System.out.printf("%s successfully download%n", Thread.currentThread().getName());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Validation valid = new Validation(args);
        if (valid.isValid()) {
            String url = args[0];
            int speed = Integer.parseInt(args[1]);
            String file = args[2];
            Thread wget = new Thread(new Wget(url, speed, file));
            wget.start();
            wget.join();
        }
    }
}
