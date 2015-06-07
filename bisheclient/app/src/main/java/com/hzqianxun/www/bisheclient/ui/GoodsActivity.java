package com.hzqianxun.www.bisheclient.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static com.hzqianxun.www.bisheclient.common.Constant.CATEGORYID;
import static com.hzqianxun.www.bisheclient.common.Constant.CATEGORYNAME;

import com.hzqianxun.www.bisheclient.AppContext;
import com.hzqianxun.www.bisheclient.R;
import com.hzqianxun.www.bisheclient.adapter.GoodAdapter;
import com.hzqianxun.www.bisheclient.api.RestApi;
import com.hzqianxun.www.bisheclient.bean.Good;
import com.hzqianxun.www.bisheclient.bean.GoodInCart;
import com.hzqianxun.www.bisheclient.ui.baseactivity.TitleActivity;
import com.hzqianxun.www.bisheclient.utils.AddToCart;
import com.hzqianxun.www.bisheclient.utils.JsonUtils;
import com.hzqianxun.www.bisheclient.viewholder.GoodHolder;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.List;

public class GoodsActivity extends TitleActivity {

    private int categoryid;
    private String categoryname;

    GoodAdapter goodAdapter;
//    TextView tv_categoryid;
    ListView listView;

    List<Good> goods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);

        setupViews();
    }

    private void setupViews(){


        showBackwardView(R.string.button_backward, true);

        Intent intent = getIntent();
        categoryid = intent.getIntExtra(CATEGORYID, -1);
        categoryname = intent.getStringExtra(CATEGORYNAME);

        setTitle(categoryname);

        //get the categoriedgoods
        RestApi.getCategoriedGoods(categoryid, 1, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                goods = JsonUtils.getList(Good[].class, responseBody);

                Log.v("test if goods is loaded", goods.get(1).getGoodname());
                setadapter();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(AppContext.getInstance(), "data load error, please try again", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setadapter(){
        Good[] goodArray = goods.toArray(new Good[goods.size()]);
        goodAdapter = new GoodAdapter(this, R.layout.listview_item_good, goodArray);

        listView = (ListView) findViewById(R.id.listView_categoriedgoods);
        listView.setAdapter(goodAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(AppContext.getInstance(), view.getTag().toString(), Toast.LENGTH_LONG).show();
                GoodHolder goodHolder = (GoodHolder) view.getTag();
                int goodid = Integer.parseInt(goodHolder.tv_goodid.getText().toString());
//                AddToCart.addGood(goodid);

                AppContext.db.addGood(goodid);


                List<GoodInCart> data = AppContext.db.getAllGoods();

                //Toast.makeText(AppContext.getInstance(), "add good NO " + goodid + " to cart", Toast.LENGTH_LONG).show();
                Toast.makeText(AppContext.getInstance(), data.get(0).getNgoodid()+ " success " +data.get(0).getNcount(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
