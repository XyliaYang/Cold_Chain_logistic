package com.example.hp.cold_chain_logistic.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hp.cold_chain_logistic.R;
import com.example.hp.cold_chain_logistic.base.BaseActivity;

/**
 * @author liz
 * @version V1.0
 * @date 2018/4/4
 */

public class LoginActivity extends BaseActivity {

    private TextView tv_login_register;
    private Button btn_login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        tv_login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAct(LoginActivity.this,RegisterActivity.class);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAct(LoginActivity.this,MainActivity.class);
                finish();
            }
        });


    }

    @Override
    protected void init() {
        tv_login_register=findViewById(R.id.tv_login_register);
        btn_login=findViewById(R.id.btn_login);

    }




}
