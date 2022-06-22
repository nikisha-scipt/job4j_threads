package ru.job4j.concurrent.synchronization;

import lombok.*;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
@ToString(exclude = {"readLock"})
public class UserStorage {

    @GuardedBy("this")
    private final Object readLock = new Object();
    private Map<Integer, User> users = new HashMap<>();

    public boolean add(User user) {
        return users.putIfAbsent(user.getId(), user) != null;
    }

    public boolean update(User user) {
        return users.replace(user.getId(), user) != null;
    }

    public boolean delete(User user) {
        return users.remove(user.getId()) != null;
    }

    @Synchronized("readLock")
    public void transfer(int fromId, int toId, int amount) {
        User fromUser = users.get(fromId);
        User toUser = users.get(toId);
        toUser.setAmount(toUser.getAmount() + amount);
        fromUser.setAmount(fromUser.getAmount() - amount);
    }

    @Data
    public static final class User {

        private int id;
        private String name;
        private int amount;

        public User(int id, String name, int amount) {
            this.id = id;
            this.name = name;
            this.amount = amount;
        }
    }

    public static void main(String[] args) {
        UserStorage store = new UserStorage();
        store.add(new User(1, "Danil", 100));
        store.add(new User(2, "Petr", 200));
        Thread thread = new Thread(
                () -> store.transfer(1, 2, 50)
        );
        thread.start();
        System.out.println(store);
    }
}
