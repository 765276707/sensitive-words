package com.sw.checker;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.sw.config.CacheConfig;
import com.sw.config.CheckerConfig;
import com.sw.config.WordsProperties.MatchType;
import com.sw.exception.WordsInitException;
import com.sw.factory.SensitiveWordsFactory;
import com.sw.factory.impl.SimpleSwFactory;
import com.sw.model.WordsFile;
import com.sw.model.WordsLibrary;

/**
 * 敏感词过滤器
 * @author xzb
 *
 */
public class SwChecker {

	// 最终使用的敏感词库
	private Map<String, String> sensitiveWordsMap;
	
	// 配置
	private CheckerConfig config;
	
	/**
	 * 默认工厂进行构造过滤器
	 */
	public SwChecker(CheckerConfig checkerConfig) {
		// 配置
		if (null == checkerConfig) {			
			// 如果传入的配置为空，则给一个默认配置（最大深度）	
			config = new CheckerConfig(MatchType.MAX_MATCH_TYPE, new CacheConfig(false));
		} else {
			config = checkerConfig;
		}
		// 采用默认工厂初始化
		SimpleSwFactory simpleFactory = new SimpleSwFactory();
		// 获取缓存配置
		WordsLibrary library = null;
		WordsFile wordsFile = simpleFactory.getFile();
		CacheConfig cacheConfig = config.getCache();		
		if (cacheConfig.getEnableCache()) {
			library = simpleFactory.initLibrary(simpleFactory.getCache(), wordsFile);
		} else {
			library = simpleFactory.initLibrary(wordsFile);
		}
		// 判断词库是否为空
		if (null == library) {
			// 字典初始化失败
			throw new WordsInitException("default sensitive words library initialization failed: library can not be null");
		}
		// 赋值
		sensitiveWordsMap = library.getWordsMap();				
	}
	
	
	/**
	 * 自定义工厂构造过滤器
	 * @param factory
	 */
	public SwChecker(SensitiveWordsFactory factory, CheckerConfig checkerConfig) {
		// 配置
		if (null == checkerConfig) {
			// 如果传入的配置为空，则给一个默认配置（最大深度）	
			config = new CheckerConfig(MatchType.MAX_MATCH_TYPE, new CacheConfig(false));
		} else {
			config = checkerConfig;
		}
		// 获取缓存配置
		WordsLibrary library = null;
		WordsFile wordsFile = factory.getFile();
		CacheConfig cacheConfig = config.getCache();
		if (cacheConfig.getEnableCache()) {
			library = factory.initLibrary(factory.getCache(), wordsFile);
		} else {
			library = factory.initLibrary(wordsFile);
		}
		if (null == library) {
			// 字典初始化失败
			throw new WordsInitException("custom sensitive words library initialization failed: library can not be null");
		}
		// 赋值
		sensitiveWordsMap = library.getWordsMap();	
	}
	
	
	/**
	 * 检查是否有敏感字符
	 * @param content 内容
	 * @param startIndex 开始下标
	 * @return
	 */
    @SuppressWarnings({ "rawtypes"})
    public int check(String content, int startIndex) {
    	// 敏感词结束标识位：用于敏感词只有1位的情况
    	boolean flag = false;   
    	// 匹配标识数默认为0
    	int matchFlag = 0;     
        char word = 0;
        // 如果用户没有自己加装敏感字符，则默认
        Map wordMap = sensitiveWordsMap;
        for(int i = startIndex; i < content.length() ; i++){
            word = content.charAt(i);
            // 获取指定key
            wordMap = (Map) wordMap.get(word);     
            if(wordMap != null){     
            	// 存在，则判断是否为最后一个，找到相应key，匹配标识+1
                matchFlag++;
                // 如果为最后一个匹配规则,结束循环，返回匹配标识数
                if("1".equals(wordMap.get("isEnd"))) {
                	// 结束标志位为true 
                    flag = true;         
                    // 最小规则，直接返回,最大规则还需继续查找
                    if(1 == config.getMatchType()) {    
                        break;
                    }
                }
            } else { 
            	// 不存在，直接返回
                break;
            }
        }     
        if(!flag) {     
            matchFlag = 0;
        }
        return matchFlag;
    }
    
    
    /**
     * 判断文字是否包含敏感字符
     * @param content 内容
     * @return
     */
	public boolean contain(String content){
		boolean flag = false;
		for(int i = 0 ; i < content.length() ; i++){
			int matchFlag = check(content, i); 
			// 判断是否包含敏感字符
			if(matchFlag > 0){    
				// 大于0存在，返回true
				flag = true;
			}
		}
		return flag;
	}
    
	
    /**
     * 替换敏感字字符
     * @param content 内容
     * @param replaceChar 替换字符
     * @return
     */
	public String replace(String content, String replaceChar){
		String resultTxt = content;
		// 获取所有的敏感词
		Set<String> set = get(content);     
		Iterator<String> iterator = set.iterator();
		String word = null;
		String replaceString = null;
		// 如果替换字符为null，默认成*
		replaceChar = replaceChar==null?"*":replaceChar;
		while (iterator.hasNext()) {
			word = iterator.next();
			replaceString = getReplaceChars(replaceChar, word.length());
			resultTxt = resultTxt.replaceAll(word, replaceString);
		}
		// 返回替换后的文本
		return resultTxt;
	}
	
	
	/**
	 * 获取文字中的敏感词集
	 * @param content 内容
	 * @return
	 */
	public Set<String> get(String content){
		Set<String> sensitiveWordList = new HashSet<String>();		
		for(int i = 0 ; i < content.length() ; i++){
			int length = check(content, i);    
			// 判断是否包含敏感字符
			if(length > 0){    
				// 存在,加入list中
				sensitiveWordList.add(content.substring(i, i+length));
				 // 减1的原因，是因为for会自增
				i = i + length - 1;   
			}
		}
		// 返回文字中的敏感词
		return sensitiveWordList;
	}
	
	
	/**
	 * 获取替换字符串
	 * @param replaceChar 替换字符
	 * @param length 长度
	 * @return
	 */
	private String getReplaceChars(String replaceChar, int length){
		String resultReplace = replaceChar;
		for(int i = 1 ; i < length ; i++){
			resultReplace += replaceChar;
		}
		return resultReplace;
	}
	
}
