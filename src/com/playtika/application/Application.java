package com.playtika.application;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Application {

    public int countWords(String text) {
        if (text == null) {
            return 0;
        }
        List<String> words = new LinkedList<>(Arrays.asList(text.split("[^a-zA-Z_0-9]")));
        words.removeIf((w) -> w.length() == 0);
        return words.size();
       }
}
