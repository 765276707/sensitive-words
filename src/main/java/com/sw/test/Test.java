package com.sw.test;

import java.io.File;
import java.util.Map;
import java.util.Set;

import com.sw.checker.SwChecker;
import com.sw.config.CacheConfig;
import com.sw.config.CheckerConfig;
import com.sw.config.WordsProperties.MatchType;


public class Test {

	// 待校验内容
	public static String CONTENT = "三月，醉一场青春的流年。慢步在三月的春光里，走走停停逢八必灾，看花开嫣然，看春雨绵绵，感受春风拂面，春天，就是青春的流年。青春，"
				   + "是人生中最美的风景。青春，是一场花开的遇见；青春，是一场痛并快乐着的旅行海洛因；青春，是一场轰轰烈烈的比赛台湾国；青春heroin，"
				   + "是一场鲜衣奴马的争荣岁月；青春，是一场风花雪月的光阴短信群发器。";
	
	public static void main(String[] args) {		
		// 构建校验器
		
				
//		SensitiveWordsFilter filter = new SensitiveWordsFilter(() -> {
//			File file = FileUtils.readerFile("classpath:sensitive.txt");
//			Map<String, String> wordMap = FileUtils.initializer(CharSet.UTF_8, file);
//			WordsLibrary library = new WordsLibrary();
//			library.setWordsMap(wordMap);
//			return library;
//		}, new FilterConfig(MatchType.MAX_MATCH_TYPE));
		// 执行校验
		for (int i = 0; i < 2; i++) {
			CacheConfig cacheConfig = new CacheConfig(true);
			SwChecker checker = new SwChecker(new CheckerConfig(MatchType.MAX_MATCH_TYPE, cacheConfig));
			long startTime = System.currentTimeMillis();
			Set<String> defaultResult = checker.get(CONTENT);
//			System.out.println("是否包含敏感词：" + checker.contain(CONTENT));
//			System.out.println("语句中包含敏感词的个数为：" + defaultResult.size() + "。包含：" + defaultResult);
//			System.out.println("------------替换关敏感词结果------------");
//			System.out.println(checker.replace(CONTENT, "*"));
			long endTime = System.currentTimeMillis();
			System.out.println("操作耗时:" + (endTime - startTime));
		}
		
		
		
	}
}
