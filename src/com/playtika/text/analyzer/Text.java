package com.playtika.text.analyzer;

import java.util.*;

public class Text {
	private final String inputText;

	public Text(String inputText) {
		this.inputText = inputText;
	}

	public int getLengthInChars() {
		List<String> allWordsList = getAllWordsList();
		int totalLength = 0;
		for (String word : allWordsList) {
			totalLength = totalLength + word.length();
		}
		return totalLength;
	}

	public List<String> getTopWords(int topWordsCount) {
		List<String> uniqueWordsList = getUniqueWordsList(getWordFrequencies());
		Collections.sort(uniqueWordsList);
		if (uniqueWordsList.size() < topWordsCount) return uniqueWordsList;
		return uniqueWordsList.subList(0, topWordsCount);
	}

	public Map<String, Integer> getWordFrequencies() {
		List<String> wordsList = getAllWordsList();
		Map<String, Integer> wordFrequencies = new HashMap<>();
		wordsList.forEach(
				(word) -> {
					word = word.toLowerCase();
					wordFrequencies.computeIfPresent(word, (key, value) -> value = value + 1);
					wordFrequencies.putIfAbsent(word, 1);
		});
		return wordFrequencies;
	}

	private List<String> getUniqueWordsList(Map<String, Integer> wordFrequencies) {
		wordFrequencies.entrySet().removeIf((entry) -> entry.getValue().compareTo(1) == 1);
		return new ArrayList<>(wordFrequencies.keySet());
	}

	private List<String> getAllWordsList() {
		if (inputText == null) {
			return new LinkedList<>();
		}
		List<String> resultList = new LinkedList<>(Arrays.asList(inputText.split("[^a-zA-Z_0-9]")));
		resultList.removeIf((w) -> w.length() == 0);
		return resultList;
	}
}
