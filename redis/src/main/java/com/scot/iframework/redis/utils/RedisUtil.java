package com.scot.iframework.redis.utils;

import com.alibaba.fastjson.JSON;
import com.scot.iframework.redis.enums.RedisExpxEnum;
import com.scot.iframework.redis.enums.RedisNxxxEnum;
import com.scot.iframework.redis.factory.JedisClusterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;


/**
 * Created by shengke on 2016/11/24.
 */
@Service("redisUtil")
public class RedisUtil {

    /**
     * 存储成功标志.
     */
    private static final String SET_OK = "OK";

    /**
     * 删除成功标志.
     */
    private static final Long DEL_OK = 1L;

    /**
     * redis集群工厂.
     */
    @Autowired
    private JedisClusterFactory jedisClusterFactory;

    /**
     * redis操作API.
     */
    private JedisCluster jedisCluster;

    /**
     * 存储对象.
     * @param key   key
     * @param obj   value
     * @param nxxxEnum  存储方式
     * @param expxEnum  时间类型
     * @param liveTime  超时时间
     * @throws Exception    异常信息
     * @return 存储结果
     */
    public boolean setObject(String key, Object obj, RedisNxxxEnum nxxxEnum, RedisExpxEnum expxEnum, Integer liveTime) throws Exception {
        jedisCluster = jedisClusterFactory.getObject();
        return SET_OK.equals(jedisCluster.set(key, JSON.toJSONString(obj),
                nxxxEnum.getValue(), expxEnum.getValue(), liveTime));
    }

    /**
     * 存储对象.
     * @param key   key
     * @param obj   value
     * @param liveTime  超时时间
     * @throws Exception    异常信息
     * @return  存储结果
     */
    public boolean setObject(String key, Object obj, Integer liveTime) throws Exception {
        jedisCluster = jedisClusterFactory.getObject();
        RedisNxxxEnum nxxx = null;
        if (jedisCluster.exists(key)) {
            nxxx = RedisNxxxEnum.XX;
        } else {
            nxxx = RedisNxxxEnum.NX;
        }
        return SET_OK.equals(jedisCluster.set(key, JSON.toJSONString(obj),
               nxxx.getValue(), RedisExpxEnum.EX.getValue(), liveTime));
    }

    /**
     * 存储对象.
     * @param key   key
     * @param obj   value
     * @throws Exception    异常信息
     * @return  存储结果
     */
    public boolean setObject(String key, Object obj) throws Exception {
        jedisCluster = jedisClusterFactory.getObject();
        return SET_OK.equals(jedisCluster.set(key, JSON.toJSONString(obj)));
    }

    /**
     * 获取对象.
     * @param key   key
     * @param c 类型
     * @param <T>   泛型
     * @return  t
     * @throws Exception    异常信息
     */
    public <T> T getObject(String key, Class<T> c) throws Exception {
        jedisCluster = jedisClusterFactory.getObject();
        return (T) JSON.parseObject(jedisCluster.get(key), c);
    }

    /**
     * 删除key.
     * @param key   key
     * @throws Exception    异常信息
     * @return  删除结果
     */
    public boolean deleteKey(String key) throws Exception {
        jedisCluster = jedisClusterFactory.getObject();
        return DEL_OK.equals(jedisCluster.del(key));
    }

}
