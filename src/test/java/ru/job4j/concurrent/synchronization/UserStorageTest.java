package ru.job4j.concurrent.synchronization;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UserStorageTest {

    private UserStorage storage;

    @Before
    public void init() {
        storage = new UserStorage();
    }

    @Test
    public void whenAddUsersVariousThread() throws InterruptedException {
        Thread one = new Thread(
                () -> {
                    storage.add(new User(1, "Danil", 100));
                    storage.add(new User(2, "Petr", 200));
                }
        );
        Thread two = new Thread(
                () -> {
                    storage.add(new User(3, "Test", 250));
                    storage.add(new User(4, "Testovich", 350));
                }
        );
        one.start();
        two.start();
        one.join();
        two.join();
        assertThat(storage.getUsers().size(), is(4));
    }

    @Test
    public void whenAddUsersVariousThreadAndTransfer50() throws InterruptedException {
        Thread one = new Thread(
                () -> {
                    storage.add(new User(1, "Danil", 100));
                    storage.add(new User(2, "Petr", 200));
                }
        );
        Thread two = new Thread(
                () -> {
                    storage.add(new User(3, "Test", 250));
                    storage.add(new User(4, "Testovich", 350));
                }
        );
        Thread transferThread = new Thread(
                () -> storage.transfer(1, 2, 50)
        );
        one.start();
        two.start();
        one.join();
        two.join();
        transferThread.start();
        transferThread.join();
        assertThat(storage.getUsers().get(1).getAmount(), is(50));
    }

    @Test
    public void whenAddUsersVariousThreadAndTransfer50Exception() throws InterruptedException {
        Thread one = new Thread(
                () -> {
                    storage.add(new User(1, "Danil", 20));
                    storage.add(new User(2, "Petr", 200));
                }
        );
        Thread two = new Thread(
                () -> {
                    storage.add(new User(3, "Test", 250));
                    storage.add(new User(4, "Testovich", 350));
                }
        );
        one.start();
        two.start();
        one.join();
        two.join();
        storage.transfer(1, 2, 50);
        assertThat(storage.getUsers().get(1).getAmount(), is(20));
    }

}