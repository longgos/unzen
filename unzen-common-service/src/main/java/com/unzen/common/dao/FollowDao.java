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

import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.persist.entity.FollowPO;
import com.unzen.common.core.persist.entity.UserPO;


/**
 * @author langhsu
 * 
 */
@Mapper
public interface FollowDao {

	/**
	 * 检查是否已关注
	 * 
	 * @param user
	 * @param follow
	 * @return
	 */
	FollowPO findByUserAndFollow(UserPO user, UserPO follow);

	/**
	 * 查询我的关注
	 *
	 * @param pageable
	 * @param user
	 */
	DataPageModel<FollowPO> findAllByUser(UserPO user);

	/**
	 * 查询关注我的 (我的粉丝)
	 * 
	 * @param pageable
	 * @param follow
	 */
	DataPageModel<FollowPO> findAllByFollow(UserPO follow);

	/**
	 * 检查是否相互关注
	 * 
	 * @param userId
	 * @param followId
	 * @return
	 */
//	@Query("from FollowPO f1 where f1.user.id = :userId and f1.follow.id = :followId and f1.user.id in (select f2.follow.id from FollowPO f2 where f2.user.id = :followId)")
//	List<FollowPO> findAllCrossFollow(@Param("userId") long userId, @Param("followId") long followId);
	/** 上方挂起↑重要 */
	List<FollowPO> findAllCrossFollow(long userId,long followId);
	
	/**
	 * 取消关注
	 * 
	 * @param user
	 * @param follow
	 * @return
	 */
	int deleteByUserAndFollow(UserPO user, UserPO follow);

	void save(FollowPO po);
}
