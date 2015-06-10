package com.hzqianxun.www.bisheclient.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.hzqianxun.www.bisheclient.bean.GoodInCart;

import java.sql.SQLData;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by ubuntu on 15-6-5.
 */
public class AddToCart extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "cartDB";

    public AddToCart(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE  tbcart( " +  //IF NOT EXIST
                "ngoodid INTEGER PRIMARY KEY," +
                "ncount INTEGER)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//        return;
    }

    private static final String TABLE_NAME = "tbcart";
    private static final String KEY_NGOODID = "ngoodid";
    private static final String KEY_NCOUNT = "ncount";

    private static final String[] COLUMNS = {KEY_NGOODID, KEY_NCOUNT};

    //add a good, should check whether it is already exist,
    //if exist, update
    //if not, just insert

    public void addGood(int goodid){
        Log.d("addGood", String.valueOf(goodid));

        int count = 1;

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select ncount from " + TABLE_NAME + " where ngoodid = " + goodid;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {

            // if this kind of good is not in cart,
            // just insert to the sqlite
            if (cursor.getCount() == 0){

                ContentValues values = new ContentValues();
                values.put(KEY_NGOODID, goodid);
                values.put(KEY_NCOUNT, count);

                db.insert(TABLE_NAME,
                        null,
                        values);

                db.close();

                // if already in cart,
                // add the count
            }else{
                cursor.moveToFirst();
                count += cursor.getInt(0);

                ContentValues values = new ContentValues();
                values.put(KEY_NCOUNT, count);

                db.update(TABLE_NAME,
                        values,
                        KEY_NGOODID + " = ?",
                        new String[]{String.valueOf(goodid)});

                db.close();
            }
        }
    }

    public void minusGood(int goodid){
        Log.d("minusGood", String.valueOf(goodid));

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "select ncount from " + TABLE_NAME + " where ngoodid = " + goodid;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {

            // if this kind of good is not in cart,
            // just insert to the sqlite
            if (cursor.getCount() == 0){

                //nothing to do

            }else{
                cursor.moveToFirst();
                int count = cursor.getInt(0);
                if (count == 1){
                    db.delete(TABLE_NAME,KEY_NGOODID+" = ?", new String[]{String.valueOf(goodid)});
                    db.close();
                }else {
                    ContentValues values = new ContentValues();
                    values.put("ncount", count);

                    db.update(TABLE_NAME,
                            values,
                            KEY_NGOODID + " = ?",
                            new String[]{String.valueOf(goodid)});

                    db.close();
                }
            }
        }
    }

    public void setGoodCount(int goodid, int goodCount){
        Log.d("set goodid count", String.valueOf(goodid) +" "+ String.valueOf(goodCount));

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select ncount from " + TABLE_NAME + " where ngoodid = " + goodid;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {

            // if this kind of good is not in cart,
            // just insert to the sqlite
            if (cursor.getCount() == 0){

                ContentValues values = new ContentValues();
                values.put(KEY_NGOODID, goodid);
                values.put(KEY_NCOUNT, goodCount);

                db.insert(TABLE_NAME,
                        null,
                        values);

                db.close();
                // if already in cart,
                // update
            }else{
                cursor.moveToFirst();

                ContentValues values = new ContentValues();
                values.put(KEY_NCOUNT, goodCount);

                db.update(TABLE_NAME,
                        values,
                        KEY_NGOODID + " = ?",
                        new String[]{String.valueOf(goodid)});

                db.close();
            }
        }
    }

    // 获取在购物车的数量
    public int getGoodCount(int goodid){
        Log.d("get goodid count", String.valueOf(goodid));

        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select ncount from " + TABLE_NAME + " where ngoodid = " + goodid;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            if (cursor.getCount() > 0){

                cursor.moveToFirst();
                count = cursor.getInt(0);
            }
        }
        db.close();
        return count;
    }


    public void removeGood(int goodid){
        Log.d("removeGood", String.valueOf(goodid));

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,KEY_NGOODID+" = ?", new String[]{String.valueOf(goodid)});
        db.close();
    }

    public List<GoodInCart> getAllGoods(){
        List<GoodInCart> goodInCarts = new LinkedList<GoodInCart>();
        String query = "select * from " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        GoodInCart goodInCart = null;

        if (cursor.moveToFirst()){
            do{
                goodInCart = new GoodInCart();
                goodInCart.setNgoodid(cursor.getInt(0));
                goodInCart.setNcount(cursor.getInt(1));

                goodInCarts.add(goodInCart);
            }while(cursor.moveToNext());
        }
        Log.d("getAllGoods", goodInCarts.toString());

        return goodInCarts;
    }

    //clear all
    public void clearAllGoods(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, null, null);
        db.close();
    }
}
