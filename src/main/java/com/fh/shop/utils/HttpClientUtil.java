package com.fh.shop.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {

    public static String sentHttpClient(String url, Map<String, String> heads, Map<String, String> params) {
        CloseableHttpClient client = null;
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        String result = null;
        try {
            //打开浏览器
            client = HttpClientBuilder.create().build();
            //输入网址
            httpPost = new HttpPost(url);
            //参数信息数组
            List<BasicNameValuePair> listBasic = new ArrayList<>();
            //头信息
            Iterator<Map.Entry<String, String>> iterator = heads.entrySet().iterator();
            //迭代器循环，是否下一个有值
            while (iterator.hasNext()) {
                //取出每一个
                Map.Entry<String, String> entry = iterator.next();
                String key = entry.getKey();
                String value = entry.getValue();
                //添加到 打开的网址里面
                httpPost.addHeader(key, value);
            }
            if (params != null && params.size() > 0) {
                //参数信息
                Iterator<Map.Entry<String, String>> paramIterator = params.entrySet().iterator();
                while (paramIterator.hasNext()) {
                    Map.Entry<String, String> entry = paramIterator.next();
                    String key = entry.getKey();
                    String value = entry.getValue();
                    BasicNameValuePair basicNameValuePair = new BasicNameValuePair(key, value);
                    listBasic.add(basicNameValuePair);
                }
            }
            UrlEncodedFormEntity urlEncoded = new UrlEncodedFormEntity(listBasic, "utf-8");
            httpPost.setEntity(urlEncoded);
            response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpPost != null) {
                httpPost.completed();
            }
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
