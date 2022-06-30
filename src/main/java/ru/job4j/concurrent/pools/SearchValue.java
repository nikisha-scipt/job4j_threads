package ru.job4j.concurrent.pools;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

@RequiredArgsConstructor
public class SearchValue<T> extends RecursiveTask<Integer> {

    private final T[] list;
    private final T value;
    private final int start;
    private final int finish;

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

    public static int search(Integer[] list, int value) {
        ForkJoinPool fork = new ForkJoinPool();
        return fork.invoke(new SearchValue<>(list, value, 0, list.length - 1));
    }

}

