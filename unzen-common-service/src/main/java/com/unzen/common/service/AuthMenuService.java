package com.unzen.common.service;

import java.util.List;

import com.unzen.common.core.data.AuthMenu;
import com.unzen.common.core.persist.entity.AuthMenuPO;
import com.unzen.common.core.persist.param.AuthMenuParam;


public interface AuthMenuService {
	
	List<AuthMenu> findByParentId(long parentId);

	List<AuthMenu> tree(Long id);

	List<AuthMenu> listAll();

	List<AuthMenu> findList(AuthMenuParam param);
	
	AuthMenu get(Long id);

	void save(AuthMenu authMenu);

	void delete(Long id);

	List<AuthMenuPO> findAuthMenuByRole(int roleId);
}
