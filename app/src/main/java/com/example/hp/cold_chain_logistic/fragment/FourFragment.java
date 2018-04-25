package com.example.hp.cold_chain_logistic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.hp.cold_chain_logistic.R;
import com.example.hp.cold_chain_logistic.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_list_item_2;

/**
 * @author liz
 * @version V1.0
 * @date 2018/4/8
 */

public class FourFragment extends Fragment {
    private View view;
    private ListView  lv_fg_four;
    private List<String>  list=new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapter;
    private ImageView iv_fg_four_setting;
    private  FourHelpFragment fourHelpFragment;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_four,container,false);
        lv_fg_four=view.findViewById(R.id.lv_fg_four);
        iv_fg_four_setting=view.findViewById(R.id.iv_fg_four_setting);
        fourHelpFragment=new FourHelpFragment();

        //显示IMSI列表
        initList();
        arrayAdapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,list);
        lv_fg_four.setAdapter(arrayAdapter);



        return view;
    }

    private void initList() {

        for(int i=0;i<10;i++){
            list.add("111111111111111");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        iv_fg_four_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(getContext(),iv_fg_four_setting);
                popupMenu.getMenuInflater().inflate(R.menu.menu_setting,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.item_setting_help:

                                //fg调用activity的方法
                                MainActivity mainActivity= (MainActivity) getActivity();
                                mainActivity.changeFragment(fourHelpFragment);
                                mainActivity.transaction.addToBackStack(null);

                        }
                        return true;
                    }
                });

                popupMenu.show();
            }
        });
    }
}
