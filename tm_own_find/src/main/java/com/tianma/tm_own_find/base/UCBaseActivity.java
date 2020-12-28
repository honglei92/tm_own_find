package com.tianma.tm_own_find.base;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tenma.ventures.base.TMActivity;
import com.tenma.ventures.bean.utils.TMSharedPUtil;
import com.tenma.ventures.tools.TMStatusBarUtil;
import com.tenma.ventures.tools.TMStatusBarUtil2;
import com.tianma.tm_own_find.R;
import com.tianma.tm_own_find.utils.StatusBarUtil;

/**
 * 用户中心Activity基类
 * Created by bin on 2018/2/7.
 */

public class UCBaseActivity extends TMActivity {

    protected int themeColor;
    protected int nightThemeColor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        themeColor = Color.parseColor(TMSharedPUtil.getTMThemeColor(this));
        String nightColor = TMSharedPUtil.getTMNightThemeColor(this);
        if (TextUtils.isEmpty(nightColor)) {
            nightThemeColor = Color.parseColor(nightColor);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void initTheme() {
        StatusBarUtil.translucentStatusBar(this, true);
        super.initTheme();
        try {
            //设置标题栏背景色
            RelativeLayout baseHeaderRL = findViewById(R.id.base_header_rl);
            if (null != baseHeaderRL) {
                if (TMSharedPUtil.getTMTitleBarColor(UCBaseActivity.this) != null) {
                    Drawable drawable = new BitmapDrawable(getResources(), TMSharedPUtil.getTMTitleBarColor(UCBaseActivity.this));
                    baseHeaderRL.setBackground(drawable);
                } else {
                    baseHeaderRL.setBackgroundColor(themeColor);
                }
            }

            TextView tvTitle = findViewById(R.id.title_tv);
            if (tvTitle != null) {
                tvTitle.setTextColor(Color.parseColor(TMSharedPUtil.getTMTitleTextColor(UCBaseActivity.this)));
            }
            TextView backTv = findViewById(R.id.back_tv);
            if (backTv != null) {
                backTv.setTextColor(Color.parseColor(TMSharedPUtil.getTMTitleTextColor(UCBaseActivity.this)));
            }
            ImageView backIv = findViewById(R.id.common_back_iv);
            if (backIv != null) {
                backIv.setColorFilter(Color.parseColor(TMSharedPUtil.getTMTitleTextColor(UCBaseActivity.this)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected StateListDrawable createCommonButton() {

        int strokeWidth = getResources().getDimensionPixelOffset(R.dimen.dimen_1); //  边框宽度
        int roundRadius = getResources().getDimensionPixelOffset(R.dimen.dimen_4); //  圆角半径
        int strokeColor = Color.parseColor(TMSharedPUtil.getTMThemeColor(this));//边框颜色
        int fillColor = Color.parseColor(TMSharedPUtil.getTMThemeColor(this));//内部填充颜色

        // 常态
        GradientDrawable normalGradientDrawable = new GradientDrawable();//创建drawable
        normalGradientDrawable.setColor(fillColor);
        normalGradientDrawable.setCornerRadius(roundRadius);
        normalGradientDrawable.setStroke(strokeWidth, strokeColor);

        // 按下
        GradientDrawable pressedGradientDrawable = new GradientDrawable();//创建drawable
        pressedGradientDrawable.setColor(fillColor);
        pressedGradientDrawable.setCornerRadius(roundRadius);
        pressedGradientDrawable.setStroke(strokeWidth, strokeColor);

        // 禁用状态
        GradientDrawable disableGradientDrawable = new GradientDrawable();
        disableGradientDrawable.setColor(getResources().getColor(R.color.find_uc_validate_btn_disable_color));
        disableGradientDrawable.setCornerRadius(roundRadius);
        disableGradientDrawable.setStroke(strokeWidth, getResources().getColor(R.color.find_uc_validate_btn_disable_color));

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled},
                normalGradientDrawable);
        stateListDrawable.addState(new int[]{-android.R.attr.state_pressed, android.R.attr.state_enabled},
                pressedGradientDrawable);
        stateListDrawable.addState(new int[]{-android.R.attr.state_enabled},
                disableGradientDrawable);
        return stateListDrawable;
    }

    protected StateListDrawable createNightCommonButton() {

        int strokeWidth = getResources().getDimensionPixelOffset(R.dimen.dimen_1); //  边框宽度
        int roundRadius = getResources().getDimensionPixelOffset(R.dimen.dimen_4); //  圆角半径
        int strokeColor = getResources().getColor(R.color.find_main_button_bg_color_night);//边框颜色
        int fillColor = getResources().getColor(R.color.find_main_button_bg_color_night);//内部填充颜色

        // 常态
        GradientDrawable normalGradientDrawable = new GradientDrawable();//创建drawable
        normalGradientDrawable.setColor(fillColor);
        normalGradientDrawable.setCornerRadius(roundRadius);
        normalGradientDrawable.setStroke(strokeWidth, strokeColor);

        // 按下
        GradientDrawable pressedGradientDrawable = new GradientDrawable();//创建drawable
        pressedGradientDrawable.setColor(fillColor);
        pressedGradientDrawable.setCornerRadius(roundRadius);
        pressedGradientDrawable.setStroke(strokeWidth, strokeColor);

        // 禁用状态
        GradientDrawable disableGradientDrawable = new GradientDrawable();
        disableGradientDrawable.setColor(getResources().getColor(R.color.find_uc_validate_btn_disable_color));
        disableGradientDrawable.setCornerRadius(roundRadius);
        disableGradientDrawable.setStroke(strokeWidth, getResources().getColor(R.color.find_uc_validate_btn_disable_color));

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled},
                normalGradientDrawable);
        stateListDrawable.addState(new int[]{-android.R.attr.state_pressed, android.R.attr.state_enabled},
                pressedGradientDrawable);
        stateListDrawable.addState(new int[]{-android.R.attr.state_enabled},
                disableGradientDrawable);
        return stateListDrawable;
    }

    private boolean isAutoFull(String url) {
        String isAutoFull = getValueByName(url, "isAutoFull");
        return "1".equals(isAutoFull);
    }

    public static String getValueByName(String url, String name) {
        String result = "";
        int index = url.indexOf("?");
        String temp = url.substring(index + 1);
        String[] keyValue = temp.split("&");
        for (String str : keyValue) {
            if (str.contains(name)) {
                result = str.replace(name + "=", "");
                break;
            }
        }
        return result;
    }

}
