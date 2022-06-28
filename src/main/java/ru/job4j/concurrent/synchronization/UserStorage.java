package ru.job4j.concurrent.synchronization;

import lombok.*;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
@ToString(exclude = {"readLock"})
@Data
public class UserStorage {

    @GuardedBy("readLock")
    private final Object readLock = new Object();
    private final Map<Integer, User> users = new HashMap<>();

    @Synchronized("readLock")
    public boolean add(User user) {
        return users.putIfAbsent(user.getId(), user) == null;
    }

    @Synchronized("readLock")
    public boolean update(User user) {
        return users.replace(user.getId(), user) != null;
    }

    @Synchronized("readLock")
    public boolean delete(User user) {
        return users.remove(user.getId()) != null;
    }

    @Synchronized("readLock")
    public void transfer(int fromId, int toId, int amount) {
        User fromUser = users.get(fromId);
        User toUser = users.get(toId);
        if (fromUser == null || toUser == null) {
            throw new IllegalArgumentException("User is null");
        }
        if (fromUser.getAmount() >= amount) {
            toUser.setAmount(toUser.getAmount() + amount);
            fromUser.setAmount(fromUser.getAmount() - amount);
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
