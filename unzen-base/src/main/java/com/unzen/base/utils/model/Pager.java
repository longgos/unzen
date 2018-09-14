package com.unzen.base.utils.model;

import com.github.pagehelper.PageHelper;

/**
 * 分页查询参数
 * mybatis会根据该参数返回DataPageModel 
 * @author hufeng
 */
public class Pager extends Limiter {
	
	private static final long serialVersionUID = -9117625236348839030L;
	/**
	 * 页面
	 */
	
	private Integer pageNo = 0;
	/**
	 * 页大小
	 */
	private Integer pageSize = 0;
	
	public Pager(Integer pageNo, Integer pageSize) {
		super();
		PageHelper.startPage(pageNo, pageSize);
		this.setPageNo(pageNo);
		this.setPageSize(pageSize);
	}
	
	public Pager() {
		PageHelper.startPage(pageNo, pageSize);
		this.setPageNo(1);
		this.setPageSize(10);
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		if (!isValidPageNo(pageNo)) {
			pageNo = 1;
		}
		this.pageNo = pageNo;
		super.setLimit(pageSize);
		super.setOffset((this.pageNo - 1) * this.pageSize);
	}

	private boolean isValidPageNo(Integer pageNo) {
		return null != pageNo && pageNo > 0;
	}
	
	private boolean isValidPageSize(Integer pageSize) {
		return null != pageSize && pageSize > 0;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		if (!isValidPageSize(pageSize)) {
			pageSize = 10;
		}
		this.pageSize = pageSize;
		super.setLimit(pageSize);
		super.setOffset((this.pageNo - 1) * this.pageSize);
	}


	@Override
	public void setOffset(Integer offset) {
		if (!isValidOffset(offset)) {
			offset = 0;
		}
		super.setOffset(offset);
		if (super.getLimit() != 0) {
			this.pageNo = (super.getOffset() / super.getLimit()) + 1;	
		} else {
			this.pageNo = 0;
		}
		
	}


	private boolean isValidOffset(Integer offset) {
		return null != offset && offset > 0;
	}

	@Override
	public void setLimit(Integer limit) {
		if (!isValidPageSize(limit)) {
			limit = 10;
		}
		super.setLimit(limit);
		this.pageSize = limit;
		if (super.getLimit() != 0) {
			this.pageNo = (super.getOffset() / super.getLimit()) + 1;
		} else {
			this.pageNo = 0;
		}
	}

 
}
