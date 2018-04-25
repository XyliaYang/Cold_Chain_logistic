package com.example.hp.cold_chain_logistic.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.hp.cold_chain_logistic.R;
import com.example.hp.cold_chain_logistic.db.Para;
import com.example.hp.cold_chain_logistic.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * @author liz
 * @version V1.0
 * @date 2018/4/8
 */

public class ThreeFragment extends Fragment {

    private ListView lv_fg_three_show;
    private ArrayList<HashMap<String,String>> mArrayList=new ArrayList<>();
    private SimpleAdapter simpleAdapter;
    private List<Para> paraList;
    private String IMSI;
    private String url;
    private int count=0;
    Handler handler=new Handler();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_three,container,false);
        lv_fg_three_show=view.findViewById(R.id.lv_fg_three_show);

        initListPara();
        simpleAdapter=new SimpleAdapter(getContext(),
                mArrayList,
                R.layout.item_listview_fg_one,
                new String[]{"one_show_item_title","one_show_item_text"}, //动态数组里与ListItem对应的子项
                new int[]{R.id.one_show_item_title,R.id.one_show_item_text} //
        );

        lv_fg_three_show.setAdapter(simpleAdapter);

        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                handler.postDelayed((Runnable) getActivity(),1000);
                Log.d(TAG, "count: "+count++);


            }
        };

        return view;
    }

    private void initListPara() {
        //实时IMSI号
        IMSI="460042189005105";

        Random random=new Random();
        int s=random.nextInt(100);
        url="https://www.suda-iot.com/AHL-Serve-Interface-CCL2/03_Web/FrameMessage.aspx?get&Android&"+IMSI+"&"+String.valueOf(s);

        HttpUtils.sendOkHttpRequest(url,new okhttp3.Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                //网络错误
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setMessage("请确认已连接上服务器！")
                        .setPositiveButton("确定",null);
                builder.show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String data=response.body().string();
                Gson gson=new Gson();
                List<Para> paraList=gson.fromJson(data,new TypeToken<List<Para>>(){}.getType());

                for(int i=2;i<paraList.size();i++){
                    Para para=paraList.get(i);
                    HashMap<String,String>  map=new HashMap<String,String>();
                    map.put("one_show_item_title",para.getName());
                    map.put("one_show_item_text",para.getValue());

                    mArrayList.add(map);
                }

                }
            });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
