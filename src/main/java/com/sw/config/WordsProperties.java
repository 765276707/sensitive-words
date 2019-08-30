package com.sw.config;

/**
 * 词库的相关属性
 * @author xzb
 *
 */
public class WordsProperties {

	// 默认的配置文件路径前缀
	public static String[] FILE_LOCATION_PREFFIX = {"", "static/", "public/", "config/"};
	
	// 默认的文件名
	public static String DEFAULT_FILE_NAME = "sensitive-words.txt";
	
	// 匹配深度
	public static MatchType match;
	
	// 字符集
	public static CharSet charset;
	
	/**
	 * 匹配程度
	 * @author xzb
	 *
	 */
	public enum MatchType {
		
		/**最小匹配规则*/
		MIN_MATCH_TYPE(1),
		
		/**最大匹配规则*/
		MAX_MATCH_TYPE(2);
		
		MatchType(Integer value) {
			this.value = value;
		}
		
		public Integer value;

		public Integer value() {
			return value;
		}
	}
	
	/**
	 * 字符集
	 * @author xzb
	 *
	 */
	public enum CharSet {
		
		UTF_8("UTF-8"),
		UTF_16("UTF-16"),
		UTF_32("UTF-32"),
		GBK("GBK"),
		GB_2312("GB2312"),
		BIG_5("Big5");
		
		CharSet(String value) {
			this.value = value;
		}
		
		public String value;

		public String value() {
			return value;
		}
	}
	
}
