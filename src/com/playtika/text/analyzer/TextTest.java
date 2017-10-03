package com.playtika.text.analyzer;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class TextTest {

    Text text;
    String textWithoutWords;
    String textForTestingGetTopWordsMethod;

    @Before
    public void setUp() {
        textWithoutWords = "   \n \t ,.'//\n";
        textForTestingGetTopWordsMethod = "My name is Roma. It is my name";
    }

    @Test
    public void nullableTextHasZeroLength() {
        initialiseTestTextObject(null);
        int wordsTotalLength = text.getLengthInChars();
        assertThat(wordsTotalLength, is(0));
    }

    @Test
    public void textWithoutWordsHasZeroWordsLength() {
        initialiseTestTextObject(textWithoutWords);
        int wordsTotalLength = text.getLengthInChars();
        assertThat(wordsTotalLength, is(0));
    }

    @Test
    public void emptyTextHasZeroWordsLength() {
        initialiseTestTextObject("");
        int wordsTotalLength = text.getLengthInChars();
        assertThat(wordsTotalLength, is(0));
    }

    @Test
    public void countOfWordsTotalLengthThatSplitByNonLetterSymbols() {
        initialiseTestTextObject("   I,.\n want (WaNt) to Be a GOOd specialist. \n\t I  have 5 dollars");
        int wordsTotalLength = text.getLengthInChars();
        assertThat(wordsTotalLength, is(41));
    }

    @Test
    public void correctCountOfWordsTotalLength() {
        initialiseTestTextObject("I have 5 dollars");
        int wordsTotalLength = text.getLengthInChars();
        assertThat(wordsTotalLength, is(13));
    }

    @Test
    public void getTopUniqueWordsDetermination() {
        initialiseTestTextObject("What is your name? - My name is Roma.");
        List<String> topWords = text.getTopWords(2);
        assertThat(topWords, hasSize(is(2)));
        assertThat(topWords, hasItems(equalToIgnoringCase("My"), equalToIgnoringCase("Roma")));
    }

    @Test
    public void getTopUniqueWordsWithLettersInDifferentRegisters() {
        initialiseTestTextObject(textForTestingGetTopWordsMethod);
        List<String> topWords = text.getTopWords(2);
        assertThat(topWords, hasSize(is(2)));
        assertThat(topWords, hasItems(equalToIgnoringCase("It"), equalToIgnoringCase("Roma")));
    }

    @Test
    public void getLessUniqueWordsThenExpected() {
        initialiseTestTextObject(textForTestingGetTopWordsMethod);
        List<String> topWords = text.getTopWords(3);
        assertThat(topWords, hasSize(is(2)));
        assertThat(topWords, hasItems(equalToIgnoringCase("It"), equalToIgnoringCase("Roma")));
    }

    @Test
    public void emptyTextHasNoUniqueWords() {
        initialiseTestTextObject("");
        List<String> topWords = text.getTopWords(3);
        assertThat(topWords, empty());
    }

    @Test
    public void textWithoutWordsHasNoUniqueWords() {
        initialiseTestTextObject(textWithoutWords);
        List<String> topWords = text.getTopWords(3);
        assertThat(topWords, empty());
    }

    @Test
    public void nullableTextHasNoUniqueWords() {
        initialiseTestTextObject(null);
        List<String> topWords = text.getTopWords(3);
        assertThat(topWords, empty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTopWordsMethodWithZeroParameter() {
        initialiseTestTextObject(textForTestingGetTopWordsMethod);
        text.getTopWords(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTopWordsMethodWithNegativeParameterThrowsException() {
        initialiseTestTextObject(textForTestingGetTopWordsMethod);
        text.getTopWords(-3);
    }

    @Test
    public void getWordFrequenciesSimpleFlow() {
        initialiseTestTextObject("one, one, two");
        Map<String, Integer> wordFrequencies = text.getWordFrequencies();
        assertThat(wordFrequencies.size(), is(equalTo(2)));
        assertThat(wordFrequencies, allOf(
                hasEntry(equalToIgnoringCase("one"), is(2)),
                hasEntry(equalToIgnoringCase("two"), is(1))));
    }

    @Test
    public void getWordFrequenciesWithDifferentRegisterOfSymbolsInWords() {
        initialiseTestTextObject("One, one, two");
        Map<String, Integer> wordFrequencies = text.getWordFrequencies();
        assertThat(wordFrequencies.size(), is(equalTo(2)));
        assertThat(wordFrequencies, allOf(
                hasEntry(equalToIgnoringCase("one"), is(2)),
                hasEntry(equalToIgnoringCase("two"), is(1))));
    }

    @Test
    public void getWordFrequenciesOfOneRootWords() {
        initialiseTestTextObject("One, onedrive, two");
        Map<String, Integer> wordFrequencies = text.getWordFrequencies();
        assertThat(wordFrequencies.size(), is(equalTo(3)));
        assertThat(wordFrequencies, allOf(
                hasEntry(equalToIgnoringCase("one"), is(1)),
                hasEntry(equalToIgnoringCase("two"), is(1)),
                hasEntry(equalToIgnoringCase("onedrive"), is(1))));
    }

    @Test
    public void emptyTextHasEmptyWordFrequenciesMap() {
        initialiseTestTextObject("");
        Map<String, Integer> wordFrequencies = text.getWordFrequencies();
        assertThat(wordFrequencies.entrySet(), empty());
    }

    @Test
    public void nullableTextHasEmptyWordFrequenciesMap() {
        initialiseTestTextObject(null);
        Map<String, Integer> wordFrequencies = text.getWordFrequencies();
        assertThat(wordFrequencies.entrySet(), empty());
    }

    @Test
    public void textWithoutWordsHasEmptyWordFrequenciesMap() {
        initialiseTestTextObject(textWithoutWords);
        Map<String, Integer> wordFrequencies = text.getWordFrequencies();
        assertThat(wordFrequencies.entrySet(), empty());
    }

    void initialiseTestTextObject(String testedText) {
        this.text = new Text(testedText);
    }
}