package com.unzen.common.core.persist.param;

import com.unzen.base.utils.model.Pager;
import com.unzen.common.core.data.Post;

public class PostParam extends Post{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Pager pager = new Pager();
	
	private String ord;

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public String getOrd() {
		return ord;
	}

	public void setOrd(String ord) {
		this.ord = ord;
	}
	
	

}
