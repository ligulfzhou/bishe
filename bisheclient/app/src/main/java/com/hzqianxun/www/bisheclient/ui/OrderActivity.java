package com.hzqianxun.www.bisheclient.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.hzqianxun.www.bisheclient.R;
import com.hzqianxun.www.bisheclient.adapter.OrderitemAdapter;
import com.hzqianxun.www.bisheclient.api.RestApi;
import com.hzqianxun.www.bisheclient.bean.Orderitem;
import com.hzqianxun.www.bisheclient.ui.baseactivity.TitleActivity;
import com.hzqianxun.www.bisheclient.utils.JsonUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import static com.hzqianxun.www.bisheclient.common.Constant.ORDERID;
import static com.hzqianxun.www.bisheclient.common.Constant.ORDER_CREATE_AT;
import static com.hzqianxun.www.bisheclient.common.Constant.ORDER_HANDLERED;
import static com.hzqianxun.www.bisheclient.common.Constant.ORDER_TOTAL;

import java.util.List;

public class OrderActivity extends TitleActivity {

    OrderitemAdapter orderitemAdapter;
    ListView listView;
    List<Orderitem> data;

    String orderid, handlered, create_at, ordertotal;
    TextView tv_orderid, tv_handlered, tv_create_at, tv_total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        setupview();
    }


    private void setupview(){

        showBackwardView(R.string.button_backward, true);

        Intent intent = getIntent();
        orderid = intent.getStringExtra(ORDERID);
        handlered = intent.getStringExtra(ORDER_HANDLERED);
        create_at = intent.getStringExtra(ORDER_CREATE_AT);
        ordertotal = intent.getStringExtra(ORDER_TOTAL);

        Log.d("intent", orderid + " " + ordertotal + " " + ordertotal);
        setTitle("订单" + orderid);

        tv_orderid = (TextView) findViewById(R.id.tv_orderid);
        tv_total = (TextView) findViewById(R.id.tv_order_total);
        tv_create_at = (TextView) findViewById(R.id.tv_ordercreate_at);
        tv_handlered = (TextView) findViewById(R.id.tv_orderhandlred);

        tv_orderid.setText(orderid);
        tv_handlered.setText(handlered);
        tv_create_at.setText(create_at);
        tv_total.setText(ordertotal);

        getList();

    }

    private void getList(){
        int orderId = Integer.parseInt(orderid);
        RestApi.getOrderOrderitems(orderId, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                data = JsonUtils.getList(Orderitem[].class, responseBody);

                setadapter();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void setadapter(){
        Orderitem[] orderitems = data.toArray(new Orderitem[data.size()]);
        orderitemAdapter = new OrderitemAdapter(this, R.layout.list_item_orderitem, orderitems);

        listView = (ListView) findViewById(R.id.listView_orderitems);
        listView.setAdapter(orderitemAdapter);
    }
}
