package com.tianma.tm_own_find.view.discover_new;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tenma.ventures.base.TMFragment;
import com.tenma.ventures.bean.TMLocationInfo;
import com.tenma.ventures.bean.utils.TMLocationUtils;
import com.tenma.ventures.bean.utils.TMSharedPUtil;
import com.tianma.tm_own_find.Adapter.ContentAdapter;
import com.tianma.tm_own_find.Adapter.TopAdapter;
import com.tianma.tm_own_find.R;
import com.tianma.tm_own_find.utils.FindUtil;
import com.tianma.tm_own_find.utils.GlideRoundTransform;
import com.tianma.tm_own_find.utils.GlideImageLoader;
import com.youth.banner.Banner;

import java.lang.ref.WeakReference;

public class DiscoverFragment extends TMFragment implements DiscoverContract.View {

    private static final String TAG = "DiscoverFragment";

    private DiscoverPresenter presenter;
    private Banner banner;

    private RecyclerView topRv;

    private RecyclerView contentRv;

    TextView local_city;
    ImageView local_icon;

    private LinearLayout findTitle;
    private View frameBackground;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AppBarLayout appBarLayout;


    private DiscoverFragment.LocationHandler locationHandler;
    private TMLocationInfo tmLocationInfo = null;

    public static Fragment newInstance(Bundle bundle) {
        Fragment fragment = new DiscoverFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_main_new, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new DiscoverPresenter(this, getActivity());

        findTitle = view.findViewById(R.id.find_title);
        frameBackground = view.findViewById(R.id.frame_background);

        /*根据状态栏高度动态设置高度和pading*/
        int statusHeight = FindUtil.getStatusHeight(getActivity());
        findTitle.setPadding(0, statusHeight, 0, 0);

        local_city = view.findViewById(R.id.local_city);
        local_icon = view.findViewById(R.id.local_icon);
        local_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocationNew();
            }
        });
        if (TMSharedPUtil.getTMTitleBarColor(getActivity()) != null) {//背景图片不为空  设置主题色
            local_city.setTextColor(Color.parseColor(TMSharedPUtil.getTMTitleTextColor(getActivity())));//设置主题色
            local_icon.setColorFilter(Color.parseColor(TMSharedPUtil.getTMTitleTextColor(getActivity())));//设置主题色
        } else {
            local_icon.setColorFilter(Color.parseColor(TMSharedPUtil.getTMTitleTextColor(getActivity())));//设置标题栏文字颜色
            local_city.setTextColor(Color.parseColor(TMSharedPUtil.getTMTitleTextColor(getActivity())));//设置标题栏文字颜色
        }

        banner = view.findViewById(R.id.banner);
        banner.setImageLoader(new GlideImageLoader());

        topRv = view.findViewById(R.id.top_rv);
        contentRv = view.findViewById(R.id.content_rv);

        swipeRefreshLayout = view.findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setProgressViewOffset(true, -20, 100);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor(TMSharedPUtil.getTMThemeColor(getActivity())));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getData();
            }
        });
        presenter.setRefreshLayout(swipeRefreshLayout);
        appBarLayout = view.findViewById(R.id.app_bar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    swipeRefreshLayout.setEnabled(true);
                } else {
                    swipeRefreshLayout.setEnabled(false);
                }
            }
        });

        initTheme();

        locationHandler = new DiscoverFragment.LocationHandler(this);
        getLocationNew();
        presenter.start();
        presenter.getData();
    }

    private void getLocationNew() {
        local_city.setText("定位中...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                tmLocationInfo = TMLocationUtils.getLocation(getActivity());
                Message message = new Message();
                message.what = 1;
                locationHandler.sendMessage(message);
            }
        }).start();
    }

    private static class LocationHandler extends Handler {
        WeakReference<DiscoverFragment> mainFragmentWeakReference;

        LocationHandler(DiscoverFragment findMainFragment) {
            mainFragmentWeakReference = new WeakReference<>(findMainFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (mainFragmentWeakReference.get() != null) {
                    mainFragmentWeakReference.get().refreshLocation();
                }
            }
        }
    }

    private void refreshLocation() {
        if (tmLocationInfo != null) {
            if (tmLocationInfo.isAutoLocation() && tmLocationInfo.getErrorCode() == TMLocationInfo.SUCCESS) {
                local_city.setText(tmLocationInfo.getAddressComponent().getCity());
            } else if (!TextUtils.isEmpty(tmLocationInfo.getDefaultCity().getCity())) {
                local_city.setText(tmLocationInfo.getDefaultCity().getCity());
            } else {
                local_city.setText("定位失败");
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    protected void initTheme() {
        Log.d(TAG, "initTheme: ");
        super.initTheme();
    }

    @Override
    public void refresh(boolean topMaxLine) {
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        try {
            int height = 0;
            if (topMaxLine) {
                height = FindUtil.dip2px(getActivity(), 230);
            } else {
                WindowManager wm = getActivity().getWindowManager();
                height = wm.getDefaultDisplay().getHeight() / 3 - FindUtil.dip2px(getActivity(), 68);
            }
            ViewGroup.LayoutParams layoutParams = frameBackground.getLayoutParams();
            layoutParams.height = height;
            frameBackground.setLayoutParams(layoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refreshBackground(String colorString) {
        if (!colorString.startsWith("#")) {
            colorString = "#" + colorString;
        }
        int color = Color.parseColor(colorString);
        findTitle.setBackgroundColor(color);
        frameBackground.setBackgroundColor(color);
    }

    @Override
    public void refreshBackground(int color) {
        findTitle.setBackgroundColor(color);
        frameBackground.setBackgroundColor(color);
    }

    @Override
    public Banner getBanner() {
        return banner;
    }

    public void setAdapter(TopAdapter topAdapter, final ContentAdapter contentAdapter) {
        topRv.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        topRv.setAdapter(topAdapter);
        contentRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        contentRv.setAdapter(contentAdapter);
        //contentRv.addItemDecoration(new ContentDecoration());
    }
}
