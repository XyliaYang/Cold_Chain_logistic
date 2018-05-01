package com.example.hp.cold_chain_logistic.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by LizYang
 * Version: V 1.0
 * Date: 2018/5/1
 * Description:usual widget encapsulation.
 */

public class ComWidget {

    /**
     * Toast
     * @param msg show message
     * @param context
     */
    public static void  ToastShow(String msg, Context context){
        AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setMessage(msg)
                .setPositiveButton("确定", null);
        builder.show();
    }


}
