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


import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.persist.entity.CommentPO;
import com.unzen.common.core.persist.param.PostParam;

/**
 * @author langhsu
 *
 */
@Mapper
public interface CommentDao {
	List<CommentPO> findAll();
	DataPageModel<CommentPO> findAllByToIdOrderByCreatedDesc(CommentPO po);
	//long authorId
	DataPageModel<CommentPO> findAllByAuthorIdOrderByCreatedDesc(PostParam post);
	List<CommentPO> findByIdIn(Set<Long> ids);
	List<CommentPO> findAllByAuthorIdAndToIdOrderByCreatedDesc(long authorId, long toId);

	int deleteAllByIdIn(@Param(value="ids") Collection<Long> ids);
	
	
	void save(CommentPO po);
	CommentPO selectOne(long id);
	void delete(CommentPO po);
}
