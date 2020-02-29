package com.wsl.tools;

import com.alibaba.fastjson.JSONObject;

import com.wsl.constant.LogRootConstant;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: WebRequestUtil
 * @Description: TODO
 * @author: zhengnf
 * @date: 2019/12/18/018 13:45
 * @Copyright: 上海城地
 */
public class WebRequestUtil {
    /**
     * sysLog: 系统日志
     */
    public static final Logger sysLog = LogUtil.get(LogRootConstant.SYSTEM_LOG.getLogRootName());

    /**
     * 发送GET请求
     * @param url   请求url
     * @param nameValuePairList    请求参数
     * @return JSON或者字符串
     */
    public static JSONObject sendGet(String url, List<NameValuePair> nameValuePairList) {
        JSONObject jsonObject = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try{
            /**
             * 创建HttpClient对象
             */
            client = HttpClients.createDefault();
            /**
             * 创建URIBuilder
             */
            URIBuilder uriBuilder = new URIBuilder(url);
            /**
             * 设置参数
             */
            uriBuilder.addParameters(nameValuePairList);
            /**
             * 创建HttpGet
             */
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            /**
             * 请求服务
             */
            response = client.execute(httpGet);
            /**
             * 获取响应吗
             */
            int statusCode = response.getStatusLine().getStatusCode();

            if (HttpStatus.SC_OK == statusCode){
                /**
                 * 获取返回对象
                 */
                HttpEntity entity = response.getEntity();
                /**
                 * 通过EntityUitls获取返回内容
                 */
                String result = EntityUtils.toString(entity,"UTF-8");
                /**
                 * 转换成json,根据合法性返回json或者字符串
                 */
                try{
                    jsonObject = JSONObject.parseObject(result);

                }catch (Exception e){
                    LogUtil.error(sysLog, "GET请求失败！");
                }
            }else{
                LogUtil.error(sysLog, "非JSON格式！");
            }
        }catch (Exception e){
            LogUtil.error(sysLog, e);
        } finally {
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(client);
        }
        return jsonObject;
    }

    /**
     * 发送POST请求
     * @param url
     * @param nameValuePairList
     * @return JSON或者字符串
     */

    public static JSONObject sendPost(String url, List<NameValuePair> nameValuePairList) {
        JSONObject jsonObject = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try{
            /**
             *  创建一个httpclient对象
             */
            client = HttpClients.createDefault();
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);

            // 设置请求超时
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(15000)						// 设置连接超时时间,单位毫秒。
                    .setConnectionRequestTimeout(15000)  			// 从连接池获取到连接的超时,单位毫秒。
                    .setSocketTimeout(15000).build();    	// 请求获取数据的超时时间,单位毫秒; 如果访问一个接口,多少时间内无法返回数据,就直接放弃此次调用。
            httpPost.setConfig(requestConfig);
            // 创建请求内容
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList, "UTF-8"));
            // 执行http请求
            response = client.execute(httpPost);

            /**
             * 获取响应码
             */
            int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode){
                /**
                 * 通过EntityUitls获取返回内容
                 */
                String result = EntityUtils.toString(response.getEntity(),"UTF-8");
                /**
                 * 转换成json,根据合法性返回json或者字符串
                 */
                try{
                    jsonObject = JSONObject.parseObject(result);
                }catch (Exception e){
                    LogUtil.error(sysLog, "非JSON格式！");
                }
            }else{
                LogUtil.error(sysLog, "POST请求失败！");
            }
        }catch (Exception e){
            LogUtil.error(sysLog, e);
        }finally {
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(client);
        }
        return jsonObject;
    }
    /**
     * 组织请求参数{参数名和参数值下标保持一致}
     * @param params    参数名数组
     * @param values    参数值数组
     * @return 参数对象
     */

    public static List<NameValuePair> getParams(Object[] params, Object[] values){
        /**
         * 校验参数合法性
         */
        boolean flag = params.length>0 && values.length>0 &&  params.length == values.length;
        if (flag){
            List<NameValuePair> nameValuePairList = new ArrayList<>();
            for(int i =0; i<params.length; i++){
                nameValuePairList.add(new BasicNameValuePair(params[i].toString(),values[i].toString()));
            }
            return nameValuePairList;
        }else{
            LogUtil.error(sysLog, "请求参数为空且参数长度不一致！");
        }
        return null;
    }
    
    
    
    
    
    
    
    public static String getHttpRequestData(String urlPath) {
    	 
        // 首先抓取异常并处理
        String returnString = "1";
        try{
            // 代码实现以GET请求方式为主,POST跳过
            /** 1 GET方式请求数据 start*/
             
            // 1  创建URL对象,接收用户传递访问地址对象链接
            URL url = new URL(urlPath);
             
            // 2 打开用户传递URL参数地址
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
             
            // 3 设置HTTP请求的一些参数信息
            connect.setRequestProperty("Content-type","application/x-www-form-urlencoded;charset=UTF-8");
            connect.setRequestMethod("GET"); // 参数必须大写
            connect.connect();
             
            // 4 获取URL请求到的数据，并创建数据流接收
            InputStream isString = connect.getInputStream();
             
            // 5 构建一个字符流缓冲对象,承载URL读取到的数据
            BufferedReader isRead = new BufferedReader(new InputStreamReader(isString,"UTF-8"));
              
            // 6 输出打印获取到的文件流
            String str = "";
            while ((str = isRead.readLine()) != null) {
                //str = new String(str.getBytes(),"UTF-8"); //解决中文乱码问题
                returnString = str;
            }
             
            // 7 关闭流
            isString.close();
            connect.disconnect();
             
            // 8 JSON转List对象
            // do somthings
             
             
        }catch(Exception e){
            e.printStackTrace();
        }
         
        return returnString;
    }
    
    
    
    
    
    
    
    
    
}
