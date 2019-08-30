package com.sw.factory.impl;

import java.io.File;
import java.net.URL;
import java.util.Map;

import com.sw.cache.SwCache;
import com.sw.cache.impl.SimpleSwCache;
import com.sw.config.WordsProperties.CharSet;
import com.sw.factory.SensitiveWordsFactory;
import com.sw.model.WordsFile;
import com.sw.model.WordsLibrary;
import com.sw.utils.FileUtil;
/**
 * 默认的词库工厂
 * @author xzb
 *
 */
public class SimpleSwFactory implements SensitiveWordsFactory{

	@Override
	public WordsFile getFile() {
		// 获取默认的词库文件
		URL url = FileUtil.readerDefaultFile();
		// 返回结果
		WordsFile file = new WordsFile();
		file.setFileName(url.getFile());
		file.setFile(new File(url.getFile()));
		return file;
	}
	
	
	@Override
	public SwCache getCache() {
		return new SimpleSwCache();
	}


	@Override
	public WordsLibrary initLibrary(SwCache swCache, WordsFile file) {
		// 判断数据源
		if (null == swCache) {
			swCache = new SimpleSwCache();
		}
		WordsLibrary library = swCache.get(file.getFileName());
		if (null != library) {
			return library;
		}
		// 如果缓存没有 则创建一个
		Map<String, String> wordsMap = FileUtil.initializer(CharSet.UTF_8, file.getFile());
		library = new WordsLibrary(wordsMap);
		// 重新存入缓存
		swCache.put(file.getFileName(), library);
		// 返回仓库
		return library;
	}
	
	
	@Override
	public WordsLibrary initLibrary(WordsFile file) {
		// 获取词库内容
		Map<String, String> wordsMap = FileUtil.initializer(CharSet.UTF_8, file.getFile());
		// 返回仓库
		return new WordsLibrary(wordsMap);
	}

}
