package com.tianma.tm_own_find.config;

/**
 * Created by wcc on 2018/8/8.
 */

public class TMUrlConfig {
    //获取配置信息
    public static final String APP_GET_CONFIG = "/api/Appconf/getConfig";
    public static final String THIRD_LOGIN = "/Member/login/anotherLogin";   //三方登录
    public static final String BIND_OTHER_LOGIN_INFO = "/Member/login/bindOtherLoginInfo";   //绑定三方账号
    public static final String GET_MEMBER_INFO = "/api/member/getMemberInfo/";  //获取个人信息
    public static final String CANCEL_BIND_INFO = "/Member/Login/cancelBindInfo";  //取消绑定三方账号
    public static final String GET_RELIEF_ARTICLE = "/Member/Article/getReliefArticle.html";  //用户使用协议
    public static final String ADD_OPINION_INFO = "/Member/Memberopinion/addOpinionInfo.html";  //意见反馈
    public static final String GET_STAR_LIST = "/Member/Memberstar/getStarList.html";  //收藏列表
    public static final String GET_FOOTPR_INT_LIST = "/Member/Memberfootprint/getFootprintList.html";  //历史列表
    public static final String CHANGE_MOBILE = "/Member/member/changeMobile";  //绑定手机
    public static final String GET_PRIVACY_ARTICLE = "/Member/Article/getPrivacyArticle.html";  //隐私协议
    public static final String MESSAGE_LIST = "member/membermessagepush/messageList";  //系统消息
    public static final String GET_ABOUT_US_ARTICLE = "/Member/Article/getAboutUsArticle.html";  //关于我们
    public static final String DISCOVER_INFO = "/tmgc_discover/Modelclient/discoverInfo";  //发现页面数据整合接口
    public static final String GET_LOCATION_CONFIG = "/tmgc_discover/system/getLocationConfig";  //发现--获取自动定位参数
    public static final String DISCOVER_INFO_NEW = "/clzjcscscs_i2caN/Modelclient/discoverInfo";   //新版发现

    public static final String GET_LOCATION = "/geocoder";  //百度地图api请求
}
