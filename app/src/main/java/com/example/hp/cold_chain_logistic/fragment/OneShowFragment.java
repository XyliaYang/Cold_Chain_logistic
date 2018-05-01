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
import com.example.hp.cold_chain_logistic.base.ConstData;
import com.example.hp.cold_chain_logistic.db.IMSI;
import com.example.hp.cold_chain_logistic.db.Para;
import com.example.hp.cold_chain_logistic.ui.ComWidget;
import com.example.hp.cold_chain_logistic.utils.HttpUrlCallback;
import com.example.hp.cold_chain_logistic.utils.HttpUtils;
import com.example.hp.cold_chain_logistic.utils.Utility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

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
private String IMSICODE; //当前输入的IMSI号
private String url="";

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
    url = ConstData.getAhlInterfaceServer()+"get:" +
            "curlen(" + curFraNum + "&" + IMSICODE;

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
                //update listview
                mArrayList = Utility.parseJsonArrayToHashArray(data);
                simpleAdapter.notifyDataSetChanged();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lv_fg_one_show.setAdapter(simpleAdapter);
                    }
                });
            }
        }
    });

}
});

//下一帧数据
iv_fg_one_show_right.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
    curFraNum++;
    url = ConstData.getAhlInterfaceServer()+"get:" +
            "curlen(" + curFraNum + "&" + IMSICODE;

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
                //update listview
                mArrayList = Utility.parseJsonArrayToHashArray(data);
                simpleAdapter.notifyDataSetChanged();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lv_fg_one_show.setAdapter(simpleAdapter);
                    }
                });
            }
        }
    });

}
});

//第一帧
iv_fg_one_show_first.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
    url = ConstData.getAhlInterfaceServer()+"get:old&" +
            IMSICODE;

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
                //update listview
                mArrayList = Utility.parseJsonArrayToHashArray(data);
                simpleAdapter.notifyDataSetChanged();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lv_fg_one_show.setAdapter(simpleAdapter);
                    }
                });
            }
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
            builder.setMessage("确定保存该IMSI号吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            url=ConstData.getUSER_ITERFACE_SERVER()+
                                    "save_imsi.php?imsi="+IMSICODE+"&account="+ConstData.getAccount();

                            HttpUtils.sendOkHttpRequest(url, new okhttp3.Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ComWidget.ToastShow("请检查网络连接！",getActivity());
                                        }
                                    });
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {

                                    String data = response.body().string();

                                    switch ((int) Utility.getJsonObjectAttr(data, "code")) {
                                        case 200:
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ComWidget.ToastShow("存储成功！",getActivity());
                                                }
                                            });
                                            break;
                                        case 401:
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ComWidget.ToastShow("已经存储过该IMSI号！",getActivity());
                                                }
                                            });
                                            break;
                                       default:
                                            break;

                                    }


                                }
                            });


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


public void setData(String  data) {

mArrayList = Utility.parseJsonArrayToHashArray(data);
firstFraNum = mArrayList.get(0).get("one_show_item_text");
curFraNum = Integer.parseInt(firstFraNum);
IMSICODE = mArrayList.get(1).get("one_show_item_text");
}
}
