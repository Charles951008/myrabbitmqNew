package com.wsl.tools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @ClassName: LogUtil
 * @Description: 日志工具类
 */
public class LogUtil {

	/**
	 * 替换模板
	 */
	public static final String TEMPLATE_STRING = "{{}}";

	/**
	 * 
	 * @Title: get
	 * @Description: 获得Logger
	 * @param clazz 日志发出的类
	 * @return
	 */
	public static Logger get(Class<?> clazz) {
		return LogManager.getLogger(clazz);
	}

	/**
	 * 
	 * @Title: get
	 * @Description: 获得Logger
	 * @param name 自定义的日志发出者名称
	 * @return
	 */
	public static Logger get(String name) {
		return LogManager.getLogger(name);
	}

	/**
	 * 
	 * @Title: get
	 * @Description: 获得日志，自动判定日志发出者
	 * @return
	 */
	public static Logger get() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		return LogManager.getLogger(stackTrace[2].getClassName());
	}

	/**
	 * 
	 * @Title: trace
	 * @Description: Trace等级日志，小于debug<br>
	 *               由于动态获取Logger，效率较低，建议在非频繁调用的情况下使用
	 * @param format    格式文本，{} 代表变量
	 * @param arguments 变量对应的参数
	 */
	public static void trace(String format, Object... arguments) {
		trace(innerGet(), format, arguments);
	}

	/**
	 * 
	 * @Title: trace
	 * @Description: race等级日志，小于Debug
	 * @param log       日志对象
	 * @param format    格式文本，{} 代表变量
	 * @param arguments 变量对应的参数
	 */
	public static void trace(Logger log, String format, Object... arguments) {
		log.trace(format, arguments);
	}

	/**
	 * 
	 * @Title: debug
	 * @Description: Debug等级日志，小于Info<br>
	 *               由于动态获取Logger，效率较低，在非频繁调用的情况下使用
	 * @param format    格式文本，{} 代表变量
	 * @param arguments 变量对应的参数
	 */
	public static void debug(String format, Object... arguments) {
		debug(innerGet(), format, arguments);
	}

	/**
	 * 
	 * @Title: debug
	 * @Description: Debug等级日志，小于Info
	 * @param log       日志对象
	 * @param format    格式文本，{} 代表变量
	 * @param arguments 变量对应的参数
	 */
	public static void debug(Logger log, String format, Object... arguments) {
		log.debug(format, arguments);
	}

	/**
	 * 
	 * @Title: info
	 * @Description: Info等级日志，小于Warn<br>
	 *               由于动态获取Logger，效率较低，在非频繁调用的情况下使用
	 * @param format    格式文本，{} 代表变量
	 * @param arguments 变量对应的参数
	 */
	public static void info(String format, Object... arguments) {
		info(innerGet(), format, arguments);
	}

	/**
	 * 
	 * @Title: info
	 * @Description: Info等级日志，小于Warn
	 * @param log       日志对象
	 * @param format    格式文本，{} 代表变量
	 * @param arguments 变量对应的参数
	 */
	public static void info(Logger log, String format, Object... arguments) {
		log.info(format, arguments);
	}

	/**
	 * 
	 * @Title: warn
	 * @Description: Warn等级日志，小于Error<br>
	 *               由于动态获取Logger，效率较低，建议在非频繁调用的情况下使用
	 * @param format    格式文本，{} 代表变量
	 * @param arguments 变量对应的参数
	 */
	public static void warn(String format, Object... arguments) {
		warn(innerGet(), format, arguments);
	}

	/**
	 * Warn等级日志，小于Error
	 * 
	 * @param log       日志对象
	 * @param format    格式文本，{} 代表变量
	 * @param arguments 变量对应的参数
	 */
	/**
	 * 
	 * @Title: warn
	 * @Description: Warn等级日志，小于Error
	 * @param log       日志对象
	 * @param format    格式文本，{} 代表变量
	 * @param arguments 变量对应的参数
	 */
	public static void warn(Logger log, String format, Object... arguments) {
		log.warn(format, arguments);
	}

	/**
	 * 
	 * @Title: warn
	 * @Description: Warn等级日志，小于Error<br>
	 *               由于动态获取Logger，效率较低，建议在非频繁调用的情况下使用
	 * @param e         需在日志中堆栈打印的异常
	 * @param format    格式文本，{} 代表变量
	 * @param arguments 变量对应的参数
	 */
	public static void warn(Throwable e, String format, Object... arguments) {
		warn(innerGet(), e, format(format, arguments));
	}


	/**
	 * 
	 * @Title: warn
	 * @Description: Warn等级日志，小于Error
	 * @param log       日志对象
	 * @param e         需在日志中堆栈打印的异常
	 * @param format    格式文本，{} 代表变量
	 * @param arguments 变量对应的参数
	 */
	public static void warn(Logger log, Throwable e, String format, Object... arguments) {
		log.warn(format(format, arguments), e);
	}

	/**
	 * @Title: warn
	 * @Description: Warn等级日志，小于Error
	 * @param log 日志对象
	 * @param e   需在日志中堆栈打印的异常
	 */
	public static void error(Logger log, Throwable e) {
		log.error(e);
	}
	
	/**
	 * 
	 * @Title: error
	 * @Description: Error等级日志<br>
	 *               由于动态获取Logger，效率较低，建议在非频繁调用的情况下使用
	 * @param format    格式文本，{} 代表变量
	 * @param arguments 变量对应的参数
	 */
	public static void error(String format, Object... arguments) {
		error(innerGet(), format, arguments);
	}

	/**
	 * 
	 * @Title: error
	 * @Description: Error等级日志<br>
	 * @param log       日志对象
	 * @param format    格式文本，{} 代表变量
	 * @param arguments 变量对应的参数
	 */
	public static void error(Logger log, String format, Object... arguments) {
		log.error(format, arguments);
	}

	/**
	 * 
	 * @Title: error
	 * @Description: Error等级日志<br>
	 *               由于动态获取Logger，效率较低，建议在非频繁调用的情况下使用
	 * @param e         需在日志中堆栈打印的异常
	 * @param format    格式文本，{} 代表变量
	 * @param arguments 变量对应的参数
	 */
	public static void error(Throwable e, String format, Object... arguments) {
		error(innerGet(), e, format(format, arguments));
	}

	/**
	 * 
	 * @Title: error
	 * @Description: Error等级日志<br>
	 *               由于动态获取Logger，效率较低，建议在非频繁调用的情况下使用
	 * @param log       日志对象
	 * @param e         需在日志中堆栈打印的异常
	 * @param format    格式文本，{{}} 代表变量
	 * @param arguments 变量对应的参数
	 */
	public static void error(Logger log, Throwable e, String format, Object... arguments) {
		log.error(format(format, arguments), e);
	}

	/**
	 * 
	 * @Title: format
	 * @Description: 格式化文本
	 * @param template 文本模板，被替换的部分用 {{}} 表示
	 * @param values   参数值
	 * @return 格式化后的文本
	 */
	private static String format(String template, Object... values) {
		if (template != null && template.indexOf(TEMPLATE_STRING) == -1) {
			return template;
		}
		return String.format(template.replace(TEMPLATE_STRING, "%s"), values);
	}

	/**
	 * 
	 * @Title: innerGet
	 * @Description: 获得日志，自动判定日志发出者
	 * @return
	 */
	private static Logger innerGet() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		return LogManager.getLogger(stackTrace[3].getClassName());
	}

}
