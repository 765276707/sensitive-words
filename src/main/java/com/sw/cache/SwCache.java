package com.sw.cache;

import com.sw.model.WordsLibrary;

/**
 * <b style="color:red;">缓存接口</b><br>
 * 【注意】缓存的过期时间应由expire()的返回值来决定，单位为毫秒
 * @author xzb
 *
 */
public interface SwCache {

	/**
	 * 新增缓存
	 * @param key 缓存key
	 * @param library 词库
	 */
	public void put(String key, WordsLibrary library);
	
	/**
	 * 获取缓存的词库
	 * @param key 缓存key
	 * @return
	 */
	public WordsLibrary get(String key);
	
	/**
	 * 移除缓存
	 * @param key 缓存key
	 */
	public void remove(String key);
	
	/**
	 * 清除缓存
	 */
	public void clear();
	
	/**
	 * 设置过期时间, 单位毫秒
	 * @return 
	 */
	public void expire(long time);
}
