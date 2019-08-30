package com.sw.factory;

import com.sw.cache.SwCache;
import com.sw.model.WordsFile;
import com.sw.model.WordsLibrary;

/**
 * 敏感词抽象工厂
 * @author xzb
 *
 */
public interface SensitiveWordsFactory {

	/**
	 * 读取文件
	 * @return WordsFile 词库文件
	 */
	WordsFile getFile();
	
	/**
	 * 获取缓存
	 * @return
	 */
	SwCache getCache();
	
	/**
	 * 从缓存中获取词库
	 * @param swCache
	 * @param fileName
	 * @return
	 */
	WordsLibrary initLibrary(SwCache swCache, WordsFile file);
	
	/**
	 * 生成词库
	 * @return 
	 */
	WordsLibrary initLibrary(WordsFile file);
}
