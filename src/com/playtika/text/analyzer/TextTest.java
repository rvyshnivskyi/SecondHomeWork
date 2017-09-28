package com.playtika.text.analyzer;

import org.junit.Test;

import static org.junit.Assert.*;

public class TextTest {

    public Text text;

    @Test
    public void nullableTextHasZeroLength() {
        initialiseTestTextObject(null);
        int wordsTotalLength = text.getLengthInChars();
        assertEquals(0, wordsTotalLength);
    }

    @Test
    public void textWithoutWordsHasZeroWordsLength() {
        initialiseTestTextObject("   \n \t ,.'//\n");
        int wordsTotalLength = text.getLengthInChars();
        assertEquals(0, wordsTotalLength);
    }

    @Test
    public void correctCountOfWordsTotalLength() {
        initialiseTestTextObject("   I,.\n want (WaNt) to Be a GOOd specialist. \n\t I  have 5 dollars");
        int wordsTotalLength = text.getLengthInChars();
        assertEquals(41, wordsTotalLength);
    }

    private void initialiseTestTextObject(String testedText) {
        this.text = new Text(testedText);
    }
}