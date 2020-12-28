package com.tianma.tm_own_find.server;

import com.tenma.ventures.api.callback.RxStringCallback;

/**
 * Created by wcc on 2018/9/11.
 */

public interface TMThreeAjaxModel {
    void getLocation(String output, String location, String key, final RxStringCallback callback);
}
