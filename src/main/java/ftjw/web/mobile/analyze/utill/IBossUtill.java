package ftjw.web.mobile.analyze.utill;

import cn.hutool.http.HttpUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.thymeleaf.util.StringUtils;

import java.util.*;

/**
 * 殷晓龙
 * 2020/3/18 11:03
 */
public class IBossUtill {

    static String appid="b04102ea9a4db3f2995d61cdf28364a9";
    static String secret="bdcfae111231b569e8d4a4b39c8ab680";
    static String verify_token="v_22296f978bd4d885138fb9823a2918c7";
    static String QUERY_URL="https://api.bi.fumenhu.cn/api/query/awsc?appid=APPID&access_token=ACCESS_TOKEN";


    static String appid14="653bb73c0199fe1218254b162353c2bf";
    static String secret14="bdcfae111231b569e8d4a4b39c8ab699";
    static String verify_token14="v_7158e5ac937055e91ec4b6d25dd6b6a6";

    /**
     * 一个简单的签名认证，规则：
     * 1. 将请求参数按ascii码排序
     * 2. 拼接为a=value&b=value...这样的字符串（不包含sign）
     * 3. 混合密钥（secret）进行md5获得签名，与请求的签名进行比较
     */
    public static String  getAccessToken(Map params) {
        String linkString = getLinkString(params);
        //混合密钥md5
        String sign = DigestUtils.md5Hex(appid +linkString +secret+verify_token);
        return sign;
    }
    public static String  getAccessToken14(Map params) {
        String linkString = getLinkString(params);
        //混合密钥md5
        String sign = DigestUtils.md5Hex(appid14 +linkString +secret14+verify_token14);
        return sign;
    }

    private static String getLinkString(Map params) {
        List<String> keys = new ArrayList(params.keySet());
        //排序
        Collections.sort(keys);
        StringBuilder sb = new StringBuilder();
        //拼接字符串
        for (String key : keys) {
            sb.append(key).append("=").append(params.get(key)).append("&");
        }
        String linkString = sb.toString();
        //去除最后一个'&'
        linkString = StringUtils.substring(linkString, 0, linkString.length() - 1);
        return linkString;
    }


    /**
     * 获取公司访问人数
     * @param sid  公司id
     * @return
     */
    public static String query_awsc(Integer sid){
        Map param=new HashMap();
        param.put("sid",sid);
        String url=  QUERY_URL.replace("APPID",appid)
                    .replace("ACCESS_TOKEN",getAccessToken(param));


        return HttpUtil.get(url,param);
    }

    /**
     * uid为14的公司要特殊处理
     * @param sid
     * @return
     */
    public static String query_a2sc(Integer sid){
        Map param=new HashMap();
        param.put("sid",sid);
        String url =QUERY_URL.replace("awsc","a2sc")
                .replace("APPID",appid14)
                .replace("ACCESS_TOKEN",getAccessToken14(param));
        try {
            return  HttpUtil.get(url,param);
        } catch (Exception e) {
            return "err";
        }

    }
}
