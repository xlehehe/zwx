package com.zwx.scan.app.data.http;

/**
 * author : lizhilong
 * time   : 2018/08/28
 * description   : 变量和常量内容
 * version: 1.0
 **/
public class ApiConstants {

    public static  String refreshToken="";

    //设缓存有效期为3天
    protected static final long CACHE_STALE_SEC = 60 * 60 * 24 * 3;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    public static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置
    //(假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)
    protected static final String CACHE_CONTROL_NETWORK = "Cache-Control: public, max-age=3600";
    // 避免出现 HTTP 403 Forbidden，参考：http://stackoverflow.com/questions/13670692/403-forbidden-with-java-but-not-web-browser
    protected static final String AVOID_HTTP403_FORBIDDEN = "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";

    //HTTP请求前缀
    public static final String BASE_URL="http://192.168.10.32:8080/aumc/";
    //    public static final String BASE_URL="http://192.168.10.29:8080/aumc/";

    //登录
    public static final  String LOGIN_URL="api/tokens/login.do";

    //token验证
    public static final String TOKEN_AUTH_URL="api/tokens/info.do";

    public static final String PRIZE_DEFAULT="5c386aa25ab7e02e784fb7f1";

}
