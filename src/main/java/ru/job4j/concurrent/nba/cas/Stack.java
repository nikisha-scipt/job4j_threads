package ru.job4j.concurrent.nba.cas;

import net.jcip.annotations.NotThreadSafe;

import java.util.NoSuchElementException;

@NotThreadSafe
public class Stack<T> {

    private Node<T> head;

    public void offer(T value) {
        Node<T> temp = new Node<>(value);
        if (head == null) {
            head = temp;
            return;
        }
        temp.next = head;
        head = temp;
    }

    public T poll() {
        Node<T> temp = head;
        if (temp == null) {
            throw new NoSuchElementException("Stack is empty");
        }
        head = temp.next;
        temp.next = null;
        return temp.value;
    }

    private static class Node<T> {
        Node<T> next;
        T value;

        public Node(final T value) {
            this.value = value;
        }
    }

}
