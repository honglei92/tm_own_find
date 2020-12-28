package com.tianma.tm_own_find.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.tenma.ventures.base.TMWebFragment;
import com.tenma.ventures.config.TMConstant;
import com.tenma.ventures.config.TMServerConfig;
import com.tenma.ventures.tools.change_activity.BackPressListener;
import com.tenma.ventures.tools.change_activity.TitleChange;
import com.tianma.tm_own_find.R;
import com.tianma.tm_own_find.base.UCBaseActivity;
import com.tianma.tm_own_find.config.TMUCConstant;
import com.tianma.tm_own_find.utils.StatusBarUtil;

import java.lang.reflect.Method;

import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportActivity;
import me.yokeyword.fragmentation.SupportActivityDelegate;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by wcc on 2018/9/10.
 */

public class FindThreeFragmentAutoFullActivity extends UCBaseActivity implements View.OnClickListener, TitleChange, ISupportActivity {
    private String url;
    private String model_name;
    private int type;
    private static final String TAG = "FindThreeFragmentAut";
    private WebView webView;
    private Fragment fragment;
    final SupportActivityDelegate mDelegate = new SupportActivityDelegate(this);
    Gson gson = new Gson();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_three_fragment);
        mDelegate.onCreate(savedInstanceState);
        setTheme(R.style.BlueTheme);
        Bundle bundle1 = getIntent().getExtras();
        Intent intent = getIntent();
        try {
            if (intent.hasExtra(TMConstant.BundleParams.MULTI_WINDOW_ARGUMENTS)) {
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(intent.getStringExtra(TMConstant.BundleParams.MULTI_WINDOW_ARGUMENTS), JsonObject.class);
                url = jsonObject.get("url").getAsString();
                model_name = jsonObject.get("model_name").getAsString();
                type = jsonObject.get("type").getAsInt();
                Log.i("xxx", gson.toJson(jsonObject));
            }
            if (intent.hasExtra("url")) {
                if (bundle1 != null) {
                    url = bundle1.getString("url");
                    model_name = bundle1.getString("model_name");
                    type = bundle1.getInt("type");
                }
            }
            //跳转原生要传的bundle
            Bundle bundle = new Bundle();
            if (intent.hasExtra("param")) {
                Gson gson = new Gson();
                if (bundle1 != null) {
                    if (bundle1.getString("param") != null && !bundle1.getString("param").equals("") &&
                            !bundle1.getString("param").equals("null")) {
                        JsonObject jsonObject = gson.fromJson(bundle1.getString("param"), new TypeToken<JsonObject>() {
                        }.getType());
                        if (jsonObject.has("tmHideNavgation")) {
                            if (jsonObject.get("tmHideNavgation").getAsInt() == 1) {
                                hideTitle();
                            }
                        }
                        if (jsonObject.has("id")) {
                            bundle.putString("paramStr", jsonObject.get("id").getAsString());
                        }
                        if (jsonObject.has("title")) {
                            model_name = jsonObject.get("title").getAsString();
                        }
                        bundle.putString("param", bundle1.getString("param"));
                        bundle.putString("json_parameter", bundle1.getString("param"));
                    }
                }
            }

            /*1 原生*/
            if (1 == type) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                bundle.putBoolean(TMUCConstant.BundleParam.SHOW_BACK_BUTTON, true);
                bundle.putString("title", model_name);
                if (url.equals("org.jer.app.ui.DiscloseHomeFragment")
                        || url.equals("com.tianma.tm_new_time.MainFragmentBurst")//新爆料
                        || url.contains("YouzanMainFragment")) {
                    LinearLayout header_ic = findViewById(R.id.header_ic);
                    header_ic.setVisibility(View.GONE);
                }

                try {
                    fragment = Fragment.instantiate(this, url);
                    Log.d(TAG, "onCreate: " + fragment);
                    if (fragment != null) {
                        fragment.setArguments(bundle);

                        if (url.equals("com.higgses.news.mobile.live_xm.video.ui.VideoNoLazyFragment")) {
                            try {
                                Class<?> appClass = Class.forName("com.higgses.news.mobile.live_xm.video.utils.VideoUtils");//完整类名
                                Method init = appClass.getMethod("initModule", Context.class);//获得私有方法
                                init.setAccessible(true);//调用方法前，设置访问标志
                                init.invoke(null, getApplicationContext());//使用方法
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        transaction.add(R.id.user_center_ll, fragment);
                        transaction.commit();
                        fragment.setUserVisibleHint(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    finish();
                    return;
                }

            }
            if (2 == type) {
                Bundle tmBundle = new Bundle();
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    tmBundle.putString(TMConstant.BundleParams.LOAD_URL, url);
                } else {
                    tmBundle.putString(TMConstant.BundleParams.LOAD_URL, TMServerConfig.BASE_URL + url);
                }
                Log.d(TAG, "whl: FindThreeFragmentActivity TmNewJsFragment");
/*
                if (isAutoFull(url)) {
                    Intent fullIntent = new Intent(FindThreeFragmentActivity.this, FindThreeFragmentAutoFullActivity.class);
                    Bundle bundleAutoFull = getIntent().getExtras();
                    bundleAutoFull.putString("model_name", TextUtils.isEmpty(model_name) ? "" : model_name);
                    bundleAutoFull.putInt("type", 2);
                    bundleAutoFull.putString("url", url);
                    fullIntent.putExtras(bundleAutoFull);
                    startActivity(fullIntent);
                    mDelegate.pop();
                    mDelegate.onDestroy();
                    finish();
                    return;
                }
*/
                if (isNewJS(url)) {
                    JsonObject jsonObject = new JsonObject();
                    if (bundle1.getString("param") != null && !bundle1.getString("param").equals("") &&
                            !bundle1.getString("param").equals("null")) {
                        jsonObject = gson.fromJson(bundle1.getString("param"), new TypeToken<JsonObject>() {
                        }.getType());
                        if (jsonObject.has("jsurl")) {
                            fragment = Fragment.instantiate(this, "com.tianma.tm_new_time.entrance.frag.TMFragmentJsInject");
                        } else {
                            fragment = Fragment.instantiate(this, "com.tianma.tm_new_time.entrance.frag.TmNewJsFragment");
                        }
                    } else {
                        fragment = Fragment.instantiate(this, "com.tianma.tm_new_time.entrance.frag.TmNewJsFragment");
                    }
                    Log.d(TAG, "onCreate: " + fragment);
                    jsonObject.addProperty("urlStr", url);
                    bundle.putString("param", gson.toJson(jsonObject));
                    if (fragment != null) {
                        fragment.setArguments(bundle);
                    }
                } else {
                    fragment = TMWebFragment.newInstance(tmBundle);
                }

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.user_center_ll, fragment).commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("wwww","onDestroy 2");
        ((TextView) findViewById(R.id.title_tv)).setText(model_name);

        StatusBarUtil.translucentStatusBar(this, true);
    }

    private boolean isAutoFull(String url) {
        String isAutoFull = getValueByName(url, "isAutoFull");
        return "1".equals(isAutoFull);
    }

    private boolean isNewJS(String url) {
        String isNewJS = getValueByName(url, "isNewJS");
        String isnewjs = getValueByName(url, "isnewjs");
        return "1".equals(isNewJS) || "1".equals(isnewjs);
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


    @Override
    public void onClick(View v) {
        super.onClick(v);
        int viewId = v.getId();
        if (viewId == R.id.back_rl) {
            if (fragment != null && fragment instanceof BackPressListener) {
                if (((BackPressListener) fragment).onBackPress()) {
                    return;
                }
            }
            finish();
        }
    }

    @Override
    protected void initTheme() {
        super.initTheme();
        /*RelativeLayout baseHeaderRL = (RelativeLayout) findViewById(R.id.base_header_rl);
        if (null != baseHeaderRL) {
            baseHeaderRL.setBackgroundColor(TMThemeManager.getThemeMode() == TMThemeManager.ThemeMode.DAY ? themeColor : getResources().getColor(R.color.find_base_header_bg_color_night));
        }*/
        /*TextView aboutUsContentTv = findViewById(R.id.about_us_content_tv);

        LinearLayout basicsLl = (LinearLayout) findViewById(R.id.basics_ll);
        RelativeLayout baseHeaderRL = (RelativeLayout) findViewById(R.id.base_header_rl);

        basicsLl.setBackgroundColor(getResources().getColor(TMThemeManager.getCurrentThemeRes(this, R.color.basics_bg_color)));
        baseHeaderRL.setBackgroundColor(getResources().getColor(TMThemeManager.getCurrentThemeRes(this, R.color.base_header_bg_color)));

        if (TMThemeManager.getThemeMode() == TMThemeManager.ThemeMode.DAY) {
            baseHeaderRL.setBackgroundResource(R.drawable.bg_header_common);
            aboutUsContentTv.setTextColor(getResources().getColor(R.color.uc_dark_gray_text));
        } else {
            baseHeaderRL.setBackgroundColor(nightThemeColor);
            aboutUsContentTv.setTextColor(getResources().getColor(R.color.white));
        }*/
    }

    @Override
    public void showTitle() {
        findViewById(R.id.header_ic).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTitle() {
        findViewById(R.id.header_ic).setVisibility(View.GONE);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDelegate.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDelegate.onDestroy();
        Log.i("wwww","onDestroy 1");
    }

    @Override
    public SupportActivityDelegate getSupportDelegate() {
        return mDelegate;
    }

    @Override
    public ExtraTransaction extraTransaction() {
        return mDelegate.extraTransaction();
    }

    @Override
    public FragmentAnimator getFragmentAnimator() {
        return mDelegate.getFragmentAnimator();
    }

    @Override
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        mDelegate.setFragmentAnimator(fragmentAnimator);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return mDelegate.onCreateFragmentAnimator();
    }

    @Override
    public void post(Runnable runnable) {
        mDelegate.post(runnable);
    }

    @Override
    public void onBackPressed() {
        if (fragment instanceof BackPressListener) {
            if (((BackPressListener) fragment).onBackPress()) {
                return;
            }
        }
        super.onBackPressed();
    }

    @Override
    public void onBackPressedSupport() {
        mDelegate.onBackPressedSupport();
    }
}
