package com.wsl.tools;
import com.wsl.constant.LogRootConstant;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @ClassName: IPUtils
 * @Description: IP地址获取工具
 */
public final class IPUtils {

	public static final Logger log = LogUtil.get(LogRootConstant.SYSTEM_LOG.getLogRootName());
	public static final String X_REAL_IP = "X-Real-IP";
	public static final String X_FORWARDED_FOR = "X-Forwarded-For";
	public static final String PROXY_CLIENT_IP = "Proxy-Client-IP";
	public static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
	public static final String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
	public static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";
	public static final String UNKNOWN = "unKnown";

	/**
	 * @Title: getRequestIP
	 * @Description: 获取访问者IP地址
	 * @param request
	 * @return
	 */
	public static String getRequestIP(HttpServletRequest request) {
		String ip = "";
		try {
			// 获取用户真是的地址
			String Xip = request.getHeader(X_REAL_IP);
			// 获取多次代理后的IP字符串值
			String XFor = request.getHeader(X_FORWARDED_FOR);
			if (!StringUtils.isEmpty(XFor) && !UNKNOWN.equalsIgnoreCase(XFor)) {
				// 多次反向代理后会有多个IP值，第一个用户真实的IP地址
				int index = XFor.indexOf(",");
				if (index >= 0) {
					return XFor.substring(0, index);
				} else {
					return XFor;
				}
			}
			ip = Xip;
			if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
				ip = request.getHeader(PROXY_CLIENT_IP);
			}
			if (StringUtils.isEmpty(ip) || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
				ip = request.getHeader(WL_PROXY_CLIENT_IP);
			}
			if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
				ip = request.getHeader(HTTP_CLIENT_IP);
			}
			if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
				ip = request.getHeader(HTTP_X_FORWARDED_FOR);
			}
			if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		} catch (Exception e) {
			LogUtil.error(log, e, e.getMessage());
		}
		return ip;
	}

	/**   
	 * @Title:	getAccessPath   
	 * @Description:	获取请求访问路径(不包含项目名称)
	 * @param request
	 * @return
	 */
	public static String getAccessPath(HttpServletRequest request) {
		String path = "";
		path = request.getServletPath();
		return path;
	}
	
	/**   
	 * @Title:	getAccessURI   
	 * @Description:	获取请求访问路径(包含项目名称)
	 * @param request
	 * @return
	 */
	public static String getAccessURI(HttpServletRequest request) {
		String path = "";
		path = request.getRequestURI();
		return path;
	}
	
}
