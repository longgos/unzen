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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.unzen.base.context.SpringContextHolder;
import com.unzen.base.lang.Consts;
import com.unzen.base.lang.EntityStatus;
import com.unzen.base.utils.PreviewTextUtils;
import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.data.Channel;
import com.unzen.common.core.data.Post;
import com.unzen.common.core.data.User;
import com.unzen.common.core.persist.entity.PostAttributePO;
import com.unzen.common.core.persist.entity.PostPO;
import com.unzen.common.core.persist.param.PostParam;
import com.unzen.common.core.persist.utils.BeanMapUtils;
import com.unzen.common.core.persist.utils.MybatisBeanUtils;
import com.unzen.common.core.persist.utils.PageModel;
import com.unzen.common.dao.PostDao;
import com.unzen.common.event.FeedsEvent;
import com.unzen.common.service.ChannelService;
import com.unzen.common.service.FavorService;
import com.unzen.common.service.PostService;
import com.unzen.common.service.UserEventService;
import com.unzen.common.service.UserService;


/**
 * @author langhsu
 *
 */
@Service
@Transactional(readOnly = true)
@CacheConfig(cacheNames = "postsCaches")
public class PostServiceImpl  extends MybatisBeanUtils implements PostService{
	@Autowired
	private PostDao postDao;
	@Autowired
	private UserService userService;
	@Autowired
	private UserEventService userEventService;
	@Autowired
	private FavorService favorService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private PostAttributeServiceImpl postAttributeService;

	/**
	 * 分页查询所有博客
	 */
	/*@Override
	@Cacheable
	public Page<Post> paging(Pageable pageable, int channelId, String ord) {
		Page<PostPO> page = postDao.findAll((root, query, builder) -> {

			List<Order> orders = new ArrayList<>();

			if (Consts.order.FAVOR.equals(ord)) {
				orders.add(builder.desc(root.<Long>get("favors")));
			} else if (Consts.order.HOTTEST.equals(ord)) {
				orders.add(builder.desc(root.<Long>get("comments")));
			} else {
				orders.add(builder.desc(root.<Long>get("weight")));
			}
			orders.add(builder.desc(root.<Long>get("created")));

			Predicate predicate = builder.conjunction();

			if (channelId > Consts.ZERO) {
				predicate.getExpressions().add(
						builder.equal(root.get("channelId").as(Integer.class), channelId));
			}

			if (Consts.order.HOTTEST.equals(ord)) {
				orders.add(builder.desc(root.<Long>get("views")));
			}

//			predicate.getExpressions().add(
//					builder.equal(root.get("featured").as(Integer.class), Consts.FEATURED_DEFAULT));

			query.orderBy(orders);

			return predicate;
		}, pageable);

		return new PageImpl<>(toPosts(page.getContent()), pageable, page.getTotalElements());
	}*/

	/**
	 * 上面原方法先行挂起，本法有调用错误，待后期重新编写业务逻辑
	 */
	@Override
	public PageModel<Post> paging(PostParam param) {
		List<PostPO> relList = postDao.findAll(param);
		PageModel<PostPO> page = setPageModel(relList);
		List<Post> list = toPosts(page.getDatas());
		PageModel<Post> post = new PageModel<Post>();
		post.setDatas(list);
		post.setTotal(list.size());
		return post;
	}
	
	/*@Override
	public Page<Post> paging4Admin(PostParam param) {
		Page<PostPO> page = new Page<>();
		List<PostPO> postList = postDao.findAll(param);
		List<Post> list = toPosts(page.getDatas());
		Page<Post> post = new Page<Post>();
		post.setDatas(list);
		return post;
	}*/

