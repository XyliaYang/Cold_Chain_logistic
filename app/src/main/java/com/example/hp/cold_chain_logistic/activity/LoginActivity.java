package com.example.hp.cold_chain_logistic.activity;

import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.solver.widgets.WidgetContainer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hp.cold_chain_logistic.R;
import com.example.hp.cold_chain_logistic.base.BaseActivity;
import com.example.hp.cold_chain_logistic.base.ConstData;
import com.example.hp.cold_chain_logistic.ui.ComWidget;
import com.example.hp.cold_chain_logistic.utils.HttpUtils;
import com.example.hp.cold_chain_logistic.utils.Utility;

import java.io.IOException;
import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.hp.cold_chain_logistic.utils.Utility.getJsonObjectAttr;

/**
 * @author liz
 * @version V1.0
 * @date 2018/4/4
 */

public class LoginActivity extends BaseActivity {

    private TextView tv_login_register;
    private Button btn_login;
    private EditText et_account;
    private EditText et_pwd;
    private Button bt_find_pwd;

    private String account = "";
    private String pwd = "";
    private String url = ConstData.getUSER_ITERFACE_SERVER() + "login.php?";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();


        //find password
        bt_find_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAct(LoginActivity.this,FindPwdActivity.class);
                finish();
            }
        });

        //register
        tv_login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAct(LoginActivity.this, RegisterActivity.class);


            }
        });

        //login in
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                account = et_account.getText().toString();
                pwd = et_pwd.getText().toString();
                url += "account=" + account + "&pwd=" + pwd;
                if (account.equals("") || pwd.equals("")) {
                    ComWidget.ToastShow("账户名和密码不能为空！", LoginActivity.this);
                } else {

                    HttpUtils.sendOkHttpRequest(url, new okhttp3.Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ComWidget.ToastShow("请检查网络状态！", LoginActivity.this);
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String data = response.body().string();

                            switch ((int) Utility.getJsonObjectAttr(data, "code")) {
                                case 200:
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ComWidget.ToastShow("登录成功！", LoginActivity.this);
                                        }
                                    });
                                    ConstData.setAccount(account);
                                    startAct(LoginActivity.this, MainActivity.class);
                                    finish();
                                    break;
                                case 401:
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ComWidget.ToastShow("请检查用户账号信息！", LoginActivity.this);
                                        }
                                    });
                                    break;
                                case 403:

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ComWidget.ToastShow("密码错误！", LoginActivity.this);
                                        }
                                    });
                                    break;
                                default:
                                    break;
                            }

                        }
                    });
                }

//
            }
        });


    }



    @Override
    protected void init() {
        tv_login_register = findViewById(R.id.tv_login_register);
        btn_login = findViewById(R.id.btn_login);
        et_account = findViewById(R.id.et_account);
        et_pwd = findViewById(R.id.et_pwd);
        bt_find_pwd = findViewById(R.id.bt_find_pwd);
    }



}
