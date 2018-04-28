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
import com.example.hp.cold_chain_logistic.db.Para;
import com.example.hp.cold_chain_logistic.utils.HttpUtils;
import com.example.hp.cold_chain_logistic.utils.getParaListCallback;
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
    public  String IMSICODE;
    public EditText et_fg_one;
    public ImageView iv_fg_one_del;
    public Button btn_fg_one_listen;
    public String url;
    public ThreeShowFragment threeShowFragment;

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

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


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

                /**
                 * 1.IMSICODE号不正确
                 * 2.IMSICODE返回false
                 * 3.IMSICODE号从服务器中查询出
                 * 4.没有连接上服务器，网络错误
                 */
                IMSICODE = et_fg_one.getText().toString();

                if (IMSICODE.length() != 15) {  //IMSICODE号不正确
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("请输入正确的15位IMSI号！")
                            .setPositiveButton("确定", null);
                    builder.show();

                } else {
                    Random random = new Random();
                    int s = random.nextInt(1000);

                    url="https://www.suda-iot.com/AHL-Serve-Interface-CCL2/03_Web/FrameMessage.aspx?get:new&"+IMSICODE;
                    HttpUtils.getParaList(url, new getParaListCallback() {
                        @Override
                        public void onSuccess(String data) {
                            MainActivity mainActivity = (MainActivity) getActivity();
                            threeShowFragment.setData(data,url);
                            mainActivity.changeFragment(threeShowFragment);
                            mainActivity.transaction.addToBackStack(null);

                        }

                        @Override
                        public void onInternetError() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setMessage("IMSI号无实时数据，核对后再重新发送！")
                                            .setPositiveButton("确定", null);
                                    builder.show();
                                }
                            });

                        }
                    });

                }
            }
        });

    }
}
