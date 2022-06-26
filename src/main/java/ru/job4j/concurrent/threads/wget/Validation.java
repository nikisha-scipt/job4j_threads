package ru.job4j.concurrent.threads.wget;

public class Validation {

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
