package com.tianma.tm_own_find.server.apiserver;

import com.tianma.tm_own_find.config.TMUrlConfig;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by wcc on 2018/8/8.
 */

public interface TMUserAcAPIService {
    @POST(TMUrlConfig.APP_GET_CONFIG)
    Observable<ResponseBody> getConfig(@Body RequestBody requestBody);

    @POST(TMUrlConfig.THIRD_LOGIN)
    Observable<ResponseBody> thirdLogin(@Body RequestBody requestBody);

    @POST(TMUrlConfig.BIND_OTHER_LOGIN_INFO)
    Observable<ResponseBody> bindOtherLoginInfo(@Body RequestBody requestBody);

    /*@GET(TMUrlConfig.GET_MEMBER_INFO)
    Observable<ResponseBody> getMemberInfo(@Query("member_code") String member_code);*/

    @GET(TMUrlConfig.GET_MEMBER_INFO)
    Observable<ResponseBody> getMemberInfoCommon(@Query("member_code") String member_code);

    @POST(TMUrlConfig.CANCEL_BIND_INFO)
    Observable<ResponseBody> cancelBindInfo(@Body RequestBody requestBody);

    @GET(TMUrlConfig.GET_RELIEF_ARTICLE)
    Observable<ResponseBody> getReliefArticle();

    @POST(TMUrlConfig.ADD_OPINION_INFO)
    Observable<ResponseBody> addOpinionInfo(@Body RequestBody requestBody);

    @GET(TMUrlConfig.GET_STAR_LIST)
    Observable<ResponseBody> getStarList(@Query("index") int index, @Query("member_code") String member_code, @Query("page_size") int page_size, @Query("type") int type);

    @GET(TMUrlConfig.GET_FOOTPR_INT_LIST)
    Observable<ResponseBody> getFootprintList(@Query("index") int index, @Query("member_code") String member_code, @Query("page_size") int page_size,
                                              @Query("get_time_list") int get_time_list, @Query("returnType") int returnType);

    @POST(TMUrlConfig.CHANGE_MOBILE)
    Observable<ResponseBody> changeMobile(@Body RequestBody requestBody);

    @GET(TMUrlConfig.GET_PRIVACY_ARTICLE)
    Observable<ResponseBody> getPrivacyArticle();

    @GET(TMUrlConfig.MESSAGE_LIST)
    Observable<ResponseBody> messageList(@Query("page") int page, @Query("page_size") int page_size);

    @GET(TMUrlConfig.GET_ABOUT_US_ARTICLE)
    Observable<ResponseBody> getAboutUsArticle();

    @GET(TMUrlConfig.DISCOVER_INFO)
    Observable<ResponseBody> discoverInfo();

    @GET(TMUrlConfig.GET_LOCATION_CONFIG)
    Observable<ResponseBody> getLocationConfig();

    @GET(TMUrlConfig.GET_LOCATION)
    Observable<ResponseBody> getLocation(@Query("output") String output, @Query("location") String location, @Query("key") String key);

    @GET(TMUrlConfig.DISCOVER_INFO_NEW)
    Observable<ResponseBody> discoverInfoNew();
}
