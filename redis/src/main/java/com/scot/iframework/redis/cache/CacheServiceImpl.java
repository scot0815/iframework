package com.scot.iframework.redis.cache;

import com.scot.iframework.redis.utils.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 防缓存失效瞬间，高并发缓存穿透
 * Created by shengke on 2016/11/28.
 */
@Service("cacheService")
public class CacheServiceImpl implements ICacheService {

    /**
     * redis操作工具类.
     */
    @Resource(name = "redisUtil")
    private RedisUtil redisUtil;

    /**
     * 获取缓存数据.
     * @param key   key
     * @param c value 类型
     * @param expireSeconds 存活时间
     * @param callBackService   回调实现
     * @param <T>   t
     * @return  T
     * @throws Exception 异常信息
     */
    public <T> T loadCache(String key, Class<T> c, Integer expireSeconds, ICacheCallBackService callBackService) throws Exception {
        T t = redisUtil.getObject(key, c);
        if (null != t) {
            return t;
        } else {
            synchronized (this) {
                t = redisUtil.getObject(key, c);
                if (null != t) {
                    return t;
                } else {
                    t = callBackService.load();
                    redisUtil.setObject(key, t, expireSeconds);
                    return t;
                }
            }
        }
    }

}
