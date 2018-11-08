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

import org.apache.ibatis.annotations.Param;

import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.data.AccountProfile;
import com.unzen.common.core.data.AuthMenu;
import com.unzen.common.core.data.User;
import com.unzen.common.core.persist.entity.UserPO;
import com.unzen.common.core.persist.param.UserParam;
import com.unzen.common.core.persist.utils.PageModel;


/**
 * @author langhsu
 *
 */
public interface UserService {
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
	AccountProfile login(String username, String password);

	/**
	 * 登录,用于记住登录时获取用户信息
	 * @param username
	 * @return
	 */
	AccountProfile getProfileByName(String username);

	/**
	 * 注册
	 * @param user
	 */
	User register(User user);

	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	AccountProfile update(User user);

	/**
	 * 修改用户信息
	 * @param email
	 * @return
	 */
	AccountProfile updateEmail(Long id, String email);

	/**
	 * 查询单个用户
	 * @param id
	 * @return
	 */
	User get(Long id);
	
	UserPO get(UserParam param);

	User getByUsername(String username);

	/**
	 * 修改头像
	 * @param id
	 * @param path
	 * @return
	 */
	AccountProfile updateAvatar(Long id, String path);

	/**
	 * 修改密码
	 * @param id
	 * @param newPassword
	 */
	void updatePassword(Long id, String newPassword);

	/**
	 * 修改密码
	 * @param id
	 * @param oldPassword
	 * @param newPassword
	 */
	void updatePassword(Long id, String oldPassword, String newPassword);

	/**
	 * 修改用户状态
	 * @param id
	 * @param status
	 */
	void updateStatus(Long id, int status);

	AccountProfile updateActiveEmail(Long id, int activeEmail);

	void updateRole(Long id, Long[] roleIds);

	/**
	 * 分页查询
	 * @param pageable
	 */
	PageModel<User> paging();
//	Page<User> paging(Pageable pageable);

	Map<Long, User> findMapByIds(Set<Long> ids);

	List<AuthMenu> getMenuList(Long id);
	
	List<User> findHotUserByfans();

	User findParam(UserParam param);
}
