package com.hzqianxun.www.bisheclient.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hzqianxun.www.bisheclient.AppContext;
import com.hzqianxun.www.bisheclient.R;
import com.hzqianxun.www.bisheclient.adapter.GoodInCartAdapter;
import com.hzqianxun.www.bisheclient.bean.GoodInCart;
import com.hzqianxun.www.bisheclient.ui.basefragment.BaseFragment;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link //ShoppingCartFragment.//OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShoppingCartFragment#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingCartFragment extends BaseFragment {

    ListView listView;
    GoodInCartAdapter goodInCartAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shopping_cart, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<GoodInCart> goodInCarts = AppContext.db.getAllGoods();
        //convert
        GoodInCart[] goodInCartArray = goodInCarts.toArray(new GoodInCart[goodInCarts.size()]);

        listView = (ListView)  getView().findViewById(R.id.listView_goodincart);
        goodInCartAdapter = new GoodInCartAdapter(getActivity(), R.layout.list_item_goodincart, goodInCartArray);
        listView.setAdapter(goodInCartAdapter);
    }
}