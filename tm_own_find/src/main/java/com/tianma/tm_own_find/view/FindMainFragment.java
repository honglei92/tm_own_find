package com.tianma.tm_own_find.view;

import android.content.Context;
import android.content.Intent;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tenma.ventures.api.callback.RxStringCallback;
import com.tenma.ventures.base.TMFragment;
import com.tenma.ventures.bean.TMLocationInfo;
import com.tenma.ventures.bean.utils.TMLocationUtils;
import com.tenma.ventures.bean.utils.TMSharedPUtil;
import com.tenma.ventures.config.TMServerConfig;
import com.tenma.ventures.tools.TMThemeManager;
import com.tenma.ventures.tools.change_activity.TitleChange;
import com.tianma.tm_own_find.Adapter.RecyclerAdapter;
import com.tianma.tm_own_find.Adapter.TabAdapter;
import com.tianma.tm_own_find.Listener.OnItemClickListener;
import com.tianma.tm_own_find.R;
import com.tianma.tm_own_find.config.TMUCConstant;
import com.tianma.tm_own_find.server.TMLoginedUserAjaxModel;
import com.tianma.tm_own_find.server.bean.FindBannerListData;
import com.tianma.tm_own_find.server.bean.FindModelListData;
import com.tianma.tm_own_find.server.bean.FindModelTopListData;
import com.tianma.tm_own_find.server.bean.FindModelTypeNameListData;
import com.tianma.tm_own_find.server.impl.TMLoginedUserAjaxModelImpl;
import com.tianma.tm_own_find.utils.FindUtil;
import com.tianma.tm_own_find.utils.GlideRoundTransform;
import com.tianma.tm_own_find.utils.GlideImageLoader;
import com.tianma.tm_own_find.Listener.OnItemClickListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

;

public class FindMainFragment extends TMFragment implements View.OnClickListener, AbsListView.OnScrollListener, OnItemClickListener {

    private int themeColor;
    private int nightThemeColor;
    private int titleColor;
    private String title;
    private TextView titleTv;
    private RelativeLayout find_main_title;
    private static final String TAG = "FindMainFragment";
    private List<String> bannerImages = new ArrayList<String>();
    private List<FindBannerListData> findBannerList = new ArrayList<FindBannerListData>();
    private List<FindModelTopListData> findModelTopList = new ArrayList<FindModelTopListData>();
    private List<FindModelListData> findModelList = new ArrayList<FindModelListData>();
    private List<FindModelTypeNameListData> model_type_name_list = new ArrayList<FindModelTypeNameListData>();

    private Banner banner;
    private Gson gson = new Gson();
    //private TabLayout tb;
    private RecyclerView tabRv;
    private TabAdapter tabAdapter;
    RecyclerView.LayoutManager tabLayoutManager;

    private View allView;
    private RecyclerView recycler_view;
    private GridLayoutManager layoutManager;
    private RecyclerAdapter mAdapter;
    private GridLayout gridLayout;

    LinearLayout model_top_layout_1;
    LinearLayout model_top_layout_2;
    LinearLayout model_top_layout_3;
    LinearLayout model_top_layout_4;
    LinearLayout model_top_layout_5;
    LinearLayout model_top_layout_6;
    TextView local_city;
    ImageView icon_dingwei;

    LinearLayout find_title;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AppBarLayout appBarLayout;

    //判读是否是recyclerView主动引起的滑动，true- 是，false- 否，由tablayout引起的
    private boolean isRecyclerScroll;
    //记录上一次位置，防止在同一内容块里滑动 重复定位到tablayout
    private int lastPos = 0;
    //用于recyclerView滑动到指定的位置
    private boolean canScroll;
    private int scrollToPosition;
    private String set_default_city = "";


    private ListView lv;
    private ArrayList<String> list;
    private int position;
    private CustomAdapter adapter1;
    int post;

    private LocationHandler locationHandler;
    private TMLocationInfo tmLocationInfo = null;

