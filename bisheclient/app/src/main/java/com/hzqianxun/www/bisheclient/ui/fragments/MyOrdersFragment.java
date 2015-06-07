package com.hzqianxun.www.bisheclient.ui.fragments;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hzqianxun.www.bisheclient.AppContext;
import com.hzqianxun.www.bisheclient.R;
import com.hzqianxun.www.bisheclient.adapter.OrderAdapter;
import com.hzqianxun.www.bisheclient.api.RestApi;
import com.hzqianxun.www.bisheclient.bean.Order;
import com.hzqianxun.www.bisheclient.ui.basefragment.BaseFragment;
import com.hzqianxun.www.bisheclient.utils.JsonUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

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
    }
}
