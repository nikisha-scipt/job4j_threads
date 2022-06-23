package ru.job4j.concurrent.threads;

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
            while ((byteCount = in.read(downloadBuffer, 0, 1024)) != -1) {
                out.write(downloadBuffer, 0, byteCount);
                if (System.currentTimeMillis() < speed) {
                    Thread.sleep(1000);
                }
            }
            System.out.printf("%s successfully download%n", Thread.currentThread().getName());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class Validation {

        private final String[] args;

        public Validation(String[] args) {
            this.args = args;
        }

        private boolean checkSize() {
            return args.length == 3;
        }

        private boolean checkUrl() {
            return args[0].startsWith("http");
        }

        private boolean checkSpeed() {
            return Integer.parseInt(args[1]) != 0;
        }

        private boolean checkFile() {
            return args[2].contains(".");
        }

        public boolean isValid() {
            try {
                checkSize();
                checkUrl();
                checkSpeed();
                checkFile();
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Format: http://file.txt 5 file.txt");
            }
            return true;
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
