package ru.job4j.concurrent.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SearchValue<T> extends RecursiveTask<Integer> {

    private T[] list;
    private T value;
    private int start;
    private int finish;

    public SearchValue(T[] list, T value, int start, int finish) {
        this.list = list;
        this.value = value;
        this.start = start;
        this.finish = finish;
    }

    public SearchValue() {
    }

    private int searchInLittleArray() {
        for (int i = start; i <= finish; i++) {
            if (list[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    private int searchInLargeArray() {
        int mid = (start + finish) / 2;
        SearchValue<T> leftSearcher = new SearchValue<>(list, value, start, mid);
        SearchValue<T> rightSearcher = new SearchValue<>(list, value, mid + 1, finish);
        leftSearcher.fork();
        rightSearcher.fork();
        Integer first = rightSearcher.join();
        Integer second = leftSearcher.join();
        return Math.max(first, second);
    }

    @Override
    protected Integer compute() {
        return (finish - start) <= 10 ? searchInLittleArray() : searchInLargeArray();
    }

    public Integer search(T[] list, T obj) {
        ForkJoinPool fork = new ForkJoinPool();
        return fork.invoke(new SearchValue<T>(list, obj, 0, list.length - 1));
    }

}

