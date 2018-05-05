package com.example.hp.cold_chain_logistic.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.example.hp.cold_chain_logistic.R;
import com.example.hp.cold_chain_logistic.base.BaseActivity;
import com.example.hp.cold_chain_logistic.fragment.FourFragment;
import com.example.hp.cold_chain_logistic.fragment.OneFragment;
import com.example.hp.cold_chain_logistic.fragment.ThreeFragment;
import com.example.hp.cold_chain_logistic.fragment.TwoFragment;
import com.example.hp.cold_chain_logistic.ui.ComWidget;
import com.example.hp.cold_chain_logistic.utils.HttpUrlCallback;
import com.example.hp.cold_chain_logistic.utils.HttpUtils;
import com.example.hp.cold_chain_logistic.utils.Utility;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.thuongnh.zprogresshud.ZProgressHUD;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;


/**
 * @author liz
 * @version V1.0
 * @date 2018/4/7
 */

public class MainActivity extends BaseActivity {

    public   BottomBar bbar_main;
    public   OneFragment  fg_one;
    private  TwoFragment  fg_two;
    public   ThreeFragment  fg_three;
    public   FourFragment  fg_four;
    public   FragmentManager fragmentManager;
    public   FragmentTransaction transaction;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        bbar_main.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId){
                    case R.id.tab_one:
                        changeFragment(fg_one);
                        break;

//                    case R.id.tab_two:
//                        changeFragment(fg_two);
//                        break;

                    case  R.id.tab_three:

                        changeFragment(fg_three);
                        break;
                    case R.id.tab_four:
                        changeFragment(fg_four);
                        break;

                }
            }
        });

    }

    protected void init() {
        bbar_main=findViewById(R.id.bbar_main);

        fg_one=new OneFragment();
        fg_two=new TwoFragment();
        fg_three=new ThreeFragment();
        fg_four=new FourFragment();

    }

    /**
     * 变换fragment
     * @param fragment
     */
    public void changeFragment(Fragment fragment) {

        fragmentManager=getSupportFragmentManager();
        transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.fg_main,fragment); //将fragment加载到fg_main布局元素中
        //transaction.addToBackStack(null); //像栈一样回退fragment
        transaction.commit();
    }


}
