package com.example.hp.cold_chain_logistic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.cold_chain_logistic.R;
import com.example.hp.cold_chain_logistic.adapter.GridViewAdapter;
import com.example.hp.cold_chain_logistic.beans.GridViewItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liz
 * @version V1.0
 * @date 2018/4/7
 */

public class TwoFragment extends Fragment {

    private  View view;
    private Spinner sp_fg_two;
    private EditText et_fg_two_input;
    private TextView tv_fg_two_search;
    private List<String>  listSpinner;
    private ArrayAdapter<String> arrayAdapter;
    private GridView   gv_fg_two;
    private GridViewAdapter gridViewAdapter;
    private  List<GridViewItem> listGrid=new ArrayList<GridViewItem>();
    private GridViewItem gridViewItem;



    @Nullable
    @Override
    /**
     * fg中初始化控件
     */
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_two,container,false);
        sp_fg_two=view.findViewById(R.id.sp_fg_two);
        et_fg_two_input=view.findViewById(R.id.et_fg_two_input);
        tv_fg_two_search=view.findViewById(R.id.tv_fg_two_search);
        gv_fg_two=view.findViewById(R.id.gv_fg_two);



        //下拉列表
        listSpinner=new ArrayList<>();
        initSpinnerList();
        arrayAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,listSpinner);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_fg_two.setAdapter(arrayAdapter);



        //gridview
        initGridList();
        gridViewAdapter=new GridViewAdapter(getContext(),listGrid);
        gv_fg_two.setAdapter(gridViewAdapter);
        gv_fg_two.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getContext(),"您点击了"+position,Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }


    private void initGridList() {

        for(int i=0;i<10;i++){

            gridViewItem=new GridViewItem(R.mipmap.ic_fragment_2,"1111111111111111");
            listGrid.add(gridViewItem);

        }
    }

    private void initSpinnerList() {
        listSpinner.add("IMSI号");
        listSpinner.add("帧号");
        listSpinner.add("用户名");

    }

    /**
     * 初始化点击事件
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //下拉列表
        sp_fg_two.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                et_fg_two_input.setHint("请输入"+arrayAdapter.getItem(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }
}
