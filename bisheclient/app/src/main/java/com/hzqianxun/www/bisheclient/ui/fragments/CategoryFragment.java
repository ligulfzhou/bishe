package com.hzqianxun.www.bisheclient.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import static com.hzqianxun.www.bisheclient.common.Constant.CATEGORYID;
import static com.hzqianxun.www.bisheclient.common.Constant.CATEGORYNAME;

import com.hzqianxun.www.bisheclient.AppContext;
import com.hzqianxun.www.bisheclient.R;
import com.hzqianxun.www.bisheclient.adapter.CategoryAdapter;
import com.hzqianxun.www.bisheclient.api.RestApi;
import com.hzqianxun.www.bisheclient.bean.Category;
import com.hzqianxun.www.bisheclient.ui.GoodsActivity;
import com.hzqianxun.www.bisheclient.ui.basefragment.BaseFragment;
import com.hzqianxun.www.bisheclient.utils.JsonUtils;
import com.hzqianxun.www.bisheclient.viewholder.CategoryHolder;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.List;

public class CategoryFragment extends BaseFragment {

    List<Category> categories;
    CategoryAdapter categoryAdapter;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return  inflater.inflate(R.layout.fragment_category, container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RestApi.getCategories(1, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                categories = JsonUtils.getList(Category[].class, responseBody);

                setadapter();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(AppContext.getInstance(), "网络错误", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void setadapter(){

        Category[] categoriesArray = categories.toArray(new Category[categories.size()]);
        categoryAdapter = new CategoryAdapter(getActivity(), R.layout.list_item_category, categoriesArray);

        listView = (ListView) getView().findViewById(R.id.listView_categories);
        listView.setAdapter(categoryAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CategoryHolder categoryHolder = (CategoryHolder)view.getTag();
                int categoryid = Integer.parseInt(categoryHolder.tv_categoryid.getText().toString());
                String categoryname = categoryHolder.tv_categoryname.getText().toString();


                //according to the categoryid to go to another activity
                //and get the according goods
                Intent intent = new Intent(getActivity(), GoodsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(CATEGORYID, categoryid);
                bundle.putString(CATEGORYNAME, categoryname);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
