package com.playtika.application;

import com.playtika.text.analyzer.Text;

import java.util.*;
import java.util.function.Predicate;

public class Application {
    public static void main(String[] args) {
        Text text = new Text("\n I    I, have 5 dollars 5 alphabet 1 Alpha alpha");
        List<String> result = text.getTopWords(3);
        System.out.println(result);
    }

    public int countWords(String text) {
        List<String> resultList = new LinkedList<>(Arrays.asList(text.split("[^a-zA-Z_0-9]")));
        resultList.removeIf((w) -> w.length() == 0);
        return resultList.size();
       }
}