    public static Fragment newInstance(Bundle bundle) {
        Fragment fragment = new FindMainFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() instanceof TitleChange) {
            ((TitleChange) getActivity()).hideTitle();
        }
        themeColor = Color.parseColor(TMSharedPUtil.getTMThemeColor(getActivity()));
        nightThemeColor = Color.parseColor(TMSharedPUtil.getTMNightThemeColor(getActivity()));
        titleColor = Color.parseColor(TMSharedPUtil.getTMTitleTextColor(getActivity()));
        Bundle bundle = getArguments();
        if (null != bundle) {
            boolean showBackButton = bundle.getBoolean(TMUCConstant.BundleParam.SHOW_BACK_BUTTON);
            if (showBackButton) {
                //backRl.setVisibility(View.VISIBLE);
            }
            title = bundle.getString("title", "生活圈");
        }
        allView = view;
        find_main_title = (RelativeLayout) view.findViewById(R.id.find_main_title);
        find_title = (LinearLayout) view.findViewById(R.id.find_title);
        titleTv = view.findViewById(R.id.title_tv);
        titleTv.setText(title);
        titleTv.setTextColor(titleColor);
        banner = (Banner) view.findViewById(R.id.banner);
        tabRv = (RecyclerView) view.findViewById(R.id.tab_rv);
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        local_city = (TextView) view.findViewById(R.id.local_city);
        icon_dingwei = (ImageView) view.findViewById(R.id.icon_dingwei);

        model_top_layout_1 = (LinearLayout) view.findViewById(R.id.model_top_layout_1);
        model_top_layout_2 = (LinearLayout) view.findViewById(R.id.model_top_layout_2);
        model_top_layout_3 = (LinearLayout) view.findViewById(R.id.model_top_layout_3);
        model_top_layout_4 = (LinearLayout) view.findViewById(R.id.model_top_layout_4);
        model_top_layout_5 = (LinearLayout) view.findViewById(R.id.model_top_layout_5);
        model_top_layout_6 = (LinearLayout) view.findViewById(R.id.model_top_layout_6);

