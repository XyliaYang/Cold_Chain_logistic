package com.example.hp.cold_chain_logistic.activity;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hp.cold_chain_logistic.R;
import com.example.hp.cold_chain_logistic.base.BaseActivity;
import com.example.hp.cold_chain_logistic.base.ConstData;
import com.example.hp.cold_chain_logistic.ui.ComWidget;
import com.example.hp.cold_chain_logistic.utils.HttpUtils;
import com.example.hp.cold_chain_logistic.utils.Utility;

import java.io.IOException;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Response;

/**
 * @Createdby LizYang
 * @Version: V 1.0
 * @Date: 2018/5/5
 * @Description:
 */

public class FindPwdActivity extends BaseActivity {

    EditText et_find_pwd_phone;
    EditText et_find_pwd_validate;
    EditText et_find_pwd_pwd;
    EditText et_pwd_find_pwd_again;
    Button bt_find_pwd_send;
    Button btn_find_pwd;


    String phone="";
    String validate="";
    String pwd="";
    String pwd_again="";
    String url= ConstData.getUSER_ITERFACE_SERVER()+"updateuser.php?";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd);

        init();



        //发送
        bt_find_pwd_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone=et_find_pwd_phone.getText().toString();
                if (phone.equals("")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ComWidget.ToastShow("请输入手机号!",FindPwdActivity.this);
                        }
                    });
                }else{
                    sendCode("86",phone);
                }


            }
        });


        //确认修改
        btn_find_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate=et_find_pwd_validate.getText().toString();
                pwd=et_find_pwd_pwd.getText().toString();
                pwd_again=et_pwd_find_pwd_again.getText().toString();

                if (validate.equals("")||pwd.equals("")||pwd_again.equals("")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ComWidget.ToastShow("请检查输入不为空！",FindPwdActivity.this);
                        }
                    });

                }else if(!pwd.equals(pwd_again)){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ComWidget.ToastShow("两次输入密码不一致！",FindPwdActivity.this);
                        }
                    });

                }else{
                    submitCode("86",phone,validate);
                    onDestroy();
                }

            }
        });





    }

    @Override
    protected void init() {
         et_find_pwd_phone=findViewById(R.id.et_find_pwd_phone);
        et_find_pwd_validate=findViewById(R.id.et_find_pwd_validate);
         et_find_pwd_pwd=findViewById(R.id.et_find_pwd_pwd);
         et_pwd_find_pwd_again=findViewById(R.id.et_pwd_find_pwd_again);
         bt_find_pwd_send=findViewById(R.id.bt_find_pwd_send);
         btn_find_pwd=findViewById(R.id.btn_find_pwd);
    }



    private void submitCode(String country, final String phone, String code) {
        // 注册一个事件回调，用于处理提交验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理验证成功的结果
                    url+="account="+ phone+"&pwd="+pwd;
                    HttpUtils.sendOkHttpRequest(url, new okhttp3.Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ComWidget.ToastShow("请检查网络连接状态！",FindPwdActivity.this);
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            String data=response.body().string();
                            int result= Utility.getJsonObjectAttr(data,"code");
                            if(result==200){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ComWidget.ToastShow("修改成功！",FindPwdActivity.this);
                                    startAct(FindPwdActivity.this,LoginActivity.class);
                                    finish();
                                    }
                                });

                            }else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ComWidget.ToastShow("更新操作失败！",FindPwdActivity.this);
                                    }
                                });
                            }
                        }
                    });


                } else{
                    // TODO 处理错误的结果
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ComWidget.ToastShow("验证不成功！",FindPwdActivity.this);

                        }
                    });

                }

            }
        });
        // 触发操作
        SMSSDK.submitVerificationCode(country, phone, code);
    }

    private void sendCode(String country, String phone) {
        // 注册一个事件回调，用于处理发送验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理成功得到验证码的结果
                    // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ComWidget.ToastShow("发送成功！",FindPwdActivity.this);
                        }
                    });

                } else{
                    // TODO 处理错误的结果
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ComWidget.ToastShow("发送失败，请核对手机号后重新发送！",FindPwdActivity.this);
                        }
                    });
                }
            }
        });
        // 触发操作
        SMSSDK.getVerificationCode(country,phone);
    }

    /**
     * destroy msg SDK after user it
     */
    protected void onDestroy() {
        super.onDestroy();
        //用完回调要注销掉，否则可能会出现内存泄露
        SMSSDK.unregisterAllEventHandler();
    };
}
