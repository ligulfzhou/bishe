package com.hzqianxun.www.bisheclient.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link //MyinfoFragment.//OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyinfoFragment#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyinfoFragment extends BaseFragment {

    TextView tv_username, tv_email, tv_register_time;
    Button btn_logout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("test appcontext", AppContext.getInstance().getLoginInfo().getUseremail());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_myinfo, container, false);

        tv_username = (TextView)v.findViewById(R.id.tv_username);
        tv_email = (TextView) v.findViewById(R.id.tv_email);
        tv_register_time = (TextView) v.findViewById(R.id.tv_register_time);
        btn_logout = (Button) v.findViewById(R.id.btn_logout);

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

//        return inflater.inflate(R.layout.fragment_myinfo, container, false);
        return v;
    }
    private void logout(){
        AppContext.getInstance().logout();

        Intent i = new Intent(AppContext.getInstance(), LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
