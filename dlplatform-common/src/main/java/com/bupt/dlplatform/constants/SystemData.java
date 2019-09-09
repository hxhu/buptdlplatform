package com.bupt.dlplatform.constants;

public class SystemData {
    /**
     * (0:未删除 1:已删除)
     */
    public static final Integer IS_DEL_0 = 0;
    /**
     * (0:未删除 1:已删除)
     */
    public static final Integer IS_DEL_1 = 1;

    //订单状态Code（1:待审批；2:待支付；3:待确认；5:已确认；6:满房；7:已离店；8:待退订； 10:已退订；14:已取消；）
    public static final Integer ORDER_STATUS_1 = 1;
    //订单状态Code（1:待审批；2:待支付；3:待确认；5:已确认；6:满房；7:已离店；8:待退订； 10:已退订；14:已取消；）
    public static final Integer ORDER_STATUS_2 = 2;
    //订单状态Code（1:待审批；2:待支付；3:待确认；5:已确认；6:满房；7:已离店；8:待退订； 10:已退订；14:已取消；）
    public static final Integer ORDER_STATUS_3 = 3;
    //订单状态Code（1:待审批；2:待支付；3:待确认；5:已确认；6:满房；7:已离店；8:待退订； 10:已退订；14:已取消；）
    public static final Integer ORDER_STATUS_5 = 5;
    //订单状态Code（1:待审批；2:待支付；3:待确认；5:已确认；6:满房；7:已离店；8:待退订； 10:已退订；14:已取消；）
    public static final Integer ORDER_STATUS_6 = 6;
    //订单状态Code（1:待审批；2:待支付；3:待确认；5:已确认；6:满房；7:已离店；8:待退订； 10:已退订；14:已取消；）
    public static final Integer ORDER_STATUS_7 = 7;
    //订单状态Code（1:待审批；2:待支付；3:待确认；5:已确认；6:满房；7:已离店；8:待退订； 10:已退订；14:已取消；）
    public static final Integer ORDER_STATUS_8 = 9;
    //订单状态Code（1:待审批；2:待支付；3:待确认；5:已确认；6:满房；7:已离店；8:待退订； 10:已退订；14:已取消；）
    public static final Integer ORDER_STATUS_10 = 10;
    //订单状态Code（1:待审批；2:待支付；3:待确认；5:已确认；6:满房；7:已离店；8:待退订； 10:已退订；14:已取消；）
    public static final Integer ORDER_STATUS_14 = 14;


    //供应商类别 2-酒店 1-机票
    public static final Integer SUPPLIER_TYPE_1 = 1;
    //供应商类别 2-酒店 1-机票
    public static final Integer SUPPLIER_TYPE_2 = 2;


    //订单产品来源（0:前台页面（慧通官网）；1:呼叫中心；61:openapi-pc；62:openapi-ios；63:openapi-andriod；64:openapi-others）
    public static final Integer ORDER_SOURCE_0 = 0;
    //订单产品来源（0:前台页面（慧通官网）；1:呼叫中心；61:openapi-pc；62:openapi-ios；63:openapi-andriod；64:openapi-others）
    public static final Integer ORDER_SOURCE_1 = 1;
    //订单产品来源（0:前台页面（慧通官网）；1:呼叫中心；61:openapi-pc；62:openapi-ios；63:openapi-andriod；64:openapi-others）
    public static final Integer ORDER_SOURCE_61 = 61;
    //订单产品来源（0:前台页面（慧通官网）；1:呼叫中心；61:openapi-pc；62:openapi-ios；63:openapi-andriod；64:openapi-others）
    public static final Integer ORDER_SOURCE_62 = 62;
    //订单产品来源（0:前台页面（慧通官网）；1:呼叫中心；61:openapi-pc；62:openapi-ios；63:openapi-andriod；64:openapi-others）
    public static final Integer ORDER_SOURCE_63 = 63;
    //订单产品来源（0:前台页面（慧通官网）；1:呼叫中心；61:openapi-pc；62:openapi-ios；63:openapi-andriod；64:openapi-others）
    public static final Integer ORDER_SOURCE_64 = 64;

    //是否需要担保（0:不需要担保；1:需要担保；）
    public static final boolean IS_GUARANTEE_BOOKING_0 = false;
    //是否需要担保（0:不需要担保；1:需要担保；）
    public static final boolean IS_GUARANTEE_BOOKING_1 = true;

    //担保类型（0:无 1:首晚担保；2:全额担保）
    public static final Integer GUARANTEE_TYPE_0 = 0;
    //担保类型（0:无 1:首晚担保；2:全额担保）
    public static final Integer GUARANTEE_TYPE_1 = 1;
    //担保类型（0:无 1:首晚担保；2:全额担保）
    public static final Integer GUARANTEE_TYPE_2 = 2;

    //出行方式（0:因公出行；1:因私出行；）
    public static final Integer TRAVELT_TYPE_0 = 0;
    //出行方式（0:因公出行；1:因私出行；）
    public static final Integer TRAVELT_TYPE_1 = 1;

