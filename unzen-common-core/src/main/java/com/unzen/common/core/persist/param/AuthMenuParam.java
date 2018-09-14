package com.unzen.common.core.persist.param;

import java.io.Serializable;

import com.unzen.base.utils.model.Pager;


public class AuthMenuParam implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/** 作者Id */
	private String authorID; 
	/** 暂不知此id */
	private String toId;
	
	private Pager pager = new Pager();

	public String getAuthorID() {
		return authorID;
	}

	public void setAuthorID(String authorID) {
		this.authorID = authorID;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	
	
}
