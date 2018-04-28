package com.example.hp.cold_chain_logistic.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.hp.cold_chain_logistic.R;
import com.example.hp.cold_chain_logistic.activity.MainActivity;
import com.example.hp.cold_chain_logistic.db.IMSI;
import com.example.hp.cold_chain_logistic.db.Para;
import com.example.hp.cold_chain_logistic.utils.HttpUtils;
import com.example.hp.cold_chain_logistic.utils.getParaListCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * @author liz
 * @version V1.0
 * @date 2018/4/7
 */

public class OneFragment extends Fragment {

    private View view;
    private Button btn_fg_one_listen;
    private OneShowFragment oneShowFragment;
    public EditText et_fg_one;
    public static String IMSICODE = "";
    private String url;
    private ImageView iv_fg_one_del;
    private List<Para> paraList = new ArrayList<>();


    public String getIMSICODE(){
        return IMSICODE;
    }


    @Nullable
    @Override
/**
 * 该方法用于加载动态布局，和初始化控件
 */
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_one, container, false);

        btn_fg_one_listen = view.findViewById(R.id.btn_fg_one_listen);
        et_fg_one = view.findViewById(R.id.et_fg_one);
        iv_fg_one_del = view.findViewById(R.id.iv_fg_one_del);
        oneShowFragment = new OneShowFragment();

        return view;
    }

    /**
     * 该方法实现各种点击监听事件，以及类似oncreate（）初始化面向过程
     *
     * @param savedInstanceState
     */
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

//侦听按钮
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
                    url = "https://www.suda-iot.com/AHL-Serve-Interface-CCL2/03_Web/FrameMessage.aspx?get&Android&" + IMSICODE + "&" + String.valueOf(s);


                    HttpUtils.getParaList(url, new getParaListCallback() {
                        @Override
                        public void onSuccess(String data) {
                            MainActivity mainActivity = (MainActivity) getActivity();
                            oneShowFragment.setData(data);
                            mainActivity.changeFragment(oneShowFragment);
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
