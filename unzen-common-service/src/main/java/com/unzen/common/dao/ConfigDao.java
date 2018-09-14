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


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.unzen.common.core.persist.entity.ConfigPO;


/**
 * @author langhsu
 *
 */
@Mapper
public interface ConfigDao{
	
	ConfigPO findByKey(String key);

	List<ConfigPO> findAll();

	void save(ConfigPO entity);
}
