package com.tianma.tm_own_find.view.discover_new;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tenma.ventures.api.callback.RxStringCallback;
import com.tenma.ventures.bean.utils.TMSharedPUtil;
import com.tenma.ventures.config.TMServerConfig;
import com.tenma.ventures.share.util.TMShareUtil;
import com.tianma.tm_own_find.Adapter.ContentAdapter;
import com.tianma.tm_own_find.Adapter.TopAdapter;
import com.tianma.tm_own_find.R;
import com.tianma.tm_own_find.server.TMLoginedUserAjaxModel;
import com.tianma.tm_own_find.server.impl.TMLoginedUserAjaxModelImpl;
import com.tianma.tm_own_find.utils.StringU;
import com.tianma.tm_own_find.view.FindThreeFragmentActivity;
import com.tianma.tm_own_find.view.FindThreeFragmentAutoFullActivity;
import com.tianma.tm_own_find.view.discover_new.bean.DiscoverBannerItem;
import com.tianma.tm_own_find.view.discover_new.bean.DiscoverModelItem;
import com.tianma.tm_own_find.view.discover_new.bean.DiscoverModelList;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

public class DiscoverPresenter implements DiscoverContract.Presenter {
    private Context context;
    private DiscoverContract.View mView;

    private DiscoverModel discoverModel;
    private TopAdapter topAdapter;
    private ContentAdapter contentAdapter;
    private List<String> bannerImages;
    private DiscoverContract.DiscoverItemClickListener clickListener;
    Gson gson = new Gson();
    private SwipeRefreshLayout mSwipeRefreshLayout;


    public DiscoverPresenter(DiscoverContract.View view, Context context) {
        this.mView = view;
        this.context = context;
        discoverModel = new DiscoverModel();
        topAdapter = new TopAdapter(context);
        contentAdapter = new ContentAdapter(context);
        bannerImages = new ArrayList<>();
        clickListener = new DiscoverContract.DiscoverItemClickListener() {
            @Override
            public void onItemClick(DiscoverModelItem discoverModelItem) {
                Intent pushIntent = new Intent(DiscoverPresenter.this.context, FindThreeFragmentActivity.class);
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
                        pushIntent = new Intent(DiscoverPresenter.this.context, FindThreeFragmentAutoFullActivity.class);
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
                DiscoverPresenter.this.context.startActivity(pushIntent);
            }
        };
    }

