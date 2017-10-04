package com.playtika.text.analyzer;

import java.util.*;

import static java.util.Arrays.asList;

public class Text {
	private final String text;

	public Text(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Input text mustn't be nullable");
		}
		this.text = text;
	}

	public int getLengthInChars() {
		List<String> words = splitInWords();
		int totalLength = 0;
		for (String word : words) {
			totalLength = totalLength + word.length();
		}
		return totalLength;
	}

	public List<String> getTopWords(int topWordsCount) {
		if (topWordsCount <= 0) {
			throw new IllegalArgumentException("Parameter of this method must be positive");
		}
		TreeSet<String> uniqueWords = new TreeSet<>();
		uniqueWords.addAll(splitInWordsAndModifyToLowerCases());
		if (uniqueWords.size() < topWordsCount) {
			return new LinkedList<>(uniqueWords);
		}
		return new LinkedList<>(uniqueWords).subList(0, topWordsCount);
	}

	public Map<String, Integer> getWordFrequencies() {
		List<String> words = splitInWordsAndModifyToLowerCases();
		Map<String, Integer> wordFrequencies = new HashMap<>();
		words.forEach(
				(word) -> {
					wordFrequencies.computeIfPresent(word, (key, value) -> value = value + 1);
					wordFrequencies.putIfAbsent(word, 1);
		});
		return wordFrequencies;
	}

	private List<String> splitInWordsAndModifyToLowerCases() {
		LinkedList<String> lowerCaseWords = new LinkedList<>();
		splitInWords().forEach(word -> lowerCaseWords.add(word.toLowerCase()));
		return lowerCaseWords;
	}

	private List<String> splitInWords() {
		if (text.isEmpty()) {
			return new LinkedList<>();
		}
		return asList(text.split("\\W+"));
	}
}
