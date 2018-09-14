package com.unzen.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.unzen.common.core.persist.entity.PostAttributePO;


/**
 * Created by langhsu on 2017/9/27.
 */

@Mapper
public interface PostAttributeDao {

	PostAttributePO get(@Param(value="id") long id);

	void save(PostAttributePO attr);

	int insert(PostAttributePO attr);

	int update(PostAttributePO attr);

}
