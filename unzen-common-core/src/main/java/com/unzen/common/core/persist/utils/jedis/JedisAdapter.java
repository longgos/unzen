package com.unzen.common.core.persist.utils.jedis;

import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

/**
 * Redis调用点赞
 * @author ljk
 *
 */

@Service
public class JedisAdapter{

	private  static final Logger  logger = org.slf4j.LoggerFactory.getLogger(JedisAdapter.class);
	
	private static final String ipAddr = "127.0.0.1";
	private static final int port = 6379;
	private static Jedis jedis = null;
	
	public JedisAdapter(){
		init();
	}
	public static void main(String[] args) {
		JedisUtil jedisUtil = JedisUtil.getInstance();
		jedis = jedisUtil.getJedis(ipAddr, port);
	}
	
	@BeforeClass
	public static void init() {
		jedis = JedisUtil.getInstance().getJedis(ipAddr, port);
//		close();
	}

	/*@AfterClass
	public static void close() {
		JedisUtil.getInstance().closeJedis(jedis, ipAddr, port);
	}*/

	/**
	 * 点赞
	 * @param key
	 * @param value
	 * @return
	 */
	public long sadd(String key , String value){
		try{
			jedis = JedisUtil.getInstance().getJedis(ipAddr, port);
			return jedis.sadd(key, value);
		} catch (Exception e) {
			  logger.error("Jedis sadd 异常：" + e.getMessage());
	            return 0;
		} finally {
			if (jedis != null){
              jedis.close();
          }
		}
	}
	
	/**
	 * 取消点赞(移除)
	 * @param key
	 * @param value
	 * @return
	 */
	public long srem(String key ,String value){
		try {
			jedis = JedisUtil.getInstance().getJedis(ipAddr, port);
			return jedis.srem(key, value);
		} catch (Exception e) {
			  logger.error("Jedis srem 异常：" + e.getMessage());
	            return 0;
		} finally {
			if (jedis != null){
                jedis.close();
            }
		}
	}
	
	/**
	 * 判断key,value是否是集合中值
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean sismember(String key, String value){
		try {
			jedis = JedisUtil.getInstance().getJedis(ipAddr, port);
			return jedis.sismember(key, value);
		} catch (Exception e) {
			 logger.error("Jedis sismember 异常：" + e.getMessage());
	            return false;
		} finally {
			  if (jedis != null){
	                try{
	                    jedis.close();
	                }catch (Exception e){
	                    logger.error("Jedis关闭异常" + e.getMessage());
	                }
	            }
		}
	}
	
	/**
	 * 获取集合大小
	 * @param key
	 * @return
	 */
	public long scard(String key){
       try{
    	   jedis = JedisUtil.getInstance().getJedis(ipAddr, port);
           return jedis.scard(key);
       }catch (Exception e){
           logger.error("Jedis scard 异常：" + e.getMessage());
           return 0;
       }finally {
           if (jedis != null){
               jedis.close();
           }
       }
   }
	
	
	
}
