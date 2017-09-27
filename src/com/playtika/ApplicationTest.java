package com.playtika;

import org.junit.Test;

import static org.junit.Assert.*;

public class ApplicationTest {
    @Test
    public void textIsSplitByWhitespace() {
        int wordsCount = new Application().countWords("I have 5 dollars");
        assertEquals(4, wordsCount);
    }

    @Test
    public void emptyTextHasNoWords() {
        int wordsCount = new Application().countWords("");
        assertEquals(0, wordsCount);
    }
    
    @Test
    public void textIsSplitByDoubleWhiteSpace() {
        int wordsCount = new Application().countWords("I  have  5  dollars");
        assertEquals(4, wordsCount);
    }

    @Test
    public void whitespaceTextHasNoWords() {
        int wordsCount = new Application().countWords("   ");
        assertEquals(0, wordsCount);
    }

    @Test
    public void textIsSplitByNonLetterSymbol() {
        int wordsCount = new Application().countWords("I,Have.5/dollars");
        assertEquals(4, wordsCount);
    }

    @Test
    public void textStartsFromWhiteSpace() {
        int wordsCount = new Application().countWords(" I Have 5 dollars");
        assertEquals(4, wordsCount);
    }

    @Test
    public void textStartsFromNonLetterSymbols() {
        int wordsCount = new Application().countWords("1/,I Have 5 dollars");
        assertEquals(4, wordsCount);
    }
}
