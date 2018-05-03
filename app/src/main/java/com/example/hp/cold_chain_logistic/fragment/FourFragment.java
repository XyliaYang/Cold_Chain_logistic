package com.example.hp.cold_chain_logistic.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.hp.cold_chain_logistic.R;
import com.example.hp.cold_chain_logistic.activity.LoginActivity;
import com.example.hp.cold_chain_logistic.activity.MainActivity;
import com.example.hp.cold_chain_logistic.base.ConstData;
import com.example.hp.cold_chain_logistic.ui.ComWidget;
import com.example.hp.cold_chain_logistic.utils.HttpUtils;
import com.example.hp.cold_chain_logistic.utils.Utility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thuongnh.zprogresshud.ZProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static android.R.layout.simple_list_item_2;
import static android.content.ContentValues.TAG;

/**
* @author liz
* @version V1.0
* @date 2018/4/8
*/

public class FourFragment extends Fragment {
private View view;
private ListView lv_fg_four;
private ImageView iv_fg_four_setting;

private List<String> list = new ArrayList<String>();
private ArrayAdapter<String> arrayAdapter;
private FourHelpFragment fourHelpFragment;
private String url = ConstData.getUSER_ITERFACE_SERVER() + "query_imsi.php?account=" + ConstData.getAccount();


@Nullable
@Override
public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

view = inflater.inflate(R.layout.fragment_four, container, false);
lv_fg_four = view.findViewById(R.id.lv_fg_four);
iv_fg_four_setting = view.findViewById(R.id.iv_fg_four_setting);
fourHelpFragment = new FourHelpFragment();

//显示IMSI列表
initList();

arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, list);
lv_fg_four.setAdapter(arrayAdapter);

return view;
}

private void initList() {
list.clear();
list.add("~!!你还没有添加过IMSI哦!!~");
}

@Override
public void onActivityCreated(@Nullable Bundle savedInstanceState) {
super.onActivityCreated(savedInstanceState);


lv_fg_four.setOnItemClickListener(new AdapterView.OnItemClickListener() {
@Override
public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    MainActivity activity= (MainActivity) getActivity();
    OneFragment oneFragment=new OneFragment();
    oneFragment.setData(list.get(position));
    activity.changeFragment(oneFragment);
    activity.bbar_main.selectTabWithId(R.id.tab_one);
}
});


HttpUtils.sendOkHttpRequest(url, new okhttp3.Callback() {
@Override
public void onFailure(Call call, IOException e) {
    getActivity().runOnUiThread(new Runnable() {
        @Override
        public void run() {
            ComWidget.ToastShow("请检查网络状态!", getActivity());
            initList();
            arrayAdapter.notifyDataSetChanged();
            lv_fg_four.setAdapter(arrayAdapter);
        }
    });

}

@Override
public void onResponse(Call call, Response response) throws IOException {
    String data = response.body().string();

    switch ((int) Utility.getJsonObjectAttr(data, "code")) {
        case 200:
            list.clear();
            JSONArray jsonArray=Utility.getJsonObjectAttr(data,"data");
            for (int i=0;i<jsonArray.length();i++){

                try {
                    String s= (String) jsonArray.get(i);
                    s=s.replace("\"","");
                    list.add(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    arrayAdapter.notifyDataSetChanged();
                    lv_fg_four.setAdapter(arrayAdapter);
                }
            });
            break;

        case 401:
            initList();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    arrayAdapter.notifyDataSetChanged();
                    lv_fg_four.setAdapter(arrayAdapter);
                }
            });
            break;
        default:
            break;

    }

}
});


iv_fg_four_setting.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
    PopupMenu popupMenu = new PopupMenu(getContext(), iv_fg_four_setting);
    popupMenu.getMenuInflater().inflate(R.menu.menu_setting, popupMenu.getMenu());

    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item_setting_help:

                    //fg调用activity的方法
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.changeFragment(fourHelpFragment);
                    mainActivity.transaction.addToBackStack(null);
                    break;

                //exit
                case R.id.item_setting_exit:
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                            builder.setMessage("确定要退出吗？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            Intent intent=new Intent(getActivity(),LoginActivity.class);
                                            startActivity(intent);
                                            getActivity().finish();

                                        }
                                    })
                            .setNegativeButton("取消",null);
                            builder.show();
                        }
                    });


            }
            return true;
        }
    });

    popupMenu.show();
}
});
}
}
