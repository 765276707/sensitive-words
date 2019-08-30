package com.sw.cache.impl;

import java.util.HashMap;
import java.util.Map;

import com.sw.cache.SwCache;
import com.sw.model.WordsLibrary;
/**
 * <p style="color:red;">轻量级缓存</p>
 * <span style="color:red;">【说明】</span>适用于词库量不大的情况下，词库 <=2000条
 * <span style="color:red;">【说明】</span>简单缓存过期时间由expire()的返回值决定，返回值小于0，代表永不过期
 * @author xzb
 *
 */
public class SimpleSwCache implements SwCache{

	// 数据缓存
	public static Map<String, WordsLibrary> LIBRARY_CACHE = new HashMap<>();
	
	// 时间缓存
	public static Map<String, Long> TIME_CACHE = new HashMap<>();
	
	public static long EXPIRE_TIME = -1;

	@Override
	public void put(String key, WordsLibrary library) {
		
		System.out.println("存入了cache");
		
		LIBRARY_CACHE.put(key, library);
		TIME_CACHE.put(key + ":TIME", System.currentTimeMillis());	
	}

	@Override
	public WordsLibrary get(String key) {
		
		System.out.println("查询了cache");
		
		Long time = TIME_CACHE.get(key + ":TIME");
		WordsLibrary library = LIBRARY_CACHE.get(key);
		// 判断是否过期, 过期时间为负数代表永不过期
		if (EXPIRE_TIME > -1) {
			if (null==time || System.currentTimeMillis()-time > EXPIRE_TIME) {			
				// 过期清除缓存
				remove(key);
				return null;
			}
		}
		return library;
	}

	@Override
	public void remove(String key) {
		LIBRARY_CACHE.remove(key);
		TIME_CACHE.remove(key + ":TIME");
	}

	@Override
	public void clear() {
		LIBRARY_CACHE.clear();
		TIME_CACHE.clear();
	}

	@Override
	public void expire(long time) {
		// TODO Auto-generated method stub
		EXPIRE_TIME = time;
	}
	
}
