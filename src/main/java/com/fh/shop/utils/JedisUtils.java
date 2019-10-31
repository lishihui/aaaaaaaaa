package com.fh.shop.utils;

import redis.clients.jedis.Jedis;

public class JedisUtils {

    public static void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = redisTool.getJedis();
            jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static String get(String key) {
        Jedis jedis = null;
        String s = null;
        try {
            jedis = redisTool.getJedis();
            s = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return s;
    }

    public static void del(String key) {
        Jedis jedis = null;
        try {
            jedis = redisTool.getJedis();
            jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static Long delToken(String key) {
        Jedis jedis = null;
        Long hdel = 0L;
        try {
            jedis = redisTool.getJedis();
            hdel = jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return hdel;
    }

    public static void setSeconds(String key, int seconds, String value) {
        Jedis jedis = null;
        try {
            jedis = redisTool.getJedis();
            jedis.setex(key, seconds, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static Boolean exists(String key) {
        Jedis jedis = null;
        Boolean exists = false;
        try {
            jedis = redisTool.getJedis();
            exists = jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return exists;
    }


    public static void hSet(String key, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = redisTool.getJedis();
            jedis.hset(key, field, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static String hGet(String key, String field) {
        Jedis jedis = null;
        String hget = "";
        try {
            jedis = redisTool.getJedis();
            hget = jedis.hget(key, field);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return hget;
    }

    public static void hDel(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = redisTool.getJedis();
            jedis.hdel(key, field);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
