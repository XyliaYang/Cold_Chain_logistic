package com.example.hp.cold_chain_logistic.utils;

import android.support.v4.view.ViewCompat;
import android.text.TextUtils;

import com.example.hp.cold_chain_logistic.db.IMSI;
import com.example.hp.cold_chain_logistic.db.Para;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
     * jsonArray change into  arraylist<hashmap>
     * @param jsonArray:jsonArrayString
     * @return
     */
    public static  ArrayList<HashMap<String,String>> parseJsonArrayToHashArray(String jsonArray){

        ArrayList<HashMap<String,String>> arrayList =new ArrayList<>();
        List<Para> paraList=new ArrayList<>();

        Gson  gson=new Gson();
        paraList=gson.fromJson(jsonArray, new TypeToken<List<Para>>(){}.getType());

        for(int i=2;i<paraList.size();i++){
            Para para=paraList.get(i);
            HashMap<String,String> map=new HashMap<>();
            map.put("one_show_item_title",para.getName());
            map.put("one_show_item_text", para.getValue());

            arrayList.add(map);
        }

        return arrayList;
    }

    /**
     * if the first jsonObject of this jsonArray's value is true or false
     * @param jsonArray :jsonArrayString
     * @return
     */
    public static String isValueTure(String jsonArray){
        try {
            JSONArray array=new JSONArray(jsonArray);
            JSONObject object=array.getJSONObject(0);

            return (String) object.get("value");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "false";
    }

    /**
     * get a special attribute jsonObject String value with gived type
     * @param response : jsonString
     * @param type
     * @return <T>
     */
    public   static <T>  T getJsonObjectAttr(String response,String type){
        T result=null;
        try {
            JSONObject jsonObject=new JSONObject(response);
            result= (T) jsonObject.get(type);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

//
//    /**
//     * 将para型的json数据解析成用于Listview显示的HashList
//     * @return
//     */
//    public static ArrayList<HashMap<String,String>> parseParatoHashList(String data){
//        ArrayList<HashMap<String,String>> arrayList =new ArrayList<>();
//        List<Para> paraList=new ArrayList<>();
//
//        Gson  gson=new Gson();
//        paraList=gson.fromJson(data, new TypeToken<List<Para>>(){}.getType());
//
//        for(int i=2;i<paraList.size();i++){
//            Para para=paraList.get(i);
//            HashMap<String,String> map=new HashMap<>();
//            map.put("one_show_item_title",para.getName());
//            map.put("one_show_item_text", para.getValue());
//
//            arrayList.add(map);
//        }
//
//        return arrayList;
//    }
}
