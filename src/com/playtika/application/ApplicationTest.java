package com.playtika.application;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ApplicationTest {

    public Application application;

	@Before
	public void setUp() {
		application = new Application();
	}

	@Test
    public void textIsSplitByWhitespace() {
        int wordsCount = application.countWords(" I have 5     dollars");
        assertEquals(4, wordsCount);
    }

    @Test
    public void emptyTextHasNoWords() {
        int wordsCount = application.countWords("");
        assertEquals(0, wordsCount);
    }

    @Test
    public void textWithoutWordsHasNoWords() {
        int wordsCount = application.countWords("\n\n  ,./ ");
        assertEquals(0, wordsCount);
    }

    @Test
    public void textIsSplitByNonLetterSymbols() {
        int wordsCount = application.countWords(",\nI,\tHave.5/dollars");
        assertEquals(4, wordsCount);
    }

    @Test
    public void nullableTextHasNoWords() {
        int wordCount = application.countWords(null);
        assertEquals(0, wordCount);
    }
}
