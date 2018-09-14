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

import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.data.User;

/**
 * @author langhsu
 *
 */
public interface FollowService {
	/**
	 * 关注某用户
	 * @param userId
	 * @param followId
	 * @return
	 */
	long follow(long userId, long followId);

	/**
	 * 取消关注某用户
	 * @param userId
	 * @param followId
	 */
	void unfollow(long userId, long followId);

	/**
	 * 查询我的关注
	 *
	 * @param pageable
	 * @param user
	 */
//	Page<User> follows(Pageable pageable, long userId);

	DataPageModel<User> findMyFollow(User user);
	/**
	 * 查询关注我的 (我的粉丝)
	 *
	 * @param pageable
	 * @param user
	 */
//	Page<User> fans(Pageable pageable, long userId);

	DataPageModel<User> findFollowMe(User user);
	/**
	 * 检查是否已关注
	 *
	 * @param userId
	 * @param followId
	 * @return true:已关注
	 */
	boolean checkFollow(long userId, long followId);

	/**
	 * 检查是否相互关注
	 *
	 * @param userId
	 * @param targetUserId
	 * @return true:相互关注
	 */
	boolean checkCrossFollow(long userId, long targetUserId);


}
