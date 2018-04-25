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

import com.example.hp.cold_chain_logistic.R;
import com.example.hp.cold_chain_logistic.db.Para;
import com.example.hp.cold_chain_logistic.utils.HttpUtils;
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
    private List<Para> paraList;
    private ImageView iv_fg_one_show_del;
    private ImageView iv_fg_one_show_save;
    private ImageView iv_fg_one_show_left;
    private ImageView iv_fg_one_show_right;
    private ImageView iv_fg_one_show_first;
    private int curFraNum;  //当前帧号
    private String firstFraNum; //最初帧号
    private String url;
    private String IMSI; //当前输入的IMSI号


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
        mArrayList.clear();
        for (int i = 2; i < paraList.size(); i++) {
            Para para = paraList.get(i);
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("one_show_item_title", para.getName());
            map.put("one_show_item_text", para.getValue());


            mArrayList.add(map);
        }

        simpleAdapter = new SimpleAdapter(getContext(),
                mArrayList,
                R.layout.item_listview_fg_one,
                new String[]{"one_show_item_title", "one_show_item_text"}, //动态数组里与ListItem对应的子项
                new int[]{R.id.one_show_item_title, R.id.one_show_item_text} //
        );

        for (HashMap<String, String> map : mArrayList) {
            Log.d(TAG, "run: " + map.get("one_show_item_title"));
            Log.d(TAG, "run: " + map.get("one_show_item_text"));
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lv_fg_one_show.setAdapter(simpleAdapter);
            }
        });

    }




@Override
public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    //上一帧数据
    iv_fg_one_show_left.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            curFraNum--;
            url = "https://www.suda-iot.com/AHL-Serve-Interface-CCL2/03_Web/FrameMessage.aspx?get:" +
                    "curlen(" + curFraNum + "&" + IMSI;
            updateList(url);

        }
    });

    //下一帧数据
    iv_fg_one_show_right.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            curFraNum++;
            url = "https://www.suda-iot.com/AHL-Serve-Interface-CCL2/03_Web/FrameMessage.aspx?get:" +
                    "curlen(" + curFraNum + "&" + IMSI;
            updateList(url);

        }
    });

    //第一帧
    iv_fg_one_show_first.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            url = "https://www.suda-iot.com/AHL-Serve-Interface-CCL2/03_Web/FrameMessage.aspx?get:" +
                    "curlen(" + firstFraNum + "&" + IMSI;
            updateList(url);

        }
    });


    //保存IMSI号
    iv_fg_one_show_save.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("确定保存该IMSI号信息吗?")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setNegativeButton("取消", null);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("确定清空数据吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //清空数据
                            mArrayList.clear();
                            for (int i = 2; i < paraList.size(); i++) {
                                Para para = paraList.get(i);
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put("one_show_item_title", para.getName());
                                map.put("one_show_item_text", "");

                                mArrayList.add(map);
                            }

                            simpleAdapter = new SimpleAdapter(getContext(),
                                    mArrayList,
                                    R.layout.item_listview_fg_one,
                                    new String[]{"one_show_item_title", "one_show_item_text"}, //动态数组里与ListItem对应的子项
                                    new int[]{R.id.one_show_item_title, R.id.one_show_item_text} //
                            );

                            lv_fg_one_show.setAdapter(simpleAdapter);


                        }

                    }).setNegativeButton("取消", null);

            builder.show();
        }

    });
}

/**
 * 获取最新url里的数据并且显示
 *
 * @param url
 */
private void updateList(String url) {
    HttpUtils.sendOkHttpRequest(url, new okhttp3.Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            //网络错误
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
            builder.setMessage("请确认已连接上服务器！")
                    .setPositiveButton("确定", null);
            builder.show();
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            //返回正确数据，json串存于response.body().string()
            String data = response.body().string();
            Gson gson = new Gson();
            paraList.clear();
            paraList = gson.fromJson(data, new TypeToken<List<Para>>() {
            }.getType());

            showList();

        }
    });

}


public void setData(String data) {
    Gson gson = new Gson();
    paraList = gson.fromJson(data, new TypeToken<List<Para>>() {
    }.getType());
    firstFraNum = paraList.get(2).getValue();
    curFraNum = Integer.parseInt(firstFraNum);
    IMSI = paraList.get(3).getValue();
}
}
