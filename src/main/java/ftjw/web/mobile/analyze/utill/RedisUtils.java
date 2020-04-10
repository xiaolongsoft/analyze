package ftjw.web.mobile.analyze.utill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisUtils {
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 写入缓存
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, String value) {
        boolean result = false;
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 写入缓存设置时效时间
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, String value, Long expireTime ,TimeUnit timeUnit) {
        boolean result = false;
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(key, value, expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 批量删除对应的value
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        if (!keys.isEmpty()){
            redisTemplate.delete(keys);
        }
    }
    /**
     * 删除对应的value
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }
    /**
     * 判断缓存中是否有对应的value
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }
    /**
     * 读取缓存
     * @param key
     * @return
     */
    public String get(final String key) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    /**
     * 读取缓存中的数据，并且刷新时间
     * @param key
     * @return
     */
    public String refreshGet(String key){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String value=operations.get(key);
        if(!StringUtils.isEmpty(value)){
            operations.set(key,value,30L,TimeUnit.MINUTES);
        }
        return value;
    }
    /**
     * 哈希 添加
     * @param key
     * @param hashKey
     * @param value
     */
    public void hmSet(String key, Object hashKey, Object value){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key,hashKey,value);
    }

    /**
     * 哈希获取数据
     * @param key
     * @param hashKey
     * @return
     */
    public Object hmGet(String key, Object hashKey){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key,hashKey);
    }

    /**
     * 列表添加
     * @param k
     * @param v
     */
    public void lPush(String k,String v){
        ListOperations<String, String> list = redisTemplate.opsForList();
        list.leftPush(k,v);
    }


    public String rPop(String k){
        ListOperations<String, String> list = redisTemplate.opsForList();
        return list.rightPop(k);
    }

    /**
     * 获取数据
     * 
     * @param k
     * @return
     */
    public String lPop(String k){
        ListOperations<String, String> list = redisTemplate.opsForList();
        return list.leftPop(k);
    }

    /**
     * 列表获取
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public List<String> lRange(String k, long l, long l1){
        ListOperations<String, String> list = redisTemplate.opsForList();
        return list.range(k,l,l1);
    }

    /**
     * 集合添加
     * @param key
     * @param value
     */
    public void add(String key,String value){
        SetOperations<String, String> set = redisTemplate.opsForSet();
        set.add(key,value);
    }

    /**
     * 集合获取
     * @param key
     * @return
     */
    public Set<String> setMembers(String key){
        SetOperations<String, String> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     * @param key
     * @param value
     * @param scoure
     */
    public void zAdd(String key,String value,double scoure){
        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        zset.add(key,value,scoure);
    }

    /**
     * 有序集合获取
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    public Set<String> rangeByScore(String key,double scoure,double scoure1){
        ZSetOperations<String, String> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }

}