package com.example.hp.cold_chain_logistic.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.hp.cold_chain_logistic.R;
import com.example.hp.cold_chain_logistic.base.BaseActivity;
import com.example.hp.cold_chain_logistic.base.ConstData;
import com.example.hp.cold_chain_logistic.ui.ComWidget;
import com.example.hp.cold_chain_logistic.utils.HttpUtils;
import com.example.hp.cold_chain_logistic.utils.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;


/**
 * @author liz
 * @version V1.0
 * @date 2018/4/7
 */

public class RegisterActivity extends BaseActivity {
    private EditText et_reg_account;
    private EditText et_reg_pwd;
    private EditText et_reg_pwd_again;
    private EditText et_reg_signature;
    private EditText et_reg_nickname;
    private Button btn_register;
    private String account="";
    private String pwd="";
    private String pwd_again="";
    private String signature="";
    private String nickname="";
    private  String url= ConstData.getUSER_ITERFACE_SERVER()+"register.php?";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

        //register
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                account=et_reg_account.getText().toString();
                pwd=et_reg_pwd.getText().toString();
                pwd_again=et_reg_pwd_again.getText().toString();
                signature=et_reg_signature.getText().toString();
                nickname=et_reg_nickname.getText().toString();


                if(account.equals("")||pwd.equals("")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ComWidget.ToastShow("账户和密码不能为空!",RegisterActivity.this);
                        }
                    });

                }else if (!pwd.equals(pwd_again)){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ComWidget.ToastShow("两次密码不一致!",RegisterActivity.this);
                        }
                    });

                }else{
                    if(signature.equals(""))
                        signature=ConstData.getSIGNATURE();
                    if(nickname.equals(""))
                        nickname=ConstData.getNICKNAME();

                    url+="account="+account+"&pwd="+pwd+"&signature="+signature+"&nickname="+nickname;
                    HttpUtils.sendOkHttpRequest(url, new okhttp3.Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ComWidget.ToastShow("请检查网络连接!",RegisterActivity.this);
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String data=response.body().string();
                            switch ((int)Utility.getJsonObjectAttr(data,"code")){
                                case 200:
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ComWidget.ToastShow("注册成功！", RegisterActivity.this);
                                            startAct(RegisterActivity.this, LoginActivity.class);
                                            finish();
                                        }
                                    });
                                    break;
                                case 401:

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ComWidget.ToastShow("注册失败！该账户已经注册！", RegisterActivity.this);
                                        }
                                    });
                                    break;
                                    default:
                                        break;

                            }

                        }
                    });

                }


            }
        });


    }

    @Override
    protected void init() {
        et_reg_account=findViewById(R.id.et_reg_account);
        et_reg_pwd=findViewById(R.id.et_reg_pwd);
        et_reg_nickname=findViewById(R.id.et_reg_nickname);
        et_reg_signature=findViewById(R.id.et_reg_signature);
        et_reg_pwd_again=findViewById(R.id.et_reg_pwd_again);
        btn_register=findViewById(R.id.btn_register);
    }


}
