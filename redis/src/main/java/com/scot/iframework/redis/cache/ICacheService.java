package com.scot.iframework.redis.cache;

/**
 * 缓存服务
 * Created by shengke on 2016/11/24.
 */
public interface ICacheService {

    /**
     * 获取缓存数据.
     * @param key   key
     * @param c value 类型
     * @param expireSeconds 超时时间
     * @param callBackService   回调实现
     * @param <T>   t
     * @return  T
     * @throws Exception 异常信息
     */
    <T> T loadCache(String key, Class<T> c, Integer expireSeconds, ICacheCallBackService callBackService) throws Exception;
}
