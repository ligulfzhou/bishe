package com.hzqianxun.www.bisheclient.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.widget.TextView;

import com.hzqianxun.www.bisheclient.R;
import com.hzqianxun.www.bisheclient.ui.fragments.CategoryFragment;
import com.hzqianxun.www.bisheclient.ui.fragments.MyOrdersFragment;
import com.hzqianxun.www.bisheclient.ui.fragments.MyinfoFragment;
import com.hzqianxun.www.bisheclient.ui.fragments.ShoppingCartFragment;
import com.hzqianxun.www.bisheclient.ui.widgets.TabView;
import com.hzqianxun.www.bisheclient.interfaces.FragmentCallback;
import com.hzqianxun.www.bisheclient.utils.DialogUtils;
import com.hzqianxun.www.bisheclient.utils.FragmentUtils;


public class StartActivity extends FragmentActivity implements TabView.OnTabChangeListener, FragmentCallback {

    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;
    private TabView mTabView;
    private TextView mTitleTextView;

    /** 上一次的状态 */
    private int mPreviousTabIndex = 1;
    /** 当前状态 */
    private int mCurrentTabIndex = 1;

    /** 再按一次退出程序*/
    private long exitTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = getSupportFragmentManager();
        mCurrentTabIndex = 0;
        mPreviousTabIndex = 0;
        setupViews();
    }
    private void setupViews()
    {
        setContentView(R.layout.activity_start);
        mTitleTextView = (TextView) findViewById(R.id.text_title);
        mTabView = (TabView) findViewById(R.id.view_tab);
        mTabView.setOnTabChangeListener(this);
        mTabView.setCurrentTab(mCurrentTabIndex);
        mCurrentFragment = new MyinfoFragment();
        FragmentUtils.replaceFragment(mFragmentManager, R.id.layout_content, MyinfoFragment.class, null, false);
    }
    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onActivityResult(int, int, android.content.Intent)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {/*
            case BaseActivity.REQUEST_OK_LOGIN:
                if (resultCode == RESULT_OK) {
                    ApplicationUtils.showToast(this, R.string.text_loginsuccess);
                    mTitleTextView.setText(R.string.text_tab_profile);
                    final ProfileFragment profileFragment =
                            (ProfileFragment) mFragmentManager.findFragmentByTag(ProfileFragment.class.getSimpleName());
                    if (profileFragment != null) {
                        Log.d(TAG, "ProfileFragment is refreshing.");
                        profileFragment.refreshViews();
                    } else {
                        Log.e(TAG, "ProfileFragment is null.");
                    }
                } else {
                    // 返回原来的页面
                    mTabView.setCurrentTab(mPreviousTabIndex);
                    ApplicationUtils.showToast(this, R.string.toast_login_failed);
                }
                break;

            default:
                break;
        */}
    }


    /* (non-Javadoc)
     * @see app.ui.FragmentCallback#onFragmentCallback(android.support.v4.app.Fragment, int, android.os.Bundle)
     */
    @Override
    public void onFragmentCallback(Fragment fragment, int id, Bundle args) {
        mTabView.setCurrentTab(0);
    }
    /* (non-Javadoc)
     * @see app.ui.widget.TabView.OnTabChangeListener#onTabChange(java.lang.String)
     */
    @Override
    public void onTabChange(String tag) {

        if (tag != null) {
            if (tag.equals("myinfo")) {
                mPreviousTabIndex = mCurrentTabIndex;
                mCurrentTabIndex = 0;
                mTitleTextView.setText("个人信息");
                replaceFragment(MyinfoFragment.class);
                // 检查，如果没有登录则跳转到登录界面
              /*  final UserConfigManager manager = UserConfigManager.getInstance();
                if (manager.getId() <= 0) {
                    startActivityForResult(new Intent(this, LoginActivity.class),
                            BaseActivity.REQUEST_OK_LOGIN);
                }*/
            }else if ("categorys".equals(tag)) {
                mPreviousTabIndex = mCurrentTabIndex;
                mCurrentTabIndex = 1;
                mTitleTextView.setText("类别");
                replaceFragment(CategoryFragment.class);

            } else if (tag.equals("myorders")) {
                mPreviousTabIndex = mCurrentTabIndex;
                mCurrentTabIndex = 2;
                mTitleTextView.setText("我的订单");
                replaceFragment(MyOrdersFragment.class);
                // 检查，如果没有登录则跳转到登录界面
              /*  final UserConfigManager manager = UserConfigManager.getInstance();
                if (manager.getId() <= 0) {
                    startActivityForResult(new Intent(this, LoginActivity.class),
                            BaseActivity.REQUEST_OK_LOGIN);
                }*/
            } else if (tag.equals("shoppingcart")) {
                mPreviousTabIndex = mCurrentTabIndex;
                mCurrentTabIndex = 3;
                mTitleTextView.setText("购物车");
                replaceFragment(ShoppingCartFragment.class);
                // 检查，如果没有登录则跳转到登录界面
               /* final UserConfigManager manager = UserConfigManager.getInstance();
                if (manager.getId() <= 0) {
                    startActivityForResult(new Intent(this, LoginActivity.class),
                            BaseActivity.REQUEST_OK_LOGIN);
                }*/
            }
        }
    }

    private void replaceFragment(Class<? extends Fragment> newFragment) {

        mCurrentFragment = FragmentUtils.switchFragment(mFragmentManager,
                R.id.layout_content, mCurrentFragment,
                newFragment, null, false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                DialogUtils.showToast(this, "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
