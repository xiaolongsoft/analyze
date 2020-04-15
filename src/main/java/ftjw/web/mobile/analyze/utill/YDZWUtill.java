package ftjw.web.mobile.analyze.utill;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import ftjw.web.mobile.analyze.entity.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 殷晓龙
 * 2020/4/14 16:24
 */
public class YDZWUtill {
    static String appid="gmfa08b6922c42b";
    static String secret="39a643046130664c9d752ce6c0793659";
    static String verify_token="";
    static String QUERY_URL="https://gmwz.mobi/index.php/api/user/create.html?appid=APPID&access_token=ACCESS_TOKEN";




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


    /**
     * 获取公司访问人数
     * @return
     */
    public static String createUser(Map map){

        String url=  QUERY_URL.replace("APPID",appid)
                .replace("ACCESS_TOKEN",getAccessToken(map));
        return HttpUtil.post(url,map);
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

}
