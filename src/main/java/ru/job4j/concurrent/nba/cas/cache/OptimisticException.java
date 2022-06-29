package ru.job4j.concurrent.nba.cas.cache;

public class OptimisticException extends RuntimeException {

    public OptimisticException(String msg) {
        super(msg);
    }
}
