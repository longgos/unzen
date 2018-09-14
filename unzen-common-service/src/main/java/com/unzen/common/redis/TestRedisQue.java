package com.unzen.common.redis;




import java.io.IOException;
import java.util.List;

public class TestRedisQue {
	
	public static byte [] redisKey = "key".getBytes();
	
	static{
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void init() throws IOException{
		for (int i = 0; i < 100; i++) {
			Message message = new Message(i, "好了，这是第"+i+"条信息，好好看看！");
			RedisUtil.lpush(redisKey, SerializationUtil.object2Byte(message));
		}
		
	}

	public static void main(String[] args) {
		try {
			pop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void pop() throws Exception{
		List<byte []> list = RedisUtil.lpopList(redisKey);
		for (int i = 0; i < list.size(); i++) {
			Message msg = (Message) SerializationUtil.bytes2Object(list.get(i));
			if(msg!=null){
				System.out.println(msg.getId() + "----" + msg.getContent());
			}
		}
	}
}
