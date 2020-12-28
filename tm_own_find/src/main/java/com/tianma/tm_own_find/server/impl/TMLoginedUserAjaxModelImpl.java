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
import com.tenma.ventures.inf.SSLHelper;
import com.tenma.ventures.tools.encrypt.TMEncryptBean;
import com.tianma.tm_own_find.config.TMUrlConfig;
import com.tianma.tm_own_find.server.TMLoginedUserAjaxModel;
import com.tianma.tm_own_find.server.apiserver.TMUserAcAPIService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wcc on 2018/8/8.
 */

public class TMLoginedUserAjaxModelImpl implements TMLoginedUserAjaxModel {
    static TMLoginedUserAjaxModelImpl instance;

    static TMApiManager tmApiManager;

    public static TMLoginedUserAjaxModelImpl getInstance(Context context) {
        //Map<String, String> headers = new HashMap<>();
        //headers.put(TMConstant.ServerHeader.SH_TOKEN, TMSharedPUtil.getTMToken(context));
        if (null == instance) {
            instance = new TMLoginedUserAjaxModelImpl();
        }
        tmApiManager = new TMApiManager
                .Builder(context)
                .baseUrl(TMServerConfig.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
//                    .addConverterFactory(JacksonConverterFactory.create())
//                    .addConverterFactory(FastJsonConverterFactory.create())
                .addInterceptor(new LogInterceptor())
                .addCache(false)
                //.addHeader(headers)
                .addSSLSocketFactory(SSLHelper.getSSLSocketFactory(context.getApplicationContext()))
                .addHeader(new TMEncryptBean().getEncryptHeaderNew())
                .build();
        return instance;
    }


    @Override
    public void getConfig(String commentJson, RxStringCallback callback) {
        TMUserAcAPIService tmCommentAPIService = tmApiManager
                .create(TMUserAcAPIService.class);

        String tag = TMUrlConfig.APP_GET_CONFIG;

        tmApiManager.call(tmCommentAPIService.getConfig(Utils.createJson(commentJson)), new RxSubscriber<>(tag, callback));
    }

    @Override
    public void thirdLogin(String commentJson, RxStringCallback callback) {
        TMUserAcAPIService tmCommentAPIService = tmApiManager
                .create(TMUserAcAPIService.class);

        String tag = TMUrlConfig.THIRD_LOGIN;

        tmApiManager.call(tmCommentAPIService.thirdLogin(Utils.createJson(commentJson)), new RxSubscriber<>(tag, callback));
    }

    @Override
    public void bindOtherLoginInfo(String commentJson, RxStringCallback callback) {
        TMUserAcAPIService tmCommentAPIService = tmApiManager
                .create(TMUserAcAPIService.class);

        String tag = TMUrlConfig.BIND_OTHER_LOGIN_INFO;

        tmApiManager.call(tmCommentAPIService.bindOtherLoginInfo(Utils.createJson(commentJson)), new RxSubscriber<>(tag, callback));
    }

    @Override
    public void getMemberInfoCommon(String commentJson, RxStringCallback callback) {
        TMUserAcAPIService tmCommentAPIService = tmApiManager
                .create(TMUserAcAPIService.class);

        String tag = TMUrlConfig.GET_MEMBER_INFO;

        tmApiManager.call(tmCommentAPIService.getMemberInfoCommon(commentJson), new RxSubscriber<>(tag, callback));
    }

    @Override
    public void cancelBindInfo(String commentJson, RxStringCallback callback) {
        TMUserAcAPIService tmCommentAPIService = tmApiManager
                .create(TMUserAcAPIService.class);

        String tag = TMUrlConfig.CANCEL_BIND_INFO;

        tmApiManager.call(tmCommentAPIService.cancelBindInfo(Utils.createJson(commentJson)), new RxSubscriber<>(tag, callback));
    }

    @Override
    public void addOpinionInfo(String commentJson, RxStringCallback callback) {
        TMUserAcAPIService tmCommentAPIService = tmApiManager
                .create(TMUserAcAPIService.class);

        String tag = TMUrlConfig.ADD_OPINION_INFO;

        tmApiManager.call(tmCommentAPIService.addOpinionInfo(Utils.createJson(commentJson)), new RxSubscriber<>(tag, callback));
    }

    @Override
    public void getStarList(int index, String member_code, int page_size, int type, RxStringCallback callback) {
        TMUserAcAPIService tmCommentAPIService = tmApiManager
                .create(TMUserAcAPIService.class);

        String tag = TMUrlConfig.GET_STAR_LIST;

        tmApiManager.call(tmCommentAPIService.getStarList(index, member_code, page_size, type), new RxSubscriber<>(tag, callback));
    }

    @Override
    public void getFootprintList(int index, String member_code, int page_size, int get_time_list, int returnType, RxStringCallback callback) {
        TMUserAcAPIService tmCommentAPIService = tmApiManager
                .create(TMUserAcAPIService.class);

        String tag = TMUrlConfig.GET_FOOTPR_INT_LIST;

        tmApiManager.call(tmCommentAPIService.getFootprintList(index, member_code, page_size, get_time_list, returnType), new RxSubscriber<>(tag, callback));
    }

    @Override
    public void changeMobile(String commentJson, RxStringCallback callback) {
        TMUserAcAPIService tmCommentAPIService = tmApiManager
                .create(TMUserAcAPIService.class);

        String tag = TMUrlConfig.CHANGE_MOBILE;

        tmApiManager.call(tmCommentAPIService.changeMobile(Utils.createJson(commentJson)), new RxSubscriber<>(tag, callback));
    }

    public void getPrivacyArticle(RxStringCallback callback) {
        TMUserAcAPIService tmCommentAPIService = tmApiManager
                .create(TMUserAcAPIService.class);

        String tag = TMUrlConfig.GET_PRIVACY_ARTICLE;

        tmApiManager.call(tmCommentAPIService.getPrivacyArticle(), new RxSubscriber<>(tag, callback));
    }

    @Override
    public void messageList(int page, int page_size, RxStringCallback callback) {
        TMUserAcAPIService tmCommentAPIService = tmApiManager
                .create(TMUserAcAPIService.class);

        String tag = TMUrlConfig.MESSAGE_LIST;

        tmApiManager.call(tmCommentAPIService.messageList(page, page_size), new RxSubscriber<>(tag, callback));
    }

    @Override
    public void getAboutUsArticle(RxStringCallback callback) {
        TMUserAcAPIService tmCommentAPIService = tmApiManager
                .create(TMUserAcAPIService.class);

        String tag = TMUrlConfig.GET_ABOUT_US_ARTICLE;

        tmApiManager.call(tmCommentAPIService.getAboutUsArticle(), new RxSubscriber<>(tag, callback));
    }

    @Override
    public void discoverInfo(RxStringCallback callback) {
        TMUserAcAPIService tmCommentAPIService = tmApiManager
                .create(TMUserAcAPIService.class);

        String tag = TMUrlConfig.DISCOVER_INFO;

        tmApiManager.call(tmCommentAPIService.discoverInfo(), new RxSubscriber<>(tag, callback));
    }

    @Override
    public void getLocationConfig(RxStringCallback callback) {
        TMUserAcAPIService tmCommentAPIService = tmApiManager
                .create(TMUserAcAPIService.class);

        String tag = TMUrlConfig.GET_LOCATION_CONFIG;

        tmApiManager.call(tmCommentAPIService.getLocationConfig(), new RxSubscriber<>(tag, callback));
    }

    @Override
    public void discoverInfoNew(RxStringCallback callback) {
        TMUserAcAPIService tmCommentAPIService = tmApiManager
                .create(TMUserAcAPIService.class);

        String tag = TMUrlConfig.DISCOVER_INFO_NEW;

        tmApiManager.call(tmCommentAPIService.discoverInfoNew(), new RxSubscriber<>(tag, callback));
    }
}
