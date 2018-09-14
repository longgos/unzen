package com.unzen.common.core.persist.utils.jedis;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class JedisUtil {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private JedisUtil() {
	}

	private static class RedisUtilHolder {
		private static final JedisUtil instance = new JedisUtil();
	}

	public static JedisUtil getInstance() {
		return RedisUtilHolder.instance;
	}

	private static Map<String, JedisPool> maps = new HashMap<String, JedisPool>();

	/**
	 * 获取Jedispool
	 * @param ip
	 * @param port
	 * @return
	 */
	private static JedisPool getPool(String ip, int port) {
		String key = ip + ":" + port;
		JedisPool pool = null;
		if (!maps.containsKey(key)) {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(RedisConfig.MAX_ACTIVE);
			config.setMaxIdle(RedisConfig.MAX_IDLE);
			config.setMaxWaitMillis(RedisConfig.MAX_WAIT);
			config.setTestOnBorrow(false);
			config.setTestOnReturn(false);

			pool = new JedisPool(config, ip, port, RedisConfig.TIMEOUT);
			maps.put(key, pool);
		} else {
			pool = maps.get(key);
		}
		return pool;
	}
	/**
	 *获取一个jedis
	 * @param ip
	 * @param port
	 * @return
	 */
	public Jedis getJedis(String ip, int port) {
		Jedis jedis = null;
		int count = 0;
		do {
			try {
				jedis = getPool(ip, port).getResource();
			} catch (Exception e) {
				logger.error("获取jedis失败", e);
				getPool(ip, port).close();
			} finally {
				count++;
			}
		} while (jedis == null && count < RedisConfig.RETRY_NUM);
		return jedis;
	}

	public void closeJedis(Jedis jedis, String ip, int port) {
		if (jedis != null) {
			getPool(ip, port).close();
		}
	}
	
	/*public static void saveAllRedis(String ip, int port){
		Jedis jedis = getPool(ip, port).getResource();
		jedis.auth("");
		Set keys = jedis.keys("*");
		Iterator t1 = keys.iterator();
		while (t1.hasNext()) {
			Object object = t1.next();
			saveRedisObject(redis, obj1 + "", redisIp, redisPort + "", appCode);
		}
	}*/
}
