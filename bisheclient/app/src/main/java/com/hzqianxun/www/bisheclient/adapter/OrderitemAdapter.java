package com.hzqianxun.www.bisheclient.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hzqianxun.www.bisheclient.R;
import com.hzqianxun.www.bisheclient.bean.Orderitem;
import com.hzqianxun.www.bisheclient.viewholder.OrderitemHolder;

/**
 * Created by ubuntu on 15-6-10.
 */
public class OrderitemAdapter extends ArrayAdapter<Orderitem>{

    Context context;
    int layoutResourceId;
    Orderitem data[];

    public OrderitemAdapter(Context context, int layoutResourceId, Orderitem[] data) {
        super(context, layoutResourceId, data);

        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        OrderitemHolder holder = null;

        if (row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new OrderitemHolder();
            holder.tv_nid = (TextView) row.findViewById(R.id.tv_listview_item_orderitem_nid);
            holder.tv_goodid = (TextView) row.findViewById(R.id.tv_listview_item_orderitem_goodid);
            holder.tv_count = (TextView) row.findViewById(R.id.tv_listview_item_orderitem_count);

            row.setTag(holder);
        }else{
            holder = (OrderitemHolder)row.getTag();
        }

        Orderitem orderitem = data[position];
        holder.tv_nid.setText(Integer.toString(orderitem.getOrderitemid()));
        holder.tv_goodid.setText(Integer.toString(orderitem.getGoodid()));
        holder.tv_count.setText(Integer.toString(orderitem.getCount()));

        return row;
    }
}
