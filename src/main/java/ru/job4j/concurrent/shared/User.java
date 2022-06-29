package ru.job4j.concurrent.shared;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class User {

    private int id;
    private String name;

    public static User of(String name) {
        User user = new User();
        user.name = name;
        return user;
    }

    public static List<User> listOf(List<User> list) {
        List<User> copy = new ArrayList<>(list.size());
        Collections.copy(copy, list);
        return copy;
    }

}
