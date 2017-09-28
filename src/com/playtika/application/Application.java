package com.playtika.application;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Application {

    public int countWords(String text) {
        List<String> resultList = new LinkedList<>(Arrays.asList(text.split("[^a-zA-Z_0-9]")));
        resultList.removeIf((w) -> w.length() == 0);
        return resultList.size();
       }
}
