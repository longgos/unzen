/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.unzen.common.core.persist.entity;

import java.util.Date;

/**
 * 我的关注
 * 
 * @author langhsu
 * 
 */
public class FollowPO {
	private long id;

	/**
	 * 所属用户Id
	 */
	private UserPO user;

	/**
	 * 关注用户Id
	 */
	private UserPO follow;

	private Date created;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserPO getUser() {
		return user;
	}

	public void setUser(UserPO user) {
		this.user = user;
	}

	public UserPO getFollow() {
		return follow;
	}

	public void setFollow(UserPO follow) {
		this.follow = follow;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
}
