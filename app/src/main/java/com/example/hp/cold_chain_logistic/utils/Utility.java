package com.example.hp.cold_chain_logistic.utils;

import android.support.v4.view.ViewCompat;
import android.text.TextUtils;

import com.example.hp.cold_chain_logistic.db.IMSI;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

/**
 * 解析json数据并将其存储进数据库
 * @author liz
 * @version V1.0
 * @date 2018/4/25
 */

public class Utility {


    /**
     * 解析传递的json字符串并存储进数据库
     * @param response
     * @return
     */
    public static  boolean handleIMSIResponse(String response,int userId){

        if(!TextUtils.isEmpty(response)) {

            Gson gson=new Gson();
            List<IMSI> imsiList = gson.fromJson(response,new TypeToken<List<IMSI>>(){}.getType());

            for (IMSI imsi : imsiList) {
                imsi.setUserId(userId);
                imsi.save();
            }

            return true;
        }
        return false;
    }
}
