package com.hzqianxun.www.bisheclient.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hzqianxun.www.bisheclient.AppContext;
import com.hzqianxun.www.bisheclient.R;
import com.hzqianxun.www.bisheclient.adapter.OrderAdapter;
import com.hzqianxun.www.bisheclient.api.RestApi;
import com.hzqianxun.www.bisheclient.bean.Order;
import com.hzqianxun.www.bisheclient.ui.OrderActivity;
import com.hzqianxun.www.bisheclient.ui.basefragment.BaseFragment;
import com.hzqianxun.www.bisheclient.utils.JsonUtils;
import com.hzqianxun.www.bisheclient.viewholder.OrderHolder;
import com.hzqianxun.www.bisheclient.viewholder.OrderitemHolder;
import com.loopj.android.http.AsyncHttpResponseHandler;

import static com.hzqianxun.www.bisheclient.common.Constant.ORDERID;
import static com.hzqianxun.www.bisheclient.common.Constant.ORDER_CREATE_AT;
import static com.hzqianxun.www.bisheclient.common.Constant.ORDER_HANDLERED;
import static com.hzqianxun.www.bisheclient.common.Constant.ORDER_TOTAL;

import org.apache.http.Header;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyOrdersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyOrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyOrdersFragment extends BaseFragment {

    TextView tv_you_do_not_have_any_orders;
    Button btn_refresh_orders;
    private ListView listView;
    List<Order> orders;
    OrderAdapter orderAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_orders, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        helloworld();
    }

    private void helloworld(){
        RestApi.getOrders(1, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                orders = JsonUtils.getList(Order[].class, responseBody);
                if (orders.size() == 0){
                    setNoOrders();
                }else {
                    setadapter();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(AppContext.getInstance(), "error", Toast.LENGTH_LONG).show();
            }
        });

    }
    private void setNoOrders(){
        tv_you_do_not_have_any_orders = (TextView) getView().findViewById(R.id.tv_you_do_not_have_any_orders);
        tv_you_do_not_have_any_orders.setText("tv_you_do_not_have_any_orders");
    }

    private void setadapter(){
        Order[] orderArray = orders.toArray(new Order[orders.size()]);
        orderAdapter = new OrderAdapter(getActivity(), R.layout.list_item_order, orderArray);

        listView = (ListView) getView().findViewById(R.id.listView_order);
        listView.setAdapter(orderAdapter);

        // order list
        // click every order listitem ,
        // go to another activity to show the order info,
        // especially the orderitems list
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                OrderHolder orderHolder = (OrderHolder) view.getTag();
                String orderid = orderHolder.tv_orderid.getText().toString();
                String total  = orderHolder.tv_order_total.getText().toString();
                String create_at = orderHolder.tv_order_create_at.getText().toString();
                String handlered = orderHolder.tv_order_handlered.getText().toString();

                Intent intent = new Intent(getActivity(), OrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(ORDERID, orderid);
                bundle.putString(ORDER_CREATE_AT, create_at);
                bundle.putString(ORDER_HANDLERED, handlered);
                bundle.putString(ORDER_TOTAL, total);
                intent.putExtras(bundle);

                startActivity(intent);

//                Toast.makeText(AppContext.getInstance(), "you want to know more about this order info", Toast.LENGTH_LONG).show();
            }
        });

        //just setup view again -> just call helloworld()
        btn_refresh_orders = (Button) getView().findViewById(R.id.btn_refresh_orders);
        btn_refresh_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helloworld();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
}
