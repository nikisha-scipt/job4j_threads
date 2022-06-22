package ru.job4j.concurrent.shared;

public class Cache {

    private static Cache cache;

    public static synchronized Cache instOf() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }

}
