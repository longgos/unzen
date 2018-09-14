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

import com.unzen.common.core.persist.entity.OpenOauthPO;


/**
 * 第三方开发授权登录
 * @author langhsu on 2015/8/12.
 */
@Mapper
public interface OpenOauthDao {
    OpenOauthPO findByAccessToken(String accessToken);

    OpenOauthPO findByOauthUserId(String oauthUserId);
    
    OpenOauthPO findByUserId(long userId);

	void save(OpenOauthPO po);
}
