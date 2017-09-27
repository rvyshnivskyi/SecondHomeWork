package com.playtika.application;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class Application {
    public static void main(String[] args) {

    }

    public int countWords(String text) {
        List<String> resultList = new LinkedList<>(Arrays.asList(text.split("[^a-zA-Z_0-9]")));
        resultList.removeIf((w) -> w.length() == 0);
        return resultList.size();
    }
}
