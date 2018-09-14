package com.unzen.common.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



/**
 * 序列化工具
 * @author ljk
 *
 */
public class SerializationUtil {

	/**
	 * 对象序列化成byte数组
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public static byte[] object2Byte(Object obj) throws IOException{
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = new ObjectOutputStream(bo);
		oo.writeObject(obj);
		byte[] bytes=bo.toByteArray();
		bo.close();
		oo.close();
		return bytes ;
	}
	
	/**
	 * byte数组序列化成对象
	 * @param bytes
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public static Object bytes2Object(byte [] bytes) throws Exception{
		ByteArrayInputStream bo = new ByteArrayInputStream(bytes);
		ObjectInputStream is = new ObjectInputStream(bo);
		return is.readObject();
	}
}
