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
 * 所属用户动态
 * 
 * @author langhsu
 * 
 */
public class FeedsPO {
	private Long id;

	private Long ownId; // 所属用户Id

	private int type;

	private Long postId; // 目标Id

	private Long authorId; // 作者
	
	private Date created;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOwnId() {
		return ownId;
	}

	public void setOwnId(Long ownId) {
		this.ownId = ownId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
}
