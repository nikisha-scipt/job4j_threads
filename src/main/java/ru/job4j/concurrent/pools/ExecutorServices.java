package ru.job4j.concurrent.pools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServices {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors() /* Создает пул нитей по количеству доступных процессоров */
        );

        pool.submit(() -> System.out.println("Execute " + Thread.currentThread().getName())); /* Добавляет задачу в пул и сразу ее выполняет */
        pool.submit(() -> System.out.println("Execute " + Thread.currentThread().getName()));

        pool.shutdown(); /* Закрываем пул и ждем пока все задачи завершатся */
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Execute " + Thread.currentThread().getName());
    }

}
