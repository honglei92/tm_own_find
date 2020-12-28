package com.tianma.tm_own_find.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.tenma.ventures.tools.TMThemeManager;
import com.tianma.tm_own_find.R;
import com.tianma.tm_own_find.base.UCBaseActivity;
import com.tianma.tm_own_find.config.TMUCConstant;
import com.tianma.tm_own_find.utils.StatusBarUtil;

public class FindMainActivity extends UCBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_main);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putBoolean(TMUCConstant.BundleParam.SHOW_BACK_BUTTON, true);
        Fragment userCenterFragment = new FindMainFragment();
        userCenterFragment.setArguments(bundle);
        transaction.add(R.id.user_center_ll, userCenterFragment);
        transaction.commit();

        StatusBarUtil.translucentStatusBar(this, true);
    }

    @Override
    protected void initTheme() {
        super.initTheme();

        FrameLayout userCenterLl = findViewById(R.id.user_center_ll);
        userCenterLl.setBackgroundColor(getResources().getColor(TMThemeManager.getCurrentThemeRes(this, R.color.find_basics_bg_color)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        /*if (viewId == R.id.back_rl) {
            finish();
        }*/
    }
}
