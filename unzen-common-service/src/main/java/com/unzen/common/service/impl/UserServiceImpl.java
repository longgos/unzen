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
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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

import com.unzen.base.lang.EntityStatus;
import com.unzen.base.lang.MtonsException;
import com.unzen.base.utils.MD5;
import com.unzen.base.utils.model.DataPageModel;
import com.unzen.common.core.data.AccountProfile;
import com.unzen.common.core.data.AuthMenu;
import com.unzen.common.core.data.BadgesCount;
import com.unzen.common.core.data.User;
import com.unzen.common.core.persist.entity.AuthMenuPO;
import com.unzen.common.core.persist.entity.RolePO;
import com.unzen.common.core.persist.entity.UserPO;
import com.unzen.common.core.persist.param.UserParam;
import com.unzen.common.core.persist.utils.BeanMapUtils;
import com.unzen.common.core.persist.utils.MybatisBeanUtils;
import com.unzen.common.core.persist.utils.PageModel;
import com.unzen.common.dao.RoleDao;
import com.unzen.common.dao.UserDao;
import com.unzen.common.service.AuthMenuService;
import com.unzen.common.service.NotifyService;
import com.unzen.common.service.UserService;


@Service
@Transactional(readOnly = true)
@CacheConfig(cacheNames = "usersCaches")
public class UserServiceImpl extends MybatisBeanUtils implements UserService {
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private NotifyService notifyService;
	
	@Autowired
	private AuthMenuService authMenuService;
	

	@Override
	@Transactional
	public AccountProfile login(String username, String password) {
		UserParam param = new UserParam();
		param.setUsername(username);
		UserPO po = userDao.get(param);
		AccountProfile u = null;

		Assert.notNull(po, "账户不存在");

//		Assert.state(po.getStatus() != Const.STATUS_CLOSED, "您的账户已被封禁");

		Assert.state(StringUtils.equals(po.getPassword(), password), "密码错误");

		po.setLastLogin(Calendar.getInstance().getTime());
		userDao.update(po);
		u = BeanMapUtils.copyPassport(po);

		BadgesCount badgesCount = new BadgesCount();
		badgesCount.setNotifies(notifyService.unread4Me(u.getId()));

		u.setBadgesCount(badgesCount);
		return u;
	}

	@Override
	@Transactional
	public AccountProfile getProfileByName(String username) {
		UserParam param = new UserParam();
		param.setUsername(username);
		UserPO po = userDao.get(param);
		AccountProfile u = null;

		Assert.notNull(po, "账户不存在");

//		Assert.state(po.getStatus() != Const.STATUS_CLOSED, "您的账户已被封禁");
		po.setLastLogin(Calendar.getInstance().getTime());

		u = BeanMapUtils.copyPassport(po);

		BadgesCount badgesCount = new BadgesCount();
		badgesCount.setNotifies(notifyService.unread4Me(u.getId()));

		u.setBadgesCount(badgesCount);

		return u;
	}

	@Override
	@Transactional
	public User register(User user) {
		Assert.notNull(user, "Parameter user can not be null!");

		Assert.hasLength(user.getUsername(), "用户名不能为空!");
//		Assert.hasLength(user.getEmail(), "邮箱不能为空!");
		Assert.hasLength(user.getPassword(), "密码不能为空!");
		UserParam param = new UserParam();
		param.setUsername(user.getUsername());
		UserPO check = userDao.get(param);

		Assert.isNull(check, "用户名已经存在!");

		if (StringUtils.isNotBlank(user.getEmail())) {
			param.setEmail(user.getEmail());
			check = userDao.get(param);
			Assert.isNull(check, "邮箱已经被注册!");
		}

		UserPO po = new UserPO();

		BeanUtils.copyProperties(user, po);

		Date now = Calendar.getInstance().getTime();
		po.setPassword(MD5.md5(user.getPassword()));
		po.setStatus(EntityStatus.ENABLED);
		po.setActiveEmail(EntityStatus.ENABLED);
		po.setCreated(now);

		userDao.update(po);

		return BeanMapUtils.copy(po, 0);
	}

	@Override
	@Transactional
	@CacheEvict(key = "#user.getId()")
	public AccountProfile update(User user) {
		UserParam param = new UserParam();
		param.setId(user.getId());
		UserPO po = userDao.get(param);
		if (null != po) {
			po.setName(user.getName());
			po.setSignature(user.getSignature());
			userDao.update(po);
		}
		return BeanMapUtils.copyPassport(po);
	}

	@Override
	@Transactional
	@CacheEvict(key = "#id")
	public AccountProfile updateEmail(Long id, String email) {
		UserParam param = new UserParam();
		param.setId(id);
		UserPO po = userDao.get(param);

		if (null != po) {

			if (email.equals(po.getEmail())) {
				throw new MtonsException("邮箱地址没做更改");
			}
			param.setEmail(email);
			UserPO check = userDao.get(param);

			if (check != null && check.getId() != po.getId()) {
				throw new MtonsException("该邮箱地址已经被使用了");
			}
			po.setEmail(email);
			po.setActiveEmail(EntityStatus.ENABLED);

			userDao.update(po);
		}

		return BeanMapUtils.copyPassport(po);
	}

