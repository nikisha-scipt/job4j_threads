package ru.job4j.concurrent.threads.wget;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

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
            long start = System.currentTimeMillis();
            while ((byteCount = in.read(downloadBuffer, 0, 1024)) != -1) {
                out.write(downloadBuffer, 0, byteCount);
                downloadData += byteCount;
                if (downloadData >= speed) {
                    long currentStart = System.currentTimeMillis() - start;
                    if (currentStart + start < 1000) {
                        Thread.sleep(1000 - (start - currentStart));
                        downloadData = 0;
                        start = 0;
                    }
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
