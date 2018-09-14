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
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.unzen.base.lang.MtonsException;
import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.data.User;
import com.unzen.common.core.persist.entity.FollowPO;
import com.unzen.common.core.persist.entity.UserPO;
import com.unzen.common.core.persist.utils.BeanMapUtils;
import com.unzen.common.dao.FollowDao;
import com.unzen.common.service.FollowService;
import com.unzen.common.service.UserEventService;


/**
 * @author langhsu
 *
 */
@Service
public class FollowServiceImpl implements FollowService {
	@Autowired
	private FollowDao followDao;
	@Autowired
	private UserEventService userEventService;

	@Override
	@Transactional
	public long follow(long userId, long followId) {
		long ret = 0;

		Assert.state(userId != followId, "您不能关注自己");

		FollowPO po = followDao.findByUserAndFollow(new UserPO(userId), new UserPO(followId));

		if (po == null) {
			po = new FollowPO();
			po.setUser(new UserPO(userId));
			po.setFollow(new UserPO(followId));
			po.setCreated(new Date());

			followDao.save(po);

			ret = po.getId();

			userEventService.identityFollow(userId, followId, true);
			userEventService.identityFans(followId, userId, true);
		} else {
			throw new MtonsException("您已经关注过此用户了");
		}
		return ret;
	}

	@Override
	@Transactional
	public void unfollow(long userId, long followId) {
		int ret = followDao.deleteByUserAndFollow(new UserPO(userId), new UserPO(followId));

		if (ret <= 0) {
			throw new MtonsException("取消关注失败");
		} else {
			userEventService.identityFollow(userId, followId, false);
			userEventService.identityFans(followId, userId, false);
		}
	}

	/*@Override
	@Transactional(readOnly = true)
	public Page<User> follows(Pageable pageable, long userId) {
		Page<FollowPO> page = followDao.findAllByUser(pageable, new UserPO(userId));
		List<User> rets = new ArrayList<>();

		for (FollowPO po : page.getContent()) {
			rets.add(BeanMapUtils.copy(po.getFollow(), 0));
		}
		return new PageImpl<>(rets, pageable, page.getTotalElements());
	}*/

	/**
	 * 暂行方法↑
	 */
	@Override
	@Transactional(readOnly = true)
	public DataPageModel<User> findMyFollow(User user) {
		UserPO userpo = new UserPO();
		userpo.setId(user.getId());
		DataPageModel<FollowPO> page = followDao.findAllByUser(userpo);
		List<User> rets = new ArrayList<>();

		for (FollowPO po : page.getDatas()) {
			rets.add(BeanMapUtils.copy(po.getFollow(), 0));
		}
		DataPageModel<User> users = new DataPageModel<User>();
		users.setDatas(rets);
		return users;
	}
	
	/*@Override
	@Transactional(readOnly = true)
	public Page<User> fans(Pageable pageable, long userId) {
		Page<FollowPO> page = followDao.findAllByFollow(pageable, new UserPO(userId));
		List<User> rets = new ArrayList<>();

		for (FollowPO po : page.getContent()) {
			rets.add(BeanMapUtils.copy(po.getUser(), 0));
		}

		return new PageImpl<>(rets, pageable, page.getTotalElements());
	}*/

	/**
	 * 暂行方法↑
	 */
	@Override
	@Transactional(readOnly = true)
	public DataPageModel<User> findFollowMe(User user) {
		DataPageModel<FollowPO> page = followDao.findAllByFollow(new UserPO(user.getId()));
		List<User> rets = new ArrayList<>();

		for (FollowPO po : page.getDatas()) {
			rets.add(BeanMapUtils.copy(po.getUser(), 0));
		}
		DataPageModel<User> users = new DataPageModel<>();
		users.setDatas(rets);
		return users;
	}
	
	@Override
	@Transactional
	public boolean checkFollow(long userId, long followId) {
		return (followDao.findByUserAndFollow(new UserPO(userId), new UserPO(followId)) != null);
	}

	@Override
	@Transactional
	public boolean checkCrossFollow(long userId, long targetUserId) {
		List<FollowPO> list = followDao.findAllCrossFollow(userId, targetUserId);
		return  list != null && list.size() > 0;
	}



}
