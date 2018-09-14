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

import org.apache.ibatis.annotations.Mapper;

import com.unzen.common.core.persist.entity.LogPO;


/**
 * @author langhsu
 *
 */
@Mapper
public interface LogDao {

	void save(LogPO po);
}
