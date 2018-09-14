package com.unzen.common.core.persist.utils.jedis;

public class RedisKeyUtil {

    private static String SPLIT = ":";
    private static String BIZ_LIKE = "LIKE";
    private static String BIZ_DISLIKE = "DISLIKE";
    
    /**
     * 生成一个点赞的key: LIKE:ENTITY_NEWS:2
     * @param entityId
     * @param entityType
     * @return
     */
    public static String getLikeKey(int entityId,String entityType){
    	return BIZ_LIKE+SPLIT+entityType+SPLIT+String.valueOf(entityId);
    }
    
    /**
     * 生成一个取消赞的key：DISLIKE:ENTITY_NEWS:2
     * @param entityId
     * @param entityType
     * @return
     */
    public static String getDisLikeKey(int entityId,String entityType){
    	return BIZ_DISLIKE+SPLIT+entityType+SPLIT+String.valueOf(entityId);
    }
}
