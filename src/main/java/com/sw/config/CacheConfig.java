package com.sw.config;

public class CacheConfig {

	// 是否开启缓存
	private Boolean enableCache;
	
	// 过期时间(单位：秒)
	private Integer expire;
	
	public CacheConfig(Boolean enableCache) {
		this.enableCache = enableCache;
	}

	public Boolean getEnableCache() {
		return enableCache;
	}

	public void setEnableCache(Boolean enableCache) {
		this.enableCache = enableCache;
	}

	public Integer getExpire() {
		return expire;
	}

	public void setExpire(Integer expire) {
		this.expire = expire;
	}

}
