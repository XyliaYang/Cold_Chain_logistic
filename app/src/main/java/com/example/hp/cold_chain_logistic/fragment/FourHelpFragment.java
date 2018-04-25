package com.example.hp.cold_chain_logistic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hp.cold_chain_logistic.R;

/**
 * @author liz
 * @version V1.0
 * @date 2018/4/9
 */

public class FourHelpFragment extends Fragment {

    private  View view;
    private ImageView iv_fg_four_help_back;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_four_help,container,false);

        iv_fg_four_help_back=view.findViewById(R.id.iv_fg_four_help_back);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        iv_fg_four_help_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }
}
