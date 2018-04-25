package com.example.hp.cold_chain_logistic.ui;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.hp.cold_chain_logistic.R;

/**
 * @author liz
 * @version V1.0
 * @date 2018/4/11
 */

public class TitleActivityLayout extends LinearLayout {
    ImageView btn_title_back;

    public TitleActivityLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.title_activity,this);

        btn_title_back=findViewById(R.id.iv_title_activity_back);

        btn_title_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });

    }
}
