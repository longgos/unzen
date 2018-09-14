package com.unzen.common.dao;

import org.apache.ibatis.annotations.Mapper;

import com.unzen.common.core.persist.entity.HelloEntity;

@Mapper
public interface HelloDao {

	HelloEntity get();

}