        swipeRefreshLayout = view.findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setProgressViewOffset(true, -20, 100);
        swipeRefreshLayout.setColorSchemeColors(themeColor);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                discoverInfo();
            }
        });
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

        /*根据状态栏高度动态设置高度和pading*/
        int statusHeight = FindUtil.getStatusHeight(getActivity());
        int statusHeightdp = FindUtil.px2dip(getActivity(), (float) statusHeight);
        find_main_title.setPadding(0, statusHeight, 0, 0);
        Log.d(TAG, "onViewCreated:1 " + statusHeight);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) find_main_title.getLayoutParams();
        Log.d(TAG, "onViewCreated: " + statusHeightdp);
        params.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70 + statusHeightdp, getResources().getDisplayMetrics()));
        //  params.height=200;//设置当前控件布局的高度
        find_main_title.setLayoutParams(params);//将设置好的布局参数应用到控件中


        model_top_layout_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = 0;
                int form = findModelTopList.get(num).getForm();
                Intent pushIntent = new Intent(getActivity(), FindThreeFragmentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("model_name", findModelTopList.get(num).getModel_name());
                if (0 == form) {
                    bundle.putInt("type", 2);
                    if (!findModelTopList.get(0).getUrl().startsWith("http://") && !findModelTopList.get(num).getUrl().startsWith("https://")) {
                        bundle.putString("url", TMServerConfig.BASE_URL + findModelTopList.get(num).getUrl());
                    } else {
                        bundle.putString("url", findModelTopList.get(num).getUrl());
                    }
                } else {
                    bundle.putInt("type", 1); //原生
                    bundle.putString("url", findModelTopList.get(num).getAndroid_info());

                }
                pushIntent.putExtras(bundle);
                startActivity(pushIntent);
            }
        });

        model_top_layout_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = 1;
                int form = findModelTopList.get(num).getForm();
                Intent pushIntent = new Intent(getActivity(), FindThreeFragmentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("model_name", findModelTopList.get(num).getModel_name());
                if (0 == form) {
                    bundle.putInt("type", 2);
                    if (!findModelTopList.get(0).getUrl().startsWith("http://") && !findModelTopList.get(num).getUrl().startsWith("https://")) {
                        bundle.putString("url", TMServerConfig.BASE_URL + findModelTopList.get(num).getUrl());
                    } else {
                        bundle.putString("url", findModelTopList.get(num).getUrl());
                    }
                } else {
                    bundle.putInt("type", 1); //原生
                    bundle.putString("url", findModelTopList.get(num).getAndroid_info());
                }
                pushIntent.putExtras(bundle);
                startActivity(pushIntent);
            }
        });

        model_top_layout_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = 2;
                int form = findModelTopList.get(num).getForm();
                Intent pushIntent = new Intent(getActivity(), FindThreeFragmentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("model_name", findModelTopList.get(num).getModel_name());
                if (0 == form) {
                    bundle.putInt("type", 2);
                    if (!findModelTopList.get(0).getUrl().startsWith("http://") && !findModelTopList.get(num).getUrl().startsWith("https://")) {
                        bundle.putString("url", TMServerConfig.BASE_URL + findModelTopList.get(num).getUrl());
                    } else {
                        bundle.putString("url", findModelTopList.get(num).getUrl());
                    }
                } else {
                    bundle.putInt("type", 1); //原生
                    bundle.putString("url", findModelTopList.get(num).getAndroid_info());
                }
                pushIntent.putExtras(bundle);
                startActivity(pushIntent);
            }
        });

        model_top_layout_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = 3;
                int form = findModelTopList.get(num).getForm();
                Intent pushIntent = new Intent(getActivity(), FindThreeFragmentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("model_name", findModelTopList.get(num).getModel_name());
                if (0 == form) {
                    bundle.putInt("type", 2);
                    if (!findModelTopList.get(0).getUrl().startsWith("http://") && !findModelTopList.get(num).getUrl().startsWith("https://")) {
                        bundle.putString("url", TMServerConfig.BASE_URL + findModelTopList.get(num).getUrl());
                    } else {
                        bundle.putString("url", findModelTopList.get(num).getUrl());
                    }
                } else {
                    bundle.putInt("type", 1); //原生
                    bundle.putString("url", findModelTopList.get(num).getAndroid_info());
                }
                pushIntent.putExtras(bundle);
                startActivity(pushIntent);
            }
        });

        model_top_layout_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = 4;
                int form = findModelTopList.get(num).getForm();
                Intent pushIntent = new Intent(getActivity(), FindThreeFragmentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("model_name", findModelTopList.get(num).getModel_name());
                if (0 == form) {
                    bundle.putInt("type", 2);
                    if (!findModelTopList.get(0).getUrl().startsWith("http://") && !findModelTopList.get(num).getUrl().startsWith("https://")) {
                        bundle.putString("url", TMServerConfig.BASE_URL + findModelTopList.get(num).getUrl());
                    } else {
                        bundle.putString("url", findModelTopList.get(num).getUrl());
                    }
                } else {
                    bundle.putInt("type", 1); //原生
                    bundle.putString("url", findModelTopList.get(num).getAndroid_info());
                }
                pushIntent.putExtras(bundle);
                startActivity(pushIntent);
            }
        });

        model_top_layout_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = 5;
                int form = findModelTopList.get(num).getForm();
                Intent pushIntent = new Intent(getActivity(), FindThreeFragmentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("model_name", findModelTopList.get(num).getModel_name());
                if (0 == form) {
                    bundle.putInt("type", 2);
                    if (!findModelTopList.get(0).getUrl().startsWith("http://") && !findModelTopList.get(num).getUrl().startsWith("https://")) {
                        bundle.putString("url", TMServerConfig.BASE_URL + findModelTopList.get(num).getUrl());
                    } else {
                        bundle.putString("url", findModelTopList.get(num).getUrl());
                    }
                } else {
                    bundle.putInt("type", 1); //原生
                    bundle.putString("url", findModelTopList.get(num).getAndroid_info());
                }
                pushIntent.putExtras(bundle);
                startActivity(pushIntent);
            }
        });

        local_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getLocation();
                getLocationNew();
            }
        });

        initTheme();

        discoverInfo();
        //getLocationConfig();
        locationHandler = new LocationHandler(this);
        getLocationNew();
        /*tab栏*/
        /*list = new ArrayList<>();
        for (int i = 0; i <200; i++) {
            list.add("龙雀" + i);
        }
        initView(view);*/

        /*清楚缓存*/
        /*RxView.clicks(clearCacheRl).throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        clearCache();
                    }
                });*/


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
        WeakReference<FindMainFragment> mainFragmentWeakReference;

        LocationHandler(FindMainFragment findMainFragment) {
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
                local_city.setVisibility(View.VISIBLE);
                icon_dingwei.setVisibility(View.VISIBLE);
            } else if (!TextUtils.isEmpty(tmLocationInfo.getDefaultCity().getCity())) {
                local_city.setText(tmLocationInfo.getDefaultCity().getCity());
                local_city.setVisibility(View.VISIBLE);
                icon_dingwei.setVisibility(View.VISIBLE);
            } else {
                local_city.setText("定位失败");
                local_city.setVisibility(View.GONE);
                icon_dingwei.setVisibility(View.GONE);
            }
        }
    }


    /*初始化content*/
    private void initContent() {
        //计算内容块所在的高度，全屏高度-状态栏高度-tablayout的高度(这里固定高度50dp)，用于recyclerView的最后一个item view填充高度
        int screenH = getScreenHeight();
        int statusBarH = getStatusBarHeight(getActivity());
        int tabH = 50 * 3;
        int lastH = screenH - statusBarH - tabH;

        layoutManager = new GridLayoutManager(getActivity(), 4);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int content_type = findModelList.get(position).getContent_type();
                if (content_type == FindModelListData.TYPE.TYPE_TLTLE) {
                    return 4;
                } else if (content_type == FindModelListData.TYPE.TYPE_ITEM) {
                    return 1;
                }
                return 0;
            }
        });
        recycler_view.setLayoutManager(layoutManager);
        //  recycler_view.addItemDecoration(new SpacesItemDecoration(2));

        // 填充数据
        mAdapter = new RecyclerAdapter(getActivity(), findModelList, lastH);
        mAdapter.setOnItemClickListener(this);
        recycler_view.setAdapter(mAdapter);

        recycler_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //当滑动由recyclerView触发时，isRecyclerScroll 置true
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    isRecyclerScroll = true;
                }
                return false;
            }
        });


        recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (canScroll) {
                    canScroll = false;
                    moveToPosition(layoutManager, recyclerView, scrollToPosition);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isRecyclerScroll) {
                    //第一个可见的view的位置，即tablayou需定位的位置
                    int position = layoutManager.findFirstVisibleItemPosition();
                    if (lastPos != position) {
                        int calcPosition = calcTabPosition(position);
                        tabAdapter.setSelectPosition(calcPosition);
                        tabLayoutMove(calcPosition);
                    }
                    lastPos = position;
                }
            }
        });

    }

    private int calcTabPosition(int visibleItemPosition) {
        int j = 0;
        for (int i = 0; i < model_type_name_list.size(); i++) {
            if (visibleItemPosition < model_type_name_list.get(i).getPos()) {
                if (j > 0) {
                    j--;
                }
                return j;
            } else {
                j++;
            }
        }
        return model_type_name_list.size() - 1;
    }

    /*初始化content_tab*/
    private void initTabRv() {
        if (getWidthDp() >= 90 * model_type_name_list.size()) {
            tabLayoutManager = new GridLayoutManager(getActivity(), model_type_name_list.size());
        } else {
            tabLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        }

        tabRv.setLayoutManager(tabLayoutManager);

        tabAdapter = new TabAdapter(getActivity(), model_type_name_list);
        tabAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                isRecyclerScroll = false;
                moveToPosition(layoutManager, recycler_view, model_type_name_list.get(position).getPos());
            }
        });
        tabRv.setAdapter(tabAdapter);
    }

    private int getWidthDp() {
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        return screenWidth;
    }

    private void tabLayoutMove(int position) {
        if (tabLayoutManager instanceof LinearLayoutManager) {
            tabLayoutManager.scrollToPosition(position);
        }
    }

    /*初始化置顶*/
    //private  int top_num_flag = 1;
    public void initTop() {
        LinearLayout model_top_layout;
        ImageView model_top_img;
        TextView model_top_text;
        int top_num_flag = 1;
        //  private List<FindModelTopListData> findModelTopList = new ArrayList<FindModelTopListData>();
        for (FindModelTopListData findModelTopListData : findModelTopList) {

            switch (top_num_flag) {
                case 1:
                    model_top_layout = (LinearLayout) allView.findViewById(R.id.model_top_layout_1);
                    model_top_img = (ImageView) allView.findViewById(R.id.model_top_img_1);
                    model_top_text = (TextView) allView.findViewById(R.id.model_top_text_1);
                    model_top_layout.setVisibility(View.VISIBLE);
                    if (findModelTopListData.getImage_url().indexOf("http") != -1) {
                        Glide.with(getActivity()).load(findModelTopListData.getImage_url()).into(model_top_img);
                    } else {
                        Glide.with(getActivity()).load(TMServerConfig.BASE_URL + findModelTopListData.getImage_url()).into(model_top_img);
                    }
                    model_top_text.setText(findModelTopListData.getModel_name());
                    break;
                case 2:
                    model_top_layout = (LinearLayout) allView.findViewById(R.id.model_top_layout_2);
                    model_top_img = (ImageView) allView.findViewById(R.id.model_top_img_2);
                    model_top_text = (TextView) allView.findViewById(R.id.model_top_text_2);
                    model_top_layout.setVisibility(View.VISIBLE);
                    if (findModelTopListData.getImage_url().indexOf("http") != -1) {
                        Glide.with(getActivity()).load(findModelTopListData.getImage_url()).into(model_top_img);
                    } else {
                        Glide.with(getActivity()).load(TMServerConfig.BASE_URL + findModelTopListData.getImage_url()).into(model_top_img);
                    }
                    model_top_text.setText(findModelTopListData.getModel_name());
                    break;
                case 3:
                    model_top_layout = (LinearLayout) allView.findViewById(R.id.model_top_layout_3);
                    model_top_img = (ImageView) allView.findViewById(R.id.model_top_img_3);
                    model_top_text = (TextView) allView.findViewById(R.id.model_top_text_3);
                    model_top_layout.setVisibility(View.VISIBLE);
                    if (findModelTopListData.getImage_url().indexOf("http") != -1) {
                        Glide.with(getActivity()).load(findModelTopListData.getImage_url()).into(model_top_img);
                    } else {
                        Glide.with(getActivity()).load(TMServerConfig.BASE_URL + findModelTopListData.getImage_url()).into(model_top_img);
                    }
                    model_top_text.setText(findModelTopListData.getModel_name());
                    break;
                case 4:
                    model_top_layout = (LinearLayout) allView.findViewById(R.id.model_top_layout_4);
                    model_top_img = (ImageView) allView.findViewById(R.id.model_top_img_4);
                    model_top_text = (TextView) allView.findViewById(R.id.model_top_text_4);
                    model_top_layout.setVisibility(View.VISIBLE);
                    if (findModelTopListData.getImage_url().indexOf("http") != -1) {
                        Glide.with(getActivity()).load(findModelTopListData.getImage_url()).into(model_top_img);
                    } else {
                        Glide.with(getActivity()).load(TMServerConfig.BASE_URL + findModelTopListData.getImage_url()).into(model_top_img);
                    }
                    model_top_text.setText(findModelTopListData.getModel_name());
                    break;
                case 5:
                    model_top_layout = (LinearLayout) allView.findViewById(R.id.model_top_layout_5);
                    model_top_img = (ImageView) allView.findViewById(R.id.model_top_img_5);
                    model_top_text = (TextView) allView.findViewById(R.id.model_top_text_5);
                    model_top_layout.setVisibility(View.VISIBLE);
                    if (findModelTopListData.getImage_url().indexOf("http") != -1) {
                        Glide.with(getActivity()).load(findModelTopListData.getImage_url()).into(model_top_img);
                    } else {
                        Glide.with(getActivity()).load(TMServerConfig.BASE_URL + findModelTopListData.getImage_url()).into(model_top_img);
                    }
                    model_top_text.setText(findModelTopListData.getModel_name());
                    break;
                case 6:
                    model_top_layout = (LinearLayout) allView.findViewById(R.id.model_top_layout_6);
                    model_top_img = (ImageView) allView.findViewById(R.id.model_top_img_6);
                    model_top_text = (TextView) allView.findViewById(R.id.model_top_text_6);
                    model_top_layout.setVisibility(View.VISIBLE);
                    if (findModelTopListData.getImage_url().indexOf("http") != -1) {
                        Glide.with(getActivity()).load(findModelTopListData.getImage_url()).into(model_top_img);
                    } else {
                        Glide.with(getActivity()).load(TMServerConfig.BASE_URL + findModelTopListData.getImage_url()).into(model_top_img);
                    }
                    model_top_text.setText(findModelTopListData.getModel_name());
                    break;
            }

            /*if(top_num_flag<7){
                initTopHandle(findModelTopListData,top_num_flag-1);
            }*/
            top_num_flag++;
        }
    }


    @Override
    public void OnItemClick(int position) {
        //String title = mList.get(position).title;
        // Toast.makeText(this, title, Toast.LENGTH_SHORT).show();

        Log.d(TAG, "OnItemClick: " + position + findModelList.get(position).getContent_type());
        if (FindModelListData.TYPE.TYPE_ITEM == findModelList.get(position).getContent_type()) {
            int form = findModelList.get(position).getForm();

            Intent pushIntent = new Intent(getActivity(), FindThreeFragmentActivity.class);
            Bundle bundle = new Bundle();
            String loadUrl = findModelList.get(position).getUrl();
            bundle.putString("model_name", findModelList.get(position).getModel_name());
            if (0 == form) {
                bundle.putInt("type", 2);
                if (!loadUrl.startsWith("http://") && !loadUrl.startsWith("https://")) {
                    bundle.putString("url", TMServerConfig.BASE_URL + loadUrl);
                } else {
                    bundle.putString("url", loadUrl);
                }
            } else {
                bundle.putString("url", findModelList.get(position).getAndroid_info());
                bundle.putInt("type", 1);
            }
            pushIntent.putExtras(bundle);
            startActivity(pushIntent);
        }

    }


    public void moveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int position) {
        // 第一个可见的view的位置
        int firstItem = manager.findFirstVisibleItemPosition();
        // 最后一个可见的view的位置
        int lastItem = manager.findLastVisibleItemPosition();
        if (position <= firstItem) {
            // 如果跳转位置firstItem 之前(滑出屏幕的情况)，就smoothScrollToPosition可以直接跳转，
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 跳转位置在firstItem 之后，lastItem 之间（显示在当前屏幕），smoothScrollBy来滑动到指定位置
            int top = mRecyclerView.getChildAt(position - firstItem).getTop();
            mRecyclerView.smoothScrollBy(0, top);
        } else {
            // 如果要跳转的位置在lastItem 之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用当前moveToPosition方法，执行上一个判断中的方法
            mRecyclerView.smoothScrollToPosition(position);
            scrollToPosition = position;
            canScroll = true;
        }
    }

    private int getScreenHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /*发件内容整合接口*/
    private void discoverInfo() {
        //   Toast.makeText(getActivity(), "数据加载中...", Toast.LENGTH_SHORT).show();
        TMLoginedUserAjaxModel tMUserAjaxModel = TMLoginedUserAjaxModelImpl.getInstance(getActivity());
        tMUserAjaxModel.discoverInfo(new RxStringCallback() {
            @Override
            public void onNext(Object tag, String response) {

                Log.d(TAG, "onNext: " + response);
                //TODO，发送到后端的成功回调

                Gson gson = new Gson();
                JsonObject thirdLoginAll = gson.fromJson(response, JsonObject.class);
                // 后端数据返回异常
                if (null == thirdLoginAll || !thirdLoginAll.has("code")) {
                    Toast.makeText(getActivity(), "网骆错误", Toast.LENGTH_LONG).show();
                    return;
                }
                int code = thirdLoginAll.get("code").getAsInt();
                if (200 == code) {
                    if (thirdLoginAll.has("data")) {
                        JsonObject dataObj = thirdLoginAll.getAsJsonObject("data");

                        if (null != dataObj) {

                            /*处理banner*/
                            if (dataObj.has("banner_list")) {
                                JsonArray banner_list = dataObj.get("banner_list").getAsJsonArray();
                                bannerImages.clear();
                                findBannerList.clear();
                                for (JsonElement banner : banner_list) {
                                    JsonObject bannert_object = banner.getAsJsonObject();
                                    bannerImages.add(bannert_object.get("image_url").getAsString());
                                    FindBannerListData findBannerListData = gson.fromJson(banner, FindBannerListData.class);
                                    findBannerList.add(findBannerListData);
                                }

                                //   banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
                                //设置图片加载器
                                banner.setImageLoader(new GlideImageLoader());
                                //设置图片集合
                                banner.setImages(bannerImages);
                                //banner设置方法全部调用完毕时最后调用

                                banner.setOnBannerListener(new OnBannerListener() {
                                    @Override
                                    public void OnBannerClick(int position) {
                                        if (findBannerList.size() <= position) {
                                            return;
                                        }
                                        FindBannerListData findBannerListData = findBannerList.get(position);
                                        if (findBannerListData.getForm() == 0 && TextUtils.isEmpty(findBannerListData.getUrl())) {
                                            return;
                                        }
                                        if (findBannerListData.getForm() == 1 && TextUtils.isEmpty(findBannerListData.getAndroid_info())) {
                                            return;
                                        }
                                        Intent pushIntent = new Intent(getActivity(), FindThreeFragmentActivity.class);
                                        Bundle bundle = new Bundle();
                                        if (findBannerListData.getForm() == 0) {
                                            bundle.putString("model_name", "");
                                            bundle.putInt("type", 2);
                                            if (!findBannerListData.getUrl().startsWith("http://") && !findBannerListData.getUrl().startsWith("https://")) {
                                                bundle.putString("url", TMServerConfig.BASE_URL + findBannerListData.getUrl());
                                            } else {
                                                bundle.putString("url", findBannerListData.getUrl());
                                            }
                                        } else {
                                            bundle.putString("model_name", "");
                                            bundle.putInt("type", 1);
                                            bundle.putString("url", findBannerListData.getAndroid_info());
                                        }
                                        pushIntent.putExtras(bundle);
                                        startActivity(pushIntent);

                                        Log.d(TAG, "OnBannerClick: " + position + findBannerList.get(position).getUrl());
                                    }
                                });
                                banner.start();
                            }

                            /*处理置顶*/
                            if (dataObj.has("model_top_list")) {
                                JsonArray model_top_list = dataObj.get("model_top_list").getAsJsonArray();
                                findModelTopList.clear();
                                for (JsonElement model_top : model_top_list) {
                                    FindModelTopListData findModelTopListData = gson.fromJson(model_top, FindModelTopListData.class);
                                    findModelTopList.add(findModelTopListData);
                                }
                            }

                            /*处理content*/
                            if (dataObj.has("model_list")) {
                                JsonArray model_list = dataObj.get("model_list").getAsJsonArray();
                                findModelList.clear();
                                model_type_name_list.clear();
                                int pos_flag = 0;
                                for (JsonElement model : model_list) {
                                    JsonObject model_object = model.getAsJsonObject();
                                    JsonArray lists = model_object.get("list").getAsJsonArray();
                                    String model_type_name = model_object.get("model_type_name").getAsString();
                                    Log.d(TAG, "onNext: " + lists.size());
                                    if (lists.size() > 0) {
                                        FindModelTypeNameListData findModelTypeNameListData = new FindModelTypeNameListData();
                                        findModelTypeNameListData.setType_name(model_type_name);
                                        findModelTypeNameListData.setPos(pos_flag);
                                        model_type_name_list.add(findModelTypeNameListData);
                                        FindModelListData findModelListData = new FindModelListData();
                                        findModelListData.setContent_type(1);
                                        findModelListData.setModel_type_name(model_type_name);
                                        findModelList.add(findModelListData);
                                        pos_flag++;
                                        for (JsonElement list : lists) {
                                            FindModelListData findModelListData1 = gson.fromJson(list, FindModelListData.class);
                                            findModelListData1.setContent_type(2);
                                            findModelList.add(findModelListData1);
                                            pos_flag++;
                                        }
                                    }
                                }
                            }

                            initTop();
                            initTabRv(); //tab初始化
                            initContent();

                            if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }
                    }
                } else if (501 == code) {
                    Toast.makeText(getActivity(), "用户信息过期，请重新登录", Toast.LENGTH_LONG).show();
                    TMSharedPUtil.clearTMUser(getActivity());
                    Intent intent = new Intent(getActivity().getPackageName() + ".usercenter.login");
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), thirdLoginAll.get("msg").getAsString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(Object tag, Throwable e) {
                Toast.makeText(getActivity(), "网骆错误", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel(Object tag, Throwable e) {

            }
        });
    }


    private void initView(View view) {
        lv = (ListView) view.findViewById(R.id.lv);
        adapter1 = new CustomAdapter(list, getActivity());
        lv.setAdapter(adapter1);
        lv.setOnScrollListener(this);
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case 0:
                switch (post) {
                    /*case 0:

                        TabLayout.Tab tab = tb.getTabAt(0);
                        tab.select();
                        break;

                    case 50:
                        TabLayout.Tab tab2 = tb.getTabAt(1);
                        tab2.select();

                        break;
                    case 70:
                        TabLayout.Tab tab3 = tb.getTabAt(2);
                        tab3.select();

                        break;*/


                }
                Toast.makeText(getActivity(), "我现在处于不滑动状态", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(getActivity(), "我现在处于滑动状态", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        switch (firstVisibleItem) {

            case 0:
                post = 0;
                break;

            case 50:
                post = 50;
                break;
            case 70:
                post = 70;
                break;
            case 49:
                post = 0;
                break;

            case 69:
                post = 50;
                break;

        }
    }

    /**
     * 跳转到爆料
     */
   /* private void goToPcUserThreeFragmentActivity() {
        startActivity(new Intent(getActivity(), PcUserThreeFragmentActivity.class));
    }*/
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int viewId = v.getId();
      /*  if (viewId == R.id.weichatThirhLogin) {  //微信登录
            thirdTMLoginUtil(Wechat.NAME);
        }else if(viewId == R.id.qqThirhLogin){   //QQ登录
            thirdTMLoginUtil(QQ.NAME);
        }else if(viewId == R.id.weiboThirhLogin){   //新浪微博登录
            thirdTMLoginUtil(SinaWeibo.NAME);
        }*/
    }


    @Override
    protected void initTheme() {
        Log.d(TAG, "initTheme: ");
        super.initTheme();
        find_title.setBackgroundColor(TMThemeManager.getThemeMode() == TMThemeManager.ThemeMode.DAY ? themeColor : getResources().getColor(R.color.find_base_header_bg_color_night));
        find_main_title.setBackgroundColor(TMThemeManager.getThemeMode() == TMThemeManager.ThemeMode.DAY ? themeColor : getResources().getColor(R.color.find_base_header_bg_color_night));
    }

    public class CustomAdapter extends BaseAdapter {
        private ArrayList<String> list = new ArrayList<>();
        private Context context;

        public CustomAdapter(ArrayList<String> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.find_main_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv.setText(list.get(position));
            return convertView;
        }

        public class ViewHolder {
            public View rootView;
            public TextView tv;

            public ViewHolder(View rootView) {
                this.rootView = rootView;
                this.tv = (TextView) rootView.findViewById(R.id.tv);
            }
        }
    }

}
