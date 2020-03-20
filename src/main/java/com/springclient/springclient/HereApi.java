package com.springclient.springclient;

import com.alibaba.fastjson.JSON;
import com.jfinal.kit.HttpKit;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class HereApi {

    public static AtomicInteger num = new AtomicInteger(0);
    public static String[] APIKEYS= new String[]{
            "Km2CuSdf-sdJFdgcD45A6cqCnJ4VhoDc3L9nfOZWoeM",
            "k_x9IfJFp-tCeP9wx755YHfkTVE-Ia0N-3KctYftCqE",
            "AQjxHuxamWsHYKNHa60x8HxTZfBU1_UuKxa2nsYvsF8",
            "4ltHMhV7m55dUGk0LAFx30kciySDt5xBpqhqcVMLbRc",
    };
    public static void main(String args[])  {
        String lng = "121.341762";
        String lat = "24.3707";
        reverseGeocode(lng,lat);
//        while (true){
//            Thread.sleep(2000l);
//        }
    }
    public static String reverseGeocode(String lng,String lat){
        String url = "https://reverse.geocoder.ls.hereapi.com/6.2/reversegeocode.json" +
                "?mode=retrieveAddresses" +
                "&prox=LAT,LNG&apiKey=APIKEY";
        url = url.replace("APIKEY",geneKey());
        url = url.replace("LAT",lat);
        url = url.replace("LNG",lng);
        url = url.replace("LAT",lat);
        System.out.println(url);
        String res = doGet(url);
        String location = null;
        try {
             location = JSON.parseObject(res).getJSONObject("Response")
                    .getJSONArray("View")
                    .getJSONObject(0)
                    .getJSONArray("Result")
                    .getJSONObject(0)
                    .getJSONObject("Location").getJSONObject("Address").getString("Label");
            System.out.println(location);
        }catch (Exception e){
            e.printStackTrace();
        }
        return location;
    }

    public static String geneKey(){
        num.addAndGet(1);
        int i=num.get()%APIKEYS.length;
        return APIKEYS[i];
    }

    /**
     * GET---无参测试
     *
     * @date 2018年7月13日 下午4:18:50
     */
    public static String doGet(String url) {
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet(url);

        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
//            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
//                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
//                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
                return EntityUtils.toString(responseEntity);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
