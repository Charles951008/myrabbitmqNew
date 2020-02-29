package com.wsl.tools;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;

/**
 * @ClassName: PropertiesUtil
 * @Description: proprties文件处理
 */
public class PropertiesUtil {

	/**
	 * @Title: getPropertyByFileAndName
	 * @Description: 通过文件名与属性名获取属性值
	 * @param fileName 文件名
	 * @param key      属性key值
	 * @return
	 */
	public static String getPropertyByFileAndName(String fileName, String key) {
		java.util.Properties properties = new java.util.Properties();
		//Resource resource = new ClassPathResource(fileName);
		try {
			//properties = PropertiesLoaderUtils.loadProperties(resource);
			properties.load(PropertiesLoaderUtils.class.getClassLoader()
            .getResourceAsStream("configs/spring/openOffice.properties"));
			if (properties != null) {
				return properties.get(key).toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
