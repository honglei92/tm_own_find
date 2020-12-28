package com.tianma.tm_own_find.view.discover_new;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tenma.ventures.config.TMServerConfig;
import com.tenma.ventures.share.util.TMShareUtil;
import com.tenma.ventures.tools.change_activity.TitleChange;
import com.tianma.tm_own_find.Adapter.DefaultModelAdapter;
import com.tianma.tm_own_find.R;
import com.tianma.tm_own_find.base.UCBaseActivity;
import com.tianma.tm_own_find.utils.StatusBarUtil;
import com.tianma.tm_own_find.utils.StringU;
import com.tianma.tm_own_find.view.FindThreeFragmentActivity;
import com.tianma.tm_own_find.view.FindThreeFragmentAutoFullActivity;
import com.tianma.tm_own_find.view.discover_new.bean.DiscoverModelItem;
import com.tianma.tm_own_find.view.discover_new.bean.DiscoverModelList;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * 默认样式点击耕读哟按钮跳转后的页面
 */
public class FindDefaultMoreActivity extends UCBaseActivity implements View.OnClickListener, TitleChange {
    private static final String TAG = "FindDefaultMoreActivity";
    Gson gson = new Gson();
    private TextView title;
    private RecyclerView recyclerView;
    private Context mContext;
    private DefaultModelAdapter defaultModelAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_more);
        setTheme(R.style.BlueTheme);
        mContext = FindDefaultMoreActivity.this;
        initView();
        initData();
        StatusBarUtil.translucentStatusBar(this, true);
    }

    private void initData() {
        Bundle bundle1 = getIntent().getExtras();
        if (bundle1.getString("data") != null) {
            DiscoverModelList discoverModelList = gson.fromJson(bundle1.getString("data"), DiscoverModelList.class);
            title.setText(discoverModelList.getModelTyeName());
            defaultModelAdapter.setContent(discoverModelList.getModelTyeName(), discoverModelList.getDiscoverModelItems());
        }
    }

    private void initView() {
        title = findViewById(R.id.content_title_text);
        recyclerView = findViewById(R.id.default_content_rv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        defaultModelAdapter = new DefaultModelAdapter(mContext, true);
        recyclerView.setAdapter(defaultModelAdapter);
        defaultModelAdapter.setDiscoverItemClickListener(new DiscoverContract.DiscoverItemClickListener() {
            @Override
            public void onItemClick(DiscoverModelItem discoverModelItem) {
                Intent pushIntent = new Intent(mContext, FindThreeFragmentActivity.class);
                Bundle bundle = new Bundle();
                String loadUrl = discoverModelItem.getUrl();
                bundle.putString("model_name", discoverModelItem.getModelName());
                if (0 == discoverModelItem.getForm()) {
                    bundle.putInt("type", 2);
                    if (!loadUrl.startsWith("http://") && !loadUrl.startsWith("https://")) {
                        bundle.putString("url", TMServerConfig.BASE_URL + loadUrl);
                    } else {
                        bundle.putString("url", loadUrl);
                    }
                    if (StringU.isAutoFull(bundle.getString("url"))) {
                        pushIntent = new Intent(mContext, FindThreeFragmentAutoFullActivity.class);
                    }
                    bundle.putString("param", gson.toJson(discoverModelItem.getParam()));
                } else {
                    if (1 == discoverModelItem.getForm()) {
                        bundle.putString("url", discoverModelItem.getAndroidInfo());
                        bundle.putString("param", gson.toJson(discoverModelItem.getParam()));
                        bundle.putInt("type", 1);
                    } else if (2 == discoverModelItem.getForm()) {//小程序
                        goWxapp(discoverModelItem.getParam());
                        return;
                    }
                }
                pushIntent.putExtras(bundle);
                mContext.startActivity(pushIntent);
            }
        });
    }

    private void goWxapp(JsonObject param) {
        if (param != null && !param.isJsonNull()) {
            String userName = param.get("username").getAsString();
            String path = "";
            if (param.has("path")) {
                path = param.get("path").getAsString();
            }
            int type = param.get("type").getAsInt();
            TMShareUtil.getInstance(mContext).goWXApp(userName, path, type, new PlatformActionListener() {

                @Override
                public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                    Log.i("sharemm4", "拉起小程序完成");
                }

                @Override
                public void onError(Platform platform, int i, Throwable throwable) {
                    Log.i("sharemm4", "拉起小程序失败");
                }

                @Override
                public void onCancel(Platform platform, int i) {
                    Log.i("sharemm4", "拉起小程序取消");
                }
            });
        }


    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        int viewId = v.getId();
        if (viewId == R.id.back_rl) {
            finish();
        }
    }

    @Override
    protected void initTheme() {
        super.initTheme();
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

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("wwww", "onDestroy 1");
    }
}
