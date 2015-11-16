package com.hzqianxun.www.bisheclient.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hzqianxun.www.bisheclient.AppContext;
import com.hzqianxun.www.bisheclient.R;
import com.hzqianxun.www.bisheclient.api.RestApi;
import com.hzqianxun.www.bisheclient.bean.Session;
import com.hzqianxun.www.bisheclient.common.Constant;
import com.hzqianxun.www.bisheclient.utils.JsonUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

public class LoginActivity extends Activity {

    EditText et_email, et_password;
    Button btn_enter;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_login);

        et_email = (EditText) findViewById(R.id.editText_login_email);
        et_password = (EditText) findViewById(R.id.editText_login_password);
        btn_enter = (Button) findViewById(R.id.button_enter);

        et_email.setText(AppContext.getInstance().getProperty(Constant.ACCOUNT_EMAIL));
        et_password.setText(AppContext.getInstance().getProperty(Constant.ACCOUNT_PASSWD));

        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });


        if (AppContext.getInstance().isLogin()){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        }

    }

    private void checkLogin(){
        String email = et_email.getText().toString();
        String passwd = et_password.getText().toString();

        if (!email.isEmpty() && !passwd.isEmpty())
        {
            AppContext.getInstance().saveAccountInfo(email, passwd);
            login(email, passwd);
        }
    }

    private void login(String email, String passwd){
//        if (mLoginProgressDialog == null) {
//            mLoginProgressDialog = new ProgressDialog(this);
//            mLoginProgressDialog.setCancelable(true);
//            mLoginProgressDialog.setCanceledOnTouchOutside(false);
//            mLoginProgressDialog.setMessage("正在登陆");
//        }

        RestApi.login(email, passwd, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Session session = JsonUtils.toBean(Session.class, responseBody);

                Log.v("la", session.getUseremail() +" " + session.getUserid()+"--"+ session.getToken() +" "+ session.getUsername() + " " + session.getCreate_at());


                if (session != null) {

                    AppContext.getInstance().saveLoginInfo(session);
                    if (session != null && session.getToken() != null) {
                        AppContext.getInstance().setProperty(Constant.PROP_KEY_PRIVATE_TOKEN, session.getToken());

                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(AppContext.getInstance(), "login error", Toast.LENGTH_LONG).show();
            }
        });
    }


}
