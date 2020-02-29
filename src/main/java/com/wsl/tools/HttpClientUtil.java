package com.wsl.tools;

import org.apache.commons.codec.CharEncoding;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: HttpClientUtil
 * @Description: 调用第三方接口工具,使用HttpClient
 */
public class HttpClientUtil {
	/**
	 * @Title: send
	 * @Description: GET请求,无参
	 * @param url URL地址
	 * @return
	 */
	public static String sendGET(String url) {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse httpResponse = null;
		try {
			//org.apache.commons.httpclient.URI uri = new org.apache.commons.httpclient.URI(url, false, "UTF-8");
			HttpGet request = new HttpGet(url);
			
			httpResponse = httpClient.execute(request);
			/*
			 * if (httpResponse.getStatusLine().getStatusCode() == 200) { return
			 * EntityUtils.toString(httpResponse.getEntity(), "utf-8"); }
			 */
			HttpEntity entity = httpResponse.getEntity();
			String result = EntityUtils.toString(entity, "utf-8");
			EntityUtils.consume(entity);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResource(httpResponse, httpClient);
		}
		return url;
	}
	
	
	/**   
	 * @Title:	sendGETChinesePath   
	 * @Description:	包含中文请求路径
	 * @param url
	 * @return
	 */
	public static String sendGETChinesePath(String url) {	
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse httpResponse = null;
		try {
			org.apache.commons.httpclient.URI uri = new org.apache.commons.httpclient.URI(url, false, "UTF-8");
			HttpGet request = new HttpGet(uri.toString());
			
			httpResponse = httpClient.execute(request);
			/*
			 * if (httpResponse.getStatusLine().getStatusCode() == 200) { return
			 * EntityUtils.toString(httpResponse.getEntity(), "utf-8"); }
			 */
			HttpEntity entity = httpResponse.getEntity();
			String result = EntityUtils.toString(entity, "utf-8");
			EntityUtils.consume(entity);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResource(httpResponse, httpClient);
		}
		return url;
	}
	
	
	/**   
	 * @Title:	handlePathChinese   
	 * @Description:	访问时,路径有中文处理
	 * @param url
	 * @return
	 */
	public static String handleChinesePath(String url)  {
		org.apache.commons.httpclient.URI uri =null;
		try {
			uri= new org.apache.commons.httpclient.URI(url, false, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uri.toString();
	}

	/**
	 * @Title: sendPOST
	 * @Description: POST请求,带参数
	 * @param url
	 * @param requestBody
	 * @return
	 */
	public static String sendPOST(String url, Map<String, String> requestBody) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse httpResponse = null;

		try {
			// 设置请求参数
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			if (requestBody != null) {
				//遍历传参
				requestBody.forEach((key, value) -> {
					nvps.add(new BasicNameValuePair(key, value));
				});
				// 设置参数
				httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			}

			// 执行请求
			httpResponse = httpClient.execute(httpPost);
			HttpEntity entity = httpResponse.getEntity();
			String result = EntityUtils.toString(entity, CharEncoding.UTF_8);
			EntityUtils.consume(entity);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResource(httpResponse, httpClient);
		}
		return url;
	}

	/**
	 * @Title: closeResource
	 * @Description: 释放资源
	 * @param httpResponse response
	 * @param httpClient   httpClient
	 */
	private static void closeResource(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient) {
		try {
			if (httpResponse != null) {
				httpResponse.close();
			}
			httpClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Title: sendViaProxy
	 * @Description: 代理请求
	 * @param url      URL地址
	 * @param host     代理地址
	 * @param port     端口号
	 * @param account  认证账号
	 * @param password 认证秘密
	 * @return
	 */
	public static String sendViaProxy(String url, String host, int port, String account, String password) {
		HttpHost proxy = new HttpHost(host, port, "http");

		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(new AuthScope(proxy.getHostName(), proxy.getPort()),
				new UsernamePasswordCredentials(account, password));

		CloseableHttpClient httpClient = HttpClients.custom().setDefaultCredentialsProvider(credentialsProvider)
				.build();
		RequestConfig config = RequestConfig.custom().setConnectTimeout(3000).setConnectionRequestTimeout(3000)
				.setProxy(proxy).build();

		HttpGet request = new HttpGet(url);
		request.setConfig(config);

		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(request);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(httpResponse.getEntity(), "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResource(httpResponse, httpClient);
		}
		return url;
	}
	
	
	/**   
	 * @Title:	parameterJoin   
	 * @Description:	GET参数拼接
	 * @param paramMap	参数Map
	 * @return
	 */
	public static  String joinParameter(Map<String, Object> paramMap) {
        // 将参数输出到连接
		StringBuffer param = new StringBuffer();
		try {
        for (Object key : paramMap.keySet()) {
            Object result = paramMap.get(key);
            if (key != null && result != null) {
                param.append(key.toString());
                param.append("=");
                param.append(URLEncoder.encode(result.toString(), "UTF-8"));
                param.append("&");
            }
        }
        if (param.length() > 1) {
            param.delete(param.length() - 1, param.length());
        }
       
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return param.toString();
	}
	
	
	public static String URLEncoderToString(String result) {
		String encodeStr ="";
		try {
			encodeStr= URLEncoder.encode(result.toString(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  encodeStr;
	}
}
