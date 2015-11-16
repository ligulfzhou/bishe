package com.hzqianxun.www.bisheclient.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.hzqianxun.www.bisheclient.ui.LoginActivity;

import java.util.HashMap;


//
// 很可耻的直接做了代码搬运，当然还是需要非常非常细微的修改【可以忽略不计】   ^ v ^
// 原链接（貌似需要翻墙）：http://androidexample.com/Android_Session_Management_Using_SharedPreferences_-_Android_Example/index.php?view=article_discription&aid=127&aaid=147
//

public class UserSessionManager {

    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREFER_NAME = "lalalalala";
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    public static final String KEY_TMP_PASS = "temp password";

    public static final String KEY_NID = "user id";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_ROLE = "user role";
    public static final String KEY_CREATE_AT = "create_at";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_TOKEN = "token";

    public static final String KEY_CART = "cart items";

    // Constructor
    public UserSessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setKeyTmpPass(String tmpPass){
        editor.putString(KEY_TMP_PASS, tmpPass);
        editor.commit();
    }
    public String getKeyTmpPass(){
        return pref.getString(KEY_TMP_PASS, null);
    }

    //Create login session
    public void createUserLoginSession(Integer nid, String email, String username, String password, Integer create_at, Integer role, String token) {
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        editor.putString(KEY_NID, Integer.toString(nid));
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);

        //should transfer to time format
        editor.putString(KEY_CREATE_AT, Integer.toString(create_at));

        //should transfer to role first
        editor.putString(KEY_ROLE, Integer.toString(role));
        editor.putString(KEY_TOKEN, token);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     */
    public boolean checkLogin() {
        // Check login status
        if (!this.isUserLoggedIn()) {

            // user is not logged in redirect him to Login Activity
            //原先未LoginActivity，随意啦，只是名字取的不一样
            Intent i = new Intent(_context, LoginActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

            return true;
        }
        return false;
    }


    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_NID, pref.getString(KEY_NID, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        user.put(KEY_CREATE_AT, pref.getString(KEY_CREATE_AT, null));
        user.put(KEY_ROLE, pref.getString(KEY_ROLE, null));
        user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, LoginActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }


    // Check for login
    public boolean isUserLoggedIn() {
        return pref.getBoolean(IS_USER_LOGIN, false);
    }
}
