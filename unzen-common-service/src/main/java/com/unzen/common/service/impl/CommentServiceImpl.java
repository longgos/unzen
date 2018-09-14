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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.data.Comment;
import com.unzen.common.core.data.Post;
import com.unzen.common.core.data.User;
import com.unzen.common.core.persist.entity.CommentPO;
import com.unzen.common.core.persist.param.PostParam;
import com.unzen.common.core.persist.utils.BeanMapUtils;
import com.unzen.common.core.persist.utils.MybatisBeanUtils;
import com.unzen.common.core.persist.utils.PageModel;
import com.unzen.common.dao.CommentDao;
import com.unzen.common.service.CommentService;
import com.unzen.common.service.PostService;
import com.unzen.common.service.UserEventService;
import com.unzen.common.service.UserService;


/**
 * @author langhsu
 *
 */
@Service
@Transactional(readOnly = true)
@CacheConfig(cacheNames = "commentsCaches")
public class CommentServiceImpl extends MybatisBeanUtils implements CommentService {
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private UserService userService;
	@Autowired
	private UserEventService userEventService;
	@Autowired
	private PostService postService;
	
	/*@Override
	public Page<Comment> paging4Admin(Pageable pageable) {
		Page<CommentPO> page = commentDao.findAll(pageable);
		List<Comment> rets = new ArrayList<>();

		HashSet<Long> uids= new HashSet<>();

		page.getContent().forEach(po -> {
			uids.add(po.getAuthorId());
			rets.add(BeanMapUtils.copy(po));
		});

		buildUsers(rets, uids);

		return new PageImpl<>(rets, pageable, page.getTotalElements());
	}*/

	/**
	 * 暂行方法 ↑
	 */
	@Override
	public PageModel<Comment> findPageAdmin() {
		List<CommentPO> list = commentDao.findAll();
		List<Comment> rets = new ArrayList<>();
		
		HashSet<Long> uids= new HashSet<>();
		/** jdk1.7不兼容1.8版本新循环格式。暂时挂起选用旧循环方式 */
		for (int i = 0; i < list.size(); i++) {
			uids.add(list.get(i).getAuthorId());
			rets.add(BeanMapUtils.copy(list.get(i)));
		}
		/*page.getDatas().forEach(po -> {
			uids.add(po.getAuthorId());
			rets.add(BeanMapUtils.copy(po));
		});*/
		buildUsers(rets, uids);
		PageModel<Comment> page = setPageModel(rets);
		return page;
	}

	/*@Override
	@Cacheable
	public Page<Comment> paging4Home(Pageable pageable, long authorId) {
		Page<CommentPO> page = commentDao.findAllByAuthorIdOrderByCreatedDesc(pageable, authorId);

		List<Comment> rets = new ArrayList<>();
		Set<Long> parentIds = new HashSet<>();
		Set<Long> uids = new HashSet<>();
		Set<Long> postIds = new HashSet<>();

		page.getContent().forEach(po -> {
			Comment c = BeanMapUtils.copy(po);

			if (c.getPid() > 0) {
				parentIds.add(c.getPid());
			}
			uids.add(c.getAuthorId());
			postIds.add(c.getToId());

			rets.add(c);
		});

		// 加载父节点
		buildParent(rets, parentIds);

		buildUsers(rets, uids);
		buildPosts(rets, postIds);

		return new PageImpl<>(rets, pageable, page.getTotalElements());
	}*/
	/**
	 * 暂行方法 ↑
	 */
	@Override
	public DataPageModel<Comment> findPageHome(PostParam post) {
		DataPageModel<CommentPO> page = commentDao.findAllByAuthorIdOrderByCreatedDesc(post);

		List<Comment> rets = new ArrayList<>();
		Set<Long> parentIds = new HashSet<>();
		Set<Long> uids = new HashSet<>();
		Set<Long> postIds = new HashSet<>();
		/** jdk1.7不兼容1.8版本新循环格式。暂时挂起选用旧循环方式 */
		List<CommentPO> datas = page.getDatas();
		for (int i = 0; i < datas.size(); i++) {
			Comment c = BeanMapUtils.copy(datas.get(i));

			if (c.getPid() > 0) {
				parentIds.add(c.getPid());
			}
			uids.add(c.getAuthorId());
			postIds.add(c.getToId());

			rets.add(c);
		}
		/*page.getDatas().forEach(po -> {
			Comment c = BeanMapUtils.copy(po);

			if (c.getPid() > 0) {
				parentIds.add(c.getPid());
			}
			uids.add(c.getAuthorId());
			postIds.add(c.getToId());

			rets.add(c);
		});*/

		// 加载父节点
		buildParent(rets, parentIds);

		buildUsers(rets, uids);
		buildPosts(rets, postIds);
		return null;
	}

	/*@Override
	@Cacheable
	public Page<Comment> paging(Pageable pageable, long toId) {
		Page<CommentPO> page = commentDao.findAllByToIdOrderByCreatedDesc(pageable, toId);
		
		List<Comment> rets = new ArrayList<>();
		Set<Long> parentIds = new HashSet<>();
		Set<Long> uids = new HashSet<>();

		page.getContent().forEach(po -> {
			Comment c = BeanMapUtils.copy(po);

			if (c.getPid() > 0) {
				parentIds.add(c.getPid());
			}
			uids.add(c.getAuthorId());

			rets.add(c);
		});

		// 加载父节点
		buildParent(rets, parentIds);

		buildUsers(rets, uids);

		return new PageImpl<>(rets, pageable, page.getTotalElements());
	}*/
	
