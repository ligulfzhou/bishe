package com.hzqianxun.www.bisheclient.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hzqianxun.www.bisheclient.R;
import com.hzqianxun.www.bisheclient.bean.Category;
import com.hzqianxun.www.bisheclient.viewholder.CategoryHolder;

/**
 * Created by ubuntu on 15-6-4.
 */


public class CategoryAdapter extends ArrayAdapter<Category> {

    Context context;
    int layoutResourceId;
    Category[] data = null;


    public CategoryAdapter(Context context, int layoutResourceId, Category[] data) {
        super(context, layoutResourceId, data);

        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        CategoryHolder holder = null;

        if (row == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new CategoryHolder();
            holder.tv_categoryid = (TextView) row.findViewById(R.id.tv_listview_item_category_categoryid);
            holder.tv_categoryname = (TextView) row.findViewById(R.id.tv_listview_item_category_categoryname);

            row.setTag(holder);
        }else{
            holder = (CategoryHolder)row.getTag();
        }

        Category category = data[position];

        holder.tv_categoryid.setText((category.getCategoryid()).toString());
        holder.tv_categoryname.setText(category.getCategoryname());

        return row;
    }
}
