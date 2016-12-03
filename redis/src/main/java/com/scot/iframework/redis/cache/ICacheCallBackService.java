package com.scot.iframework.redis.cache;

/**
 * 缓存回调service
 * Created by Administrator on 2016/11/28.
 */
public interface ICacheCallBackService {

    /**
     * 获取数据.
     * @param <T>   泛型t
     * @return  T
     */
    <T> T load();
}