	/**
	 * 暂行方法 ↑
	 */
	@Override
	public DataPageModel<Comment> paging(Comment comment) {
		CommentPO commentpo = new CommentPO();
		commentpo.setToId(comment.getToId());
		DataPageModel<CommentPO> page = commentDao.findAllByToIdOrderByCreatedDesc(commentpo);
		List<Comment> rets = new ArrayList<>();
		Set<Long> parentIds = new HashSet<>();
		Set<Long> uids = new HashSet<>();
		/** jdk1.7不兼容1.8版本新循环格式。暂时挂起选用旧循环方式 */
		List<CommentPO> datas = page.getDatas();
		for (int i = 0; i < datas.size(); i++) {
			Comment c = BeanMapUtils.copy(datas.get(i));

			if (c.getPid() > 0) {
				parentIds.add(c.getPid());
			}
			uids.add(c.getAuthorId());

			rets.add(c);
		}
		/*page.getDatas().forEach(po -> {
			Comment c = BeanMapUtils.copy(po);

			if (c.getPid() > 0) {
				parentIds.add(c.getPid());
			}
			uids.add(c.getAuthorId());

			rets.add(c);
		});*/

		// 加载父节点
		buildParent(rets, parentIds);

		buildUsers(rets, uids);
		DataPageModel<Comment> dataComment = new DataPageModel<>();
		dataComment.setDatas(rets);
		return dataComment;
	}

	@Override
	public Map<Long, Comment> findByIds(Set<Long> ids) {
		List<CommentPO> list = commentDao.findByIdIn(ids);
		Map<Long, Comment> ret = new HashMap<>();
		Set<Long> uids = new HashSet<>();
		/** jdk1.7不兼容1.8版本新循环格式。暂时挂起选用旧循环方式 */
		for (int i = 0; i <list.size(); i++) {
			uids.add(list.get(i).getAuthorId());
			ret.put(list.get(i).getId(), BeanMapUtils.copy(list.get(i)));
		}
		/*list.forEach(po -> {
			uids.add(po.getAuthorId());
			ret.put(po.getId(), BeanMapUtils.copy(po));
		});*/
		List<Comment> rets = new ArrayList<Comment>();
		rets.addAll(ret.values());
		buildUsers(rets, uids);
		return ret;
	}

	@Override
	@Transactional
	@CacheEvict(allEntries = true)
	public long post(Comment comment) {
		CommentPO po = new CommentPO();
		
		po.setAuthorId(comment.getAuthorId());
		po.setToId(comment.getToId());
		po.setContent(comment.getContent());
		po.setCreated(new Date());
		po.setPid(comment.getPid());
		commentDao.save(po);

		userEventService.identityComment(comment.getAuthorId(), po.getId(), true);
		return po.getId();
	}

	@Override
	@Transactional
	@CacheEvict(allEntries = true)
	public void delete(List<Long> ids) {
		commentDao.deleteAllByIdIn(ids);
	}

	@Override
	@Transactional
	@CacheEvict(allEntries = true)
	public void delete(long id, long authorId) {
		CommentPO po = commentDao.selectOne(id);
		if (po != null) {
			// 判断文章是否属于当前登录用户
			Assert.isTrue(po.getAuthorId() == authorId, "认证失败");
			commentDao.delete(po);
		}
	}

	@Override
	@Transactional
	public List<CommentPO> findAllByAuthorIdAndToId(long authorId, long toId) {
		return commentDao.findAllByAuthorIdAndToIdOrderByCreatedDesc(authorId, toId);
	}

	private void buildUsers(List<Comment> posts, Set<Long> uids) {
		Map<Long, User> userMap = userService.findMapByIds(uids);
		/** jdk1.7不兼容1.8版本新循环格式。暂时挂起选用旧循环方式 */
		for (int i = 0; i < posts.size(); i++) {
			posts.get(i).getAuthor();
			posts.get(i).setAuthor(userMap.get(posts.get(i).getAuthorId()));
		}
		
//		posts.forEach(p -> p.setAuthor(userMap.get(p.getAuthorId())));
	}

	private void buildPosts(List<Comment> comments, Set<Long> postIds) {
		Map<Long, Post> postMap = postService.findMapByIds(postIds);
		/** jdk1.7不兼容1.8版本新循环格式。暂时挂起选用旧循环方式 */
		for (int i = 0; i < comments.size(); i++) {
			comments.get(i).setPost(postMap.get(comments.get(i).getToId()));
		}
//		comments.forEach(p -> p.setPost(postMap.get(p.getToId())));
	}

	private void buildParent(List<Comment> comments, Set<Long> parentIds) {
		if (!parentIds.isEmpty()) {
			Map<Long, Comment> pm = findByIds(parentIds);
			/** jdk1.7不兼容1.8版本新循环格式。暂时挂起选用旧循环方式 */
			for (int i = 0; i < comments.size(); i++) {
				if(comments.get(i).getPid()>0){
					comments.get(i).setParent(pm.get(comments.get(i).getPid()));
				}
			}
//			comments.forEach(c -> {
//				if (c.getPid() > 0) {
//					c.setParent(pm.get(c.getPid()));
//				}
//			});
		}
	}



}
