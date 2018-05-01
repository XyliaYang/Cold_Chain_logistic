package com.example.hp.cold_chain_logistic.utils;

import android.app.AlertDialog;
import android.util.Log;

import com.example.hp.cold_chain_logistic.activity.MainActivity;
import com.example.hp.cold_chain_logistic.db.Para;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * http connection
 * @author liz
 * @version V1.0
 * @date 2018/3/29
 */

public class HttpUtils {

    /**
     *
     * @param address
     * @param callback
     */
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {

        //address为请求的url
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();

        client.newCall(request).enqueue(callback);
    }


    /**
     *
     * 请求url返回para的json
     * @param url
     * @return
     */
    public static void  getParaList(String url,final  getParaListCallback paraListCallback) {

        HttpUtils.sendOkHttpRequest(url, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //网络错误
                paraListCallback.onInternetError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                //返回正确数据，json串存于response.body().string()
                String data = response.body().string();
                Gson gson = new Gson();
                List<Para> paraList = gson.fromJson(data, new TypeToken<List<Para>>() {
                }.getType());


                //还没发送
                if (paraList.get(0).getValue().equals("false")) {

                    paraListCallback.onNoDataError();

                } else {
                    paraListCallback.onSuccess(data);
                }
            }
        });
    }
}