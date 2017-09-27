package com.playtika;

public class Application {
    public static void main(String[] args) {

    }

    public int countWords(String text) {
        if (text.length() == 0) return 0;
        return text.split("[^a-zA-Z_0-9]").length;
    }
}
