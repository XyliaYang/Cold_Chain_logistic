package com.example.hp.cold_chain_logistic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.cold_chain_logistic.R;
import com.example.hp.cold_chain_logistic.beans.GridViewItem;

import java.util.List;

/**
 * @author liz
 * @version V1.0
 * @date 2018/4/8
 */

public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private List<GridViewItem> list;
    LayoutInflater layoutInflater;
    private ImageView mImageView;
    private TextView  mTextView;



    public GridViewAdapter(Context context, List<GridViewItem> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.item_gridview_fg_two, null);
        mImageView = (ImageView) convertView.findViewById(R.id.iv_gridview);
        mTextView=convertView.findViewById(R.id.tv_gridview);

        mImageView.setBackgroundResource(list.get(position).getImgId());
        mTextView.setText(list.get(position).getIMSI());

        return convertView;

    }
}
