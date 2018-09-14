package com.unzen.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.unzen.common.core.data.AuthMenu;
import com.unzen.common.core.persist.entity.AuthMenuPO;
import com.unzen.common.core.persist.param.AuthMenuParam;


@Mapper
public interface AuthMenuDao  {
	
    List<AuthMenuPO> findAllByParentIdOrderBySortAsc(Long parentId);
    
    List<AuthMenuPO> findAll();
    
    AuthMenuPO get(@Param(value="id") Long id );
    
	int save(AuthMenuPO po);
	
	int delete(AuthMenuPO authMenuPO);

	List<AuthMenu> findList(AuthMenuParam param);

	List<AuthMenuPO> findAuthMenuByRole(int roleId);
}
