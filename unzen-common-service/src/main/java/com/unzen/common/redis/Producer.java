package com.unzen.common.redis;

import redis.clients.jedis.Jedis;

public class Producer extends Thread{

	public static final String MESSAGE_KEY = "message:queqe";
	
	private Jedis jedis;
	
	private String productName;
	
	private volatile int count;
	
	public  Producer (String name){
		this.productName = name;
		init();	
	}
	
	private void init(){
//		jedis = MyJedisFactory.getLocalJedis();
	}
	
}
