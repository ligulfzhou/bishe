package com.hzqianxun.www.bisheclient.ui.fragments;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.hzqianxun.www.bisheclient.api.RestApi;
import com.hzqianxun.www.bisheclient.viewholder.GoodInCartHolder;
import com.hzqianxun.www.bisheclient.AppContext;
import com.hzqianxun.www.bisheclient.R;
import com.hzqianxun.www.bisheclient.adapter.GoodInCartAdapter;
import com.hzqianxun.www.bisheclient.bean.GoodInCart;
import com.hzqianxun.www.bisheclient.ui.basefragment.BaseFragment;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.List;

public class ShoppingCartFragment extends BaseFragment {

    ListView listView;
    GoodInCartAdapter goodInCartAdapter;
    Button btn_refresh_cart, btn_clear_cart, btn_make_order;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shopping_cart, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupview();
        btn_refresh_cart = (Button) getView().findViewById(R.id.btn_refresh_cart);
        btn_clear_cart = (Button) getView().findViewById(R.id.btn_clear_cart);
        btn_make_order = (Button) getView().findViewById(R.id.btn_make_order);

        btn_refresh_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupview();
            }
        });

        btn_clear_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppContext.db.clearAllGoods();
                setupview();
            }
        });

        btn_make_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<GoodInCart> goodInCarts = AppContext.db.getAllGoods();

//                Toast.makeText(AppContext.getInstance(), "size: " + goodInCarts.size() + "goodincart 1: " + goodInCarts.get(0).getNgoodid() + " " + goodInCarts.get(0).getNcount(), Toast.LENGTH_LONG).show();
                RestApi.postMakeOrder(goodInCarts, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Toast.makeText(AppContext.getInstance(), "make order success", Toast.LENGTH_LONG).show();
                        AppContext.db.clearAllGoods();
                        setupview();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.d("error", "post order error");
                    }
                });
            }
        });
    }

    private void setupview(){
        List<GoodInCart> goodInCarts = AppContext.db.getAllGoods();
        //convert
        GoodInCart[] goodInCartArray = goodInCarts.toArray(new GoodInCart[goodInCarts.size()]);

        listView = (ListView)  getView().findViewById(R.id.listView_goodincart);
        goodInCartAdapter = new GoodInCartAdapter(getActivity(), R.layout.list_item_goodincart, goodInCartArray);
        listView.setAdapter(goodInCartAdapter);

        //点击每一个listview item， 产生一个pop up view，可以修改数量，删除该物品
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final GoodInCartHolder goodInCartHolder =  (GoodInCartHolder)view.getTag();

                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View popupView = layoutInflater.inflate(R.layout.popup_view_for_shopping_cart, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

                //删除物品的button
                Button btn_delete = (Button) popupView.findViewById(R.id.btn_delete_goodincart);
                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AppContext.db.removeGood(Integer.parseInt(goodInCartHolder.tv_goodid.getText().toString()));

                        popupWindow.dismiss();
                        setupview();
                    }
                });

                final EditText editText_change_count = (EditText) popupView.findViewById(R.id.editText_change_count);
                editText_change_count.setText(goodInCartHolder.tv_count.getText().toString());
                //editText_change_count.setHint(Integer.parseInt(goodInCartHolder.tv_goodid.getText().toString()));

                //修改数量的button
                Button btn_change_count = (Button) popupView.findViewById(R.id.btn_change_count);
                btn_change_count.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String str = editText_change_count.getText().toString();
//                        Toast.makeText(AppContext.getInstance(), "str: " + str, Toast.LENGTH_LONG).show();
                        //int count = Integer.parseInt(editText_change_count.getText().toString());
                        AppContext.db.setGoodCount(Integer.parseInt(goodInCartHolder.tv_goodid.getText().toString()), Integer.parseInt(editText_change_count.getText().toString()));
                        popupWindow.dismiss();
                        setupview();
                    }
                });

                Button btn_dismiss = (Button) popupView.findViewById(R.id.btn_dismiss);
                btn_dismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                popupWindow.showAsDropDown(view, 50, -30);
                popupWindow.setFocusable(true);
                popupWindow.setTouchable(true);
                popupWindow.update();
            }
        });

    }
}
