package com.unzen.common.service;

import java.util.List;

import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.data.Role;


public interface RoleService {
	
//	Page<Role> paging(Pageable pageable);
	DataPageModel<Role> paging();
	
	Role get(Long id);
	
	void save(Role role);
	
	void delete(Long id);
	
	List<Role> getAll();

}
