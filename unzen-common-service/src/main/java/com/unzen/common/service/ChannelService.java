/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.unzen.common.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.unzen.common.core.data.Channel;


/**
 * TODO: 暂时添加修改都在数据库操作
 * 
 * @author langhsu
 *
 */
public interface ChannelService {
	
	List<Channel> findAll(int status);
	
	Map<Integer, Channel> findMapByIds(Collection<Integer> ids);
	
	Channel get(int id);
	
	Channel getByKey(String key);
	
	void update(Channel channel);
	
	void delete(int id);
}
