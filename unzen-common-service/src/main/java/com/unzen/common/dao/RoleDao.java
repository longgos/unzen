package com.unzen.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.persist.entity.RolePO;


@Mapper
public interface RoleDao {
	DataPageModel<RolePO> findAllByOrderByIdDesc();

	RolePO selectOne(Long id);

	void save(RolePO rolePO);

	void delete(Long id);

	List<RolePO> findAll();

	RolePO findOne(Long roleId);

	RolePO get(Long roleId);

}