	/**
	 * 上面原方法先行挂起，本法有调用错误，待后期重新编写业务逻辑
	 */
	/*@Override
	public DataPageModel<Post> paging4Admin(long id, String title, int channelId) {
		DataPageModel<PostPO> page = postDao.findAll((root, query, builder) -> {
            query.orderBy(
					builder.desc(root.<Long>get("weight")),
					builder.desc(root.<Long>get("created"))
			);

            Predicate predicate = builder.conjunction();

			if (channelId > Consts.ZERO) {
				predicate.getExpressions().add(
						builder.equal(root.get("channelId").as(Integer.class), channelId));
			}

			if (StringUtils.isNotBlank(title)) {
				predicate.getExpressions().add(
						builder.like(root.get("title").as(String.class), "%" + title + "%"));
			}

			if (id > Consts.ZERO) {
				predicate.getExpressions().add(
						builder.equal(root.get("id").as(Integer.class), id));
			}

            return predicate;
        }, pageable);
		return null;
	}*/

	
	/*@Override
	@Cacheable
	public Page<Post> pagingByAuthorId(Pageable pageable, long userId) {
		Page<PostPO> page = postDao.findAllByAuthorIdOrderByCreatedDesc(pageable, userId);
		return new PageImpl<>(toPosts(page.getContent()), pageable, page.getTotalElements());
	}*/
	
	/**
	 * 暂行本方法(同逻辑异代码)↑
	 */
	@Override
	public PageModel<Post> pagingByAuthorId(PostParam param) {
		PageModel<PostPO> page = postDao.findAllByAuthorIdOrderByCreatedDesc(param);
		List<Post> list = toPosts(page.getDatas());
		PageModel<Post> postPage = new PageModel<Post>();
		postPage.setDatas(list);
		return postPage;
	}

	@Override
	@Cacheable
	public List<Post> findAllFeatured() {
		List<PostPO> list = postDao.findTop5ByFeaturedGreaterThanOrderByCreatedDesc(Consts.FEATURED_DEFAULT);
		return toPosts(list);
	}

	@Override
	public DataPageModel<Post> search(Post post) throws Exception {
		DataPageModel<Post> page = postDao.search(post);

		HashSet<Long> ids = new HashSet<>();
		HashSet<Long> uids = new HashSet<>();

		for (Post po : page.getDatas()) {
			ids.add(po.getId());
			uids.add(po.getAuthorId());
		}

		// 加载用户信息
		buildUsers(page.getDatas(), uids);

		return page;
	}
	
	@Override
	public DataPageModel<Post> searchByTag(Post post) {
		DataPageModel<Post> page = postDao.searchByTag(post);

		HashSet<Long> ids = new HashSet<>();
		HashSet<Long> uids = new HashSet<>();

		for (Post po : page.getDatas()) {
			ids.add(po.getId());
			uids.add(po.getAuthorId());
		}

		// 加载用户信息
		buildUsers(page.getDatas(), uids);
		return page;
	}
	
	@Override
	@Cacheable
	public List<Post> findLatests(int maxResults, long ignoreUserId) {
		List<PostPO> list = postDao.findTop10ByOrderByCreatedDesc();
		List<Post> rets = new ArrayList<>();
		/** jdk1.7不兼容1.8版本新循环格式。暂时挂起选用旧循环方式 */
		for (int i = 0; i < list.size(); i++) {
			 rets.add(BeanMapUtils.copy(list.get(i), 0));
		}
//		list.forEach(po -> rets.add(BeanMapUtils.copy(po, 0)));

		return rets;
	}
	
	@Override
	@Cacheable
	public List<Post> findHots(int maxResults, long ignoreUserId) {
		List<PostPO> list = postDao.findTop10ByOrderByViewsDesc();
		List<Post> rets = new ArrayList<>();
		/** jdk1.7不兼容1.8版本新循环格式。暂时挂起选用旧循环方式 */
		for (int i = 0; i < list.size(); i++) {
			rets.add(BeanMapUtils.copy(list.get(i), 0));
		}
//		list.forEach(po -> rets.add(BeanMapUtils.copy(po, 0)));
		return rets;
	}
	
	@Override
	public Map<Long, Post> findMapByIds(Set<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return Collections.emptyMap();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		List<PostPO> list = postDao.findAllByIdIn(map);
		Map<Long, Post> rets = new HashMap<>();
		HashSet<Long> imageIds = new HashSet<>();
		HashSet<Long> uids = new HashSet<>();
		/** jdk1.7不兼容1.8版本新循环格式。暂时挂起选用旧循环方式 */
		for (int i = 0; i < list.size(); i++) {
			rets.put(list.get(i).getId(), BeanMapUtils.copy(list.get(i), 0));
			uids.add(list.get(i).getAuthorId());
		}
	/*	list.forEach(po -> {
			rets.put(po.getId(), BeanMapUtils.copy(po, 0));
			uids.add(po.getAuthorId());
		});*/
		List<Post> postlist = new ArrayList<Post>();
		postlist.addAll(rets.values());
		// 加载用户信息
		buildUsers(postlist, uids);

		return rets;
	}

