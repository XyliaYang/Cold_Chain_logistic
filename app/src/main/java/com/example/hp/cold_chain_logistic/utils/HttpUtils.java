package com.example.hp.cold_chain_logistic.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 网络请求的封装
 * @author liz
 * @version V1.0
 * @date 2018/3/29
 */

public class HttpUtils {

    public static  void  sendOkHttpRequest(String address,okhttp3.Callback callback){

        //address为请求的url
        OkHttpClient client=new OkHttpClient();
        Request request =new Request.Builder()
                .url(address)
                .build();

        client.newCall(request).enqueue(callback);
    }


}
