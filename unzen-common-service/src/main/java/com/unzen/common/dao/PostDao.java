/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.unzen.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.persist.entity.PostPO;
import com.unzen.common.core.persist.param.PostParam;
import com.unzen.common.core.persist.utils.PageModel;
import com.unzen.common.dao.custom.PostDaoCustom;


/**
 * @author langhsu
 *
 */

@Mapper
public interface PostDao extends PostDaoCustom {
	/**
	 * 查询指定用户
	 * @param pageable
	 * @param authorId
	 * @return
	 */
	PageModel<PostPO> findAllByAuthorIdOrderByCreatedDesc(PostParam param);

	// findLatests
	List<PostPO> findTop10ByOrderByCreatedDesc();

	// findHots
	List<PostPO> findTop10ByOrderByViewsDesc();

	List<PostPO> findAllByIdIn(Map<String, Object> map);

	List<PostPO> findTop5ByFeaturedGreaterThanOrderByCreatedDesc(@Param(value="featured") int featured);

//	@Query("select coalesce(max(weight), 0) from PostPO")
	int maxWeight();

	List<PostPO> findAll(PostParam param);

	void save(PostPO po);

	//PostPO selectOne(long id);

	void delete(PostPO po);

	int insert(PostPO po);

	int update(PostPO po);

	//原因:Mybatis默认采用ONGL解析参数，所以会自动采用对象树的形式取 string.xxx 值，如果没在在方法中定义,则会抛异常报错
	PostPO get(@Param(value="id") Long id);


	
}
