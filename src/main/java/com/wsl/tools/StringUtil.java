package com.wsl.tools;

import java.util.UUID;

/**
 * @ClassName: StringUtil
 * @Description: 字符处理工具
 */
public final class StringUtil {

	/**
	 * @Title: isEmpty
	 * @Description: 判空/""处理
	 * @param target 字符串
	 * @return	是否为空,是true,否者false
	 */
	public static boolean isEmpty(Object target) {
		return "".equals(target) || null == target;
	}

	/**
	 * @Title: getFileSuffix
	 * @Description: 获取后缀
	 * @param fileName 文件名
	 * @return 后缀名
	 */
	public static String getFileSuffix(String fileName) {
		if (StringUtil.isEmpty(fileName) || fileName.lastIndexOf(".") < 0) {
			return "";
		}
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
	
	
	public static boolean IsNullOrEmpty(String s) {
		if (s == null || s.isEmpty())
			return true;
		return false;
	}
	
	public static String getUUID()
	{
		UUID uuid=UUID.randomUUID();
		return uuid.toString().replace("-", "").toUpperCase();
	}
	
	
}
