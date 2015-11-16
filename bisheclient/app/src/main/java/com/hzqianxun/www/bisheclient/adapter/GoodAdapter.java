package com.hzqianxun.www.bisheclient.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hzqianxun.www.bisheclient.R;
import com.hzqianxun.www.bisheclient.bean.Good;
import com.hzqianxun.www.bisheclient.viewholder.GoodHolder;


/**
 * Created by ubuntu on 2015/5/25.
 */
public class GoodAdapter extends ArrayAdapter<Good> {

    Context context;
    int layoutResourceId;
    Good data[] = null;

    public GoodAdapter(Context context, int layoutResourceId, Good[] data){
        super(context, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        GoodHolder holder = null;

        if (row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new GoodHolder();
            holder.tv_goodid = (TextView) row.findViewById(R.id.tv_listview_item_good_goodidid);
            holder.tv_goodname = (TextView) row.findViewById(R.id.tv_listview_item_good_goodname);
            holder.tv_goodprice = (TextView) row.findViewById(R.id.tv_listview_item_good_goodprice);
            holder.tv_gooddesc = (TextView) row.findViewById(R.id.tv_listview_item_good_gooddesc);
            holder.tv_goodcategoryid = (TextView) row.findViewById(R.id.tv_listview_item_good_goodcategoryid);
            holder.tv_goodcount = (TextView) row.findViewById(R.id.tv_listview_item_good_goodcount);

            row.setTag(holder);
        }else{
            holder = (GoodHolder)row.getTag();
        }

        Good good = data[position];
        holder.tv_goodid.setText(Integer.toString(good.getGoodid()));
        holder.tv_goodname.setText(good.getGoodname());
        holder.tv_gooddesc.setText(good.getGooddesc());
        holder.tv_goodcategoryid.setText(Integer.toString(good.getCategoryid()));
        holder.tv_goodprice.setText(Double.toString(good.getGoodprice()));
        holder.tv_goodcount.setText(Integer.toString(good.getCount()));

        return row;
    }
}
