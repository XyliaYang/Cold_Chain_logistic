package com.example.hp.cold_chain_logistic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.hp.cold_chain_logistic.R;
import com.example.hp.cold_chain_logistic.utils.HttpUtils;
import com.example.hp.cold_chain_logistic.utils.Utility;
import com.example.hp.cold_chain_logistic.utils.getParaListCallback;
import com.trncic.library.DottedProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;

/**
 * @author liz
 * @version V1.0
 * @date 2018/4/27
 */

public class ThreeShowFragment extends Fragment {

    private DottedProgressBar dpb_fg_three;
    private String url;
    private ArrayList<HashMap<String,String>>  mapArrayList=new ArrayList<>();
    private ImageView iv_fg_three_show_back;
    private  SimpleAdapter simpleAdapter;
    private ListView lv_fg_three_show;
    private int  count=0;
    MyTimerTask timerTask = new MyTimerTask();
    Timer timer=new Timer(true);
    Handler myHandler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    Log.d(TAG, "handleMessage: "+"实时刷新");
                    //刷新黑效果效果，停止pgb

                    updataList();
                    break;
            }
        }


    };


    private void updataList() {
        HttpUtils.getParaList(url, new getParaListCallback() {
            @Override
            public void onSuccess(final String data) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //显示成功,开始pgb
                        mapArrayList = Utility.parseParatoHashList(data);
                        simpleAdapter.notifyDataSetChanged();
                        lv_fg_three_show.setAdapter(simpleAdapter);

                    }
                });
            }

            @Override
            public void onInternetError() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //刷新失败，开始pgb
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                        builder.setMessage("请确认已连接上服务器！")
                                .setPositiveButton("确定", null);
                        builder.show();
                    }
                });

            }

            @Override
            public void onNoDataError() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                        builder.setMessage("该IMSI无实时数据，核对后再重新发送！")
                                .setPositiveButton("确定", null);
                        builder.show();
                    }
                });

            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_three_show,container,false);
        iv_fg_three_show_back=view.findViewById(R.id.iv_fg_three_show_back);
        lv_fg_three_show=view.findViewById(R.id.lv_fg_three_show);
        dpb_fg_three=view.findViewById(R.id.dpb_fg_three);

        showList();
        dpb_fg_three.startProgress();
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //返回
        iv_fg_three_show_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        timer.schedule(timerTask, 0, 3000);//定时每秒执行一次

    }

    public void  setData(String data,String url){
        mapArrayList= Utility.parseParatoHashList(data);
        this.url=url;
    }



    /**
     * 展示列表
     */
    private void showList() {
        simpleAdapter = new SimpleAdapter(getContext(),
                mapArrayList,
                R.layout.item_listview_fg_one,
                new String[]{"one_show_item_title", "one_show_item_text"}, //动态数组里与ListItem对应的子项
                new int[]{R.id.one_show_item_title, R.id.one_show_item_text} //
        );


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lv_fg_three_show.setAdapter(simpleAdapter);
            }
        });

    }

    private class MyTimerTask extends  TimerTask{

        @Override
        public void run() {
            Message message=new Message();
            message.what=1;
            myHandler.sendMessage(message);
        }
    }
}