    private void goWxapp(JsonObject param) {
        if (param != null && !param.isJsonNull()) {
            String userName = param.get("username").getAsString();
            String path = "";
            if (param.has("path")) {
                path = param.get("path").getAsString();
            }
            int type = param.get("type").getAsInt();
            TMShareUtil.getInstance(DiscoverPresenter.this.context).goWXApp(userName, path, type, new PlatformActionListener() {

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

    private void goApp(JsonObject param) {
        if (param != null && !param.isJsonNull()) {
            String APP_PACKAGE_NAME = param.get("package").getAsString();
            //判断当前手机是否有要跳入的app
            if (isAppInstalled(context, APP_PACKAGE_NAME)) {
                //如果有根据包名跳转
                context.startActivity(context.getPackageManager().getLaunchIntentForPackage(APP_PACKAGE_NAME));
            } else {
                //如果没有，走进入系统商店找到这款APP，提示你去下载这款APP的程序
                goToMarket(context, APP_PACKAGE_NAME);
            }
        }
    }

    //这里是进入应用商店，下载指定APP的方法。
    private void goToMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (Exception e) {
        }
    }

    //这里是判断APP中是否有相应APP的方法
    private boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void getData() {
        TMLoginedUserAjaxModel tmLoginedUserAjaxModel = TMLoginedUserAjaxModelImpl.getInstance(context);
        tmLoginedUserAjaxModel.discoverInfoNew(new RxStringCallback() {
            @Override
            public void onNext(Object tag, String response) {
                Log.d(TAG, "onNext: " + response);
                //TODO，发送到后端的成功回调

                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
                // 后端数据返回异常
                if (null == jsonObject || !jsonObject.has("code")) {
                    Toast.makeText(context, "网络错误", Toast.LENGTH_LONG).show();
                    return;
                }
                int code = jsonObject.get("code").getAsInt();
                if (200 == code) {
                    if (jsonObject.has("data")) {
                        JsonObject dataObj = jsonObject.getAsJsonObject("data");
                        if (null != dataObj) {
                            discoverModel.clear();
                            /*处理banner*/
                            if (dataObj.has("banner_list")) {
                                JsonArray banner_list = dataObj.get("banner_list").getAsJsonArray();
                                for (JsonElement banner : banner_list) {
                                    JsonObject banner_object = banner.getAsJsonObject();
                                    DiscoverBannerItem discoverBannerItem = new DiscoverBannerItem();
                                    discoverBannerItem.setInfo(banner_object);
                                    discoverModel.addBannerItem(discoverBannerItem);
                                }
                            }

                            /*处理置顶*/
                            if (dataObj.has("model_top_list")) {
                                JsonArray model_top_list = dataObj.get("model_top_list").getAsJsonArray();
                                for (JsonElement model_top : model_top_list) {
                                    DiscoverModelItem discoverModelItem = new DiscoverModelItem();
                                    discoverModelItem.setInfo(model_top.getAsJsonObject());
                                    discoverModel.addDiscoverTopModelItem(discoverModelItem);
                                }
                            }

                            /*处理content*/
                            if (dataObj.has("model_list")) {
                                JsonArray model_list = dataObj.get("model_list").getAsJsonArray();
                                for (JsonElement model : model_list) {
                                    JsonObject model_object = model.getAsJsonObject();
                                    JsonArray lists = model_object.get("list").getAsJsonArray();
                                    String model_type_name = model_object.get("model_type_name").getAsString();
                                    int key = model_object.get("key").getAsInt();
                                    int style = model_object.get("style").getAsInt();
                                    DiscoverModelList discoverModelList = new DiscoverModelList();
                                    discoverModelList.setKey(key);
                                    discoverModelList.setStyle(style);
                                    discoverModelList.setModelTyeName(model_type_name);

                                    for (JsonElement list : lists) {
                                        DiscoverModelItem discoverModelItem = new DiscoverModelItem();
                                        discoverModelItem.setInfo(list.getAsJsonObject());
                                        discoverModelList.getDiscoverModelItems().add(discoverModelItem);
                                    }
                                    discoverModel.addDiscoverModelItem(discoverModelList);
                                }
                            }
                        }
                        refresh();
                    }
                } else {
                    Toast.makeText(context, jsonObject.get("msg").getAsString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(Object tag, Throwable e) {
                Toast.makeText(context, "网络错误", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel(Object tag, Throwable e) {

            }
        });
    }

    private void refresh() {
        refreshBanner();
        topAdapter.setContent(discoverModel.getModelTopList());
        contentAdapter.setContent(discoverModel.getModelLists());
        mView.refresh(topAdapter.getItemCount() > 4);
    }

    private void refreshBanner() {
        bannerImages.clear();
        for (DiscoverBannerItem item : discoverModel.getBannerItemList()) {
            bannerImages.add(item.getImageUrl());
        }
        if (bannerImages.size() == 0) {
            List<Integer> images = new ArrayList<>();
            images.add(R.drawable.icon_banner_tianqi);
            mView.getBanner().setImages(images).setOnBannerListener(null).setOnPageChangeListener(null);
            mView.getBanner().start();
            mView.refreshBackground("#FE9402");
            return;
        }
        mView.getBanner().setImages(bannerImages).setDelayTime(5000).setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (discoverModel.getBannerItemList().size() <= position) {
                    return;
                }
                DiscoverBannerItem discoverBannerItem = discoverModel.getBannerItemList().get(position);
                if (discoverBannerItem.getForm() == 0 && TextUtils.isEmpty(discoverBannerItem.getUrl())) {
                    return;
                }
                if (discoverBannerItem.getForm() == 1 && TextUtils.isEmpty(discoverBannerItem.getInfo())) {
                    return;
                }
                Intent pushIntent = new Intent(context, FindThreeFragmentActivity.class);
                Bundle bundle = new Bundle();
                if (discoverBannerItem.getForm() == 0) {
                    bundle.putString("model_name", "");
                    bundle.putInt("type", 2);
                    if (!discoverBannerItem.getUrl().startsWith("http://") && !discoverBannerItem.getUrl().startsWith("https://")) {
                        bundle.putString("url", TMServerConfig.BASE_URL + discoverBannerItem.getUrl());
                    } else {
                        bundle.putString("url", discoverBannerItem.getUrl());
                    }
                    bundle.putString("param", gson.toJson(discoverBannerItem.getParam()));
                } else {
                    bundle.putString("model_name", "");
                    bundle.putInt("type", 1);
                    bundle.putString("url", discoverBannerItem.getAndroidInfo());
                    bundle.putString("param", gson.toJson(discoverBannerItem.getParam()));
                }
                pushIntent.putExtras(bundle);
                context.startActivity(pushIntent);
            }
        }).setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                List<DiscoverBannerItem> itemList = discoverModel.getBannerItemList();
                int startColor, endColor;
                startColor = endColor = Color.parseColor(TMSharedPUtil.getTMThemeColor(context));
                DiscoverBannerItem startBannerItem = itemList.get(position);
                if (!TextUtils.isEmpty(startBannerItem.getColor())) {
                    String color = startBannerItem.getColor();
                    if (!color.startsWith("#")) {
                        color = "#" + color;
                    }
                    startColor = Color.parseColor(color);
                }
                DiscoverBannerItem endBannerItem;
                if (positionOffset > 0) {
                    endBannerItem = itemList.get((position + 1) % itemList.size());
                } else {
                    endBannerItem = itemList.get((position + itemList.size() - 1) % itemList.size());
                }
                if (!TextUtils.isEmpty(endBannerItem.getColor())) {
                    String color = endBannerItem.getColor();
                    if (!color.startsWith("#")) {
                        color = "#" + color;
                    }
                    endColor = Color.parseColor(color);
                }
                ArgbEvaluator argbEvaluator = new ArgbEvaluator();
                int currentColor = (int) (argbEvaluator.evaluate(positionOffset, startColor, endColor));
                mView.refreshBackground(currentColor);
            }

            @Override
            public void onPageSelected(int position) {
                /*if (discoverModel.getBannerItemList().size() <= position || mView == null) {
                    return;
                }
                DiscoverBannerItem discoverBannerItem = discoverModel.getBannerItemList().get(position);
                if (TextUtils.isEmpty(discoverBannerItem.getColor())) {
                    mView.refreshBackground(TMSharedPUtil.getTMThemeColor(context));
                    return;
                }
                mView.refreshBackground(discoverBannerItem.getColor());*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mView.getBanner().start();
    }

    @Override
    public void start() {
        topAdapter.setDiscoverItemClickListener(clickListener);
        contentAdapter.setDiscoverItemClickListener(clickListener);
        mView.setAdapter(topAdapter, contentAdapter);
    }

    @Override
    public void attachView(DiscoverContract.View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    @Override
    public boolean isViewAttached() {
        return mView != null;
    }

    public void setRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        this.mSwipeRefreshLayout = swipeRefreshLayout;
        if (contentAdapter!=null){
            contentAdapter.setSwipeRefreshLayout(swipeRefreshLayout);
        }
    }
}
