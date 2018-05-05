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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.hp.cold_chain_logistic.R;
import com.example.hp.cold_chain_logistic.activity.MainActivity;
import com.example.hp.cold_chain_logistic.base.ConstData;
import com.example.hp.cold_chain_logistic.db.Para;
import com.example.hp.cold_chain_logistic.ui.ComWidget;
import com.example.hp.cold_chain_logistic.utils.HttpUtils;
import com.example.hp.cold_chain_logistic.utils.Utility;
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

    public TextView tv_fg_one;
    public EditText et_fg_one;
    public ImageView iv_fg_one_del;
    public Button btn_fg_one_listen;
    public String url= ConstData.getAhlInterfaceServer()+"get:new&";
    public ThreeShowFragment threeShowFragment;

    public  String IMSICODE="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_one,container,false);
        tv_fg_one=view.findViewById(R.id.tv_fg_one);
        et_fg_one=view.findViewById(R.id.et_fg_one);
        iv_fg_one_del=view.findViewById(R.id.iv_fg_one_del);
        btn_fg_one_listen=view.findViewById(R.id.btn_fg_one_listen);
        threeShowFragment=new ThreeShowFragment();

        tv_fg_one.setText("实时查询");

        et_fg_one.setSaveEnabled(false);

        if(!IMSICODE.equals("")){
            et_fg_one.setText(IMSICODE);
        }

        return view;
    }


    /**
     * set the IMSI = s
     * @param s
     */
    public  void setData(String s){
        IMSICODE=s;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d("mainactivity", "onActivityCreated: imsicode:---"+IMSICODE);


        //清空按钮
        iv_fg_one_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_fg_one.setText("");
            }
        });

        //监听按钮
        btn_fg_one_listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IMSICODE = et_fg_one.getText().toString();

                if (IMSICODE.length() != 15) {  //IMSICODE号不正确
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("请输入15位IMSI号！")
                            .setPositiveButton("确定", null);
                    builder.show();

                } else {
                    Random random = new Random();
                    int s = random.nextInt(1000);

                    url+=IMSICODE;
                    HttpUtils.sendOkHttpRequest(url, new okhttp3.Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ComWidget.ToastShow("请检查网络状态!",getActivity());
                                }
                            });

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            //jsonarray
                            String data=response.body().string();
                            //whether exists data
                            String result= Utility.isValueTure(data);

                            if(result.equals("false")){
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ComWidget.ToastShow("该IMSI无实时数据！",getActivity());
                                    }
                                });

                            }else{
                                //和 the IMSI has data,start showfragment
                                MainActivity mainActivity = (MainActivity) getActivity();

                                threeShowFragment.setData(data,url);
                                mainActivity.changeFragment(threeShowFragment);
                                mainActivity.transaction.addToBackStack(null);
                            }
                        }
                    });




                }
            }
        });

    }
}
