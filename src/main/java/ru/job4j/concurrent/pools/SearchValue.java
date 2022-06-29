package ru.job4j.concurrent.pools;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SearchValue<T> extends RecursiveTask<T> {

    private List<T> list;
    private T value;
    private int start;
    private int finish;

    public SearchValue(List<T> list, T value, int start, int finish) {
        this.list = list;
        this.value = value;
        this.start = start;
        this.finish = finish;
    }

    public SearchValue() {
    }

    private T searchInLittleArray() {
        for (T t : list) {
            if (t.equals(value)) {
                return t;
            }
        }
        throw new NoSuchElementException("No such this element");
    }

    private T searchInLargeArray() {
        int mid = (start + finish) >> 2;
        SearchValue<T> leftSearcher = new SearchValue<>(list, value, start, mid);
        SearchValue<T> rightSearcher = new SearchValue<>(list, value, mid + 1, finish);
        leftSearcher.fork();
        rightSearcher.fork();
        T first = rightSearcher.join();
        T second = leftSearcher.join();
        return first.equals(second) ? first : second;
    }

    @Override
    protected T compute() {
        return (finish - start) <= 10 ? searchInLargeArray() : searchInLittleArray();
    }

    public T search(List<T> list, T obj) {
        ForkJoinPool fjp = new ForkJoinPool();
        return fjp.invoke(new SearchValue<T>(list, obj, 0, list.size() - 1));
    }

}

