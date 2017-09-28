package com.playtika.text.analyzer;

import java.util.*;

public class Text {
	private final String inputText;

	public Text(String inputText) {
		this.inputText = inputText;
	}

	public List<String> getTopWords(int topWordsCount) {
		Map<String, Integer> allWordsWithCount = countAllWords();
		allWordsWithCount.entrySet().removeIf((entry) -> entry.getValue().compareTo(1) == 1);
		List<String> uniqueWordsList = new ArrayList<>(allWordsWithCount.keySet());
		Collections.sort(uniqueWordsList);
		if (uniqueWordsList.size() < topWordsCount) return uniqueWordsList;
		return uniqueWordsList.subList(0, topWordsCount);
	}

	private Map<String, Integer> countAllWords() {
		List<String> wordsList = getAllWords();
		Map<String, Integer> wordsCount = new HashMap<>();
		wordsList.forEach(
				(word) -> {
					wordsCount.computeIfPresent(word, (key, value) -> value = value + 1);
					wordsCount.putIfAbsent(word, 1);
		});
		return wordsCount;
	}

	private List<String> getAllWords() {
		List<String> resultList = new LinkedList<>(Arrays.asList(inputText.split("[^a-zA-Z_0-9]")));
		resultList.removeIf((w) -> w.length() == 0);
		return resultList;
	}
}
