package com.hzqianxun.www.bisheclient.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hzqianxun.www.bisheclient.R;
import com.hzqianxun.www.bisheclient.bean.GoodInCart;
import com.hzqianxun.www.bisheclient.viewholder.GoodInCartHolder;

/**
 * Created by ubuntu on 15-6-7.
 */
public class GoodInCartAdapter extends ArrayAdapter<GoodInCart>{

    Context context;
    int layoutResourceId;
    GoodInCart[] data;


    public GoodInCartAdapter(Context context, int layoutResourceId, GoodInCart[] data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        GoodInCartHolder holder = null;

        if (row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new GoodInCartHolder();
            holder.tv_goodid = (TextView) row.findViewById(R.id.tv_listview_item_goodincart_goodid);
            holder.tv_count = (TextView) row.findViewById(R.id.tv_listview_item_goodincart_count);

            row.setTag(holder);
        }else{
            holder = (GoodInCartHolder)row.getTag();
        }

        GoodInCart goodInCart = data[position];
        holder.tv_goodid.setText(Integer.toString(goodInCart.getNgoodid()));
        holder.tv_count.setText(Integer.toString(goodInCart.getNcount()));

        return row;
    }
}
