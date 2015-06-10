package com.hzqianxun.www.bisheclient.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hzqianxun.www.bisheclient.R;
import com.hzqianxun.www.bisheclient.bean.Order;
import com.hzqianxun.www.bisheclient.viewholder.OrderHolder;

/**
 * Created by ubuntu on 15-6-7.
 */
public class OrderAdapter extends ArrayAdapter<Order>{
    Context context;
    int layoutResourceId;
    Order data[] = null;

    public OrderAdapter(Context context, int layoutResourceId, Order[] data){
        super(context, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        OrderHolder holder = null;

        if (row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new OrderHolder();
            holder.tv_orderid = (TextView) row.findViewById(R.id.tv_listview_item_order_orderid);
            holder.tv_order_create_at = (TextView) row.findViewById(R.id.tv_listview_item_order_ordercreate_at);
            holder.tv_order_handlered = (TextView) row.findViewById(R.id.tv_listview_item_order_orderhandlred);
            holder.tv_order_total = (TextView) row.findViewById(R.id.tv_listview_item_order_order_total);

            row.setTag(holder);
        }else{
            holder = (OrderHolder)row.getTag();
        }

        Order order = data[position];
        holder.tv_orderid.setText(Integer.toString(order.getOrderid()));
        holder.tv_order_create_at.setText(Integer.toString(order.getCreate_at()));

        String total = "0.00";
        if (order.getTotal() != null)
            total = Double.toString(order.getTotal());
        holder.tv_order_total.setText(total);
        holder.tv_order_handlered.setText(Integer.toString(order.getHandlered()));

        return row;
    }
}
