package com.unzen.common.core.persist.utils;

import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.Assert;


public class MybatisBeanUtils {

	public void findAll(Object param){
		Object pager ;
		try {
			pager = PropertyUtils.getProperty(param,"pager");
		} catch (Exception e) {
			throw new RuntimeException("未找到分页参数:pager", e);
		}
		Assert.notNull(pager,"pager不能为null");
	}
	
	public <T> PageModel<T> setPageModel(List<T> relList){ 
		PageModel<T> pageModel = new PageModel<>();
		pageModel.setDatas(relList);
		pageModel.setTotal(relList==null ? 0:relList.size());
		return pageModel;
	}
}
