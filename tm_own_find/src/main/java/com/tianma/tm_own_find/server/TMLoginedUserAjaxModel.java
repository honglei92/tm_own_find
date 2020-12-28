package com.tianma.tm_own_find.server;

import com.tenma.ventures.api.callback.RxStringCallback;

/**
 * Created by wcc on 2018/8/8.
 */

public interface TMLoginedUserAjaxModel {
    void getConfig(String commentJson, final RxStringCallback callback);
    void thirdLogin(String commentJson, final RxStringCallback callback);
    void bindOtherLoginInfo(String commentJson, final RxStringCallback callback);
    void getMemberInfoCommon(String commentJson, final RxStringCallback callback);
    void cancelBindInfo(String commentJson, final RxStringCallback callback);
    void addOpinionInfo(String commentJson, final RxStringCallback callback);
    void getStarList(int index, String member_code, int page_size, int type, final RxStringCallback callback);
    void getFootprintList(int index, String member_code, int page_size, int get_time_list, int returnType, final RxStringCallback callback);
    void changeMobile(String commentJson, final RxStringCallback callback);
    void getPrivacyArticle(final RxStringCallback callback);
    void messageList(int page, int page_size, final RxStringCallback callback);
    void getAboutUsArticle(final RxStringCallback callback);
    void discoverInfo(final RxStringCallback callback);
    void getLocationConfig(final RxStringCallback callback);
    void discoverInfoNew(final RxStringCallback callback);
}

