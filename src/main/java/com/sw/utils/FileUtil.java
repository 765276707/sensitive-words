package com.sw.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sw.config.WordsProperties;
import com.sw.config.WordsProperties.CharSet;
import com.sw.exception.SwFileException;

/**
 * 加载配置文件工具
 * @author xzb
 *
 */
public class FileUtil {

	
	/**
	 * 获取所有默认的文件地址
	 * @return
	 */
	public static List<String> getDefaultFilePath() {
		List<String> pathList = new ArrayList<>();
		String[] preffixs = WordsProperties.FILE_LOCATION_PREFFIX;
		for (int i=0; i < preffixs.length; i++) {
			String filePath = preffixs[i] + WordsProperties.DEFAULT_FILE_NAME;
			pathList.add(filePath);
		}
		return pathList;
	}
	
	/**
	 * 读取默认文件
	 * @return
	 */
	public static URL readerDefaultFile() {
		URL url = null;
		List<String> paths = getDefaultFilePath();
		for (String path : paths) {
			url = FileUtil.class.getClassLoader().getResource(path);
			if (null != url) {
				break;
			}	
		}
		if (null == url) {
			throw new SwFileException("not found default words file");
		}
		return url;
	}
	
	
	/**
	 * 加载自定义文件
	 * @param filePath 文件路径
	 * @return
	 */
	public static URL readerFile(String filePath) {
		URL url = FileUtil.class.getClassLoader().getResource(filePath);
		if (null == url) {
			throw new SwFileException("not found default words file");
		}
		return url;
	}
	
	
	/**
	 * 读取词库
	 * @param charset 字符集
	 * @param file 文件
	 * @return
	 */
	public static Set<String> readerWords(String charset, File file) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new SwFileException("not found default words file");
		}
		InputStreamReader reader = new InputStreamReader(fis, Charset.forName(charset));
		BufferedReader bufferedReader = new BufferedReader(reader);
		String word = null;
		Set<String> wordSet = new HashSet<>();
		try {
			while ((word = bufferedReader.readLine()) != null) {
				// 将敏感词追行添加到set集合中
				wordSet.add(word.trim());
			}
		} catch (IOException e) {
			throw new SwFileException("reader file error");
		} finally {
			try {
				bufferedReader.close();
				reader.close();
			} catch (IOException e) {
				throw new SwFileException("close reader file error");
			}	
		}
		return wordSet;
	}
	
	
	/**
	 * 初始化敏感词库
	 * @param charset 字符集
	 * @param file 文件
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, String> initializer(CharSet charset, File file) {
		Map<String, String> SENSITIVE_WORDS = null;
		// 读取敏感词内容
		Set<String> wordSet = readerWords(charset.value(), file);
		// 初始胡
		SENSITIVE_WORDS = new HashMap<>(wordSet.size());
		Map tempMap = null;
		Map<String, String> resultMap = null;
		// 迭代
		Iterator<String> it = wordSet.iterator();
		while (it.hasNext()) {
			// 敏感词,去空格
			String word = it.next();
			tempMap = SENSITIVE_WORDS;
			for (int i=0; i<word.length(); i++) {
				char wordChar = word.charAt(i);       
				// 去中间空格
				if (Character.isSpaceChar(wordChar)) continue;
				// 获取值
				Object obj = tempMap.get(wordChar);
				if(null != obj) {        
					tempMap = (Map<String, Object>) obj;
				} else {    
					// 不存在则，则构建一个map，同时将isEnd设置为0
					resultMap = new HashMap<String, String>();
					// 是否为最后一个拆词字符
					resultMap.put("isEnd", "0");     
					tempMap.put(wordChar, resultMap);
					tempMap = resultMap;
				}				
				// 最后一个
				if(i == word.length() - 1){
					tempMap.put("isEnd", "1");    
				}
			}	
		}
		return SENSITIVE_WORDS;
	}
}
