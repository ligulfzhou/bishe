package com.hzqianxun.www.bisheclient.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static com.hzqianxun.www.bisheclient.common.Constant.PROP_KEY_EMAIL;
import static com.hzqianxun.www.bisheclient.common.Constant.PROP_KEY_NAME;
import static com.hzqianxun.www.bisheclient.common.Constant.PROP_KEY_CREATE_AT;

import com.hzqianxun.www.bisheclient.AppContext;
import com.hzqianxun.www.bisheclient.R;
import com.hzqianxun.www.bisheclient.ui.LoginActivity;
import com.hzqianxun.www.bisheclient.ui.basefragment.BaseFragment;

import java.util.Date;


public class MyinfoFragment extends BaseFragment {

    TextView tv_username, tv_email, tv_register_time;
    Button btn_logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myinfo, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_username = (TextView) getView().findViewById(R.id.tv_username);
        tv_email = (TextView) getView().findViewById(R.id.tv_email);
        tv_register_time = (TextView) getView().findViewById(R.id.tv_register_time);
        btn_logout = (Button) getView().findViewById(R.id.btn_logout);

        tv_username.setText(AppContext.getInstance().getProperty(PROP_KEY_NAME));
        tv_email.setText(AppContext.getInstance().getProperty(PROP_KEY_EMAIL));

        int register_time = Integer.parseInt(AppContext.getInstance().getProperty(PROP_KEY_CREATE_AT));
        Date d = new Date(register_time);
        tv_register_time.setText(d.getYear()+","+ d.getMonth() + "," + d.getDay());
//        tv_register_time.setText(AppContext.getInstance().getProperty(PROP_KEY_CREATE_AT).toString());
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }


    private void logout(){
        AppContext.getInstance().logout();

        Intent i = new Intent(AppContext.getInstance(), LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
