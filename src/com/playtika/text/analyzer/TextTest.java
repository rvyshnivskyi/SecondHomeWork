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

    @Before
    public void setUp() {
        textWithoutWords = "   \n \t ,.'//\n";
    }

    @Test(expected = IllegalArgumentException.class)
    public void textConstructorThrowsExceptionWhenGetNullableParameter() {
        new Text(null);
    }

    @Test
    public void textWithoutWordsHasZeroWordsLength() {
        text = new Text(textWithoutWords);
        int wordsTotalLength = text.getLengthInChars();
        assertThat(wordsTotalLength, is(0));
    }

    @Test
    public void emptyTextHasZeroWordsLength() {
        text = new Text("");
        int wordsTotalLength = text.getLengthInChars();
        assertThat(wordsTotalLength, is(0));
    }

    @Test
    public void countOfWordsTotalLengthThatSplitByNonLetterSymbols() {
        text = new Text("   I,.\n want (WaNt) to Be a GOOd specialist. \n\t I  have 5 dollars");
        int wordsTotalLength = text.getLengthInChars();
        assertThat(wordsTotalLength, is(41));
    }

    @Test
    public void correctCountOfWordsTotalLength() {
        text = new Text("I have 5 dollars");
        int wordsTotalLength = text.getLengthInChars();
        assertThat(wordsTotalLength, is(13));
    }

    @Test
    public void getTopUniqueWordsDetermination() {
        text = new Text("What is your name? - Roma is my name.");
        List<String> topWords = text.getTopWords(2);
        assertThat(topWords, hasSize(is(2)));
        assertThat(topWords, hasItems("is", "my"));
    }

    @Test
    public void getTopWordsWhenTextStartsFromNonLetterSymbol() {
        text = new Text(".name");
        List<String> topWords = text.getTopWords(2);
        assertThat(topWords, hasSize(is(1)));
        assertThat(topWords, hasItems("name"));
    }

    @Test
    public void getTopUniqueWordsWithLettersInDifferentRegisters() {
        text = new Text("My name is Roma. Is it my name");
        List<String> topWords = text.getTopWords(2);
        assertThat(topWords, hasSize(is(2)));
        assertThat(topWords, hasItems("is", "it"));
    }

    @Test
    public void getLessUniqueWordsThenExpected() {
        text = new Text("is it. it is");
        List<String> topWords = text.getTopWords(3);
        assertThat(topWords, hasSize(is(2)));
        assertThat(topWords, hasItems("is", "it"));
    }

    @Test
    public void emptyTextHasNoUniqueWords() {
        text = new Text("");
        List<String> topWords = text.getTopWords(3);
        assertThat(topWords, is(empty()));
    }

    @Test
    public void textWithoutWordsHasNoUniqueWords() {
        text = new Text(textWithoutWords);
        List<String> topWords = text.getTopWords(3);
        assertThat(topWords, is(empty()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTopWordsMethodWithZeroParameter() {
        text = new Text("My name is Roma. It is my name");
        text.getTopWords(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTopWordsMethodWithNegativeParameterThrowsException() {
        text = new Text("My name is Roma. It is my name");
        text.getTopWords(-3);
    }

    @Test
    public void getWordFrequenciesSimpleFlow() {
        text = new Text("one, one, two");
        Map<String, Integer> wordFrequencies = text.getWordFrequencies();
        assertThat(wordFrequencies.size(), is(equalTo(2)));
        assertThat(wordFrequencies, allOf(
                hasEntry("one", 2),
                hasEntry("two", 1)));
    }

    @Test
    public void getWordFrequenciesWithDifferentRegisterOfSymbolsInWords() {
        text = new Text("One, one, two");
        Map<String, Integer> wordFrequencies = text.getWordFrequencies();
        assertThat(wordFrequencies.size(), is(equalTo(2)));
        assertThat(wordFrequencies, allOf(
                hasEntry("one", 2),
                hasEntry("two", 1)));
    }

    @Test
    public void getWordFrequenciesWhenTextStartsFromNonLetterSymbol() {
        text = new Text(",one, one, two");
        Map<String, Integer> wordFrequencies = text.getWordFrequencies();
        assertThat(wordFrequencies.size(), is(equalTo(2)));
        assertThat(wordFrequencies, allOf(
                hasEntry("one", 2),
                hasEntry("two", 1)));
    }

    @Test
    public void getWordFrequenciesOfOneRootWords() {
        text = new Text("One, onedrive, two");
        Map<String, Integer> wordFrequencies = text.getWordFrequencies();
        assertThat(wordFrequencies.size(), is(equalTo(3)));
        assertThat(wordFrequencies, allOf(
                hasEntry("one", 1),
                hasEntry("two", 1),
                hasEntry("onedrive", 1)));
    }

    @Test
    public void emptyTextHasEmptyWordFrequenciesMap() {
        text = new Text("");
        Map<String, Integer> wordFrequencies = text.getWordFrequencies();
        assertThat(wordFrequencies.entrySet(), is(empty()));
    }

    @Test
    public void textWithoutWordsHasEmptyWordFrequenciesMap() {
        text = new Text(textWithoutWords);
        Map<String, Integer> wordFrequencies = text.getWordFrequencies();
        assertThat(wordFrequencies.entrySet(), is(empty()));
    }
}