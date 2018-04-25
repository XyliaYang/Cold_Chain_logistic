package com.example.hp.cold_chain_logistic.utils;

import android.support.v4.view.ViewCompat;
import android.text.TextUtils;

import com.example.hp.cold_chain_logistic.db.IMSI;
import com.example.hp.cold_chain_logistic.db.Para;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 解析json数据并将其存储进数据库
 * @author liz
 * @version V1.0
 * @date 2018/4/25
 */

public class Utility {


    /**
     * 将para型的json数据解析成用于Listview显示的HashList
     * @return
     */
    public static ArrayList<HashMap<String,String>> parseParatoHashList(String data){
        ArrayList<HashMap<String,String>> arrayList =new ArrayList<>();
        List<Para> paraList=new ArrayList<>();

        Gson  gson=new Gson();
        paraList=gson.fromJson(data, new TypeToken<List<Para>>(){}.getType());

        for(int i=0;i<paraList.size();i++){
            Para para=paraList.get(i);
            HashMap<String,String> map=new HashMap<>();
            map.put("one_show_item_title",para.getName());
            map.put("one_show_item_text", para.getValue());

            arrayList.add(map);
        }

        return arrayList;
    }
}
