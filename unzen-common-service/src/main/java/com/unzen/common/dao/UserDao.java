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
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;

import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.persist.entity.RolePO;
import com.unzen.common.core.persist.entity.UserPO;
import com.unzen.common.core.persist.param.UserParam;


/**
 * @author langhsu
 */
@Mapper
public interface UserDao {
    UserPO get(UserParam param);

//    UserPO findByEmail(String email);
    
    List<UserPO> findTop12ByOrderByFansDesc();

    List<UserPO> findAll();

    List<UserPO> findAllByIdIn(HashMap<String, Object> map);

//	UserPO get(Long id);

	void save(UserPO po);

	int update(UserPO po);

	List<RolePO> getRoles();

}
