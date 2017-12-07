package com.weixin.utils;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpClientUtil {
    public static JSONObject doGetStr(String accessTokenUrl) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(accessTokenUrl);
        JSONObject jsonObject = null;
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if(entity!=null){
                String result = EntityUtils.toString(entity,"UTF-8");
                jsonObject = JSONObject.fromObject(result);
            }
            httpGet.releaseConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
//        JSONObject jsonObject = null;
//        try {
//            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
//            TrustManager[] tm = { new MyX509TrustManager() };
//            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
//            sslContext.init(null, tm, new java.security.SecureRandom());
//            // 从上述SSLContext对象中得到SSLSocketFactory对象
//            SSLSocketFactory ssf = sslContext.getSocketFactory();
//
//            URL url = new URL(accessTokenUrl);
//            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
//            conn.setSSLSocketFactory(ssf);
//
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            conn.setUseCaches(false);
//            // 设置请求方式（GET/POST）
//            conn.setRequestMethod("GET");
//
//            // 当outputStr不为null时向输出流写数据
////            if (null != outputStr) {
////                OutputStream outputStream = conn.getOutputStream();
////                // 注意编码格式
////                outputStream.write(outputStr.getBytes("UTF-8"));
////                outputStream.close();
////            }
//
//            // 从输入流读取返回内容
//            InputStream inputStream = conn.getInputStream();
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            String str = null;
//            StringBuffer buffer = new StringBuffer();
//            while ((str = bufferedReader.readLine()) != null) {
//                buffer.append(str);
//            }
//
//            // 释放资源
//            bufferedReader.close();
//            inputStreamReader.close();
//            inputStream.close();
//            inputStream = null;
//            conn.disconnect();
//            jsonObject = JSONObject.fromObject(buffer.toString());
//        } catch (ConnectException ce) {
//            ce.getMessage();
//        } catch (Exception e) {
//            e.getMessage();
//        }
//        return jsonObject;
    }

    /**
     * 发送post请求
     * @param url
     * @param param
     * @return
     */
    public static JSONObject doPostStr(String url,String param){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        JSONObject jsonObject = null;
        try {
            httpPost.setEntity(new StringEntity(param, "UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity(),"UTF-8");
            jsonObject = JSONObject.fromObject(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
