package com.hzqianxun.www.bisheclient.api;

import android.util.Log;

import com.hzqianxun.www.bisheclient.AppContext;
import com.hzqianxun.www.bisheclient.bean.GoodInCart;
import com.hzqianxun.www.bisheclient.common.Constant;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.List;

import static com.hzqianxun.www.bisheclient.api.AsyncHttpHelp.getHttpClient;
/**
 * Created by ubuntu on 15-6-3.
 */

public class RestApi {
    public final static String HOST = "120.26.36.78/";
    private static final String API_VERSION = "api/v1/";
    public final static String HTTPS = "https://";
    public final static String BASE_URL = HTTPS + HOST + API_VERSION;

    public final static String SESSION = BASE_URL + "session";
    public final static String CATEGORIES = BASE_URL + "categories";
    public final static String ORDERS = BASE_URL + "orders";
    public final static String USERS = BASE_URL + "users";

    public static void login(String email, String passwd, AsyncHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("email", email);
        params.put("password", passwd);
        AsyncHttpHelp.post(SESSION, params, handler);
    }

    public static void getCategories(int page, AsyncHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("page", page);
        AsyncHttpHelp.get(CATEGORIES, params, handler);
    }


    public static void getCategoriedGoods(int categoryid, int page, AsyncHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("page", page);
        AsyncHttpHelp.get(BASE_URL + "categories/" + categoryid + "/goods", params, handler);
    }

    public static void getOrders(int page, AsyncHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("page", page);

        String token = AppContext.getInstance().getProperty(Constant.PROP_KEY_PRIVATE_TOKEN);
        AsyncHttpClient httpClient = getHttpClient();
        httpClient.addHeader("Authorization", "Basic " + token);

        httpClient.get(ORDERS, params, handler);

        Log.d("http", new StringBuilder("POST ").append(ORDERS).append("?").append(params).toString());
    }

    public static void postMakeOrder(List<GoodInCart> goodInCarts, AsyncHttpResponseHandler handler){
        AsyncHttpClient httpClient = getHttpClient();

        String token = AppContext.getInstance().getProperty(Constant.PROP_KEY_PRIVATE_TOKEN);

    }
}
