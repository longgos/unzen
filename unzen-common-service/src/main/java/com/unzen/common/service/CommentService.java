/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.unzen.common.service;


import java.util.List;
import java.util.Map;
import java.util.Set;

import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.data.Comment;
import com.unzen.common.core.persist.entity.CommentPO;
import com.unzen.common.core.persist.param.PostParam;
import com.unzen.common.core.persist.utils.PageModel;

/**
 * @author langhsu
 *
 */
public interface CommentService {
//	Page<Comment> paging4Admin(Pageable pageable);
	
	PageModel<Comment> findPageAdmin();
	

//	Page<Comment> paging4Home(Pageable pageable, long authorId);
	DataPageModel<Comment> findPageHome(PostParam post);
	/**
	 * 查询评论列表
	 * @param pageable
	 * @param comment
	 */
	
//	Page<Comment> paging(Pageable pageable, long toId);
	DataPageModel<Comment> paging(Comment comment);
	
	Map<Long, Comment> findByIds(Set<Long> ids);
	
	/**
	 * 发表评论
	 * @param comment
	 * @return
	 */
	long post(Comment comment);
	
	void delete(List<Long> ids);

	/**
	 * 带作者验证的删除
	 * @param id
	 * @param authorId
	 */
	void delete(long id, long authorId);

	List<CommentPO> findAllByAuthorIdAndToId(long authorId, long toId);
}
