package com.fh.shop.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class redisTool {
    private redisTool() {
    }

    ;

    private static JedisPool jedisPool;

    private static void initJedis() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);
        jedisPoolConfig.setMinIdle(100);
        jedisPoolConfig.setMaxIdle(100);
        jedisPoolConfig.setMaxTotal(1000);
        jedisPool = new JedisPool(jedisPoolConfig, "192.168.223.134", 7020);
    }

    static {
        initJedis();
    }

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }
}
