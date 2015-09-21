package com.goldcard.igas.http.model.base;

import android.content.Context;

import com.goldcard.igas.R;
import com.goldcard.igas.http.NameValueBean;
import com.goldcard.igas.util.CommonUtils;
import com.goldcard.igas.util.DateUtils;
import com.goldcard.igas.util.MD5;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 请求头信息
 * @author liys
 * @since 2015-5-20
 * @version 1.0.0
 */
public class ReqHeaderBean implements Serializable {

    // 版本
    private String            version;
    // 随机数
    private String            random;
    // 时间戳
    private String            timestamp;
    // 请求url
    private String            url;
    // 签名串
    private String            sign;
    // 令牌
    private String            token;
    // 唯一串
    private String            uuid;
    // 应用程序对象
    private Context context;

    public ReqHeaderBean(String reqUrl,Context ctx) {
        this.context = ctx;
        String separator = "|";
        String key = this.context.getResources ().getString (R.string.sign_key);
        this.url = reqUrl;
        this.version = this.context.getResources ().getString (R.string.server_version);
        this.random = String.valueOf (new Random().nextInt(999) + 1);
        this.timestamp = DateUtils.getNowTimeStamp ();
        this.token = CommonUtils.userUUID(context);
        this.uuid = CommonUtils.uuid ();
        StringBuffer sb = new StringBuffer ();
        sb.append(this.version).append (separator).append (this.timestamp).append (separator).append (token).append (separator).append (this.random)
                .append (separator).append (this.uuid).append (separator).append (this.url);
        this.sign = MD5.md5 (MD5.md5(sb.toString()) + MD5.md5 (key));
    }

    public String getVersion(){
        return version;
    }

    public void setVersion(String version){
        this.version = version;
    }

    public String getRandom(){
        return random;
    }

    public void setRandom(String random){
        this.random = random;
    }

    public String getTimestamp(){
        return timestamp;
    }

    public void setTimestamp(String timestamp){
        this.timestamp = timestamp;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getSign(){
        return sign;
    }

    public void setSign(String sign){
        this.sign = sign;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getUuid(){
        return uuid;
    }

    public void setUuid(String uuid){
        this.uuid = uuid;
    }

    // 获取请求参数
    public List<NameValueBean> getReqHeaderParams(){
        List<NameValueBean> headerParams = new ArrayList<NameValueBean>();
        headerParams.add (new NameValueBean("randomOnce",random));
        headerParams.add (new NameValueBean ("version",version));
        headerParams.add (new NameValueBean ("token",token));
        headerParams.add (new NameValueBean ("signature",sign));
        headerParams.add (new NameValueBean ("timestamp",timestamp));
        headerParams.add (new NameValueBean ("uuid",uuid));
        headerParams.add (new NameValueBean ("methodUrl",this.url));
        return headerParams;
    }

    @Override
    public String toString(){
        return "ReqHeaderBean{" + "version='" + version + '\'' + ", random='" + random + '\'' + ", timestamp='" + timestamp + '\'' + ", url='" + url + '\''
                + ", sign='" + sign + '\'' + ", token='" + token + '\'' + ", uuid='" + uuid + '\'' + '}';
    }
}
