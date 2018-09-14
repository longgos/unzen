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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unzen.common.core.persist.entity.UserPO;
import com.unzen.common.core.persist.param.UserParam;
import com.unzen.common.dao.UserDao;
import com.unzen.common.service.UserEventService;

/**
 * @author langhsu on 2015/8/6.
 */
@Service
public class UserEventServiceImpl implements UserEventService {
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public void identityPost(Long userId, long postId, boolean identity) {
    	UserParam param = new UserParam();
		param.setId(userId);
		UserPO po = userDao.get(param);
//        UserPO po = userDao.get(userId);

        if (po != null) {
            po.setPosts(po.getPosts() + ((identity) ? 1 : -1));
            userDao.save(po);
        }
    }

    @Override
    @Transactional
    public void identityComment(Long userId, long commentId, boolean identity) {
    	UserParam param = new UserParam();
		param.setId(userId);
		UserPO po = userDao.get(param);
//        UserPO po = userDao.get(userId);

        if (po != null) {
            po.setComments(po.getComments() + ((identity) ? 1 : -1));
            userDao.save(po);
        }
    }

    @Override
    @Transactional
    public void identityFollow(Long userId, long followId, boolean identity) {
    	UserParam param = new UserParam();
		param.setId(userId);
		UserPO po = userDao.get(param);
//        UserPO po = userDao.get(userId);

        if (po != null) {
            po.setFollows(po.getFollows() + ((identity) ? 1 : -1));
            userDao.save(po);
        }
    }

    @Override
    @Transactional
    public void identityFans(Long userId, long fansId, boolean identity) {
    	UserParam param = new UserParam();
		param.setId(userId);
		UserPO po = userDao.get(param);
//        UserPO po = userDao.get(userId);

        if (po != null) {
            po.setFans(po.getFans() + ((identity) ? 1 : -1));
            userDao.save(po);
        }
    }

    @Override
    @Transactional
    public void identityFavors(Long userId, boolean identity, int targetType, long targetId) {
    	UserParam param = new UserParam();
		param.setId(userId);
		UserPO po = userDao.get(param);
//        UserPO po = userDao.get(userId);

        if (po != null) {
            po.setFavors(po.getFavors() + ((identity) ? 1 : -1));
            userDao.save(po);
        }
    }
}