    //0:未确认，1：确认无误，2：确认有误
    public static final Integer CONFIRM_RESULT_0 = 0;
    //0:未确认，1：确认无误，2：确认有误
    public static final Integer CONFIRM_RESULT_1 = 1;
    //0:未确认，1：确认无误，2：确认有误
    public static final Integer CONFIRM_RESULT_2 = 2;

    //数据来源（1：系统平台；2：同步数据）
    public static final Integer DATE_FROM_1 = 1;
    //数据来源（1：系统平台；2：同步数据）
    public static final Integer DATE_FROM_2 = 2;


    //腾讯地图API接口状态码，0为正常,310请求参数信息有误，311Key格式错误,306请求有护持信息请检查字符串,110请求来源未被授权
    public static final Integer ADDRESS_RESOLUTION_STATUS_0 = 0;
    //腾讯地图API接口状态码，0为正常,310请求参数信息有误，311Key格式错误,306请求有护持信息请检查字符串,110请求来源未被授权
    public static final Integer ADDRESS_RESOLUTION_STATUS_310 = 310;
    //腾讯地图API接口状态码，0为正常,310请求参数信息有误，311Key格式错误,306请求有护持信息请检查字符串,110请求来源未被授权
    public static final Integer ADDRESS_RESOLUTION_STATUS_311 = 311;
    //腾讯地图API接口状态码，0为正常,310请求参数信息有误，311Key格式错误,306请求有护持信息请检查字符串,110请求来源未被授权
    public static final Integer ADDRESS_RESOLUTION_STATUS_306 = 306;
    //腾讯地图API接口状态码，0为正常,310请求参数信息有误，311Key格式错误,306请求有护持信息请检查字符串,110请求来源未被授权
    public static final Integer ADDRESS_RESOLUTION_STATUS_110 = 110;

    //是否返回周边POI列表：1.返回；0不返回(默认)
    public static final Integer GET_POI_0 = 0;
    //是否返回周边POI列表：1.返回；0不返回(默认)
    public static final Integer GET_POI_1 = 1;


    //DOMAIR国内机票、HOTEL国内酒店、INTAIR国际机票、TRAIN火车票
    public static final String FINDBILL_DOMAIR = "DOMAIR";
    //DOMAIR国内机票、HOTEL国内酒店、INTAIR国际机票、TRAIN火车票
    public static final String FINDBILL_HOTEL = "HOTEL";
    //DOMAIR国内机票、HOTEL国内酒店、INTAIR国际机票、TRAIN火车票
    public static final String FINDBILL_INTAIR = "INTAIR";
    //DOMAIR国内机票、HOTEL国内酒店、INTAIR国际机票、TRAIN火车票
    public static final String FINDBILL_TRAIN = "TRAIN";


    //同步状态（1：停止同步；2：持续同步）
    public static final Integer SYNC_TYPE_1 = 1;
    //同步状态（1：停止同步；2：持续同步）
    public static final Integer SYNC_TYPE_2 = 2;

    //是否强制同步（0：否；1：是）
    public static final Integer IS_FORCED_0 = 0;
    //是否强制同步（0：否；1：是）
    public static final Integer IS_FORCED_1 = 1;

    public static final String swagger_url = "swagger";

    //job自动同步
    public static final Integer SYSTEM_SYNC=1;
    //用户手动同步
    public static final Integer MANUAL_SYNC=2;

    //系统供应商类别-机票
    public static final Integer SUPPLIERTYPE_FLIGHT=1;
    //系统供应商类别-酒店
    public static final Integer SUPPLIERTYPE_HOTEL=2;

    //地区标识 1 国内 2 国际
    public static final Integer AREA_CODE_1=1;
    //地区标识 1 国内 2 国际
    public static final Integer AREA_CODE_2=2;

    //账单数据来源
    public static final String SOURCE_IMPORT = "用户导入";
    public static final String SOURCE_SYNC = "系统同步";

    //同步提示
    public static final String SYNC_TIPS_BEGIN = "正在同步数据,请稍后!";
    public static final String SYNC_TIPS_WORKING = "后台正在执行同步数据,请稍后点击!";

    // ticket过期时间（分钟）
    public static final int TICKET_EXPIRE_MIN_TIME = 3;

    // accessToken过期时间（秒）
    public static final int ACCESS_TOKEN_EXPIRE_SECONDS_TIME = 7200;

    // 登录Token过期时间（分钟）
    public static final int LOGIN_TOKEN_EXPIRE_MIN_TIME = 3;

    //0尚未同意隐私协议；1：已同意隐私协议
    public static final Integer AGREE_USER_INFO_STATUS_0 = 0;
    //0尚未同意隐私协议；1：已同意隐私协议
    public static final Integer AGREE_USER_INFO_STATUS_1 = 1;

    public static final String SystemName = "YiXing2";

    //1:现付  2:月结 固定填为月结 目前仅允许月结方式
    public static final String  PAY_WAY_1 = "1";
    //1:现付  2:月结 固定填为月结 目前仅允许月结方式
    public static final String PAY_WAY_2 = "2";
}
