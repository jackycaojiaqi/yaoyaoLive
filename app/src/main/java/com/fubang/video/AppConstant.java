package com.fubang.video;

/**
 * 常量工具类
 * Created by dell on 2016/4/5.
 */
public class AppConstant {
    public static final String BASE_RTMP_URL = "http://121.41.18.41:88";//获取rtmp地址
    public static final String BASE_URL = "http://121.40.144.9:888";//富邦直播
    public static final String BASE_IMG_URL = "http://121.40.144.9:888/upload_pic/";//图片服务器前缀地址
    public static final String BASE_DOWNLOAD_MUSIC = "http://120.26.127.210:333/mp3_ge/";//音乐下载链接前缀
    public static final String BASE_DOWNLOAD_LRC = "http://120.26.127.210:333/mp3_lrc/";//音乐下载链接前缀
    public static final String BASE_CONNECT_IP = "42.121.57.170";//c++服务器ip
    public static final int BASE_CONNECT_PORT = 11444;//文字、礼物、扣币端口
    public static final int BASE_POOL_CONNECT_PORT = 11445;//pool端口

    public static final String DOWNLOAD_URL = "121.43.153.125:92/download/yaoyao.apk";//下载最新app地址

    public static final String USERNAME = "username";
    public static final String USERPIC = "userpic";
    public static final String OBJECT = "onject";
    public static final String USERID = "userid";
    public static final String TOKEN = "token";
    public static final String PHONE = "phone";
    public static final String PASSWORD = "password";
    public static final String VIDEOID = "videoid";
    public static final String NKNUM = "nknum";
    public static final String APK_DOWN_LOAD_ID = "apk_id";
    public static final String VERSION = "version";
    public static final String COUNT = "count";
    public static final String CONTENT = "content";
    public static final String PAGE = "page";
    public static final String TYPE = "type";
    public static final long NKTIMES = 100;
    public static final String PRIVINCE = "privince";
    public static final String CITY = "city";
    public static final String ADDRDETAIL = "addrdetail";
    public static final String LAT = "lat";
    public static final String LON = "lon";
    public static final String GENDER = "gender";

    public static final String CALLFROM = "callfrom";
    public static final String CALLTO = "callto";


    public static final String URL_PUNLISH_UPLOAD = "/aliyun-php-sdk-vod/aliyun.php";//获取上传播放凭证
    public static final String URL_PLAY_VIDEO_INFO = "/aliyun-php-sdk-vod/aliyun_url.php";//获取播放地址


    public static final String URL_SEND_MSG = "/index.php/LineApp/send_msg";//发送短信验证码
    public static final String URL_CHECK_MSG = "/index.php/LineApp/check_msg";//核验短信验证码
    public static final String URL_CHECK_REG = "/index.php/LineApp/check_reg";//用户注册查询是否存在
    public static final String URL_LOGIN = "/index.php/LineApp/login";//用户登录
    public static final String URL_REGISTER = "/index.php/LineApp/reg";//用户注册
    public static final String URL_UPDATE_PASSWORD = "/index.php/LineApp/update_password";//用户修改密码


    public static final String URL_BASE_INFO = "/index.php/LineApp/base_info";//获取用户基本信息
    public static final String URL_UPLOAD_PHOTO = "/index.php/LineApp/upload_pic";//上传图片和头像
    public static final String URL_UPDATE_INFO = "/index.php/LineApp/update_info";//更新用户基本信息
    public static final String URL_UPDATE_EXTINFO = "/index.php/LineApp/update_extinfo";//更新用户扩展信息
    public static final String URL_FEEDBACK = "/index.php/LineApp/feedback";//意见反馈


    public static final String URL_LIFE_ADD = "/index.php/LineApp/life_add";//生活圈发布
    public static final String URL_LIFE_LIST = "/index.php/LineApp/life_list";//朋友圈列表
    public static final String URL_LIFE_DETAIL = "/index.php/LineApp/life_detail";//朋友圈详情
    public static final String URL_LIFE_REVIEW = "/index.php/LineApp/life_review";//朋友圈评论
    public static final String URL_LIFE_FLOWER = "/index.php/LineApp/life_flower";//朋友圈鲜花

    public static final String URL_LIFE_HOME_DATE = "/index.php/LineApp/index_data";//首页数据获取

    public static final String URL_PAY_INFO = "/index.php/LineApp/ali_pay";//支付宝获取订单信息


    //列表接口
    public static final String URL_LIST_MAIN = "/index.php/LineApp/main_list";//更多查询列表
    public static final String URL_LIST_SONG = "/index.php/LineApp/chao_song";//超能送
    public static final String URL_LIST_LIAO= "/index.php/LineApp/chao_liao";//超能聊


    public static final String URL_TUHAO_YUE= "/index.php/LineApp/tu_hao_yue";//土豪月排行
    public static final String URL_NVSHENG_YUE= "/index.php/LineApp/nv_sheng_yue";//女神月排行
    public static final String URL_LIWU= "/index.php/LineApp/liwu_bang";//礼物排行



}
