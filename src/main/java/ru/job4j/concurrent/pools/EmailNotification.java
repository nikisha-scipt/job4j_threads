package ru.job4j.concurrent.pools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private final ExecutorService services = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public String emailTo(User user) {
        return String.format("subject = Notification %s to email %s%nbody = Add a new event to %s%n", user.getName(), user.getEmail(), user.getName());
    }

    public void send(String subject, String body, String email) {
        System.out.println(subject);
        System.out.println(body);
        System.out.println(email);
    }

    public void close() {
        services.shutdown();
        while (!services.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public ExecutorService getServices() {
        return services;
    }

    public static void main(String[] args) {
        User user = new User("Danil", "danil.nikisha@mail.ru");
        EmailNotification notification = new EmailNotification();
        notification.getServices().submit(() -> {
            String subject = user.getName();
            String body = notification.emailTo(user);
            String email = user.getEmail();
            notification.send(subject, body, email);
        });
        notification.close();
    }

}
