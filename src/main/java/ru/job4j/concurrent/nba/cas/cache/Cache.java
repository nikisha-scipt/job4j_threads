package ru.job4j.concurrent.nba.cas.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {

    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) throws OptimisticException {
        return memory.computeIfPresent(model.getId(), (version, stored) -> {
            int oldVersion = stored.getVersion();
            if (oldVersion != model.getVersion()) {
                throw new OptimisticException("Versions two Base not equal");
            }
            return new Base(stored.getId(), oldVersion + 1);
        }) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }

    public Map<Integer, Base> getMemory() {
        return memory;
    }
}
