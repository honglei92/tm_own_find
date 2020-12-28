package com.tianma.tm_own_find.server.impl;

import android.content.Context;

import com.tenma.ventures.api.RxSubscriber;
import com.tenma.ventures.api.TMApiManager;
import com.tenma.ventures.api.callback.RxStringCallback;
import com.tenma.ventures.api.interceptor.LogInterceptor;
import com.tenma.ventures.api.utils.Utils;
import com.tenma.ventures.bean.utils.TMSharedPUtil;
import com.tenma.ventures.config.TMConstant;
import com.tenma.ventures.config.TMServerConfig;
import com.tianma.tm_own_find.config.TMUrlConfig;
import com.tianma.tm_own_find.server.TMThreeAjaxModel;
import com.tianma.tm_own_find.server.apiserver.TMUserAcAPIService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wcc on 2018/9/11.
 */

public class TMThreeAjaxModelImpl  implements TMThreeAjaxModel {

    static Context mContext;

    static TMThreeAjaxModelImpl instance;

    static TMApiManager tmApiManager;

    public static TMThreeAjaxModelImpl getInstance(Context context,String domain) {
        mContext = context;
        if (null == instance) {
            instance = new TMThreeAjaxModelImpl();
            tmApiManager = new TMApiManager
                    .Builder(mContext)
                    .baseUrl(domain)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
//                    .addConverterFactory(JacksonConverterFactory.create())
//                    .addConverterFactory(FastJsonConverterFactory.create())
                    .addInterceptor(new LogInterceptor())
                    .addCache(false)
                    .build();
        }
        return instance;
    }


    @Override
    public void getLocation(String output,String location,String key, RxStringCallback callback) {
        TMUserAcAPIService tmCommentAPIService = tmApiManager
                .create(TMUserAcAPIService.class);

        String tag = TMUrlConfig.GET_LOCATION;

        tmApiManager.call(tmCommentAPIService.getLocation(output,location,key), new RxSubscriber<>(tag, callback));
    }

}
