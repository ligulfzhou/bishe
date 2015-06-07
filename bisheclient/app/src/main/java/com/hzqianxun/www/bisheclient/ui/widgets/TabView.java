/*******************************************************************************
 *
 * Copyright (c) Weaver Info Tech Co. Ltd
 *
 * TabView
 *
 * app.ui.widget.TabView.java
 * TODO: File description or class description.
 *
 * @author: Administrator
 * @since:  2014-4-22
 * @version: 1.0.0
 *
 * @changeLogs:
 *     1.0.0: First created this class.
 *
 ******************************************************************************/
package com.hzqianxun.www.bisheclient.ui.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hzqianxun.www.bisheclient.R;


/**
 * @author Administrator
 *
 */
public class TabView extends LinearLayout implements OnClickListener {

    private OnTabChangeListener mOnTabChangedListener;

    private int mState = 0;

    private final Button mStateButton1;
    private final Button mStateButton2;
    private final Button mStateButton3;
    private final Button mStateButton4;



    public TabView(Context context) {
        this(context, null);
    }

    public TabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public TabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.view_tab, this);
        mStateButton1 = (Button) findViewById(R.id.button_state1);
        mStateButton2 = (Button) findViewById(R.id.button_state2);
        mStateButton3 = (Button) findViewById(R.id.button_state3);
        mStateButton4 = (Button) findViewById(R.id.button_state4);

        mStateButton1.setOnClickListener(this);
        mStateButton2.setOnClickListener(this);
        mStateButton3.setOnClickListener(this);
        mStateButton4.setOnClickListener(this);
    }

    public void setOnTabChangeListener(OnTabChangeListener listener) {
        mOnTabChangedListener = listener;
    }

    public void setCurrentTab(int index) {
        switchState(index);
    }

    private void switchState(int state) {
        if (mState == state) {
            return;
        } // else continue

        mState = state;
        mStateButton1.setSelected(false);
        mStateButton2.setSelected(false);
        mStateButton3.setSelected(false);
        mStateButton4.setSelected(false);

        Object tag = null;

        switch (mState) {
            case 0:
                mStateButton1.setSelected(true);
                tag = mStateButton1.getTag();
                break;

            case 1:
                mStateButton2.setSelected(true);
                tag = mStateButton2.getTag();
                break;

            case 2:
                mStateButton3.setSelected(true);
                tag = mStateButton3.getTag();
                break;

            case 3:
                mStateButton4.setSelected(true);
                tag = mStateButton4.getTag();
                break;

            default:
                break;
        }

        if (mOnTabChangedListener != null) {
            if (tag != null && mOnTabChangedListener != null) {
                mOnTabChangedListener.onTabChange(tag.toString());
            } else {
                mOnTabChangedListener.onTabChange(null);
            }
        } // else ignored
    }


    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {

        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.button_state1:
                switchState(0);
                break;

            case R.id.button_state2:
                switchState(1);
                break;

            case R.id.button_state3:
                switchState(2);
                break;

            case R.id.button_state4:
                switchState(3);
                break;

            default:
                break;
        }
    }

    public static interface OnTabChangeListener {
        public void onTabChange(String tag);
    }
}
