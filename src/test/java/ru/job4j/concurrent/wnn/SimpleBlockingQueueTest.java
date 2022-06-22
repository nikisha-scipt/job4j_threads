package ru.job4j.concurrent.wnn;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class SimpleBlockingQueueTest {

    private SimpleBlockingQueue<Integer> queue;

    @Before
    public void init() {
        this.queue = new SimpleBlockingQueue<>();
    }

    @Test
    public void whenExecute2ThreadThen0() throws InterruptedException {
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    queue.offer(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                queue.poll();
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(queue.getSize(), is(0));
    }

}