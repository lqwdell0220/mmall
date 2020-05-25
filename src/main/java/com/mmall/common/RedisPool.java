package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Discription
 * @Author:liqiwen
 * @Date:2020/5/22 15:01
 */
public class RedisPool {

    private static JedisPool pool;//jedis连接池
    private static Integer maxTotal=Integer.parseInt(PropertiesUtil.getProperty("redis.max.total"));//
    private static Integer maxIdle=Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle"));//
    private static Integer minIdle=Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle"));//

    private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow"));
    private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return"));

    private static String redisIp = PropertiesUtil.getProperty("redis1.ip");
    private static Integer redisPort = Integer.parseInt(PropertiesUtil.getProperty("redis1.port"));

    private static void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setBlockWhenExhausted(true);
        config.setMaxIdle(maxIdle);
        config.setMaxTotal(maxTotal);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        pool = new JedisPool(config,redisIp,redisPort);
    }

    static{
        initPool();
    }

    public static Jedis getJedis(){
        return pool.getResource();
    }

    public static void returnBrokenResource(Jedis jedis){
        pool.returnBrokenResource(jedis);
    }

    public static void returnResource(Jedis jedis){
        pool.returnResource(jedis);
    }

    public static void main(String[] args) {
        Jedis jedis = RedisPool.getJedis();
        jedis.set("test","test");
        RedisPool.returnResource(jedis);
        System.out.println("program is end");
    }
}
