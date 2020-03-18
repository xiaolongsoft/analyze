package ftjw.web.mobile.analyze.core;

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
    static String verify_token="22296f978bd4d885138fb9823a2918c7";
    static String QUERY_URL="https://api.bi.fumenhu.cn/api/query/awsc?appid=APPID&access_token=ACCESS_TOKEN";

    /**
     * 一个简单的签名认证，规则：
     * 1. 将请求参数按ascii码排序
     * 2. 拼接为a=value&b=value...这样的字符串（不包含sign）
     * 3. 混合密钥（secret）进行md5获得签名，与请求的签名进行比较
     */
    public static String  getAccessToken(Map params) {
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
        //混合密钥md5
        String sign = DigestUtils.md5Hex(appid +linkString +secret+verify_token);
        return sign;
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
}
