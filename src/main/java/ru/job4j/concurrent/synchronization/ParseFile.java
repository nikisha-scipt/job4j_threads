package ru.job4j.concurrent.synchronization;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {

    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized String getContent() {
        return content(character -> true);
    }

    public synchronized String getContentWithoutUnicode() {
        return content(character -> character < 0x80);
    }

    public synchronized String content(Predicate<Character> filter) {
        StringBuilder result = new StringBuilder();
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = input.read()) != -1)  {
                if (filter.test((char) data)) {
                    result.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

}
