package com.example.hp.cold_chain_logistic.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.hp.cold_chain_logistic.R;
import com.example.hp.cold_chain_logistic.db.IMSI;
import com.example.hp.cold_chain_logistic.db.Para;
import com.example.hp.cold_chain_logistic.utils.HttpUtils;
import com.example.hp.cold_chain_logistic.utils.Utility;
import com.example.hp.cold_chain_logistic.utils.getParaListCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static android.content.ContentValues.TAG;


/**
 * @author liz
 * @version V1.0
 * @date 2018/4/8
 */

public class OneShowFragment extends Fragment {

    private ListView lv_fg_one_show;
    private ArrayList<HashMap<String, String>> mArrayList = new ArrayList<>();
    private SimpleAdapter simpleAdapter;
    private ImageView iv_fg_one_show_back;
    private ImageView iv_fg_one_show_del;
    private ImageView iv_fg_one_show_save;
    private ImageView iv_fg_one_show_left;
    private ImageView iv_fg_one_show_right;
    private ImageView iv_fg_one_show_first;
    private int curFraNum;  //当前帧号
    private String firstFraNum; //最初帧号
    private String url;
    private String IMSICODE; //当前输入的IMSI号

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_one_show, container, false);
        lv_fg_one_show = view.findViewById(R.id.lv_fg_one_show);
        iv_fg_one_show_back = view.findViewById(R.id.iv_fg_one_show_back);
        iv_fg_one_show_del = view.findViewById(R.id.iv_fg_one_show_del);
        iv_fg_one_show_save = view.findViewById(R.id.iv_fg_one_show_save);
        iv_fg_one_show_left = view.findViewById(R.id.iv_fg_one_show_left);
        iv_fg_one_show_right = view.findViewById(R.id.iv_fg_one_show_right);
        iv_fg_one_show_first = view.findViewById(R.id.iv_fg_one_show_first);


        showList();

        return view;
    }


    /**
     * 展示列表
     */
    private void showList() {
        simpleAdapter = new SimpleAdapter(getContext(),
                mArrayList,
                R.layout.item_listview_fg_one,
                new String[]{"one_show_item_title", "one_show_item_text"}, //动态数组里与ListItem对应的子项
                new int[]{R.id.one_show_item_title, R.id.one_show_item_text} //
        );


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lv_fg_one_show.setAdapter(simpleAdapter);
            }
        });

    }


    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//上一帧数据
        iv_fg_one_show_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curFraNum--;
                url = "https://www.suda-iot.com/AHL-Serve-Interface-CCL2/03_Web/FrameMessage.aspx?get:" +
                        "curlen(" + curFraNum + "&" + IMSICODE;
                HttpUtils.getParaList(url, new getParaListCallback() {
                    @Override
                    public void onSuccess(final String data) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mArrayList = Utility.parseParatoHashList(data);
                                simpleAdapter.notifyDataSetChanged();
                                lv_fg_one_show.setAdapter(simpleAdapter);

                            }
                        });
                    }

                    @Override
                    public void onInternetError() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
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
                                builder.setMessage("上一帧IMSI号无实时数据，核对后再重新发送！")
                                        .setPositiveButton("确定", null);
                                builder.show();
                            }
                        });
                    }
                });

            }
        });

//下一帧数据,不确定是否是我的思路还没写
        iv_fg_one_show_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curFraNum++;
                url = "https://www.suda-iot.com/AHL-Serve-Interface-CCL2/03_Web/FrameMessage.aspx?get:" +
                        "curlen(" + curFraNum + "&" + IMSICODE;
            }
        });

//第一帧
        iv_fg_one_show_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "https://www.suda-iot.com/AHL-Serve-Interface-CCL2/03_Web/FrameMessage.aspx?get:old&" +
                        IMSICODE;

                HttpUtils.getParaList(url, new getParaListCallback() {
                    @Override
                    public void onSuccess(final String data) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mArrayList = Utility.parseParatoHashList(data);
                                simpleAdapter.notifyDataSetChanged();
                                lv_fg_one_show.setAdapter(simpleAdapter);

                            }
                        });
                    }

                    @Override
                    public void onInternetError() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
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
                                builder.setMessage("第一帧IMSI号无实时数据，核对后再重新发送！")
                                        .setPositiveButton("确定", null);
                                builder.show();
                            }
                        });
                    }
                });

            }
        });


//保存IMSI号
        iv_fg_one_show_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("确定保存该IMSI号信息吗?")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        IMSI imsi = new IMSI();
                                        imsi.setBox_id(IMSICODE);
                                        if (imsi.save()) {

                                            new AlertDialog.Builder(getContext()).setMessage("保存成功！")
                                                    .show();
                                        } else {
                                            new AlertDialog.Builder(getContext()).setMessage("保存失败！")
                                                    .show();
                                        }
                                    }
                                }).setNegativeButton("取消", null);
                        builder.show();

                    }
                });

            }
        });


//回退到上一个界面
        iv_fg_one_show_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });


//清空数据
        iv_fg_one_show_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("确定清空数据吗？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        for (int i = 0; i < mArrayList.size(); i++) {
                                            mArrayList.get(i).put("one_show_item_text", "");
                                        }

                                        simpleAdapter.notifyDataSetChanged();
                                        lv_fg_one_show.setAdapter(simpleAdapter);


                                    }
                                }).setNegativeButton("取消", null);
                        builder.show();

                    }
                });

            }

        });
    }


    public void setData(String data) {

        mArrayList = Utility.parseParatoHashList(data);
        firstFraNum = mArrayList.get(2).get("one_show_item_text");
        curFraNum = Integer.parseInt(firstFraNum);
        IMSICODE = mArrayList.get(3).get("one_show_item_text");
    }
}
