package com.unzen.base.utils.model;

import java.io.Serializable;
import java.util.List;

public class DataPageModel<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private int total;

	private List<T> datas;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

}
