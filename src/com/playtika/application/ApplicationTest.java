package com.playtika.application;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ApplicationTest {

	private Application application;

	@Before
	public void setUp() {
		application = new Application();
	}

	@Test
    public void textIsSplitByWhitespace() {
        int wordsCount = application.countWords("I have 5 dollars");
        assertEquals(4, wordsCount);
    }

    @Test
    public void emptyTextHasNoWords() {
        int wordsCount = application.countWords("");
        assertEquals(0, wordsCount);
    }
    
    @Test
    public void textIsSplitByDoubleWhitespace() {
        int wordsCount = application.countWords("I  have  5  dollars");
        assertEquals(4, wordsCount);
    }

    @Test
    public void whitespaceTextHasNoWords() {
        int wordsCount = application.countWords("   ");
        assertEquals(0, wordsCount);
    }

    @Test
    public void newLinesTextHasNoWords() {
        int wordsCount = application.countWords("\n\n\n");
        assertEquals(0, wordsCount);
    }

    @Test
    public void textIsSplitByNonLetterSymbols() {
        int wordsCount = application.countWords("I,Have.5/dollars");
        assertEquals(4, wordsCount);
    }

    @Test
    public void textStartsFromWhiteSpace() {
        int wordsCount = application.countWords(" I Have 5 dollars");
        assertEquals(4, wordsCount);
    }

    @Test
    public void textStartsFromNonLetterSymbols() {
        int wordsCount = application.countWords("/,I Have 5 dollars");
        assertEquals(4, wordsCount);
    }

    @Test
    public void textIsSplitByNewLineAndTab() {
        int wordsCount = application.countWords("\nI \n\n Have \t 5 dollars");
        assertEquals(4, wordsCount);
    }
}
