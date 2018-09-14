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

import com.unzen.common.core.persist.entity.VerifyPO;


/**
 * @author langhsu on 2015/8/14.
 */

@Mapper
public interface VerifyDao {
	
    VerifyPO findByUserIdAndType(long userId, int type);
    
    VerifyPO findByUserId(long userId);
    
	void save(VerifyPO po);
}
