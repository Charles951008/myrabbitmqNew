package com.wsl.tools;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期格式化工具
 * 
 * @author gg42
 *
 */
public class DateFormatUtil {
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 自定义转换格式
	 * 
	 * @param strDateFormat
	 * @return SimpleDateFormat类
	 */
	public static SimpleDateFormat setDateFormat(String strDateFormat) {
		return new SimpleDateFormat(strDateFormat);
	}

	/**
	 * 将默认格式字符串转化为的 Date
	 * 
	 * @param  source (strDateFormat)
	 * @return Date类
	 */
	public static Date parse(String source) {
		Date parseDate = null;
		try {
			parseDate = sdf.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parseDate;
	}

	/**
	 * 将 Date转化为默认格式的字符串
	 * 
	 * @param formatDate strDateFormat
	 * @return 默认格式的字符串
	 */
	public static String parse(Date formatDate) {
		String formatDateStr = null;
		if (!StringUtils.isEmpty(formatDate)) {
			formatDateStr = sdf.format(formatDate);
		}
		return formatDateStr;
	}

	/**
	 * 
	 * @param dateNumber  需要添加的天数
	 * @param currentDate 添加到某一日期
	 * @return 添加天数后的日期
	 */
	public static Date IncreaseDate(String dateNumber, Date currentDate) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(currentDate);
		ca.add(Calendar.DATE, Integer.parseInt(dateNumber));
		currentDate = ca.getTime();
		return currentDate;
	}

	/**
	 * 日期比较(两个日期比较大小)
	 * 
	 * @param oldDate
	 * @param newDateO
	 * @return oldDate小于newDateO返回-1，oldDate大于newDateO返回1，相等返回0
	 */
	public static Integer compareDate(Date oldDate, Date newDateO) {
		int compareTo = oldDate.compareTo(newDateO);
		return compareTo;
	}

	/**
	 * 计算两个日期相差天数
	 * @param beginDate
	 * @param endDate
	 * @return  beginDate小于endDate返回[正数]，beginDate大于endDate返回[负数]，相等返回0
	 */
	public static Integer getTimeDistance(Date beginDate, Date endDate) {
		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setTime(beginDate);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);
		long beginTime = beginCalendar.getTime().getTime();
		long endTime = endCalendar.getTime().getTime();
		int betweenDays = (int) ((endTime - beginTime) / (1000 * 60 * 60 * 24));// 先算出两时间的毫秒数之差大于一天的天数

		endCalendar.add(Calendar.DAY_OF_MONTH, -betweenDays);// 使endCalendar减去这些天数，将问题转换为两时间的毫秒数之差不足一天的情况
		endCalendar.add(Calendar.DAY_OF_MONTH, -1);// 再使endCalendar减去1天
		if (beginCalendar.get(Calendar.DAY_OF_MONTH) == endCalendar.get(Calendar.DAY_OF_MONTH))// 比较两日期的DAY_OF_MONTH是否相等
			return betweenDays + 1; // 相等说明确实跨天了
		else
			return betweenDays + 0; // 不相等说明确实未跨天
	}
}