	@Override
	@Transactional
	@CacheEvict(allEntries = true)
	public long post(Post post) {
		PostPO po = new PostPO();

		BeanUtils.copyProperties(post, po);

		po.setCreated(new Date());
		po.setStatus(EntityStatus.ENABLED);

		// 处理摘要
		if (StringUtils.isBlank(post.getSummary())) {
			po.setSummary(trimSummary(post.getContent()));
		} else {
			po.setSummary(post.getSummary());
		}

		postDao.save(po);

		PostAttributePO attr = new PostAttributePO();
		attr.setContent(post.getContent());
		attr.setId(po.getId());
		submitAttr(attr);
		
		// 更新文章统计
		userEventService.identityPost(po.getAuthorId(), po.getId(), true);

		FeedsEvent event = new FeedsEvent("feedsEvent");
		event.setPostId(po.getId());
		event.setAuthorId(post.getAuthorId());
		SpringContextHolder.publishEvent(event);

		return po.getId();
	}
	
	@Override
	@Cacheable(key = "'view_' + #id")
	public Post get(Long id) {
		PostPO po = postDao.get(id);
		Post d = null;
		if (po != null) {
			d = BeanMapUtils.copy(po, 1);

			d.setAuthor(userService.get(d.getAuthorId()));

			d.setChannel(channelService.get(d.getChannelId()));

			PostAttributePO attr = postAttributeService.get(po.getId());
			if (attr != null) {
				d.setContent(attr.getContent());
			}
		}
		return d;
	}

	/**
	 * 更新文章方法
	 * @param p
	 */
	@Override
	@Transactional(readOnly= false)
	@CacheEvict(allEntries = true)
	public void update(Post p){
		PostPO po = postDao.get(p.getId());

		if (po != null) {
			po.setTitle(p.getTitle());//标题
			po.setChannelId(p.getChannelId());

			// 处理摘要
			if (StringUtils.isBlank(p.getSummary())) {
				po.setSummary(trimSummary(p.getContent()));
			} else {
				po.setSummary(p.getSummary());
			}

			po.setTags(p.getTags());//标签
			postDao.update(po);
			// 保存扩展
			PostAttributePO attr = new PostAttributePO();
			attr.setContent(p.getContent());
			attr.setId(po.getId());
			submitAttr(attr);
		}
	}

	@Override
	@Transactional
	@CacheEvict(allEntries = true)
	public void updateFeatured(long id, int featured) {
		PostPO po = postDao.get(id);

		if (po != null) {
			int status = Consts.FEATURED_ACTIVE == featured ? Consts.FEATURED_ACTIVE: Consts.FEATURED_DEFAULT;
			po.setFeatured(status);
			save(po);
		}
	}

	@Override
	@Transactional
	@CacheEvict(allEntries = true)
	public void updateWeight(long id, int weight) {
		PostPO po = postDao.get(id);

		if (po != null) {
			int max = weight;
			if (Consts.FEATURED_ACTIVE == weight) {
				max = postDao.maxWeight() + 1;
			}
			po.setWeight(max);
			save(po);
		}
	}

	@Override
	@Transactional
	@CacheEvict(allEntries = true)
	/** 123213 */
	public void delete(long id) {
		PostPO po = postDao.get(id);
		if (po != null) {
			postDao.delete(po);
		}
	}
	
	@Override
	@Transactional
	@CacheEvict(allEntries = true)
	public void delete(long id, long authorId) {
		PostPO po = postDao.get(id);
		if (po != null) {
			// 判断文章是否属于当前登录用户
			Assert.isTrue(po.getAuthorId() == authorId, "认证失败");

			delete(id);
		}
	}

	@Override
	@Transactional
	@CacheEvict(allEntries = true)
	public void delete(List<Long> ids) {
		/** jdk1.7不兼容1.8版本新循环格式。暂时挂起选用旧循环方式 */
		for (int i = 0; i < ids.size(); i++) {
			this.delete(ids.get(i));
		}
//		ids.forEach(this::delete);
	}

