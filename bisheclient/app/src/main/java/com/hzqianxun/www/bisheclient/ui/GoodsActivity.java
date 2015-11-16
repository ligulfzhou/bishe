package com.hzqianxun.www.bisheclient.ui;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import static com.hzqianxun.www.bisheclient.common.Constant.CATEGORYID;
import static com.hzqianxun.www.bisheclient.common.Constant.CATEGORYNAME;

import com.hzqianxun.www.bisheclient.AppContext;
import com.hzqianxun.www.bisheclient.R;
import com.hzqianxun.www.bisheclient.adapter.GoodAdapter;
import com.hzqianxun.www.bisheclient.api.RestApi;
import com.hzqianxun.www.bisheclient.bean.Good;
import com.hzqianxun.www.bisheclient.ui.baseactivity.TitleActivity;
import com.hzqianxun.www.bisheclient.utils.JsonUtils;
import com.hzqianxun.www.bisheclient.viewholder.GoodHolder;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.List;

public class GoodsActivity extends TitleActivity {

    private int categoryid;
    private String categoryname;

    GoodAdapter goodAdapter;
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

//                Log.v("test if goods is loaded", goods.get(1).getGoodname());
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
                final GoodHolder goodHolder = (GoodHolder) view.getTag();

                final int goodid = Integer.parseInt(goodHolder.tv_goodid.getText().toString());

                final int ncount = AppContext.db.getGoodCount(goodid);

                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View popupView = layoutInflater.inflate(R.layout.popup_view_for_goods, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

                //移除物品出购物车的button
                Button btn_remove_from_cart = (Button) popupView.findViewById(R.id.btn_remove_from_cart);
                if (ncount > 0)
                    btn_remove_from_cart.setEnabled(true);
                else
                    btn_remove_from_cart.setEnabled(false);

                btn_remove_from_cart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //删除购物车里的goodid号物品
                        AppContext.db.removeGood(goodid);
                        popupWindow.dismiss();
                    }
                });

                final EditText editText_add_to_cart = (EditText) popupView.findViewById(R.id.editText_add_to_cart);
                editText_add_to_cart.setText(Integer.toString(ncount));

                //加入购物车的button
                Button btn_add_to_cart = (Button) popupView.findViewById(R.id.btn_add_to_cart);
                btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AppContext.db.setGoodCount(goodid, Integer.parseInt(editText_add_to_cart.getText().toString()));
                        popupWindow.dismiss();
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
