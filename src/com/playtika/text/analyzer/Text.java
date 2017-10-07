package com.playtika.text.analyzer;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

public class Text {
	private final String text;

	public Text(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Input text mustn't be nullable");
		}
		this.text = text;
	}

	public int getLengthInChars() {
		return splitInWords().parallelStream()
				.mapToInt(String::length)
				.sum();
	}

	public List<String> getTopWords(int topWordsCount) {
		if (topWordsCount <= 0) {
			throw new IllegalArgumentException("Parameter of this method must be positive");
		}
		return splitInWords().stream()
				.distinct()
				.sorted()
				.limit(topWordsCount)
				.collect(toList());
	}

	public Map<String, Integer> getWordFrequencies() {
		return splitInWords().stream()
				.collect(groupingBy(identity(), reducing(0, e -> 1, Integer::sum)));
	}

	private List<String> splitInWords() {
		LinkedList<String> words = new LinkedList<>();
		Pattern pattern = Pattern.compile("\\w+");
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			words.add(matcher.group().toLowerCase());
		}
		return words;
	}
}
