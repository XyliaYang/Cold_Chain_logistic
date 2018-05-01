package com.example.hp.cold_chain_logistic.utils;

import android.app.AlertDialog;
import android.content.Context;
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
 * @author liz
 * @version V1.0
 * @date 2018/3/29
 * description:
 */

public class HttpUtils {

    /**
     *  base http request
     * @param address url address
     * @param callback   return failed or response
     */
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {

        //address为请求的url
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();

        client.newCall(request).enqueue(callback);
    }


}