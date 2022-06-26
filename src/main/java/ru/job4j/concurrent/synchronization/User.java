package ru.job4j.concurrent.synchronization;

import lombok.Data;

@Data
public class User {

    private final int id;
    private String name;
    private int amount;

    public User(int id, String name, int amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

}
