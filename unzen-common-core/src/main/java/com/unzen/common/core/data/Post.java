/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.unzen.common.core.data;


import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.unzen.base.lang.Consts;
import com.unzen.base.utils.model.Pager;
import com.unzen.common.core.persist.entity.PostAttributePO;
import com.unzen.common.core.persist.entity.PostPO;

/**
 * @author langhsu
 * 
 */
public class Post extends PostPO implements Serializable {
	private static final long serialVersionUID = -1144627551517707139L;

	/** 内容 */
	private String content;
	/** 用户信息  */
	private User author;
	/** 博客类别信息 */
	private Channel channel;
	/** 博客内容详情 */
	@JSONField(serialize = false)
	private PostAttributePO attribute;
	
	public String[] getTagsArray() {
		if (StringUtils.isNotBlank(super.getTags())) {
			return super.getTags().split(Consts.SEPARATOR);
		}
		return null;
	}
	private Pager pager = new Pager();
	
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public PostAttributePO getAttribute() {
		return attribute;
	}

	public void setAttribute(PostAttributePO attribute) {
		this.attribute = attribute;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}
	
	
}
