package com.unzen.base.utils.model;

import java.io.Serializable;

/**
 * 限制长度查询参数
 * @author hufeng
 */
public class Limiter implements Serializable {

	private static final long serialVersionUID = -7662721818939098646L;
	/**
	 * 偏移量
	 */
	private Integer offset = 0;
	
	/**
	 * 长度限制
	 */
	private Integer limit = 0;
	
	public Limiter() {
		super();
	}

	public Limiter(Integer offset, Integer limit) {
		super();
		this.offset = offset;
		this.limit = limit;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
		if (null == this.offset) {
			this.offset = 0;
		}
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		if (null == this.limit) {
			this.limit = 0;
		}
		this.limit = limit;
	}
	
	
}
