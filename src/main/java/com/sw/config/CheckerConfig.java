package com.sw.config;

import com.sw.config.WordsProperties.MatchType;
import com.sw.exception.WordsInitException;

public class CheckerConfig {

	// 匹配深度
	private Integer matchType;

	// 配置
	private CacheConfig cache;
	
	public CheckerConfig(MatchType matchtype, CacheConfig cache) {
		if (null == matchtype) {
			// 默认为最大深度
			matchtype = MatchType.MAX_MATCH_TYPE; 
		}
		if (null == cache) {
			// 默认不开启缓存
			cache = new CacheConfig(false);
		}
		this.setCache(cache);
		this.matchType = matchtype.value();
	}

	public Integer getMatchType() {
		return matchType;
	}

	public void setMatchType(Integer matchType) {
		this.matchType = matchType;
	}
	
	public CacheConfig getCache() {
		return cache;
	}

	public void setCache(CacheConfig cache) {
		this.cache = cache;
	}


}
