package com.example.hp.cold_chain_logistic.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.hp.cold_chain_logistic.beans.ActivityCollector;
import com.mob.MobSDK;


/**
 * 功能：隐藏标题栏
 * 封装Ativity的公共方法
 * @author liz
 * @version V1.0
 * @date 2018/4/4
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MobSDK.init(this);
        startActivityState(this);
        init();

    }

    protected abstract void init();


    /**
     * 初始化activity的状态
     * @param baseActivity
     */
    public  void startActivityState(BaseActivity baseActivity) {

        //隐藏标题栏
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
            actionBar.hide();

        //将该activity放入activity的集合
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }


    /**
     * 启动新活动
     * @param context
     * @param startClass
     */
    public  void startAct(Context context, Class startClass){
        Intent intent=new Intent(context,startClass);
        startActivity(intent);
    }


    /**
     * 回退栈，确保栈中只有一个fragment就行
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }

    }
}
