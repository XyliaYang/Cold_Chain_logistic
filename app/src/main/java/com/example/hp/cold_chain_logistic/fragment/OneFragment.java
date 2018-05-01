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
import com.example.hp.cold_chain_logistic.base.ConstData;
import com.example.hp.cold_chain_logistic.db.IMSI;
import com.example.hp.cold_chain_logistic.db.Para;
import com.example.hp.cold_chain_logistic.ui.ComWidget;
import com.example.hp.cold_chain_logistic.utils.HttpUrlCallback;
import com.example.hp.cold_chain_logistic.utils.HttpUtils;
import com.example.hp.cold_chain_logistic.utils.Utility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
 * @date 2018/4/7
 */

public class OneFragment extends Fragment {

    private View view;
    private OneShowFragment oneShowFragment;

    private Button btn_fg_one_listen;
    public  EditText et_fg_one;
    private ImageView iv_fg_one_del;

    private List<Para> paraList = new ArrayList<>();

    public static String IMSICODE = "";
    private String url= ConstData.getAhlInterfaceServer()+"get&Android&";

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

        //listen
        btn_fg_one_listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random random = new Random();
                int s = random.nextInt(10000);
                IMSICODE = et_fg_one.getText().toString();

                if(IMSICODE.length()!=15){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ComWidget.ToastShow("请输入15位IMSI号！",getActivity());
                        }
                    });
                }else{
                    url+=IMSICODE+"&"+String.valueOf(s);

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
                            String result=Utility.isValueTure(data);

                            if(result.equals("false")){
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ComWidget.ToastShow("该IMSI无实时数据！",getActivity());
                                    }
                                });

                            }else{
                                //和 the IMSI has data,start showfragment
                                oneShowFragment.setData(data);
                                ((MainActivity) getActivity()).changeFragment(oneShowFragment);
                                ((MainActivity) getActivity()).transaction.addToBackStack(null);
                            }
                        }
                    });
                }
            }
        });
    }
}
