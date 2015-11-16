package com.hzqianxun.www.bisheclient.ui.basefragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.hzqianxun.www.bisheclient.interfaces.FragmentCallback;
import com.hzqianxun.www.bisheclient.interfaces.FragmentInterface;

public abstract class BaseFragment extends Fragment implements FragmentInterface, OnTouchListener {

    /**
     * 模拟后退键
     */
    protected void back() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStackImmediate();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        view.setOnTouchListener(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // 拦截触摸事件，防止传递到下一层的View
        return true;
    }

    public void dispatchCommand(int id, Bundle args) {
        if (getActivity() instanceof FragmentCallback) {
            FragmentCallback callback = (FragmentCallback) getActivity();
            callback.onFragmentCallback(this, id, args);
        } else {
            throw new IllegalStateException("The host activity does not implement FragmentCallback.");
        }
    }

    public void refreshViews() {

    }

    public boolean commitData() {
        return false;
    }
}
