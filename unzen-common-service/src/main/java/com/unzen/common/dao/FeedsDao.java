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

import org.apache.ibatis.annotations.Mapper;

import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.data.Feeds;
import com.unzen.common.core.persist.entity.FeedsPO;
import com.unzen.common.dao.custom.FeedsDaoCustom;


/**
 * @author langhsu
 *
 */
@Mapper
public interface FeedsDao extends FeedsDaoCustom {
	
	DataPageModel<FeedsPO> findAllByOwnIdOrderByIdDesc(long ownId);
	
	int deleteAllByOwnIdAndAuthorId(long ownId, long authorId);
	
	void deleteAllByPostId(long postId);

	void save(FeedsPO po);

	DataPageModel<Feeds> findUserFeeds(Feeds f);

	List<FeedsPO> findPage(Feeds feeds);
}
