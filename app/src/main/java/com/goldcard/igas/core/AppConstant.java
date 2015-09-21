package com.goldcard.igas.core;

import com.goldcard.igas.R;
import com.goldcard.igas.util.StringUtils;

/**
 * Created by jk on 15/9/14.
 */
public class AppConstant {

    /** 应用的名称 **/
    public static final String APP_NAME  =  "GasTreasure";

    /** 保存SharePreference的文件名 **/
    public final static String sharePreferenceFileName  = APP_NAME + "_SharePrefence";

    /** 是否是第一次使用app **/
    public final static String FIRST_USE_FLAG   =  "firstUseFlag";

    /** 个信推送 **/
    public final static String GEXIN_CLIENTID  =  "gexinClientId";

    /** 数据库查询的排序类型 1-－倒序 **/
    public static final int QUERY_SORT_TYPE_DESC = 1;

    /** 数据库查询的排序类型0--顺序 **/
    public static final int QUERY_SORT_TYPE_AEC = 0;

    /** 微信appId **/
    public static final String WEIXIN_APP_ID = StringUtils.trimNull(AppContext.getInstance().getResources().getString(R.string.webchat_app_id));
}
