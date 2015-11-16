package com.hzqianxun.www.bisheclient.api;

import android.util.Log;

import com.hzqianxun.www.bisheclient.AppContext;
import com.hzqianxun.www.bisheclient.bean.GoodInCart;
import com.hzqianxun.www.bisheclient.common.Constant;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.entity.ByteArrayEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.hzqianxun.www.bisheclient.api.AsyncHttpHelp.getHttpClient;
/**
 * Created by ubuntu on 15-6-3.
 */

public class RestApi {

//    public final static String HOST = "120.26.36.78/";
    public final static String HOST = "54.199.179.65/";
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

    public static void postMakeOrder(List<GoodInCart> goodInCarts, AsyncHttpResponseHandler handler) {
        AsyncHttpClient httpClient = getHttpClient();

        String token = AppContext.getInstance().getProperty(Constant.PROP_KEY_PRIVATE_TOKEN);
        httpClient.addHeader("Authorization", "Basic " + token);

        JSONArray jsonArray = new JSONArray();
        JSONObject json;

        for(int i = 0; i < goodInCarts.size(); i ++){
            json = new JSONObject();

            try {
                json.put("ngoodid", goodInCarts.get(i).getNgoodid());
                json.put("ncount", goodInCarts.get(i).getNcount());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(json);
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("orderitems", jsonArray);
//            jsonObject.put("dtotal", 1234);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ByteArrayEntity entity = null;
        try {
            entity = new ByteArrayEntity(jsonObject.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.d("post make order", entity.toString());

        httpClient.post(AppContext.getInstance(), ORDERS, entity, "application/json", handler);
    }

    public static void getOrderOrderitems(int orderid, AsyncHttpResponseHandler handler){

        String token = AppContext.getInstance().getProperty(Constant.PROP_KEY_PRIVATE_TOKEN);

        AsyncHttpClient httpClient = getHttpClient();
        httpClient.addHeader("Authorization", "Basic " + token);

        httpClient.get(BASE_URL + "orders/" + orderid + "/orderitems", handler);

    }
}
