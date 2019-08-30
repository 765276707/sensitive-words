package com.sw.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 敏感词库
 * @author xzb
 *
 */
public class WordsLibrary {

	// 词库
	private Map<String, String> wordsMap = new HashMap<>();
	
	public WordsLibrary() {
	}

	public WordsLibrary(Map<String, String> wordsMap) {
		this.wordsMap = wordsMap;
	}

	public Map<String, String> getWordsMap() {
		return wordsMap;
	}

	public void setWordsMap(Map<String, String> wordsMap) {
		this.wordsMap = wordsMap;
	}
	
}
