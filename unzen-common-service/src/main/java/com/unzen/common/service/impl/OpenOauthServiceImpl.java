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

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unzen.base.utils.MD5;
import com.unzen.common.core.data.OpenOauth;
import com.unzen.common.core.data.User;
import com.unzen.common.core.persist.entity.OpenOauthPO;
import com.unzen.common.core.persist.entity.UserPO;
import com.unzen.common.core.persist.param.UserParam;
import com.unzen.common.core.persist.utils.BeanMapUtils;
import com.unzen.common.dao.OpenOauthDao;
import com.unzen.common.dao.UserDao;
import com.unzen.common.service.OpenOauthService;


/**
 * 第三方登录授权管理
 * @author langhsu on 2015/8/12.
 */
@Service
public class OpenOauthServiceImpl implements OpenOauthService {
    @Autowired
    private OpenOauthDao openOauthDao;
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public User getUserByOauthToken(String oauth_token) {
        OpenOauthPO thirdToken = openOauthDao.findByAccessToken(oauth_token);
    	UserParam param = new UserParam();
		param.setId(thirdToken.getId());
		UserPO userPO = userDao.get(param);
        
//        UserPO userPO = userDao.get(thirdToken.getId());
        return BeanMapUtils.copy(userPO, 0);
    }

    @Override
    @Transactional
    public OpenOauth getOauthByToken(String oauth_token) {
        OpenOauthPO po = openOauthDao.findByAccessToken(oauth_token);
        OpenOauth vo = null;
        if (po != null) {
            vo = new OpenOauth();
            BeanUtils.copyProperties(po, vo);
        }
        return vo;
    }

    @Override
    @Transactional
    public OpenOauth getOauthByUid(long userId) {
        OpenOauthPO po = openOauthDao.findByUserId(userId);
        OpenOauth vo = null;
        if (po != null) {
            vo = new OpenOauth();
            BeanUtils.copyProperties(po, vo);
        }
        return vo;
    }

    @Override
    @Transactional
    public boolean checkIsOriginalPassword(long userId) {
        OpenOauthPO po = openOauthDao.findByUserId(userId);
        if (po != null) {
        	UserParam param = new UserParam();
    		param.setId(userId);
    		UserPO upo = userDao.get(param);
        	
//            UserPO upo = userDao.get(userId);

            String pwd = MD5.md5(po.getAccessToken());
            // 判断用户密码 和 登录状态
            if (pwd.equals(upo.getPassword())) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public void saveOauthToken(OpenOauth oauth) {
        OpenOauthPO po = new OpenOauthPO();
        BeanUtils.copyProperties(oauth, po);
        openOauthDao.save(po);
    }

	@Override
	@Transactional
	public OpenOauth getOauthByOauthUserId(String oauthUserId) {
		OpenOauthPO po = openOauthDao.findByOauthUserId(oauthUserId);
        OpenOauth vo = null;
        if (po != null) {
            vo = new OpenOauth();
            BeanUtils.copyProperties(po, vo);
        }
        return vo;
	}

}
