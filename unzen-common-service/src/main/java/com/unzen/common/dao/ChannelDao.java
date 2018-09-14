/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.unzen.common.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.unzen.common.core.persist.entity.ChannelPO;


/**
 * @author langhsu
 *
 */
@Mapper
public interface ChannelDao{
	
	List<ChannelPO> findAll();
	List<ChannelPO> findAllByStatus(@Param("status") String status);
	
	List<ChannelPO> findAllByIdIn(HashMap<String, Object> map);
	
	ChannelPO findByKey(String key);
	
	ChannelPO get(@Param("id") int id);
	void save(ChannelPO po);
	void delete(int id);
}
