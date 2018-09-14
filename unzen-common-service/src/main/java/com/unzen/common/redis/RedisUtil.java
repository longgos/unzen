package com.unzen.common.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * ###Redis配置工具类###
 * 
 * 利用redis做队列,我们采用的是redis中list的push和pop操作; 
 * .只允许在一端插入新元素只能在队列的尾部FIFO:先进先出原则 Redis中lpush头入(rpop尾出)或rpush尾入(lpop头出)可以满足要求,
 * .而Redis中list要push或　pop的对象仅需要转换成byte[]即可  
 * .java采用Jedis进行Redis的存储和Redis的连接池设置 
 * @author ljk
 *
 */
public class RedisUtil {

	private static String IP = "127.0.0.1";
			
	private static int PORT = 6379;
	/** 密码 */
    private static String AUTH = "941029";
    /** 连接实例的最大连接数 */
    private static int MAX_ACTIVE = 1024;
    /** 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。 */
    private static int MAX_IDLE = 200;
    /** 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException */
    private static int MAX_WAIT = 10000;
    /** 连接超时的时间　 */
    private static int TIMEOUT = 10000;
    /** 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的； */
    private static boolean TEST_ON_BORROW = true;

    private static JedisPool jedisPool = null;
   /* static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, IP, PORT, TIMEOUT,AUTH);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/
    /**
     * 获取redis值根据key （String）
     * @param key
     * @return
     */
    public static String get(String key){
    	String value = null;
    	Jedis jedis = null;
    	//从池中获取一个Jedis对象
    	try {
    		jedis = jedisPool.getResource();
        	value = jedis.get(key);
		} catch (Exception e) {
			jedisPool.close();
			e.printStackTrace();
		}
    	return value;
    }

    /**
     * 释放资源
     * @param jedis
     */
    public static void close(Jedis jedis){
    	try {
    		jedisPool.close();
		} catch (Exception e) {
			if(jedis.isConnected()){
	              jedis.quit();
	              jedis.disconnect();
	          }
		}
    }
    
    /**
     * 获取redis值根据key （byte[]）
     * @param key
     * @return
     */
    public static byte[] get(byte[] key){
    	byte[] value = null;
    	Jedis jedis = null;
    	try {
    		jedis = jedisPool.getResource();
    		value = jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		} 
    	return value;
    }
    
    /**
     * 赋值
     * @param key
     * @param value
     */
    public static void set(byte[] key ,byte [] value){
    	Jedis jedis = null;
    	try {
    		jedis = jedisPool.getResource();
    		jedis.set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 赋值（带生存时间）
     * @param key
     * @param value
     * @param time 生存时间
     */
    public static void set(byte[] key ,byte [] value,int time){
    	Jedis jedis = null;
    	try {
    		jedis = jedisPool.getResource();
    		jedis.set(key, value);
    		jedis.expire(key, time);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    /**
     * 赋值 hash类型
     * @param key
     * @param value
     * @param filed
     */
    public static void hset (byte[] key ,byte [] value, byte [] field){
    	Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hset(key, field, value);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    
    public static void hset(String key, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hset(key, field, value);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    /**
     * 获取数据  hash
     * @param key 
     * @return
     */
    public static String hget(String key, String field) {

        String value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.hget(key, field);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
    
    
    /**
     * 获取数据	 hash
     * @param key
     * @return
     */
    public static byte[] hget(byte[] key, byte[] field) {

        byte[] value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.hget(key, field);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }
    
    /**
     * 删除 hash
     * @param key
     * @param field
     */
    public static void hdel(byte[] key, byte[] field) {

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hdel(key, field);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    /**
     * 存储reids队列==>lpush 顺序存储
     * @param key
     * @param value
     */
    public static void lpush(byte[] key, byte [] value){
    	Jedis jedis = null;
    	try {
    		jedis = jedisPool.getResource();
    		jedis.lpush(key, value);
		} catch (Exception e) {
			 e.printStackTrace();
		}
    }
    
    /**
     * 存储reids队列==>lpush 反向存储
     * @param key
     * @param value
     */
    public static void rpush(byte[] key, byte [] value){
    	Jedis jedis = null;
    	try {
    		jedis = jedisPool.getResource();
    		jedis.rpush(key, value);
		} catch (Exception e) {
			 e.printStackTrace();
		}
    }
    
    /**
     * 将列表 source 中的最后一个元素(尾元素)弹出，并返回给客户端 
     * @param key
     * @param destination
     */
    public static void rpoplpush(byte [] key, byte [] destination){
    	Jedis jedis = null;
    	try {
    		jedis = jedisPool.getResource();
    		jedis.rpoplpush(key, destination);
		} catch (Exception e) {
			 e.printStackTrace();
		}
    }
    
    /**
     * 获取队列数据	(List)
     * @param  key 键名
     * @return
     */
    public static List<byte []> lpopList(byte [] key){
    	List<byte []> list = null;
    	Jedis jedis = null;
    	try {
    		jedis = jedisPool.getResource();
    		list = jedis.lrange(key, 0, -1);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return list;
    }
    
    /**
     * 获取队列数据 (byte [])
     * @param key
     * @return
     */
    public static byte [] rpop(byte [] key){
    	byte [] bytes = null;
    	Jedis jedis = null;
    	try {
    		jedis = jedisPool.getResource();
    		bytes = jedis.rpop(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return bytes;
    }
    
    /**
     * hash列表存入Map
     * @param key
     * @param hash
     */
    public static void hmset(Object key, Map hash) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hmset(key.toString(), hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * hash列表存入Map并设入生命周期
     * @param key
     * @param hash
     */
    public static void hmset(Object key, Map hash, int time) {
        Jedis jedis = null;
        try {

            jedis = jedisPool.getResource();
            jedis.hmset(key.toString(), hash);
            jedis.expire(key.toString(), time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 获取map
     * @param key
     * @param fields
     * @return
     */
    public static List hmget(Object key, String... fields) {
        List result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hmget(key.toString(), fields);
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return result;
    }

    
    public static Set hkeys(String key) {
        Set result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hkeys(key);

        } catch (Exception e) {
            e.printStackTrace();
            close(jedis);

        } 
        return result;
    }
    /**
     * 返回指定区间内的元素列表。
     * @param key
     * @param from
     * @param to
     * @return
     */
    public static List lrange(byte[] key, int from, int to) {
        List result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.lrange(key, from, to);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 返回哈希表key中所有域和值
     * @param key
     * @return
     */
    public static Map hgetAll(byte[] key) {
        Map result = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.hgetAll(key);
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return result;
    }

    /**
     * 移除给定的一个或多个key
     * @param key
     */
    public static void del(byte[] key) {

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            //释放redis对象
            e.printStackTrace();
        } 
    }

    /**
     * 返回列表key的长度。 
     * @param key
     * @return
     */
    public static long llen(byte[] key) {

        long len = 0;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.llen(key);
        } catch (Exception e) {
            //释放redis对象
            e.printStackTrace();
        } 
        return len;
    }
}
