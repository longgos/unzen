/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.unzen.common.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.data.Feeds;
import com.unzen.common.core.data.Post;
import com.unzen.common.core.persist.entity.FeedsPO;
import com.unzen.common.core.persist.utils.BeanMapUtils;
import com.unzen.common.core.persist.utils.PageModel;
import com.unzen.common.dao.FeedsDao;
import com.unzen.common.service.FeedsService;
import com.unzen.common.service.PostService;


/**
 * @author langhsu
 *
 */
@Service
public class FeedsServiceImpl implements FeedsService {
	@Autowired
	private FeedsDao feedsDao;
	@Autowired
	private PostService postService;

	@Override
	@Transactional
	public int add(Feeds feeds) {
		FeedsPO po = new FeedsPO();
		BeanUtils.copyProperties(feeds, po);

		po.setCreated(new Date());

		// 给自己保存一条
		feedsDao.save(po);

		// 派发给粉丝
		int count = feedsDao.batchAdd(feeds);
		return count;
	}

	@Override
	@Transactional
	public int deleteByAuthorId(Long ownId, Long authorId) {
		return feedsDao.deleteAllByOwnIdAndAuthorId(ownId, authorId);
	}

	/*@Override
	@Transactional(readOnly = true)
	public Page<Feeds> findUserFeeds(Pageable pageable, Long ownId) {
		Page<FeedsPO> page = feedsDao.findAllByOwnIdOrderByIdDesc(pageable, ownId);

		List<Feeds> rets = new ArrayList<>();

		Set<Long> postIds = new HashSet<>();

		for (FeedsPO po : page.getContent()) {
			Feeds f = BeanMapUtils.copy(po);
			rets.add(f);

			postIds.add(f.getPostId());
		}

		// 加载文章
		Map<Long, Post> postMap = postService.findMapByIds(postIds);

		for (Feeds f : rets) {
			f.setPost(postMap.get(f.getPostId()));
		}
		return new PageImpl<>(rets, pageable, page.getTotalElements());
	}*/
	
	/**
	 * 暂行方法↑
	 */
	@Override
	public PageModel<Feeds> findPage(Feeds feeds) {
		/** 所属用户动态 */
//		DataPageModel<FeedsPO> page =
//		PageHelper.startPage(1, 3);
		List<FeedsPO> p = feedsDao.findPage(feeds);
		List<Feeds> rets = new ArrayList<>();
		Set<Long> postIds = new HashSet<>();

		for (FeedsPO po : p) {
			Feeds f = BeanMapUtils.copy(po);
			rets.add(f);

			postIds.add(f.getPostId());
		}

		// 加载文章
		Map<Long, Post> postMap = postService.findMapByIds(postIds);

		for (Feeds f : rets) {
			f.setPost(postMap.get(f.getPostId()));
		}
		PageModel<Feeds> pageFeeds = new PageModel<Feeds>();
		pageFeeds.setDatas(rets);
		return pageFeeds;
	}
	
	@Override
	@Transactional
	public void deleteByTarget(Long postId) {
		feedsDao.deleteAllByPostId(postId);
	}

	@Override
	public DataPageModel<Feeds> findUserFeeds(Feeds f) {
		return feedsDao.findUserFeeds(f);
	}

	

}
