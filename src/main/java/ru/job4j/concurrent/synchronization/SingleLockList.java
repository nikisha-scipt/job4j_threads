package ru.job4j.concurrent.synchronization;

import lombok.Synchronized;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.*;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T>  {

    @GuardedBy("readLock")
    private final Object readLock = new Object();
    private final List<T> list;
    private int modCount;

    public SingleLockList(List<T> list) {
        this.list = list;
    }

    @Synchronized("readLock")
    public void add(T value) {
        list.add(value);
        modCount++;
    }

    public T get(int index) {
        return list.get(index);
    }

    public Iterator<T> copy(List<T> arr) {
        return new Iterator<T>() {

            final int expectedModCount = modCount;
            private int pointer;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return pointer < arr.size();
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No has element");
                }
                return arr.get(pointer++);
            }
        };
    }

    @Override
    @Synchronized("readLock")
    public Iterator<T> iterator() {
        return copy(this.list);
    }

}
