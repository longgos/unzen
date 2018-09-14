package com.unzen.common.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unzen.common.core.persist.utils.jedis.JedisAdapter;
import com.unzen.common.core.persist.utils.jedis.RedisKeyUtil;
import com.unzen.common.service.LikeService;

import redis.clients.jedis.Jedis;
import scala.collection.mutable.HashMap;

@Service
@Transactional
public class LikeServiceImpl implements LikeService{

	@Autowired
	private JedisAdapter jedisAdapter;
	
	/**
	 * 判断用户是点赞还是取消
	 * @param userId
	 * @param entityType
	 * @param entityId
	 * @return
	 */
	@Override
	public int getLikeStatus(Long userId,String entityType, int entityId){
		//根据当前用户的userId生成一个likeKey和disLikeKey再分别判断这两个值是否在对应的Like集合中和disLikeKey集合中
		////比如如果在likeKey集合中，就返回一个1，否则返回-1,如果都不存在返回0
		String likekey = RedisKeyUtil.getLikeKey(entityId, entityType);
		if(jedisAdapter.sismember(likekey, String.valueOf(userId))){
			return 1;
		}
		String disLikekey = RedisKeyUtil.getDisLikeKey(entityId, entityType);
		return jedisAdapter.sismember(disLikekey, String.valueOf(userId))? -1 : 0;
	}
	
	/**
	 * 点赞
	 * @param userId
	 * @param entityType
	 * @param entityId
	 * @return
	 */
	@Override
	public long like(Long userId,String entityType, int entityId){
		//①在喜欢集合中添加当前操作用户的userId
		String likekey = RedisKeyUtil.getLikeKey(entityId, entityType);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(likekey, String.valueOf(userId));
		jedisAdapter.sadd(likekey, String.valueOf(userId));
		
		//②删除取消赞集合中的userId
		String disLikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);
		jedisAdapter.srem(disLikeKey, String.valueOf(userId));
		
		//③返回点赞数
		return jedisAdapter.scard(likekey);
	}
	
	/**
	 * 取消点赞(移除)
	 * @param userId
	 * @param entityType
	 * @param entityId
	 * @return
	 */
	@Override
	public long disLike(Long userId,String entityType, int entityId){
		//①在不喜欢集合中添加当前操作用户的userId
		String disLikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);
		jedisAdapter.sadd(disLikeKey, String.valueOf(userId));
		
		//②在喜欢集合中删除当前操作用户的userId
		String likekey = RedisKeyUtil.getLikeKey(entityId, entityType);
		jedisAdapter.srem(likekey, String.valueOf(userId));
		
		//③返回点赞数
		return jedisAdapter.scard(likekey);
	}
}
