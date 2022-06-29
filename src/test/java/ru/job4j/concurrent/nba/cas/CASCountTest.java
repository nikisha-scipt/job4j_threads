package ru.job4j.concurrent.nba.cas;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CASCountTest {

    private CASCount casCount;

    @Before
    public void init() {
        casCount = new CASCount();
        casCount.startCountIncrement(0);
    }

    @Test
    public void whenIncrementAndGetValueAtTwoThreads() throws InterruptedException {
        Thread one = new Thread(
                () -> {
                    casCount.increment();
                    casCount.increment();
                    casCount.increment();
                    casCount.increment();
                }
        );
        one.start();

        Thread two = new Thread(
                () -> {
                    casCount.increment();
                    casCount.increment();
                    casCount.increment();
                    casCount.increment();
                }
        );
        two.start();

        one.join();
        two.join();

        int expected = 8;

        assertThat(expected, is(casCount.get()));
    }

}