	@Override
	@Transactional
	public void identityViews(long id) {
		PostPO po = postDao.get(id);
		if (po != null) {
			po.setViews(po.getViews() + Consts.IDENTITY_STEP);
			postDao.save(po);
		}
	}

	@Override
	@Transactional
	public void identityComments(long id) {
		PostPO po = postDao.get(id);
		if (po != null) {
			po.setComments(po.getComments() + Consts.IDENTITY_STEP);
			postDao.save(po);
		}
	}

	@Override
	@Transactional
	@CacheEvict(key = "'view_' + #postId")
	public void favor(long userId, long postId) {
		PostPO po = postDao.get(postId);

		Assert.notNull(po, "文章不存在");

		favorService.add(userId, postId);

		po.setFavors(po.getFavors() + Consts.IDENTITY_STEP);
	}

	@Override
	@Transactional
	@CacheEvict(key = "'view_' + #postId")
	public void unfavor(long userId, long postId) {
		PostPO po = postDao.get(postId);

		Assert.notNull(po, "文章不存在");

		favorService.delete(userId, postId);

		po.setFavors(po.getFavors() - Consts.IDENTITY_STEP);
	}
	
	@Override
	@Transactional
	public void resetIndexs() {
		postDao.resetIndexs();
	}

	/**
	 * 截取文章内容
	 * @param text
	 * @return
	 */
	private String trimSummary(String text){
		return PreviewTextUtils.getText(text, 126);
	}

	private List<Post> toPosts(List<PostPO> posts) {
		List<Post> rets = new ArrayList<>();

		HashSet<Long> pids = new HashSet<>();
		HashSet<Long> uids = new HashSet<>();
		HashSet<Integer> groupIds = new HashSet<>();
		/** jdk1.7不兼容1.8版本新循环格式。暂时挂起选用旧循环方式 */
		for (int i = 0; i < posts.size(); i++) {
			pids.add(posts.get(i).getId());
			uids.add(posts.get(i).getAuthorId());
			groupIds.add(posts.get(i).getChannelId());
			rets.add(BeanMapUtils.copy(posts.get(i), 0));
			PostAttributePO att = postAttributeService.get(posts.get(i).getId());
			if(att!= null) rets.get(i).setContent(att.getContent());
		}
		/*posts.forEach(po -> {
			pids.add(po.getId());
			uids.add(po.getAuthorId());
			groupIds.add(po.getChannelId());

			rets.add(BeanMapUtils.copy(po, 0));
		});*/

		// 加载用户信息
		buildUsers(rets, uids);
		buildGroups(rets, groupIds);

		return rets;
	}

	/**
	 * 绑定用户
	 * @param posts
	 * @param uids
	 */
	private void buildUsers(List<Post> posts, Set<Long> uids) {
		Map<Long, User> userMap = userService.findMapByIds(uids);
		/** jdk1.7不兼容1.8版本新循环格式。暂时挂起选用旧循环方式 */
		for (int i = 0; i < posts.size(); i++) {
			posts.get(i).setAuthor(userMap.get(posts.get(i).getAuthorId()));
		}
//		posts.forEach(p -> p.setAuthor(userMap.get(p.getAuthorId())));
	}

	/**
	 * 内容信息
	 * @param posts
	 * @param groupIds
	 */
	private void buildGroups(List<Post> posts, Set<Integer> groupIds) {
		Map<Integer, Channel> map = channelService.findMapByIds(groupIds);
		/** jdk1.7不兼容1.8版本新循环格式。暂时挂起选用旧循环方式 */
		for (int i = 0; i < posts.size(); i++) {
			posts.get(i).setChannel(map.get(posts.get(i).getChannelId()));
		}
//		posts.forEach(p -> p.setChannel(map.get(p.getChannelId())));
	}

	private void submitAttr(PostAttributePO attr) {
		postAttributeService.save(attr);
	}

	@Transactional(readOnly= false)
	@Override
	public int save(PostPO po){
		if(po.getId()== null){
			return postDao.insert(po);
		}else{
			return postDao.update(po);
		}
	}

	@Override
	public PostPO getById(Long id) {
		return postDao.get(id);
	}

	


	
}