	@Override
	@Cacheable(key = "#userId")
	public User get(Long userId) {
		UserParam param = new UserParam();
		param.setId(userId);
		UserPO po = userDao.get(param);
		User ret = null;
		if (po != null) {
			ret = BeanMapUtils.copy(po, 0);
		}
		return ret;
	}

	
	@Override
	public List<User> findHotUserByfans(){
		List<User> rets = new ArrayList<>();
		List<UserPO> list = userDao.findTop12ByOrderByFansDesc();
		for (UserPO po : list) {
			User u = BeanMapUtils.copy(po , 0);
			rets.add(u);
		}
		return rets;
	}
	
	@Override
	public User getByUsername(String username) {
		UserParam param = new UserParam();
		param.setUsername(username);
		UserPO po = userDao.get(param);
		List<AuthMenuPO> auth = authMenuService.findAuthMenuByRole(po.getRoleId());
		po.setRoleAuths(auth);
		User ret = null;
		if (po != null) {
			ret = BeanMapUtils.copy(po, 0);
		}
		return ret;
	}

	@Override
	@Transactional
	@CacheEvict(key = "#id")
	public AccountProfile updateAvatar(Long id, String path) {
		UserParam param = new UserParam();
		param.setId(id);
		UserPO po = userDao.get(param);
		if (po != null) {
			po.setAvatar(path);
			userDao.update(po);
		}
		return BeanMapUtils.copyPassport(po);
	}

	@Override
	@Transactional
	public void updatePassword(Long id, String newPassword) {
		UserParam param = new UserParam();
		param.setId(id);
		UserPO po = userDao.get(param);
		Assert.hasLength(newPassword, "密码不能为空!");

		if (null != po) {
			po.setPassword(MD5.md5(newPassword));
			userDao.update(po);
		}
	}

	@Override
	@Transactional
	public void updatePassword(Long id, String oldPassword, String newPassword) {
		UserParam param = new UserParam();
		param.setId(id);
		UserPO po = userDao.get(param);
		Assert.hasLength(newPassword, "密码不能为空!");

		if (po != null) {
			Assert.isTrue(MD5.md5(oldPassword).equals(po.getPassword()), "当前密码不正确");
			po.setPassword(MD5.md5(newPassword));
			userDao.update(po);
		}
	}

	@Override
	@Transactional
	public void updateStatus(Long id, int status) {
		UserParam param = new UserParam();
		param.setId(id);
		UserPO po = userDao.get(param);

		if (po != null) {
			po.setStatus(status);
			userDao.update(po);
		}
	}

	@Override
	@Transactional
	public AccountProfile updateActiveEmail(Long id, int activeEmail) {
		UserParam param = new UserParam();
		param.setId(id);
		UserPO po = userDao.get(param);
		
		if (po != null) {
			po.setActiveEmail(activeEmail);
			userDao.update(po);
		}
		return BeanMapUtils.copyPassport(po);
	}

	@Override
	@Transactional
	public void updateRole(Long id, Long[] roleIds) {
		//2018-09-10注释
		/*List<RolePO> rolePOs = new ArrayList<>();
		for(Long roleId:roleIds){
			List<AuthMenu> authMenus = authMenuService.tree(roleId);
			rolePOs.setAuthMenus(authMenus);
			RolePO rolePO = roleDao.get(roleId);
			rolePOs.add(rolePO);
		}
		UserParam param = new UserParam();
		param.setId(id);
		UserPO po = userDao.get(param);

		if (po != null) {
			po.setRoles(rolePOs);
		}
		userDao.update(po);*/
	}

	@Override
	public PageModel<User> paging() {
		List<UserPO> list = userDao.findAll();
		PageModel<UserPO> page = setPageModel(list);
		List<User> rets = new ArrayList<>();
		for (UserPO po : page.getDatas()) {
			User u = BeanMapUtils.copy(po , 1);
			rets.add(u);
		}
		PageModel<User> user = setPageModel(rets);
		return user;
	}

	@Override
	public Map<Long, User> findMapByIds(Set<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return Collections.emptyMap();
		}
		HashMap<String, Object> map = new HashMap<>();
		map.put("ids", ids);
		List<UserPO> list = userDao.findAllByIdIn(map);
		Map<Long, User> ret = new HashMap<>();
		/** jdk1.7不兼容1.8版本新循环格式。暂时挂起选用旧循环方式 */
		for (int i = 0; i < list.size(); i++) {
			ret.put(list.get(i).getId(), BeanMapUtils.copy(list.get(i), 0));
		}
		/*list.forEach(po -> {
			ret.put(po.getId(), BeanMapUtils.copy(po, 0));
		});*/
		return ret;
	}

	@Override
	public List<AuthMenu> getMenuList(Long id) {
		UserParam param = new UserParam();
		param.setId(id);
		UserPO userPO = userDao.get(param);
		//List<RolePO> roles = userDao.getRoles();
		List<AuthMenuPO> menus = authMenuService.findAuthMenuByRole(userPO.getRoleId());
		/*for(RolePO role : roles){
			List<AuthMenuPO> menuPOs = role.getAuthMenus();
			for(AuthMenuPO menuPO : menuPOs){
				AuthMenu menu = BeanMapUtils.copy(menuPO);
				if(!menus.contains(menu)){
					menus.add(menu);
				}
			}
		}*/
		
		User user = BeanMapUtils.copy(userPO,0);
		return user.getRoleAuths();
		//return menus;
	}


}
