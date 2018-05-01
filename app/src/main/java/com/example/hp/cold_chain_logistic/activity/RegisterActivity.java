package com.example.hp.cold_chain_logistic.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.hp.cold_chain_logistic.R;
import com.example.hp.cold_chain_logistic.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author liz
 * @version V1.0
 * @date 2018/4/7
 */

public class RegisterActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }

    @Override
    protected void init() {

    }


}
