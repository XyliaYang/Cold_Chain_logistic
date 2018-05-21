package com.example.hp.cold_chain_logistic.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import com.example.hp.cold_chain_logistic.R;
import com.example.hp.cold_chain_logistic.base.ConstData;
import com.example.hp.cold_chain_logistic.beans.DynamicLineChartManager;
import com.example.hp.cold_chain_logistic.ui.ComWidget;
import com.example.hp.cold_chain_logistic.utils.HttpUtils;
import com.example.hp.cold_chain_logistic.utils.Utility;
import com.github.mikephil.charting.charts.LineChart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * @Createdby LizYang
 * @Version: V 1.0
 * @Date: 2018/5/18
 * @Description:this is show canvas class
 */

public class CanvasFragment extends Fragment {

    private LineChart mLineChart;  //折线图
    private ImageView  iv_canvas_back;
    private ImageView iv_fg_canvas_setting;
    private DynamicLineChartManager dynamicLineChartManager;
    private List<Integer>  integerList=new ArrayList<>(); //数据集合
    private String name=new String(); //折现名字集合
    private int  color=R.color.colorPrimary;//折线颜色
    private String url;

    MyTimerTask timerTask = null;
    Timer timer=null;
    Handler myHandler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1: //每隔6s进行name类型的数据请求，
                    drawPoint(name);
                    break;

            }
        }
    };


    /**
     * get the url from ThreeShowFragment
     * @param url
     */
    public void setData(String url){
        this.url=url;
    }

    /**
     * send httpRequest get the data to draw on the canvas according to the name
     * @param name
     */
    private void drawPoint(final String name) {
        HttpUtils.sendOkHttpRequest(url,new okhttp3.Callback() {
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
                final String data=response.body().string();
                final  String result= Utility.isValueTure(data);
                final String value=Utility.getValueFromJsonArray(data,name);
                if(result.equals("false")){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ComWidget.ToastShow("该IMSI无实时数据！",getActivity());
                        }
                    });

                }else{
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dynamicLineChartManager.addEntry((int)Float.parseFloat(value));
                        }
                    });

                }

            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_canvas,container,false);
        mLineChart=view.findViewById(R.id.lineChart);
        iv_canvas_back=view.findViewById(R.id.iv_canvas_back);
        iv_fg_canvas_setting=view.findViewById(R.id.iv_fg_canvas_setting);
        timer=new Timer(true);
        timerTask=new MyTimerTask();



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        name="bright";
        dynamicLineChartManager=new DynamicLineChartManager(mLineChart,name,color);
        dynamicLineChartManager.setYAxis(1000, 500, 100);


        //返回
        iv_canvas_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });


        //下拉菜单
        iv_fg_canvas_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(getContext(), iv_fg_canvas_setting);
                popupMenu.getMenuInflater().inflate(R.menu.menu_fg_canvas, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.item_fg_canvas_bright:
                                name="bright";
                                dynamicLineChartManager.setYAxis(1000, 500, 100);
                                drawPoint(name);

                                break;

                            case R.id.item_fg_canvas_electric:
                                name="electric";
                                dynamicLineChartManager.setYAxis(100, 0, 20);
                                drawPoint(name);
                                break;

                            case R.id.item_fg_canvas_temperature1:
                                name="temperature1";
                                dynamicLineChartManager.setYAxis(50, 0, 5);
                                drawPoint(name);
                                break;

                            case R.id.item_fg_canvas_wetness1:
                                name="wetness1";
                                dynamicLineChartManager.setYAxis(50, 0, 5);
                                drawPoint(name);
                                break;

                            case R.id.item_fg_canvas_acceleration:
                                name="acceleration";
                                dynamicLineChartManager.setYAxis(10, 0, 1);
                                drawPoint(name);
                                break;
                            case R.id.item_fg_canvas_latitude:
                                name="latitude";
                                dynamicLineChartManager.setYAxis(50, 0, 5);
                                drawPoint(name);
                                break;

                            case R.id.item_fg_canvas_longitude:
                                name="longitude";
                                dynamicLineChartManager.setYAxis(100, 200, 10);
                                drawPoint(name);
                                break;

                            case R.id.item_fg_canvas_height:
                                name="height";
                                dynamicLineChartManager.setYAxis(1000, 500, 100);
                                drawPoint(name);
                                break;

                            case R.id.item_fg_canvas_speed:
                                name="electric";
                                dynamicLineChartManager.setYAxis(100, 0, 20);
                                drawPoint(name);
                                break;
                        }
                        return true;
                    }
                });

                popupMenu.show();
            }
        });
    }

    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            Message message=new Message();
            message.what=1;
            myHandler.sendMessage(message);
        }
    }


    /**
     * 在启动计时任务
     */
    private void startTimer() {
        if(timer==null)
            timer=new Timer(true);
        if(timerTask==null)
            timerTask=new MyTimerTask();
        timer.schedule(timerTask,0,3000);

    }
    /**
     * 在当前fg在后台时停止该timer
     */
    private void stopTimer() {
        if(timer!=null){
            timer.cancel();
            timer=null;
        }
        if(timerTask!=null){
            timerTask.cancel();
            timerTask=null;
        }

    }


    /**
     * 在fg进入后台模式时停止计时器
     */
    @Override
    public void onPause() {
        super.onPause();
        stopTimer();

    }


    /**
     * 在进入该fg时又重新加载定时器
     */
    @Override
    public void onStart() {
        super.onStart();
        startTimer();
    }
}
