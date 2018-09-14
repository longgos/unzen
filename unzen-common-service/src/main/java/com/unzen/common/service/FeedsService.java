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

import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.data.Feeds;
import com.unzen.common.core.persist.utils.PageModel;

/**
 * @author langhsu
 *
 */
public interface FeedsService {
	/**
	 * 添加动态, 同时会分发给粉丝
	 *
	 * @param feeds
	 * @return
	 */
	int add(Feeds feeds);

	/**
	 * 删除动态，取消关注时，删除之前此人的动态
	 *
	 * @param ownId
	 * @param authorId
	 * @return
	 */
	int deleteByAuthorId(Long ownId, Long authorId);

//	Page<Feeds> findUserFeeds(Pageable pageable, Long ownId);
	PageModel<Feeds> findPage(Feeds feeds);

	/**
	 * 删除文章时触发动态删除
	 *
	 * @param postId
	 */
	void deleteByTarget(Long postId);

	/**
	 * 所用用户动态信息
	 * @param f
	 * @return
	 */
	DataPageModel<Feeds> findUserFeeds(Feeds f);
}
