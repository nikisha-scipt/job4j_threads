package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {

    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("pom_temp.xml"))) {
            byte[] downloadBuffer = new byte[speed];
            int byteCount;
            while ((byteCount = in.read(downloadBuffer, 0, 1024)) != -1) {
                out.write(downloadBuffer, 0, byteCount);
                Thread.sleep(1000);
            }
            System.out.printf("%s access download%n", Thread.currentThread().getName());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
