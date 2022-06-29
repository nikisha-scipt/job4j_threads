package ru.job4j.concurrent.nba.cas.cache;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CacheTest {

    private Cache cache;

    @Before
    public void init() {
        cache = new Cache();
    }

    @Test
    public void whenAddAtCache() throws InterruptedException {
        Thread one = new Thread(
                () -> {
                    cache.add(new Base(1, 0));
                    cache.add(new Base(2, 0));
                    cache.add(new Base(3, 0));
                }
        );
        Thread two = new Thread(
                () -> {
                    cache.add(new Base(4, 0));
                    cache.add(new Base(5, 0));
                    cache.add(new Base(6, 0));
                }
        );
        one.start();
        two.start();
        one.join();
        two.join();
        int expected = 6;
        assertThat(expected, is(cache.getMemory().size()));
    }

    @Test
    public void whenDeleteAtCache() throws InterruptedException {
        Thread one = new Thread(
                () -> {
                    cache.add(new Base(1, 0));
                    cache.add(new Base(2, 0));
                    cache.add(new Base(3, 0));
                }
        );
        Thread two = new Thread(
                () -> {
                    cache.add(new Base(4, 0));
                    cache.add(new Base(5, 0));
                    cache.add(new Base(6, 0));
                    cache.delete(new Base(6, 0));
                }
        );
        one.start();
        two.start();
        one.join();
        two.join();
        int expected = 5;
        assertThat(expected, is(cache.getMemory().size()));
    }

    @Test
    public void whenUpdateAtCache() throws InterruptedException {
        Thread one = new Thread(
                () -> {
                    cache.add(new Base(1, 0));
                    cache.add(new Base(2, 0));
                    cache.add(new Base(3, 0));
                }
        );
        Thread two = new Thread(
                () -> {
                    cache.add(new Base(4, 0));
                    cache.add(new Base(5, 0));
                    cache.add(new Base(6, 0));
                }
        );
        one.start();
        two.start();
        one.join();
        two.join();
        cache.update(cache.getMemory().get(5));
        Base updateBase = cache.getMemory().get(5);
        assertThat(1, is(updateBase.getVersion()));
    }

    @Test(expected = OptimisticException.class)
    public void whenUpdateAtCacheAndException() {
        Base one = new Base(1, 0);
        Base two = new Base(1, 1);
        cache.add(one);
        cache.update(two);
    }



}